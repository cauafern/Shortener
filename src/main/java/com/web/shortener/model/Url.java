package com.web.shortener.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "urls")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Url {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String originalUrl;


    @Column(unique = true, nullable = false)
    private String code;

    @Column(nullable = false, columnDefinition = "BIGINT DEFAULT 0")
    private Long clicks = 0L;

    @Column(nullable = false)
    private LocalDateTime createdIn;

    private LocalDateTime expiredIn;

    @Column(nullable = false)
    private Boolean active = true;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


}
