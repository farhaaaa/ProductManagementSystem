/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coachbar.pms.config;

import com.coachbar.pms.entity.User;
import com.coachbar.pms.repository.UserRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 *
 * @author Farha Mansuri
 */
@Component
public class AuditAwareImpl implements AuditorAware<Long> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<Long> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return Optional.of(1l); // To enter createdBy value for seeder role and user, so it does not give Null Pointer Exception
        }
        String username = authentication.getName();
        User user = userRepository.findByUserName(username).orElse(null);
        return user != null ? Optional.of(user.getId()) : Optional.empty();
    }

}
