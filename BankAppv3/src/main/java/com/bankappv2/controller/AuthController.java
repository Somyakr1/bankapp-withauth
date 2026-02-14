package com.bankappv2.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bankappv2.dto.AuthRequest;
import com.bankappv2.security.JwtService;

import jakarta.annotation.PostConstruct;

@RestController
@RequestMapping("/authenticate")
public class AuthController {
	
//	@Autowired
//	PasswordEncoder encoder;
//
//	@PostConstruct
//	public void printPassword() {
//	    System.out.println(encoder.encode("2345"));
//	}


	@Autowired
    private AuthenticationManager authenticationManager;
	
	@Autowired
    private JwtService jwtService;
	
	@Autowired
    private UserDetailsService userDetailsService;

    @PostMapping
//    public String authenticate(@RequestBody AuthRequest request) {
//
//        authenticationManager.authenticate(
//            new UsernamePasswordAuthenticationToken(
//                request.getUsername(),
//                request.getPassword()
//            )
//        );
//
//        UserDetails userDetails =
//                userDetailsService.loadUserByUsername(request.getUsername());
//
//        return jwtService.generateToken(userDetails);
//    }
    public ResponseEntity<Map<String, String>> authenticate(
            @RequestBody AuthRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        UserDetails userDetails =
                userDetailsService.loadUserByUsername(request.getUsername());

        String jwtToken = jwtService.generateToken(userDetails);

        Map<String, String> response = new HashMap<>();
        response.put("token", jwtToken);
        response.put("type", "Bearer");

        return ResponseEntity.ok(response); // 200 OK
    }
    
    
}
