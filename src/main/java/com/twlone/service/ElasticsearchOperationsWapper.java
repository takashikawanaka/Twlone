package com.twlone.service;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.RuntimeField;
import org.springframework.data.elasticsearch.core.ScriptType;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.stereotype.Service;

import com.twlone.entity.ETw;

@Service
public class ElasticsearchOperationsWapper {
    private ElasticsearchOperations elasticsearchOperations;

    public ElasticsearchOperationsWapper(ElasticsearchOperations operations) {
        this.elasticsearchOperations = operations;
    }

    private ETw searchOne(Query query) throws EmptyResultDataAccessException {
        SearchHit<ETw> hit = elasticsearchOperations.searchOne(query, ETw.class, IndexCoordinates.of("etw"));
        if (hit == null)
            throw new EmptyResultDataAccessException(1);
        return hit.getContent();
    }

    // Add Error to elasticsearchOperations.search
    private SearchHits<ETw> search(Query query) {
        SearchHits<ETw> hits = elasticsearchOperations.search(query, ETw.class, IndexCoordinates.of("etw"));
        if (hits == null)
            throw new EmptyResultDataAccessException(1);
        return hits;
    }

    public Boolean existsTw(String eTwId) {
        return elasticsearchOperations.exists(eTwId, IndexCoordinates.of("etw"));
    }

    public ETw saveTw(ETw eTw) {
        return elasticsearchOperations.save(eTw, IndexCoordinates.of("etw"));
    }

    public void updateTw(UpdateQuery query) {
        elasticsearchOperations.update(query, IndexCoordinates.of("etw"));
    }

    public UpdateQuery.Builder getUpdateQuery(String eTwId) {
        return UpdateQuery.builder(eTwId)
                .withScriptType(ScriptType.INLINE)
                .withLang("painless");
    }

    // Get Query to Get TwOne
    private Query getQuery(Criteria criteria) {
        Query query = new CriteriaQuery(criteria);
        query.addSourceFilter(new FetchSourceFilter(null, new String[] { "favorite_user_list", "hashtag_list" }));
        return query;
    }

    // Get Query to Get TwOne Containing Favorites
    private Query getQuery(Criteria criteria, Integer loggedId) {
        Query query = this.getQuery(criteria);
        query.addFields("isfavorite");
        query.addRuntimeField(new RuntimeField("isfavorite", "boolean",
                "emit(doc['favorite_user_list'].contains(" + loggedId + "L))"));
        return query;
    }

    // Search By TwId
    public ETw searchOneById(String eTwId) {
        return this.searchOne(this.getQuery(new Criteria("_id").is(eTwId)));
    }

    public ETw searchOneById(String eTwId, Integer loggedId) {
        return this.searchOne(this.getQuery(new Criteria("_id").is(eTwId), loggedId));
    }

    // Get Query to Get TwList of users
    private Query getQuery(Integer userId) {
        Criteria criteria = new Criteria("user_id").is(userId);
        Criteria criteria2 = (new Criteria("replyETw_id").exists()).not();
        Criteria criteria3 = new Criteria("delete_flag").is(0);
        Query query = new CriteriaQuery(criteria.and(criteria2, criteria3));
        query.addSort((Sort.by("createdAt")).descending());
        query.addSourceFilter(new FetchSourceFilter(null, new String[] { "favorite_user_list", "hashtag_list" }));
        return query;
    }

    // Search By UserId
    public SearchHits<ETw> searchByUserId(Integer userId) {
        Query query = this.getQuery(userId);
        return this.search(query);
    }

    public SearchHits<ETw> searchByUserId(Integer userId, Integer loggedId) {
        Query query = this.getQuery(userId);
        query.addFields("isfavorite");
        query.addRuntimeField(new RuntimeField("isfavorite", "boolean",
                "emit(doc['favorite_user_list'].contains(" + loggedId + "L))"));
        return this.search(query);
    }

    // Search By ReplyTwId
    public SearchHits<ETw> searchByReplyTwId(String eTwId) {
        return this.search(this.getQuery(new Criteria("replyETw_id").is(eTwId)));
    }

    public SearchHits<ETw> searchByReplyTwId(String eTwId, Integer loggedId) {
        return this.search(this.getQuery(new Criteria("replyETw_id").is(eTwId), loggedId));
    }
}
