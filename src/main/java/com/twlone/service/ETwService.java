package com.twlone.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.twlone.dto.PostTwDTO;
import com.twlone.dto.TwDTO;
import com.twlone.entity.ETw;
import com.twlone.entity.Url;

@Service
public class ETwService {
    private final UserService userService;
    private final UrlService urlService;

    private final ElasticsearchOperationsWapper operations;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    private Pattern pattern;
    private String url;

    public static enum MediaType {
        jpg("image/jpeg"), jpeg("image/jpeg"), png("image/png"), gif("image/gif"), bmp("image/bmp"), webp("image/webp"),
        mp4("video/mp4"), webm("video/webm"), ogg("video/ogg"), quicktime("video/quicktime"), mpeg("video/mpeg");

        private final String contentType;

        MediaType(String contentType) {
            this.contentType = contentType;
        }

        public String getContentType() {
            return this.contentType;
        }

        public String getType() {
            return List.of(MediaType.mp4, MediaType.webm, MediaType.ogg, MediaType.quicktime, MediaType.mpeg)
                    .contains(this) ? "video" : "image";
        }
    }

    public ETwService(UserService service, UrlService service2, ElasticsearchOperations operations) {
        this.userService = service;
        this.urlService = service2;
        this.operations = new ElasticsearchOperationsWapper(operations);

        // Remove _!?
        String symbol = "\"#$%&'()\\*\\+\\-\\.,\\/:;<=>@\\[\\\\\\]^`{|}~";
        String url = "https?:\\/\\/[-_.!*\\'()a-zA-Z0-9?:#?\\/@%!$&'+,;=\\u3000-\\u30FE\\u4E00-\\u9FA0\\uFF01-\\uFFE3]+";
        this.pattern = Pattern.compile("((?<!#)#|(?<!@)@|" + url + ")([^\\s_!?" + symbol + "]+[^\\s\\d" + symbol
                + "]+[^\\s_" + symbol + "]*)");
        // Fix
        this.url = "http://" + "localhost:8080/link/";
    }

    // Get ETw One
    public ETw getETwById(String eTwId) throws EmptyResultDataAccessException {
        System.out.println("ETwService: Get ETw " + eTwId);
        return operations.searchOneById(eTwId);
    }

    public ETw getETwById(String eTwId, Integer loggedId) throws EmptyResultDataAccessException {
        System.out.println("ETwService: Get ETw " + eTwId);
        return operations.searchOneById(eTwId, loggedId);
    }

    // Get TwDTO One
    public TwDTO getTwDTOById(String eTwId) throws EmptyResultDataAccessException {
        return (this.getBuilder(this.getETwById(eTwId))).build();
    }

    public TwDTO getTwDTOById(String eTwId, Integer loggedId) throws EmptyResultDataAccessException {
        return (this.getBuilder(this.getETwById(eTwId, loggedId))).build();
    }

    // Get TwDTO List
    public List<TwDTO> getTwDTOListByUserId(Integer userId) {
        System.out.println("ETwService: Get User ETw List " + userId);
        List<TwDTO> twDTOList = new ArrayList<>();
        for (SearchHit<ETw> searchHit : operations.searchByUserId(userId)) {
            ETw eTw = searchHit.getContent();
            TwDTO.TwDTOBuilder builder = this.getBuilder(eTw);
            if (eTw.existsReETwId()) { // Add ReTw
                ETw reETw = this.getETwById(eTw.getReETwId());
                TwDTO.TwDTOBuilder reBuilder = this.getBuilder(reETw);
                if (eTw.isOnlyReETw() && reETw.existsReETwId()) // Is ReTw Only
                    reBuilder.reTw(this.getTwDTOById(reETw.getReETwId()));
                builder.reTw(reBuilder.build());
            }
            twDTOList.add(builder.build());
        }
        return twDTOList;
    }

