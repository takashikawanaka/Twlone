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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ScriptType;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.twlone.dto.PostTwDTO;
import com.twlone.dto.TwDTO;
import com.twlone.entity.ETw;
import com.twlone.entity.Media.MediaType;

@Service
public class ETwService {
    private ElasticsearchOperations elasticsearchOperations;

    private final UserService userService;

    private final ElasticsearchOperationsWapper operations;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    private String symbol;
    private Pattern pattern;

    public ETwService(ElasticsearchOperations operations, UserService service,
            ElasticsearchOperationsWapper operations2) {
        this.elasticsearchOperations = operations;
        this.operations = operations2;

        this.userService = service;

        // Remove _!?
        this.symbol = "\"#$%&'()\\*\\+\\-\\.,\\/:;<=>@\\[\\\\\\]^`{|}~";
        this.pattern = Pattern
                .compile("((?<!#)#|(?<!@)@)([^\\s_!?" + symbol + "]+[^\\s\\d" + symbol + "]+[^\\s_" + symbol + "]*)");
    }

    // TwDTO % Re TwDTO Builder
    private TwDTO.TwDTOBuilder getMiniBuilder(ETw eTw) {
        return TwDTO.builder()
                .id(eTw.getId())
                .content(this.splitContent(eTw.getHashtagListSize(), eTw.getContent()))
                .user(userService.convertMiniUserDTO(eTw.getUserId()))
                .createdAt(eTw.getCreatedAt())
                .mediaList(eTw.getMediaList())
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
    public TwDTO getMiniTwDTOById(String eTwId) throws EmptyResultDataAccessException {
        System.out.println("ETwService: Get ETw " + eTwId);
        return (this.getBuilder(operations.searchOneById(eTwId))).build();
    }

    public TwDTO getMiniTwDTOById(String eTwId, Integer loggedId) throws EmptyResultDataAccessException {
        System.out.println("ETwService: Get ETw " + eTwId);
        return (this.getBuilder(operations.searchOneById(eTwId, loggedId))).build();
    }

    // Get TwDTO
    public TwDTO getTwDTOWithReplyTwDTOById(String eTwId) {
        System.out.println("ETwService: Get ETw " + eTwId);
        ETw eTw = operations.searchOneById(eTwId);
        TwDTO.TwDTOBuilder builder = this.getBuilder(eTw);
        if (eTw.existsReETwId()) {
            builder.reTw((this.getMiniTwDTOById(eTw.getReETwId())));
        }
        if (0 < eTw.getReplyETwListSize()) {
            List<TwDTO> twDTOList = new ArrayList<>();
            for (SearchHit<ETw> searchHit : operations.searchByReplyTwId(eTwId)) {
            }
        }
        return builder.build();
    }

    public TwDTO getTwDTOWithReplyTwDTOById(String eTwId, Integer loggedId) {
        System.out.println("ETwService: Get ETw " + eTwId);
        ETw eTw = operations.searchOneById(eTwId, loggedId);
        TwDTO.TwDTOBuilder builder = this.getBuilder(eTw);
        if (eTw.existsReETwId()) {
            builder.reTw((this.getMiniTwDTOById(eTw.getReETwId())));
        }
        return builder.build();
    }

    // Get TwDTO List
    public List<TwDTO> getTwDTOListByUserId(Integer userId) {
        System.out.println("ETwService: Get User ETw List " + userId);
        List<TwDTO> twDTOList = new ArrayList<>();
        for (SearchHit<ETw> searchHit : operations.searchByUserId(userId)) {
            ETw eTw = searchHit.getContent();
            TwDTO.TwDTOBuilder builder = this.getBuilder(eTw);
            // Add ReTw
            if (eTw.existsReETwId()) {
                ETw reETw = this.getETwById(eTw.getReETwId());
                TwDTO.TwDTOBuilder reBuilder = this.getBuilder(reETw);
                // Is ReTw Only
                if (eTw.isOnlyReETw() && reETw.existsReETwId())
                    reBuilder.reTw(this.getMiniTwDTOById(reETw.getReETwId()));
                builder.reTw(reBuilder.build());
            }
            twDTOList.add(builder.build());
        }
        return twDTOList;
    }

    public List<TwDTO> getTwDTOListByUserIdLoggedIn(Integer userId, Integer loggedId) {
        System.out.println("ETwService: Get User ETw List " + userId);
        List<TwDTO> twDTOList = new ArrayList<>();
        for (SearchHit<ETw> searchHit : operations.searchByUserId(userId, loggedId)) {
            ETw eTw = searchHit.getContent();
            TwDTO.TwDTOBuilder builder = this.getBuilder(eTw);
            // Add ReTw
            if (eTw.existsReETwId()) {
                ETw reETw = this.getETwById(eTw.getReETwId(), loggedId);
                TwDTO.TwDTOBuilder reBuilder = this.getBuilder(reETw);
                // Is ReTw Only
                if (eTw.isOnlyReETw()) {
                    reBuilder.isFavorite(reETw.getIsFavorite());
                    // Add ReTw ReTw
                    if (reETw.existsReETwId())
                        reBuilder.reTw(this.getMiniTwDTOById(reETw.getReETwId()));
                }
                builder.reTw(reBuilder.build());
            }
            twDTOList.add((builder.isFavorite(eTw.getIsFavorite())).build());
        }
        return twDTOList;
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
                    hashtagList.add(name);
                }
                eTw.setHashtagList(hashtagList);
                eTw.setHashtagListSize(hashtagList.size());
            }
            eTw.setContent(postTw.getContent());
        }
        // Add relational ETw
        if (!postTw.isBlankReTwId()) {
            if (!elasticsearchOperations.exists(postTw.getReTwID(), IndexCoordinates.of("etw")))
                throw new IllegalArgumentException("NotFount ReTwID");
            eTw.setReETwId(postTw.getReTwID());
        }
        if (!postTw.isBlankReplyTwId()) {
            if (!elasticsearchOperations.exists(postTw.getReplyTwID(), IndexCoordinates.of("etw")))
                throw new IllegalArgumentException("NotFount ReplyTwID");
            eTw.setReplyETwId(postTw.getReplyTwID());
        }
        // Add Medias
        if (postTw.existsMedia())
            eTw.setMediaList(this.saveMedias(postTw.getMedia(), eTw));

        ETw saved = elasticsearchOperations.save(eTw, IndexCoordinates.of("etw"));
        System.out.println("ETwService: Save ETw " + saved.getId());
        if (eTw.existsReETwId()) {
            UpdateQuery updateQuery = UpdateQuery.builder(eTw.getReETwId())
                    .withScriptType(ScriptType.INLINE)
                    .withLang("painless")
                    .withScript("ctx._source.reETw_list_size++")
                    .build();
            elasticsearchOperations.update(updateQuery, IndexCoordinates.of("etw"));
            System.out.println("ETwService: CountUp ReTw " + eTw.getReETwId());
        } else if (eTw.existsReplyETwId()) {
            UpdateQuery updateQuery = UpdateQuery.builder(eTw.getReplyETwId())
                    .withScriptType(ScriptType.INLINE)
                    .withLang("painless")
                    .withScript("ctx._source.replyETw_list_size++")
                    .build();
            elasticsearchOperations.update(updateQuery, IndexCoordinates.of("etw"));
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

    public void deleteETw(String eTw_id) {
        // Rewrite update delete_flag
        elasticsearchOperations.delete(eTw_id, IndexCoordinates.of("etw"));
    }

    public void favoriteETw(String eTw_id, Integer user_id) {
        UpdateQuery updateQuery = UpdateQuery.builder(eTw_id)
                .withScriptType(ScriptType.INLINE)
                .withLang("painless")
                .withScript("if(!ctx._source.favorite_user_list.contains(params.user))"
                        + "{ ctx._source.favorite_user_list.add(params.user); ctx._source.favorite_user_list_size++ }")
                .withParams(Collections.singletonMap("user", user_id))
                .build();
        elasticsearchOperations.update(updateQuery, IndexCoordinates.of("etw"));
    }

    public void unfavoriteETw(String eTw_id, Integer user_id) {
        UpdateQuery updateQuery = UpdateQuery.builder(eTw_id)
                .withScriptType(ScriptType.INLINE)
                .withLang("painless")
                .withScript("if(ctx._source.favorite_user_list.contains(params.user))"
                        + "{ ctx._source.favorite_user_list.remove(params.user); ctx._source.favorite_user_list_size-- }")
                .withParams(Collections.singletonMap("user", user_id))
                .build();
        elasticsearchOperations.update(updateQuery, IndexCoordinates.of("etw"));
    }
}