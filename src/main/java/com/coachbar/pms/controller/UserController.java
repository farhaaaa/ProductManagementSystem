/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coachbar.pms.controller;

import com.coachbar.pms.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.coachbar.pms.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author Farha Mansuri
 */
@RestController
@RequestMapping("/users")
@Tag(name = "Users", description = "Manage user registration")
public class UserController {

    @Autowired
    private UserService userService;

    @PreAuthorize("hasAuthority('Admin')")
    @Operation(summary = "Register a new user (Admin only)", description = "Allows only Admin users to register new users in the system.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "User registered successfully",
                content = @Content(schema = @Schema(example = "{\"message\": \"User saves successfully\"}"))),
        @ApiResponse(responseCode = "500", description = "Failed to register user",
                content = @Content(schema = @Schema(example = "{\"error\": \"Error occurred\"}"))),
        @ApiResponse(responseCode = "403", description = "Access denied - forbidden")
    })
    @SecurityRequirement(name = "bearerAuth")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<Map<String, String>> register(@RequestBody User user) {
        Map<String, String> resultMap = userService.createUser(user);
        if (resultMap.containsKey("error")) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultMap);
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body(resultMap);
        }
    }
}
