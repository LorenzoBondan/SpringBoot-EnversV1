package com.metaway.Auditoria.dtos;

import com.metaway.Auditoria.entities.Album;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AlbumDTO implements Serializable {
    private Long id;
    private String title;
    private String author;
    private String dateCreate;
    private Long productorId;

    private List<MusicDTO> musics = new ArrayList<>();

    public AlbumDTO(){}

    public AlbumDTO(Album entity){
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.author = entity.getAuthor();
        this.dateCreate = entity.getDateCreate();
        this.productorId = entity.getProductor().getId();

        entity.getMusics().forEach(m -> this.musics.add(new MusicDTO(m)));
    }

    public AlbumDTO(Long id, String title, String author, String dateCreate, Long productorId) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.dateCreate = dateCreate;
        this.productorId = productorId;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(String dateCreate) {
        this.dateCreate = dateCreate;
    }

    public Long getProductorId() {
        return productorId;
    }

    public void setProductorId(Long productorId) {
        this.productorId = productorId;
    }

    public List<MusicDTO> getMusics() {
        return musics;
    }
}