    public List<TwDTO> getTwDTOListByUserId(Integer userId, Integer loggedId) {
        System.out.println("ETwService: Get User ETw List " + userId);
        List<TwDTO> twDTOList = new ArrayList<>();
        for (SearchHit<ETw> searchHit : operations.searchByUserId(userId, loggedId)) {
            ETw eTw = searchHit.getContent();
            TwDTO.TwDTOBuilder builder = this.getBuilder(eTw);
            if (eTw.existsReETwId()) { // Add ReTw
                ETw reETw = this.getETwById(eTw.getReETwId(), loggedId);
                TwDTO.TwDTOBuilder reBuilder = this.getBuilder(reETw);
                if (eTw.isOnlyReETw()) { // Is ReTw Only
                    reBuilder.isFavorite(reETw.getIsFavorite());
                    if (reETw.existsReETwId()) // Add ReTw ReTw
                        reBuilder.reTw(this.getTwDTOById(reETw.getReETwId()));
                }
                builder.reTw(reBuilder.build());
            }
            twDTOList.add((builder.isFavorite(eTw.getIsFavorite())).build());
        }
        return twDTOList;
    }

    public List<TwDTO> getTimeLineByUserIdList(List<Integer> userIdList, Integer loggedId) {
        userIdList.add(loggedId);
        List<TwDTO> twDTOList = new ArrayList<>();
        for (SearchHit<ETw> searchHit : operations.searchByUserIdList(userIdList, loggedId)) {
            ETw eTw = searchHit.getContent();
            TwDTO.TwDTOBuilder builder = this.getBuilder(eTw);
            if (eTw.existsReETwId()) { // Add ReTw
                ETw reETw = this.getETwById(eTw.getReETwId(), loggedId);
                TwDTO.TwDTOBuilder reBuilder = this.getBuilder(reETw);
                if (eTw.isOnlyReETw()) { // Is ReTw Only
                    reBuilder.isFavorite(reETw.getIsFavorite());
                    if (reETw.existsReETwId()) // Add ReTw ReTw
                        reBuilder.reTw(this.getTwDTOById(reETw.getReETwId()));
                }
                builder.reTw(reBuilder.build());
            }
            twDTOList.add((builder.isFavorite(eTw.getIsFavorite())).build());
        }
        return twDTOList;
    }

    // Get TwDTO With ReplyTwDTO
    public TwDTO getTwDTOWithReplyTwDTOById(String eTwId) {
        System.out.println("ETwService: Get ETw " + eTwId);
        ETw eTw = operations.searchOneById(eTwId);
        TwDTO.TwDTOBuilder builder = this.getBuilder(eTw);
        if (eTw.existsReETwId()) { // Add ReTw
            builder.reTw(this.getTwDTOById(eTw.getReETwId()));
        } else if (eTw.existsReplyETwId()) { // Add ReplyTw
            ETw replyETw = operations.searchOneById(eTw.getReplyETwId());
            TwDTO.TwDTOBuilder replyBuilder = this.getBuilder(replyETw);
            if (replyETw.existsReETwId()) // Add ReTw in ReplyTw
                replyBuilder.reTw(this.getTwDTOById(replyETw.getReETwId()));
            builder.replyTw(replyBuilder.build());
        }
        if (0 < eTw.getReplyETwListSize()) { // Add Hanging Tw
            List<TwDTO> twDTOList = new ArrayList<>();
            for (SearchHit<ETw> searchHit : operations.searchByReplyTwId(eTwId)) {
                ETw replyETw = searchHit.getContent();
                TwDTO.TwDTOBuilder replyBuilder = this.getBuilder(replyETw);
                twDTOList.add(replyBuilder.build());
            }
            builder.replyTwList(twDTOList);
        }
        return builder.build();
    }

    public TwDTO getTwDTOWithReplyTwDTOById(String eTwId, Integer loggedId) {
        System.out.println("ETwService: Get ETw " + eTwId);
        ETw eTw = operations.searchOneById(eTwId, loggedId);
        TwDTO.TwDTOBuilder builder = this.getBuilder(eTw);
        if (eTw.existsReETwId()) { // Add ReTw
            builder.reTw(this.getTwDTOById(eTw.getReETwId()));
        } else if (eTw.existsReplyETwId()) { // Add ReplyTw
            ETw replyETw = operations.searchOneById(eTw.getReplyETwId(), loggedId);
            TwDTO.TwDTOBuilder replyBuilder = this.getBuilder(replyETw);
            if (replyETw.existsReETwId()) { // Add ReTw in ReplyTw
                replyBuilder.reTw(this.getTwDTOById(replyETw.getReETwId()));
            }
            builder.replyTw(replyBuilder.isFavorite(replyETw.getIsFavorite())
                    .build());
        }
        if (0 < eTw.getReplyETwListSize()) { // Add Hanging Tw
            List<TwDTO> twDTOList = new ArrayList<>();
            for (SearchHit<ETw> searchHit : operations.searchByReplyTwId(eTwId)) {
                ETw replyETw = searchHit.getContent();
                TwDTO.TwDTOBuilder replyBuilder = this.getBuilder(replyETw);
                twDTOList.add(replyBuilder.isFavorite(replyETw.getIsFavorite())
                        .build());
            }
            builder.replyTwList(twDTOList);
        }
        return builder.isFavorite(eTw.getIsFavorite())
                .build();
    }

