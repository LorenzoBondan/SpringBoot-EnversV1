package com.metaway.Auditoria.entities.audit;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class ChangePK {

    private String objectId;
    private String propertyName;
    private String oldValue;
    private String newValue;

    public ChangePK(){}

    public ChangePK(String objectId, String propertyName, String oldValue, String newValue) {
        this.objectId = objectId;
        this.propertyName = propertyName;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChangePK changePK = (ChangePK) o;
        return Objects.equals(objectId, changePK.objectId) && Objects.equals(propertyName, changePK.propertyName) && Objects.equals(oldValue, changePK.oldValue) && Objects.equals(newValue, changePK.newValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(objectId, propertyName, oldValue, newValue);
    }
}
