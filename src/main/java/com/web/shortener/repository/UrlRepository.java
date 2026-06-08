package com.web.shortener.repository;

import com.web.shortener.model.Url;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UrlRepository extends JpaRepository<Url, Long> {

    // Search by short code
    Optional<Url> findByCode(String code);

    // Checks if a code already exists.
    boolean existsByCode(String code);

    // List only the active links.
    List<Url> findByActiveTrue();

    // Search for expired links
    List<Url> findByExpiredInBefore(LocalDateTime date);
}
