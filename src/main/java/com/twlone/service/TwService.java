package com.twlone.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.twlone.dto.TwDTO;
import com.twlone.dto.TwDTODTO;
import com.twlone.dto.UserDTO;
import com.twlone.entity.HashTag;
import com.twlone.entity.Tw;
import com.twlone.entity.User;
import com.twlone.repository.TwRepository;
import com.twlone.repository.TwSpecification;

@Service
public class TwService {
    private final TwRepository twRepository;

    @PersistenceContext
    private EntityManager em;

    private final UserService userService;
    private final HashTagService hashtagService;
    private String symbol;
    private Pattern pattern;

    public TwService(TwRepository repository, UserService service, HashTagService service2) {
        this.twRepository = repository;

        this.userService = service;
        this.hashtagService = service2;

        this.symbol = "!\"#$%&'()\\*\\+\\-\\.,\\/:;<=>?@\\[\\\\\\]^`{|}~";
        this.pattern = Pattern
                .compile("((?<!#)#|(?<!@)@)(([^\\s_" + symbol + "]*[^\\s\\d" + symbol + "][^\\s_" + symbol + "]*)+)");
    }

    public Optional<Tw> getTwById(Integer id) {
        return twRepository.findById(id);
    }

    public Optional<TwDTODTO> getTwDTODTOByID(Integer id) {
        return twRepository.findTwDTODTOById(id);
    }

    public List<TwDTO> getFollowingTw(User user) {
        return twRepository.findFollowingTw(user)
                .stream()
                .map((twDTODTO) -> this.convertTwDTO(twDTODTO, user))
                .collect(Collectors.toList());
    }

    public List<TwDTO> getTwDTOListByUserDTO(UserDTO user) {
        return twRepository.findTwDTODTOListByUserDTO(user.getId())
                .stream()
                .map((twDTODTO) -> this.convertTwDTO(twDTODTO))
                .collect(Collectors.toList());
    }

    public List<TwDTO> getTwDTOListByUserDTO(UserDTO user, User sourceUser) {
        return twRepository.findTwDTODTOListByUserDTO(user.getId())
                .stream()
                .map((twDTODTO) -> this.convertTwDTO(twDTODTO, sourceUser))
                .collect(Collectors.toList());
    }

    public List<TwDTODTO> getTwDTODTOListByWord(String word) {
        if (word.isBlank())
            return new ArrayList<TwDTODTO>();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<TwDTODTO> query = builder.createQuery(TwDTODTO.class);
        Root<Tw> root = query.from(Tw.class);
        Specification<Tw> specification = TwSpecification.deleteFlagEquals(0);
        Boolean isQuery = false;
        for (String str : word.split("( |　|,|、)")) {
            switch (str.charAt(0)) {
            case '#':
                Optional<HashTag> hashtag = hashtagService.getHashTagByName(str.substring(1));
                if (hashtag.isPresent()) {
                    specification = specification.and(TwSpecification.hashtagEquals(hashtag.get()));
                    isQuery = true;
                }
                break;
            case '@':
                break;
            default:
                specification = specification.and(TwSpecification.wordConatins(str));
                isQuery = true;
            }
        }
        if (!isQuery)
            return new ArrayList<TwDTODTO>();
        query.select(builder.construct(TwDTODTO.class, root.get("id"), root.get("content"), root.get("user"),
                (root.get("reTw")).get("id"), (root.get("replyTw")).get("id"), root.get("createdAt"),
                builder.size(root.get("reTwList")), builder.size(root.get("replyTwList")),
                builder.size(root.get("favoriteList")), builder.size(root.get("mediaList")),
                builder.size(root.get("hashtagList"))));
        query.where(specification.toPredicate(root, query, builder));
        query.orderBy(builder.desc(root.get("id")));
        return (em.createQuery(query)).getResultList();
    }

    public List<TwDTO> getTwDTOListByWord(String word) {
        return this.getTwDTODTOListByWord(word)
                .stream()
                .map((twDTODTO) -> this.convertTwDTO(twDTODTO))
                .collect(Collectors.toList());
    }

    public List<TwDTO> getTwDTOListByWord(String word, User user) {
        return this.getTwDTODTOListByWord(word)
                .stream()
                .map((twDTODTO) -> this.convertTwDTO(twDTODTO, user))
                .collect(Collectors.toList());
    }

    @Transactional
    public void saveTw(Tw tw) {
        twRepository.save(tw);
    }

