package com.decode.web.domain.elk;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;

    public void saveArticle(ArticleDto articleDto){
        articleRepository.save(articleDto);
    }

    public void deleteArticle(ArticleDto articleDto){
        articleRepository.delete(articleDto);
    }

    public Iterable<ArticleDto> getAllArticle(){
        return articleRepository.findAll();
    }

    public Optional<ArticleDto> getArticle(Long articleId){
        return articleRepository.findById(Long.toString(articleId));
    }

    public Iterable<ArticleDto> getArticleByKeyWord(String keyword){
        return articleRepository.findAllByTitleContaining(keyword);
    }
}
