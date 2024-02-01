package com.decode.web.domain.elk;

import java.util.List;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ArticleRepository extends ElasticsearchRepository<ArticleDto, String> {

    List<ArticleDto> findAllByTitleContaining(String word);

}
