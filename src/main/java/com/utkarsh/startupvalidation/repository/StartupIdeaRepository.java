package com.utkarsh.startupvalidation.repository;

import com.utkarsh.startupvalidation.entity.StartupIdea;
import com.utkarsh.startupvalidation.entity.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface StartupIdeaRepository
        extends JpaRepository<StartupIdea, Long> {

    List<StartupIdea> findByIndustry(String industry);
    long countByIndustry(String industry);

    Page<StartupIdea> findByAuthorIsNotNullOrderByCreatedAtDesc(Pageable pageable);
    Page<StartupIdea> findByAuthorInOrderByCreatedAtDesc(Collection<User> authors, Pageable pageable);
    Page<StartupIdea> findByTitleContainingIgnoreCaseAndAuthorIsNotNullOrderByCreatedAtDesc(String keyword, Pageable pageable);
    Page<StartupIdea> findByAuthorOrderByCreatedAtDesc(User author, Pageable pageable);
}