package com.utkarsh.startupvalidation.repository;

import com.utkarsh.startupvalidation.entity.IdeaSuggestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IdeaSuggestionRepository extends JpaRepository<IdeaSuggestion, Long> {
    List<IdeaSuggestion> findByIdeaIdOrderByCreatedAtDesc(Long ideaId);

    void deleteByIdea(com.utkarsh.startupvalidation.entity.StartupIdea idea);
}
