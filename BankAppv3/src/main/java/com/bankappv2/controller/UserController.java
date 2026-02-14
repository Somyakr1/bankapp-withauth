 package com.bankappv2.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bankappv2.dto.AuthRequest;
import com.bankappv2.entity.BankUser;
import com.bankappv2.repo.BankUserRepo;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/v2/users")
@AllArgsConstructor
public class UserController {

    private BankUserRepo repo;
    private PasswordEncoder passwordEncoder;

    @PreAuthorize("hasRole('MGR')")
    @PostMapping("/create-clerk")
//    public String createClerk(@RequestBody AuthRequest request) {
//
//        BankUser user = new BankUser();
//        user.setUsername(request.getUsername());
//        user.setPassword(passwordEncoder.encode(request.getPassword()));
//        user.setRole("ROLE_CLERK");
//
//        repo.save(user);
//
//        return "Clerk created";
//    }
    public ResponseEntity<Map<String, String>> createClerk(
            @RequestBody AuthRequest request) {

        BankUser user = new BankUser();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("ROLE_CLERK");

        repo.save(user);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Clerk created successfully");
        response.put("username", user.getUsername());

        return new ResponseEntity<>(response, HttpStatus.CREATED); // 201
    }
}

