package com.web.shortener.controller;

import com.web.shortener.model.Url;
import com.web.shortener.service.UrlService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@AllArgsConstructor
public class RedirectController {

    private UrlService urlService;

    @GetMapping("/{code}")
    public String redirect(@PathVariable String code) {
        try {
            Url url = urlService.searchByCode(code);
            urlService.registerClicks(code);
            return "redirect:" + url.getOriginalUrl();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "error";
        }
    }

    @GetMapping("/error/not-found")
    public String linkNotFound(){
        return "error";
    }
}
