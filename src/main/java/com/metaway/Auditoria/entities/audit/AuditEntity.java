package com.metaway.Auditoria.entities.audit;

import jakarta.persistence.*;
import org.hibernate.envers.*;
import org.springframework.data.annotation.CreatedBy;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Entity(name = "revinfo_cust")
@RevisionEntity(AuditListener.class)
public class AuditEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @RevisionNumber
    private Long id;

    @RevisionTimestamp
    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private LocalDateTime timestamp;

    public String username;
    public String ip;

    @ElementCollection
    @JoinTable(name = "audit_entity",
        joinColumns = @JoinColumn(name = "audit_id"))
    @Column(name = "nome_entidade")
    @ModifiedEntityNames
    private Set<String> modifiedEntityNames;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public String getIp() {
        return ip;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Set<String> getModifiedEntityNames() {
        return modifiedEntityNames;
    }
}
