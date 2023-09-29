package com.metaway.Auditoria.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Audited
@AuditTable(value = "album_audit")
@Data
@Entity
@Table(name = "tb_album")
public class Album implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String author;
    @Column(name = "dt_create", nullable = false)
    private String dateCreate;
    @NotAudited
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "productor_id", nullable = false)
    private Productor productor;
    @NotAudited
    @OneToMany(mappedBy = "album")
    private List<Music> musics = new ArrayList<>();

    public Album(){}

    public Album(Long id, String title, String author, String dateCreate, Productor productor){
        this.id = id;
        this.title = title;
        this.author = author;
        this.dateCreate = dateCreate;
        this.productor = productor;
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

    public Productor getProductor() {
        return productor;
    }

    public void setProductor(Productor productor) {
        this.productor = productor;
    }

    public List<Music> getMusics() {
        return musics;
    }
}
