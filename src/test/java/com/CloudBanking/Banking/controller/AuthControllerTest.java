package com.CloudBanking.Banking.controller;

import com.CloudBanking.Banking.model.User;
import com.CloudBanking.Banking.security.JwtUtil;
import com.CloudBanking.Banking.repository.UserRepository;
import com.CloudBanking.Banking.dto.AuthRequest;
import com.CloudBanking.Banking.security.SecurityConfig;
import com.CloudBanking.Banking.service.UserDetailsServiceImpl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@WebMvcTest(AuthController.class)
@Import(SecurityConfig.class)
public class AuthControllerTest {

    @Autowired private MockMvc mockMvc;

    @MockBean private AuthenticationManager authManager;
    @MockBean private JwtUtil jwtUtil;
    @MockBean private UserRepository userRepo;
    @MockBean private UserDetailsServiceImpl userDetailsService; 

    @Test
    public void testLoginSuccess() throws Exception {
        String json = """
            {
              "email": "alice@example.com",
              "password": "password123"
            }
        """;

        // return True
        
        Authentication fakeAuth = mock(Authentication.class);
        when(fakeAuth.isAuthenticated()).thenReturn(true);
        when(authManager.authenticate(any())).thenReturn(fakeAuth);

        User fakeUser = new User();
        fakeUser.setEmail("alice@example.com");
        fakeUser.setUsername("Alice");

        // BCryptPasswordEncoder test
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        fakeUser.setPassword(encoder.encode("password123"));

        when(userRepo.findByEmail("alice@example.com")).thenReturn(Optional.of(fakeUser));
        when(jwtUtil.generateToken(any())).thenReturn("mocked-jwt-token");

        mockMvc.perform(post("/api/auth/login")
                .contentType(APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("mocked-jwt-token"));
    }
}
