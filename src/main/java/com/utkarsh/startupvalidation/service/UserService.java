package com.utkarsh.startupvalidation.service;

import com.utkarsh.startupvalidation.dto.AuthResponse;
import com.utkarsh.startupvalidation.dto.LoginRequest;
import com.utkarsh.startupvalidation.dto.RegisterRequest;
import com.utkarsh.startupvalidation.dto.StartupIdeaResponse;
import com.utkarsh.startupvalidation.dto.UserProfileResponse;
import com.utkarsh.startupvalidation.entity.StartupIdea;
import com.utkarsh.startupvalidation.entity.User;
import com.utkarsh.startupvalidation.repository.UserRepository;
import com.utkarsh.startupvalidation.security.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public User register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(
                passwordEncoder.encode(request.getPassword()));
        user.setRole("USER");
        return userRepository.save(user);
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("User Not Found"));

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {
            throw new RuntimeException("Invalid Password");
        }

        String token = jwtUtil.generateToken(user.getEmail());
        return new AuthResponse(token);
    }

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void followUser(Long targetUserId) {
        User currentUser = getCurrentUser();
        if(currentUser.getId().equals(targetUserId)) {
            throw new RuntimeException("Cannot follow yourself");
        }
        User targetUser = userRepository.findById(targetUserId).orElseThrow(() -> new RuntimeException("User not found"));
        targetUser.getFollowers().add(currentUser);
        userRepository.save(targetUser); // cascade will save the relationship
    }

    public void unfollowUser(Long targetUserId) {
        User currentUser = getCurrentUser();
        User targetUser = userRepository.findById(targetUserId).orElseThrow(() -> new RuntimeException("User not found"));
        targetUser.getFollowers().remove(currentUser);
        userRepository.save(targetUser);
    }

    public UserProfileResponse getUserProfile(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return mapUserToProfileResponse(user);
    }

    public UserProfileResponse getCurrentUserProfile() {
        return mapUserToProfileResponse(getCurrentUser());
    }

    private UserProfileResponse mapUserToProfileResponse(User user) {
        UserProfileResponse response = new UserProfileResponse();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setFollowerCount(user.getFollowers().size());
        response.setFollowingCount(user.getFollowing().size());
        response.setFollowingIds(user.getFollowing().stream().map(User::getId).collect(Collectors.toList()));
        
        response.setIdeas(user.getIdeas().stream().map(idea -> new StartupIdeaResponse(
                idea.getId(),
                idea.getTitle(),
                idea.getIndustry(),
                idea.getProblem(),
                idea.getSolution(),
                idea.getCreatedAt(),
                user.getId(),
                user.getName(),
                0.0 // Could be fetched via IdeaInteractionService if needed, but 0.0 is fine for basic profile
        )).collect(Collectors.toList()));
        
        return response;
    }
}
