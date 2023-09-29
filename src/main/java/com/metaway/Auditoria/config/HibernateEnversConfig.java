package com.metaway.Auditoria.config;

import jakarta.persistence.EntityManager;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HibernateEnversConfig {

    @Autowired
    private EntityManager entityManager;

    @Bean
    public AuditReader auditReader(){
        return AuditReaderFactory.get(entityManager);
    }
}
