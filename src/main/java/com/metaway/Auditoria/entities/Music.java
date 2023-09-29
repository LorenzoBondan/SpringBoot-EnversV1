package com.metaway.Auditoria.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import java.io.Serial;
import java.io.Serializable;

@Audited
@AuditTable(value = "music_audit")
@Data
@Entity
@Table(name = "tb_music")
public class Music implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    @Column(name = "time_duration")
    private Double timeDuration;
    @NotAudited
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "album_id")
    private Album album;

    public Music(){}

    public Music(Long id, String title, Double timeDuration, Album album) {
        this.id = id;
        this.title = title;
        this.timeDuration = timeDuration;
        this.album = album;
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

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }
}
