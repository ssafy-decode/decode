package com.decode.web.domain.elk;

import com.decode.web.global.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;

    @PostMapping("/article")
    public ResponseDto createArticle(@RequestBody ArticleDto articleDto){
        articleService.saveArticle(articleDto);
        return ResponseDto.builder().data(articleService.getAllArticle()).build();
    }

    @GetMapping("/article/{keyword}")
    public ResponseDto getArticleByKeyWord(@PathVariable(name = "keyword") String keyword){
        return ResponseDto.builder().data(articleService.getArticleByKeyWord(keyword)).build();
    }

}
