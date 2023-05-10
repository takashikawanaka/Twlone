package com.twlone.entity;

import java.sql.Date;
import java.util.List;
import java.util.function.Supplier;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
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
    @Pattern(regexp = "^[a-zA-Z0-9]+")
    @NotBlank
    private String userId;

    @Column(nullable = false)
    @NotBlank
    private String name;

    @Column()
    private String description;

    @Column()
    private String icon = ((Supplier<String>) () -> {
        String[] icon = { "default_1.png", "default_2.png", "default_3.png", "default_4.png", "default_5.png",
                "default_6.png" };
        return icon[(int) (Math.random() * 6)];
    }).get();

    @Column()
    private String back = ((Supplier<String>) () -> {
        String[] back = { "default_1.jpg", "default_2.jpg", "default_3.jpg", "default_4.jpg", "default_5.jpg",
                "default_6.jpg" };
        return back[(int) (Math.random() * 6)];
    }).get();

    @Column(nullable = false)
    private Integer deleteFlag = 0;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Date createdAt;

    @OrderBy(value = "id desc")
    @OneToMany(mappedBy = "sourceUser", fetch = FetchType.LAZY)
    private List<Follow> followingList;

    @OrderBy(value = "id desc")
    @OneToMany(mappedBy = "targetUser", fetch = FetchType.LAZY)
    private List<Follow> followerList;
}
