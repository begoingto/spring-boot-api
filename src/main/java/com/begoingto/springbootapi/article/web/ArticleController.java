package com.begoingto.springbootapi.article.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/articles")
public class ArticleController {
    @GetMapping
    List<ArticleDTO> getAll(){
        List<ArticleDTO> articleDTOS = new ArrayList<>(){{
            add(new ArticleDTO(1,"ABC"));
            add(new ArticleDTO(2,"Anchor"));
        }};

        return articleDTOS;
    }
}
