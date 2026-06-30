package com.utkarsh.startupvalidation.controller;

import com.utkarsh.startupvalidation.dto.UserProfileResponse;
import com.utkarsh.startupvalidation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/{id}/follow")
    public String followUser(@PathVariable Long id) {
        userService.followUser(id);
        return "User followed successfully";
    }

    @PostMapping("/{id}/unfollow")
    public String unfollowUser(@PathVariable Long id) {
        userService.unfollowUser(id);
        return "User unfollowed successfully";
    }

    @GetMapping("/{id}/profile")
    public UserProfileResponse getUserProfile(@PathVariable Long id) {
        return userService.getUserProfile(id);
    }

    @GetMapping("/me/profile")
    public UserProfileResponse getCurrentUserProfile() {
        return userService.getCurrentUserProfile();
    }
}
