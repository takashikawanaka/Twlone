package com.twlone.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;

@Data
@Entity
@Table(name = "tw", uniqueConstraints = { @UniqueConstraint(name = "tw_user", columnNames = { "user_id" }) })
public class Tw {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer twId;

    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    //private Tw reTwId;

    //private Tw replyTwId;

    @Column(nullable = false)
    private Integer deleteFlag;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

}
