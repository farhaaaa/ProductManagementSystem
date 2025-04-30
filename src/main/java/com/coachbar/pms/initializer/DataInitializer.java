/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coachbar.pms.initializer;

import com.coachbar.pms.entity.Role;
import com.coachbar.pms.repository.RoleRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 *
 * @author Farha Mansuri
 */
@Component
public class DataInitializer implements CommandLineRunner {

    private final static Log logger = LogFactory.getLog(DataInitializer.class);

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        try {
            if (!roleRepository.findByRoleName("Admin").isPresent()) {
                Role adminRole = new Role();
                adminRole.setRoleName("Admin");
                roleRepository.save(adminRole);
                logger.info("Admin Role added successfully during first time startup.");
            }
        } catch (Exception e) {
            logger.error("Error in adding Admin Role", e);
        }
    }

}
