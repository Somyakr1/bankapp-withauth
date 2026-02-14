package com.bankappv2.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bankappv2.entity.BankUser;
import com.bankappv2.repo.BankUserRepo;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BankUserDetailsService implements UserDetailsService {

    private BankUserRepo repo;

    @Override
    public UserDetails loadUserByUsername(String username) {

        BankUser user = repo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole().replace("ROLE_", ""))
                .build();
    }
}

