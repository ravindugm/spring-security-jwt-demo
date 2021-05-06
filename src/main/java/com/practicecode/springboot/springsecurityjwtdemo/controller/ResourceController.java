package com.practicecode.springboot.springsecurityjwtdemo.controller;

import com.practicecode.springboot.springsecurityjwtdemo.models.AuthenticationRequest;
import com.practicecode.springboot.springsecurityjwtdemo.models.AuthenticationResponse;
import com.practicecode.springboot.springsecurityjwtdemo.service.MyUserDetailsService;
import com.practicecode.springboot.springsecurityjwtdemo.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResourceController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @RequestMapping("/hello")
    public String hello() {
        return "Hello World!";
    }

    // ResponseEntity - represents the whole HTTP response and can use it to fully configure the HTTP response
    // Create /authenticate endpoint
    // Create createAuthenticationToken() method and it takes authenticationRequest(username and password)
    // Using authenticationManager authenticate username and password
    // If username and password authenticated, Using userDetails generate JWT token
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        final String jwt = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

}
