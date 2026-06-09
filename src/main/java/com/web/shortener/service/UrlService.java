package com.web.shortener.service;

import com.web.shortener.exception.CodeAlreadyExistsException;
import com.web.shortener.exception.CodeExpiredException;
import com.web.shortener.exception.CodeNotFoundException;
import com.web.shortener.exception.LinkDisabledException;
import com.web.shortener.model.Url;
import com.web.shortener.repository.UrlRepository;
import com.web.shortener.util.GeneratorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.util.InvalidUrlException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;


@Service
@RequiredArgsConstructor
public class UrlService {


    private final UrlRepository urlRepository;

   // private static final String URL_REGEX = "^(https?://)([\\w\\-]+\\.)+[\\w\\-]+(:[0-9]+)?(/.*)?$";
   // private static final Pattern URL_PATTERN = Pattern.compile(URL_REGEX);

    @Transactional
    public Url shortener(String originalUrl) {

        String code;
        do {
            code = GeneratorCode.generateCode(6);
        } while (urlRepository.existsByCode(code));

        Url url = Url.builder().
                clicks(0L).
                createdIn(LocalDateTime.now()).
                code(code).
                originalUrl(originalUrl).
                active(true).
                build();

        return urlRepository.save(url);

    }

    @Transactional
    public Url createShortUrlWithCustomCode(String originalUrl, String code) {
        if (originalUrl.isBlank()) {
            throw new InvalidUrlException("URL cannot be empty");
        }

        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("Code cannot be empty");
        }

//        if (!URL_PATTERN.matcher(originalUrl).matches()) {
//            throw new InvalidUrlException("Invalid Url");
//        }

        if (urlRepository.existsByCode(code)) {
            throw new CodeAlreadyExistsException("Code already exists");
        }

        final Url url = Url.builder().
                code(code).
                originalUrl(originalUrl).
                active(true).
                clicks(0L).
                createdIn(LocalDateTime.now()).
                build();

        return urlRepository.save(url);
    }


    public Url searchByCode(String code) {
        Url url = urlRepository.findByCode(code)
                .orElseThrow(() -> new CodeNotFoundException("Code does not exists"));

        if (!Boolean.TRUE.equals(url.getActive())) {
            throw new LinkDisabledException("Link disabled");
        }
        LocalDateTime now = LocalDateTime.now();

        if (url.getExpiredIn() != null && url.getExpiredIn().isBefore(now)) {
            throw new CodeExpiredException("Code has expired");
        }
        return url;
    }

    @Transactional
    public void registerClicks(String code) {
        Url url = searchByCode(code);
        url.setClicks(url.getClicks() + 1);
        urlRepository.save(url);

    }

    public List<Url> listAll() {
        return urlRepository.findAll();
    }

    @Transactional
    public void disable(Long id) {
        Url url = urlRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID does not exists"));

        url.setActive(false);
        urlRepository.save(url);
    }

//    private void validateUrl(String originalUrl) {
//        if (originalUrl == null || !URL_PATTERN.matcher(originalUrl).matches()) {
//            throw new InvalidUrlException("Invalid Url");
//        }
//    }

    public void deleteById(Long id) {
        urlRepository.deleteById(id);
    }
}