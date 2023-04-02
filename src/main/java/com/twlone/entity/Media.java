package com.twlone.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Table(name = "media")
public class Media {
    public static enum MediaType {
        jpeg("image/jpeg"), png("image/png"), gif("image/gif"), bmp("image/bmp"), webp("image/webp"), mp4("movie/mp4"),
        webm("movie/webm"), ogg("movie/ogg"), quicktime("movie/quicktime"), mpeg("movie/mpeg");

        private final String contentType;

        MediaType(String contentType) {
            this.contentType = contentType;
        }

        public String getContentType() {
            return contentType;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tw_id", nullable = false, updatable = false)
    private Tw tw;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, updatable = false)
    private MediaType type;

    @Column(unique = true, nullable = false, updatable = false)
    private String path;

    protected Media() {
    }

    public Media(Tw tw, MediaType type, String path) {
        this.tw = tw;
        this.type = type;
        this.path = path;
    }
}
