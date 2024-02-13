package com.decode.web.domain.board.repository;

import com.decode.web.domain.board.dto.QuestionDocument;
import java.util.List;
import java.util.Optional;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface QuestionELKRepository extends ElasticsearchRepository<QuestionDocument, String> {

    List<QuestionDocument> findAllByWriterId(Long writerId);

    Optional<QuestionDocument> findById(Long id);

    @Query("{\"match_all\": {}}")
    List<QuestionDocument> findAllQuestion();

    List<QuestionDocument> findAllByOrderByIdDesc();

    @Query("{\"bool\": {\"should\": ["
            + "{\"match\": {"
            + "\"title.nori\": \"?0\"}},"
            + "{\"match\": {"
            + "\"content.nori\": \"?0\"}}]}}")
    List<QuestionDocument> findByTitleOrContent(String keyword);

    @Query("{\"terms_set\" : {"
            + "\"questionTags.tagId\": {"
            + "\"terms\" : ?0,"
            + "\"minimum_should_match_script\": {"
            + "\"source\": \"params.num_terms\"}}}}")
    List<QuestionDocument> findByQuestionTags(List<Long> tagIds);


    @Query("{\"bool\": {\"must\": ["
            + "{\"bool\": {\"should\": ["
            + "{\"match\": {\"title.nori\": \"?0\"}},"
            + "{\"match\": {\"content.nori\": \"?0\"}}]}},"
            + "{\"terms_set\": {"
            + "\"questionTags.tagId\": {"
            + "\"terms\": ?1,"
            + "\"minimum_should_match_script\": {"
            + "\"source\": \"params.num_terms\"}}}}]}}")
    List<QuestionDocument> findByQuestionTagsAndTitleAndContent(String keyword, List<Long> tagIds);
}
