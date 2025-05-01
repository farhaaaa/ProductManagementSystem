/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coachbar.pms.initializer;

import com.coachbar.pms.entity.Role;
import com.coachbar.pms.entity.User;
import com.coachbar.pms.repository.RoleRepository;
import com.coachbar.pms.repository.UserRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public void run(String... args) throws Exception {
        try {
            Role adminRole = roleRepository.findByRoleName("Admin").orElse(null);
            Role userRole = roleRepository.findByRoleName("User").orElse(null);
            User adminUser = userRepository.findByUserName("admins").orElse(null);
            // Add admin role during first time application start up
            if (adminRole == null) {
                adminRole = new Role();
                adminRole.setRoleName("Admin");
                adminRole.setActive(true);
                adminRole = roleRepository.save(adminRole);
                logger.info("Admin Role added successfully during first time startup.");
            }
            
            // Add user role during first time application start up
            if (userRole == null) {
                userRole = new Role();
                userRole.setRoleName("User");
                userRole.setActive(true);
                userRole = roleRepository.save(userRole);
                logger.info("User Role added successfully during first time startup.");
            }
            
            // Add first user during first time application start up
            if (adminUser == null) {
                adminUser = new User();
                adminUser.setUserName("admins");
                adminUser.setFirstName("super");
                adminUser.setLastName("admin");
                adminUser.setPassword(passwordEncoder.encode("admin123"));
                adminUser.setRole(adminRole);
                adminUser.setActive(true);
                userRepository.save(adminUser);
                logger.info("Admin User added successfully during first time startup.");
            }
        } catch (Exception e) {
            logger.error("Error in adding Roles and User.", e);
        }
    }

}