    // Split HashTag And Reply
    private List<List<String>> splitContent(Integer count, String content) {
        if (content == null)
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
                    yield List.of("none", str);
            case 'h':
                yield List.of("url", str);
            default: // Rewrite
                yield List.of("none", str);
            });
            i = matcher.end();
        }
        if (i != content.length())
            splitList.add(List.of("none", content.substring(i, content.length())));
        return splitList;
    }

    private List<List<String>> convertMediaList(List<String> mediaList) {
        if (mediaList == null)
            return List.of(List.of());
        List<List<String>> mediaNestList = new ArrayList<>();
        for (String media : mediaList) {
            String extention = media.substring(media.lastIndexOf('.') + 1);
            mediaNestList.add(List.of((MediaType.valueOf(extention)).getType(), media));
        }
        return mediaNestList;
    }

    // TwDTO % Re TwDTO Builder
    private TwDTO.TwDTOBuilder getMiniBuilder(ETw eTw) {
        return TwDTO.builder()
                .id(eTw.getId())
                .content(this.splitContent(eTw.getHashtagListSize(), eTw.getContent()))
                .user(userService.convertMiniUserDTO(eTw.getUserId()))
                .createdAt(eTw.getCreatedAt())
                .mediaList(this.convertMediaList(eTw.getMediaList()))
                .dayHasPassed(!eTw.getCreatedAt()
                        .toLocalDate()
                        .isEqual(LocalDate.now()));
    }

    // TwDTO Builder
    private TwDTO.TwDTOBuilder getBuilder(ETw eTw) {
        return this.getMiniBuilder(eTw)
                .reTwListSize(eTw.getReETwListSize())
                .replyTwListSize(eTw.getReplyETwListSize())
                .favoriteListSize(eTw.getFavoriteUserListSize());
    }

    public void savePostTwDTO(UserDetail userDetail, PostTwDTO postTw) throws IllegalArgumentException, IOException {
        // if not contain Value throws Error
        if (postTw.isIllegale())
            throw new IllegalArgumentException();
        ETw eTw = new ETw((userDetail.getUser()).getId());
        // Initialization List Size
        if (!postTw.isOnlyReETw())
            eTw.InitETw();
        // Convert ETw
        if (!postTw.isBlankContent()) {
            if (postTw.existsHashTag()) {
                List<String> hashtagList = new ArrayList<>();
                for (String name : postTw.getHashtag()) {
                    if (!hashtagList.contains(name))
                        hashtagList.add(name);
                }
                eTw.setHashtagList(hashtagList);
                eTw.setHashtagListSize(hashtagList.size());
            }
            if (postTw.existsURL()) {
                List<String> urlList = postTw.getUrl();
                for (String url : urlList) {
                    Url entity = urlService.saveUrl(new Url(url));
                    postTw.setContent(postTw.getContent()
                            .replace(url, this.url + entity.getId()));
                }
                eTw.setUrlList(urlList);
            }
            eTw.setContent(postTw.getContent());
        }
        // Add relational ETw
        if (!postTw.isBlankReTwId()) {
            if (!operations.existsTw(postTw.getReTwID()))
                throw new IllegalArgumentException("NotFount ReTwID");
            eTw.setReETwId(postTw.getReTwID());
        }
        if (!postTw.isBlankReplyTwId()) {
            if (!operations.existsTw(postTw.getReplyTwID()))
                throw new IllegalArgumentException("NotFount ReplyTwID");
            eTw.setReplyETwId(postTw.getReplyTwID());
        }
        // Add Medias
        if (postTw.existsMedia())
            eTw.setMediaList(this.saveMedias(postTw.getMedia(), eTw));

        ETw saved = operations.saveTw(eTw);
        System.out.println("ETwService: Save ETw " + saved.getId());
        if (eTw.existsReETwId()) {
            UpdateQuery updateQuery = operations.getUpdateQuery(eTw.getReETwId())
                    .withScript("ctx._source.reETw_list_size++")
                    .build();
            operations.updateTw(updateQuery);
            System.out.println("ETwService: CountUp ReTw " + eTw.getReETwId());
        } else if (eTw.existsReplyETwId()) {
            UpdateQuery updateQuery = operations.getUpdateQuery(eTw.getReplyETwId())
                    .withScript("ctx._source.replyETw_list_size++")
                    .build();
            operations.updateTw(updateQuery);
            System.out.println("ETwService: CountUp ReplyTw " + eTw.getReplyETwId());
        }
    }

    private List<String> saveMedias(List<MultipartFile> medias, ETw eTw) throws IllegalArgumentException, IOException {
        List<String> mediaList = new ArrayList<>();
        for (int i = 0; i < medias.size(); i++) {
            MultipartFile media = medias.get(i);
            String extention = (media.getContentType()).split("/")[1];
            // Check contained IllegalArgumentException
            MediaType.valueOf(extention);
            String filePath = eTw.getUserId() + formatter.format(LocalDateTime.now()) + i + "." + extention;
            try (InputStream inputStream = media.getInputStream();
                    OutputStream outputStream = Files.newOutputStream(Paths.get("./medias", filePath))) {
                inputStream.transferTo(outputStream);
            }
            mediaList.add(filePath);
        }
        return mediaList;
    }

    // Search Word
    public List<TwDTO> searchTwDTOListByWord(String word) {
        Criteria criteria = this.splitSearchWord(word);
        List<TwDTO> twDTOList = new ArrayList<>();
        for (SearchHit<ETw> searchHit : operations.searchByCriteria(criteria)) {
            ETw replyETw = searchHit.getContent();
            TwDTO.TwDTOBuilder replyBuilder = this.getBuilder(replyETw);
            twDTOList.add(replyBuilder.isFavorite(replyETw.getIsFavorite())
                    .build());
        }
        return twDTOList;
    }

    public List<TwDTO> searchTwDTOListByWord(String word, Integer loggedId) {
        Criteria criteria = this.splitSearchWord(word);
        List<TwDTO> twDTOList = new ArrayList<>();
        for (SearchHit<ETw> searchHit : operations.searchByCriteria(criteria, loggedId)) {
            ETw replyETw = searchHit.getContent();
            TwDTO.TwDTOBuilder replyBuilder = this.getBuilder(replyETw);
            twDTOList.add(replyBuilder.isFavorite(replyETw.getIsFavorite())
                    .build());
        }
        return twDTOList;
    }

    // Split Search Word
    private Criteria splitSearchWord(String word) {
        Criteria criteria = new Criteria();
        for (String str : word.split("( |　|,|、)")) {
            switch (str.charAt(0)) {
            case '#':
                criteria.and(new Criteria("hashtag_list").is(str.substring(1)));
                break;
            case '@':
                break;
            default:
                criteria.and(new Criteria("content").matchesAll(word));
            }
        }
        return criteria;
    }

    public void deleteETw(String eTwId) {
        UpdateQuery updateQuery = operations.getUpdateQuery(eTwId)
                .withDocument(Document.from(Map.of("delete_flag", "1")))
                .build();
        operations.updateTw(updateQuery);
    }

    public void favoriteETw(String eTwId, Integer userId) {
        UpdateQuery updateQuery = operations.getUpdateQuery(eTwId)
                .withScript("if(!ctx._source.favorite_user_list.contains(params.user))"
                        + "{ ctx._source.favorite_user_list.add(params.user); ctx._source.favorite_user_list_size++ }")
                .withParams(Collections.singletonMap("user", userId))
                .build();
        operations.updateTw(updateQuery);
    }

    public void unfavoriteETw(String eTwId, Integer userId) {
        UpdateQuery updateQuery = operations.getUpdateQuery(eTwId)
                .withScript("if(ctx._source.favorite_user_list.contains(params.user))"
                        + "{ ctx._source.favorite_user_list.remove(params.user); ctx._source.favorite_user_list_size-- }")
                .withParams(Collections.singletonMap("user", userId))
                .build();
        operations.updateTw(updateQuery);
    }
}