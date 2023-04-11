package com.twlone.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "related_tw_hashtag")
public class RelatedTwHashTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tw_id", nullable = false, updatable = false)
    private Tw tw;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hashtag_id", nullable = false, updatable = false)
    private HashTag hashtag;

    protected RelatedTwHashTag() {
    }

    public RelatedTwHashTag(Tw tw, HashTag hashtag) {
        this.tw = tw;
        this.hashtag = hashtag;
    }
}
