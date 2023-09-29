package com.metaway.Auditoria.dtos;

import com.metaway.Auditoria.entities.Music;

import java.io.Serializable;

public class MusicDTO implements Serializable {
    private Long id;
    private String title;
    private Double timeDuration;
    private Long albumId;

    public MusicDTO(){}

    public MusicDTO(Music entity){
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.timeDuration = entity.getTimeDuration();
        this.albumId = entity.getAlbum().getId();
    }

    public MusicDTO(Long id, String title, Double timeDuration, Long albumId) {
        this.id = id;
        this.title = title;
        this.timeDuration = timeDuration;
        this.albumId = albumId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getTimeDuration() {
        return timeDuration;
    }

    public void setTimeDuration(Double timeDuration) {
        this.timeDuration = timeDuration;
    }

    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }
}
