package com.mustache.bbs4.controller;

import com.mustache.bbs4.domain.entity.Article;
import com.mustache.bbs4.domain.dto.ArticleDto;
import com.mustache.bbs4.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleRepository articleRepository;

    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @GetMapping("")
    public String listpage(Model model) {
        List<Article> articles =articleRepository.findAll();
        model.addAttribute("articles",articles);
        return "list";
    }

    @GetMapping("/new")
    public String createArticlePage() {
        return "new";
    }

    @GetMapping("/{id}")
    public String showSingle(@PathVariable Long id, Model model) {
        Optional<Article> optionalArticle = articleRepository.findById(id);
        if (!optionalArticle.isEmpty()) {
            model.addAttribute("article", optionalArticle.get());
            return "show";
        } else return "error";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        Optional<Article> optionalArticle = articleRepository.findById(id);
        log.info(optionalArticle.get().getTitle());
        if(optionalArticle.isPresent()){
            System.out.println("성공");
            model.addAttribute("article", optionalArticle.get());
            return "edit";
        }else{
            return "error";
        }
    }

    @PostMapping("{id}/update")
    public String updateArticle(ArticleDto articleDto, Model model) {
        Article saveArticle = articleRepository.save(articleDto.toEntity());
        model.addAttribute("article", saveArticle);
        return String.format("redirect:/articles/%d" , saveArticle.getId());
    }

    @GetMapping("{id}/delete")
    public String deleteById(@PathVariable Long id) {
        articleRepository.deleteById(id);
        return "redirect:/articles";
    }
    @PostMapping("")
    public String createArticle(ArticleDto articleDto) { //dto란 데이터 전송을 위한 오브젝트 Entity와는 구분해서 사용하는게좋음
        Article savedArticle = articleRepository.save(articleDto.toEntity());
        return String.format("redirect:/articles/%d", savedArticle.getId());
    }

}
