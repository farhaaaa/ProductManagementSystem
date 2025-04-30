/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coachbar.pms.config;

import java.util.Optional;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

/**
 *
 * @author Farha Mansuri
 */
@Component
public class AuditAwareImpl implements AuditorAware<Long>{

    @Override
    public Optional<Long> getCurrentAuditor() {
        return Optional.of(1l);
    }
    
}
