package com.metaway.Auditoria.entities.audit;

import com.metaway.Auditoria.components.ApplicationContextProvider;
import com.metaway.Auditoria.entities.User;
import com.metaway.Auditoria.services.AuthService;
import jakarta.annotation.ManagedBean;
import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.envers.RevisionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.time.LocalDateTime;

@ManagedBean
public class AuditListener implements RevisionListener {

    @Autowired
    private HttpServletRequest request;

    @Override
    public void newRevision(Object auditEntity) {
        AuditEntity audit = (AuditEntity) auditEntity;
        audit.setTimestamp(LocalDateTime.now());
        String ip;
        try{
            ip = request.getHeader("X-FORWARDED-FOR");
            if (ip == null) {
                ip = request.getRemoteAddr();
            }
        } catch(Exception e) {
            ip = null;
        }
        audit.setIp(ip);

        AuthService authService = getAuthService();
        User me = authService.authenticated();
        audit.setUsername(me.getEmail());
    }

    private AuthService getAuthService() {
        ApplicationContext context = ApplicationContextProvider.getApplicationContext();
        return context.getBean(AuthService.class);
    }
}
