/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coachbar.pms.repository;

import com.coachbar.pms.entity.Role;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Farha Mansuri
 */
public interface RoleRepository extends JpaRepository<Role, Long>{
    
    Optional<Role> findByRoleName(String roleName);
}
