package com.twlone.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "follow")
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "target_user_id", nullable = false, updatable = false)
    private User targetUser;

    protected Follow() {
    }

    public Follow(User user, User targetUser) {
        this.user = user;
        this.targetUser = targetUser;
    }

}
