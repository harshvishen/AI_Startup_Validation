package com.utkarsh.startupvalidation.repository;

import com.utkarsh.startupvalidation.entity.IdeaRating;
import com.utkarsh.startupvalidation.entity.StartupIdea;
import com.utkarsh.startupvalidation.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface IdeaRatingRepository extends JpaRepository<IdeaRating, Long> {
    Optional<IdeaRating> findByIdeaAndUser(StartupIdea idea, User user);

    @Query("SELECT AVG(r.rating) FROM IdeaRating r WHERE r.idea.id = :ideaId")
    Double getAverageRatingByIdeaId(@Param("ideaId") Long ideaId);

    void deleteByIdea(StartupIdea idea);
}
