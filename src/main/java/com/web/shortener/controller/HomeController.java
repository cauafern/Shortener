package com.web.shortener.controller;

import com.web.shortener.model.Url;
import com.web.shortener.service.UrlService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@AllArgsConstructor
@Controller
public class HomeController {

    private UrlService urlService;

    @PostMapping("/shortener")
    public String shortener(@RequestParam String originalUrl, @RequestParam(required = false) String code, Model model) {
        try {
            Url url = (code == null || code.isBlank())
                    ? urlService.shortener(originalUrl)
                    : urlService.createShortUrlWithCustomCode(code, originalUrl);
            model.addAttribute("url", url);
            return "index";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "index";
        }
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        List<Url> urls = urlService.listAll();

        long totalCliques = urls.stream().mapToLong(Url::getClicks).sum();
        long totalAtivos = urls.stream().filter(Url::getActive).count();

        model.addAttribute("urls", urls);
        model.addAttribute("totalCliques", totalCliques);
        model.addAttribute("totalAtivos", totalAtivos);

        return "dashboard";
    }
}