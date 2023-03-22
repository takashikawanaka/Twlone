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
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;

@Data
@Entity
@Table(name = "user")
//@Where(clause = "deleteFlag = 0")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false, length = 25) // Edit length
    @Pattern(regexp = "^[a-zA-Z0-9]+$")
    @NotEmpty
    private String userId;

    @Column(nullable = false)
    @NotEmpty
    private String name;

    @Column()
    private String description;

    @Column()
    private String icon;// Edit

    @Column()
    private String back;// Edit

    @Column(nullable = false)
    private Integer deleteFlag;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Date createdAt;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Tw> twList;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Follow> following;

    @OneToMany(mappedBy = "targetUser", fetch = FetchType.LAZY)
    private List<Follow> follower;
}
