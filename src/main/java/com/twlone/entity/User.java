package com.twlone.entity;

import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Where;

import lombok.Data;

@Data
@Entity
@Table(name = "user")
@Where(clause = "delete_flag = 0")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false, length = 25) // Edit length
    @Pattern(regexp = "^[a-zA-Z0-9]+$")
    @NotBlank
    private String userId;

    @Column(nullable = false)
    @NotBlank
    private String name;

    @Column()
    private String description;

    @Column()
    private String icon;// Edit

    @Column()
    private String back;// Edit

    @Column(nullable = false)
    private Integer deleteFlag = 0;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Date createdAt;

    @OrderBy(value = "id desc")
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @Where(clause = "reply_tw_id is null and delete_flag = 0")
    private List<Tw> twList;

    @OrderBy(value = "id desc")
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Follow> followingList;

    @OrderBy(value = "id desc")
    @OneToMany(mappedBy = "targetUser", fetch = FetchType.LAZY)
    private List<Follow> followerList;

    @OrderBy(value = "id desc")
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Favorite> favoriteList;

    @Transient
    private Boolean isFollow = false;
}
