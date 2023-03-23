package com.twlone.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;

@Data
@Entity
@Table(name = "tw")
public class Tw {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "re_tw_id", updatable = false)
    private Tw reTw;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reply_tw_id", updatable = false)
    private Tw replyTw;

    @Column(nullable = false)
    private Integer deleteFlag = 0;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    protected Tw() {
    }

    public Tw(User user, String content) {
        this.user = user;
        this.content = content;
    }

    @OneToMany(mappedBy = "reTw", fetch = FetchType.LAZY)
    private List<Tw> reTwList;

    @OneToMany(mappedBy = "replyTw", fetch = FetchType.LAZY)
    private List<Tw> replyTwList;

    @OneToMany(mappedBy = "tw", fetch = FetchType.LAZY)
    private List<Favorite> favoriteList;
}