    // Split HashTag And Reply
    private List<List<String>> splitContent(Integer count, String content) {
        if (content.isEmpty())
            return List.of(List.of());
        List<List<String>> splitList = new ArrayList<>();
        Matcher matcher = this.pattern.matcher(content);
        int i = 0;
        while (matcher.find()) {
            if (i != matcher.start())
                splitList.add(List.of("none", content.substring(i, matcher.start())));
            String str = matcher.group();
            splitList.add(switch (str.charAt(0)) {
            case '#':
                yield List.of("hashtag", matcher.group(2));
            case '@':
                if ((userService.getExistsByUserId(str.substring(1))))
                    yield List.of("reply", matcher.group(2));
                else
                    yield List.of("none", matcher.group());
            default: // Rewrite
                yield List.of("none", matcher.group());
            });
            i = matcher.end();
        }
        if (i != content.length())
            splitList.add(List.of("none", content.substring(i, content.length())));
        return splitList;
    }

    public TwDTO.TwDTOBuilder getBuilder(TwDTODTO twDTODTO) {
        return TwDTO.builder()
                .id(twDTODTO.getId())
                .content(this.splitContent(twDTODTO.getHashtagListSize(), twDTODTO.getContent()))
                .user(userService.convertMiniUserDTO(twDTODTO.getUser()))
                .createdAt(twDTODTO.getCreatedAt())
                .reTwListSize(twDTODTO.getReTwListSize())
                .replyTwListSize(twDTODTO.getReplyTwListSize())
                .favoriteListSize(twDTODTO.getFavoriteListSize())
                .mediaList((twDTODTO.getMediaListSize() == 0) ? null : twRepository.findMediaByTw(twDTODTO.getId()))
                .dayHasPassed(!twDTODTO.dayHasPassed(LocalDate.now()));
    }

    // Get TwDTO
    public TwDTO convertTwDTO(TwDTODTO twDTODTO) {
        if (twDTODTO == null)
            return null;
        Integer reTwId = twDTODTO.getReTwId();
        return this.getBuilder(twDTODTO)
                .reTw(reTwId == null ? null : this.convertTwDTO((twRepository.findTwDTODTOById(reTwId)).orElse(null)))
                .build();
    }

    public TwDTO convertTwDTO(TwDTODTO twDTODTO, User user) {
        if (twDTODTO == null)
            return null;
        Integer reTwId = twDTODTO.getReTwId();
        return this.getBuilder(twDTODTO)
                .reTw(reTwId == null ? null : this.convertTwDTO((twRepository.findTwDTODTOById(reTwId)).orElse(null)))
                .isFavorite(twRepository.existsFavoriteByTwIDAndUser(twDTODTO.getId(), user) ? true : false)
                .build();
    }

    // Get TwDTO with ReplyTw
    public TwDTO convertTwDTOReplyTw(TwDTODTO twDTODTO) {
        List<TwDTO> twDTOList = null;
        if (0 < twDTODTO.getReplyTwListSize()) {
            twDTOList = twRepository.findTwDTODTOListByReplyTwId(twDTODTO.getId())
                    .stream()
                    .map(reply -> this.convertTwDTO(reply))
                    .collect(Collectors.toList());
        }
        Integer reTwId = twDTODTO.getReTwId();
        Integer replyTwId = twDTODTO.getReplyTwId();
        return this.getBuilder(twDTODTO)
                .reTw(reTwId == null ? null : this.convertTwDTO((twRepository.findTwDTODTOById(reTwId)).orElse(null)))
                .replyTw(replyTwId == null ? null
                        : this.convertTwDTO((twRepository.findTwDTODTOById(replyTwId)
                                .orElse(null))))
                .replyTwList(twDTOList)
                .build();
    }

    public TwDTO convertTwDTOReplyTw(TwDTODTO twDTODTO, User user) {
        List<TwDTO> twDTOList = null;
        if (0 < twDTODTO.getReplyTwListSize()) {
            twDTOList = twRepository.findTwDTODTOListByReplyTwId(twDTODTO.getId())
                    .stream()
                    .map(reply -> this.convertTwDTO(reply, user))
                    .collect(Collectors.toList());
        }
        Integer reTwId = twDTODTO.getReTwId();
        Integer replyTwId = twDTODTO.getReplyTwId();
        return this.getBuilder(twDTODTO)
                .reTw(reTwId == null ? null : this.convertTwDTO((twRepository.findTwDTODTOById(reTwId)).orElse(null)))
                .replyTw(replyTwId == null ? null
                        : this.convertTwDTO((twRepository.findTwDTODTOById(replyTwId)).orElse(null), user))
                .replyTwList(twDTOList)
                .isFavorite(twRepository.existsFavoriteByTwIDAndUser(twDTODTO.getId(), user) ? true : false)
                .build();
    }
}
