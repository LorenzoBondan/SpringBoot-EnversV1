package com.metaway.Auditoria.dtos;

import com.metaway.Auditoria.entities.Productor;

import java.io.Serializable;

public class ProductorDTO implements Serializable {
    private Long id;
    private String name;

    public ProductorDTO(){}

    public ProductorDTO(Productor entity){
        this.id = entity.getId();
        this.name = entity.getName();
    }

    public ProductorDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
