package com.metaway.Auditoria.entities.audit;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "tb_change")
public class Change implements Serializable {
    @EmbeddedId
    private ChangePK changePK = new ChangePK();

    private String instant;
    private String username;

    public Change(){}

    public Change(String objectId, String propertyName, String oldValue, String newValue, String instant, String username) {
        changePK.setObjectId(objectId);
        changePK.setPropertyName(propertyName);
        changePK.setOldValue(oldValue);
        changePK.setNewValue(newValue);
        this.instant = instant;
        this.username = username;
    }

    public String getPropertyName() {
        return changePK.getPropertyName();
    }

    public void setPropertyName(String propertyName) {
        changePK.setPropertyName(propertyName);
    }

    public Object getOldValue() {
        return changePK.getOldValue();
    }

    public void setOldValue(String oldValue) {
        changePK.setOldValue(oldValue);
    }

    public Object getNewValue() {
        return changePK.getNewValue();
    }

    public void setNewValue(String newValue) {
        changePK.setNewValue(newValue);
    }

    public String getObjectId() {
        return changePK.getObjectId();
    }

    public void setObjectId(String objectId) {
        changePK.setObjectId(objectId);
    }

    public String getInstant() {
        return instant;
    }

    public void setInstant(String instant) {
        this.instant = instant;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
