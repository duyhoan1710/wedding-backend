package com.example.wedding.repositories;

import com.example.wedding.entities.Template;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TemplateRepository extends JpaRepository<Template, Long> {
    Optional<Template> findOneByUserIdAndNameLike(long userId, String name);

    Optional<Template> findByIdAndUserId(long templateId, long userId);

    Page<Template> findByUserIdAndNameLike(long userId, String name, Pageable pageable);

}
