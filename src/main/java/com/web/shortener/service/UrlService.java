package com.web.shortener.service;

import com.web.shortener.model.Url;
import com.web.shortener.repository.UrlRepository;
import com.web.shortener.util.GeneratorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.KeyFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.regex.Pattern;

import static java.lang.classfile.Attributes.code;

@Service
@RequiredArgsConstructor
public class UrlService {


    private final UrlRepository urlRepository;

    private static final String URL_REGEX = "^(https?|ftp)://[a-zA-Z0-9+&@#/%?=~_|!:,.;-]*[-a-zA-Z0-9+&@#/%=~_|]";

    private static final Pattern URL_PATTERN = Pattern.compile(URL_REGEX);

    public Url shortener(String originalUrl) {
        if (!URL_PATTERN.matcher(originalUrl).matches()) {
            throw new IllegalArgumentException("Invalid Url");
        }
        Url url = Url.builder().
        clicks(0L).
        createdIn(LocalDateTime.now()).
        code(GeneratorCode.generateCode(6)).
        originalUrl(originalUrl).
        active(true).
        build();

        return urlRepository.save(url);

    }

    public Url shortenWithCode(String originalUrl, String code) {
    if (!URL_PATTERN.matcher(originalUrl).matches()) {
        throw new IllegalArgumentException("Invalid Url");
    }
        if (urlRepository.existsByCode(code)){
        throw new IllegalArgumentException("Code already exists");
        }
    Url url = Url.builder().
            clicks(0L).
            createdIn(LocalDateTime.now()).
            code(code)
            .originalUrl(originalUrl).
            active(true).
            build();

        return urlRepository.save(url);
    }



















}
