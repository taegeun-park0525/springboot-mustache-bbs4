package com.mustache.bbs4.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/articles")
public class ArticleController {

    @GetMapping("/new")
    public String createArticlePage() {
        return "new";
    }
}