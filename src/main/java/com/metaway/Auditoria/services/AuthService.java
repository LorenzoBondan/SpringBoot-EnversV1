package com.metaway.Auditoria.services;

import com.metaway.Auditoria.dtos.UserDTO;
import com.metaway.Auditoria.entities.User;
import com.metaway.Auditoria.repositories.UserRepository;
import com.metaway.Auditoria.services.exceptions.ForbiddenException;
import com.metaway.Auditoria.util.CustomUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private CustomUserUtil customUserUtil;

    @Transactional
    public User authenticated() {
        try {
            String username = customUserUtil.getLoggedUsername();
            return repository.findByEmail(username).get();
        }
        catch (Exception e) {
            throw new UsernameNotFoundException("Invalid user");
        }
    }

    public void validateSelfOrAdmin(Long userId) {
        User me = authenticated();
        if (me.hasRole("ROLE_ADMIN")) {
            return;
        }
        if(!me.getId().equals(userId)) {
            throw new ForbiddenException("Access denied. Should be self or admin");
        }
    }

    public boolean isAdmin() {
        User user = authenticated();
        return user.hasRole("ROLE_ADMIN");
    }

    @Transactional(readOnly = true)
    public UserDTO getMe() {
        User entity = authenticated();
        return new UserDTO(entity);
    }
}
