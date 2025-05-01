/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coachbar.pms.services;

import com.coachbar.pms.controller.AuthController;
import com.coachbar.pms.entity.Role;
import com.coachbar.pms.entity.User;
import com.coachbar.pms.repository.RoleRepository;
import com.coachbar.pms.repository.UserRepository;
import java.util.Collections;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author Farha Mansuri
 */
@Service
public class UserService {

    private final static Log logger = LogFactory.getLog(UserService.class);
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Map<String, String> createUser(User userReq) {
        try {
            if (userRepository.findByUserName(userReq.getUserName()).isPresent()) {
                return Collections.singletonMap("error", "User name already exists");
            }
            Role role = roleRepository.findByRoleName("User").orElse(null);
            if (role == null) {
                return Collections.singletonMap("error", "User role not found.");
            }
            User user = new User();
            user.setUserName(userReq.getUserName());
            user.setFirstName(userReq.getFirstName());
            user.setLastName(userReq.getLastName());
            user.setPassword(passwordEncoder.encode(userReq.getPassword()));
            user.setRole(role);
            user.setActive(true);
            userRepository.save(user);
            return Collections.singletonMap("success", "User saved successfully.");
        } catch (Exception e) {
            logger.error("Error occurred during createUser(): " + e);
            return Collections.singletonMap("error", "Error occurred.");
        }
    }

}
