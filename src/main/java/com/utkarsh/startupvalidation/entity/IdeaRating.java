package com.utkarsh.startupvalidation.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "idea_ratings")
public class IdeaRating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idea_id")
    private StartupIdea idea;

    private int rating;

    public IdeaRating() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public StartupIdea getIdea() {
        return idea;
    }

    public void setIdea(StartupIdea idea) {
        this.idea = idea;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
