package com.web.shortener.controller;

import com.web.shortener.service.UrlService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class UrlController {

    private UrlService urlService;

    @PostMapping("/url/disable/{id}")
    public String disable(@PathVariable Long id) {
        try {
            urlService.disable(id);
            return "redirect:/dashboard";
        } catch (Exception e) {
            return "redirect:/dashboard";
        }
    }

    @PostMapping("/url/delete/{id}")
    public String delete(@PathVariable Long id) {
        try {
            urlService.deleteById(id);
            return "redirect:/dashboard";
        } catch (Exception e) {
            return "redirect:/dashboard";
        }
    }
}
