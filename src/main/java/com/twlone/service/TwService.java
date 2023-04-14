package com.twlone.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.twlone.dto.TwDTO;
import com.twlone.dto.UserDTO;
import com.twlone.entity.Tw;
import com.twlone.entity.User;
import com.twlone.repository.TwRepository;

@Service
public class TwService {
    private final TwRepository twRepository;

    private final UserService userService;

    private String symbol;
    private Pattern pattern;

    public TwService(TwRepository repository, UserService service) {
        this.twRepository = repository;

        this.userService = service;

        this.symbol = "!\"#$%&'()\\*\\+\\-\\.,\\/:;<=>?@\\[\\\\\\]^_`{|}~";
        this.pattern = Pattern
                .compile("((?<!#)#|(?<!@)@)(([^\\s" + symbol + "]*[^\\s\\d" + symbol + "][^\\s" + symbol + "]*)+)");
    }

    public Optional<Tw> getTwById(Integer id) {
        return twRepository.findById(id);
    }

    public List<Tw> getTwListByUser(User user) {
        return twRepository.findByUser(user);
    }

    public List<Tw> getTwListByUserDTO(UserDTO user) {
        return twRepository.findByUserDTO(user.getId());
    }

    public List<TwDTO> getTwDTOListByUserDTO(UserDTO user) {
        return twRepository.findByUserDTO(user.getId())
                .stream()
                .map((tw) -> this.convertTwDTO(tw))
                .collect(Collectors.toList());
    }

    public List<TwDTO> getTwDTOListByUserDTO(UserDTO user, User sourceUser) {
        return twRepository.findByUserDTO(user.getId())
                .stream()
                .map((tw) -> this.convertTwDTO(tw, sourceUser))
                .collect(Collectors.toList());
    }

    public boolean getBooleanFavoriteByTwAndUser(Tw tw, User user) {
        return twRepository.existsFavoriteByTwAndUser(tw, user);
    }

    @Transactional
    public void saveTw(Tw tw) {
        twRepository.save(tw);
    }

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
                if ((userService.getUserByUserId(str.substring(1))).isPresent())
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

    public TwDTO.TwDTOBuilder getBuilder(Tw tw) {
        Integer[] listCount = Arrays.stream(((Object[]) twRepository.countListByTw(tw)))
                .map(o -> (Integer) o)
                .toArray(Integer[]::new);
        return TwDTO.builder()
                .id(tw.getId())
                .content(this.splitContent(listCount[3], tw.getContent()))
                .user(userService.convertMiniUserDTO(tw.getUser()))
                .createdAt(tw.getCreatedAt())
                .reTwListSize(listCount[0])
                .replyTwListSize(listCount[1])
                .favoriteListSize(listCount[2])
                .mediaList(tw.getMediaList())
                .dayHasPassed(!tw.dayHasPassed(LocalDate.now()));
    }

    // Get TwDTO
    public TwDTO convertTwDTO(Tw tw) {
        if (tw == null)
            return null;
        return this.getBuilder(tw)
                .reTw(this.convertTwDTO(tw.getReTw()))
                .build();
    }

    public TwDTO convertTwDTO(Tw tw, User user) {
        if (tw == null)
            return null;
        return this.getBuilder(tw)
                .reTw(this.convertTwDTO(tw.getReTw(), user))
                .isFavorite(twRepository.existsFavoriteByTwAndUser(tw, user) ? true : false)
                .build();
    }

    // Get TwDTO with ReplyTw
    public TwDTO convertTwDTOReplyTw(Tw tw) {
        List<TwDTO> twDTOList = tw.getReplyTwList()
                .stream()
                .map(reply -> this.convertTwDTO(reply))
                .collect(Collectors.toList());
        return this.getBuilder(tw)
                .reTw(this.convertTwDTO(tw.getReTw()))
                .replyTw(this.convertTwDTO(tw.getReplyTw()))
                .replyTwList(twDTOList)
                .build();
    }

    public TwDTO convertTwDTOReplyTw(Tw tw, User user) {
        List<TwDTO> twDTOList = tw.getReplyTwList()
                .stream()
                .map(reply -> this.convertTwDTO(reply, user))
                .collect(Collectors.toList());
        return this.getBuilder(tw)
                .reTw(this.convertTwDTO(tw.getReTw(), user))
                .replyTw(this.convertTwDTO(tw.getReplyTw(), user))
                .isFavorite(twRepository.existsFavoriteByTwAndUser(tw, user) ? true : false)
                .replyTwList(twDTOList)
                .build();
    }
}
