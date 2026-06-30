package com.utkarsh.startupvalidation.dto;

import java.util.List;

public class UserProfileResponse {
    private Long id;
    private String name;
    private String email;
    private int followerCount;
    private int followingCount;
    private List<Long> followingIds;
    private List<StartupIdeaResponse> ideas;

    public UserProfileResponse() {}

    public UserProfileResponse(Long id, String name, String email, int followerCount, int followingCount, List<Long> followingIds, List<StartupIdeaResponse> ideas) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.followerCount = followerCount;
        this.followingCount = followingCount;
        this.followingIds = followingIds;
        this.ideas = ideas;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public int getFollowerCount() { return followerCount; }
    public void setFollowerCount(int followerCount) { this.followerCount = followerCount; }

    public int getFollowingCount() { return followingCount; }
    public void setFollowingCount(int followingCount) { this.followingCount = followingCount; }

    public List<Long> getFollowingIds() { return followingIds; }
    public void setFollowingIds(List<Long> followingIds) { this.followingIds = followingIds; }

    public List<StartupIdeaResponse> getIdeas() { return ideas; }
    public void setIdeas(List<StartupIdeaResponse> ideas) { this.ideas = ideas; }
}
