/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coachbar.pms.repository;

import com.coachbar.pms.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Farha Mansuri
 */
public interface UserRepository extends JpaRepository<User, Long>{
    
    Optional<User> findByUserName(String userName);
}
