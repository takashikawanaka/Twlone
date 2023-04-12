package com.twlone.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.twlone.dto.TwDTO;
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

    public Integer getCountReTwByTw(Tw tw) {
        return twRepository.countReTwByTw(tw);
    }

    public Integer getCountReplyTwByTw(Tw tw) {
        return twRepository.countReplyTwByTw(tw);
    }

    public Integer getCountFavoriteByTw(Tw tw) {
        return twRepository.countFavoriteByTw(tw);
    }

    public Integer getCountRelatedTwHashTagByTw(Tw tw) {
        return twRepository.countRelatedTwHashTagByTw(tw);
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
            if (str.startsWith("#")) {
                splitList.add(List.of("hashtag", matcher.group(2)));
            } else if (str.startsWith("@")) {
                if ((userService.getUserByUserId(str.substring(1))).isPresent()) {
                    splitList.add(List.of("reply", matcher.group(2)));
                } else {
                    splitList.add(List.of("none", matcher.group()));
                }
            }
            i = matcher.end();
        }
        if (i != content.length())
            splitList.add(List.of("none", content.substring(i, content.length())));
        return splitList;
    }

    public TwDTO.TwDTOBuilder getBuilder(Tw tw) {
        return TwDTO.builder()
                .id(tw.getId())
                .content(this.splitContent(this.getCountRelatedTwHashTagByTw(tw), tw.getContent()))
                .user(userService.convertMiniUserDTO(tw.getUser()))
                .createdAt(tw.getCreatedAt())
                .reTwListSize(this.getCountReTwByTw(tw))
                .replyTwListSize(this.getCountReplyTwByTw(tw))
                .favoriteListSize(this.getCountFavoriteByTw(tw))
                .mediaList(tw.getMediaList())
                .dayHasPassed(!tw.getCreatedAt()
                        .toLocalDate()
                        .isEqual(LocalDate.now()));
    }
}
