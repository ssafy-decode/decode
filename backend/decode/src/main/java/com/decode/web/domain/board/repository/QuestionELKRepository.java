package com.decode.web.domain.board.repository;

import com.decode.web.domain.board.dto.CreateQuestionDocument;
import java.util.List;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface QuestionELKRepository extends ElasticsearchRepository<CreateQuestionDocument, String> {


    @Query("{\"match_all\": {\"questionTags.tagId\": \"?0\"}}")
    List<CreateQuestionDocument> findAllByQuestionTagsTagIdIn(List<Long> tagIds);

    @Query("{\"terms_set\": {\"questionTags.tagId\": {\"terms\": ?0, \"minimum_should_match_script\": {\"source\": \"params.num_terms\"}}}}")
    List<CreateQuestionDocument> findByQuestionTagsTagIdContainingAllValues(List<Long> tagIds);
//    @Query({"\"bool\": {\"should\": [{\"match_phrase_prefix\": {\"title\": \"?0\"}}, {\"match_phrase_prefix\": {\"content\": \"?1\"}}]}")
//    List<CreateQuestionDocument> findAllByTitleContainingOrContentContaining(List<String> title, List<String> content);
//    @Query("{\"bool\": {\"must\": {\"terms_set\": {\"title.keyword\": {\"terms\": ?0, \"minimum_should_match_script\": {\"source\": \"params.num_terms\"}}}}}}")
//    List<CreateQuestionDocument> findByTitleContainingAllKeywords(List<String> keywords);

    @Query("{\"match_phrase_prefix\": {\"title\": {\"query\": \"?0\"}, }}")
    List<CreateQuestionDocument> findByTitleContainingAllKeywords(String keyword);

    @Query("{\"bool\": {\"should\": [{\"match_phrase_prefix\": {\"title\": {\"query\": \"?0\"}}}, {\"match_phrase_prefix\": {\"content\": {\"query\": \"?0\"}}}]}}")
    List<CreateQuestionDocument> findByTitleOrContentContaining(String keyword);

//    @Query("{\"bool\": {\"must\": [" +
//            "{\"match_phrase_prefix\": {\"title\": ?0}}, " +
//            "{\"match_phrase_prefix\": {\"content\": ?0}}" +
//            "]}}")
    @Query("{\"bool\": {\"must\": ["
        + "{\"match\": {"
        + "\"title.keyword\": {"
        + "\"query\": \"?0\","
        + "\"minimum_should_match_script\": {\"source\": \"params.num_match\"}"
        + "}}}]}}")
    List<CreateQuestionDocument> findByTitleOrContentContainingWords(List<String> words);


    @Query("{\"bool\": {\"must\": [{\"terms_set\": {\"questionTags.tagId\": {\"terms\": ?0, \"minimum_should_match_script\": {\"source\": \"params.num_terms\"}}}}, {\"bool\": {\"should\": [{\"match_phrase_prefix\": {\"title\": \"?1\"}}, {\"match_phrase_prefix\": {\"content\": \"?2\"}}]}}]}}")
    List<CreateQuestionDocument> findByQuestionTagsAndTitleOrContent(List<Long> tagIds, String title, String content);


    @Query("{\"bool\": {\"must\": [{\"bool\": {\"should\": [{\"match\": {\"title\": \"?0\"}}, {\"match\": {\"content\": \"?0\"}}]}}, {\"terms\": {\"questionTags.tagId\": ?1}}]}}")
    List<CreateQuestionDocument> findByKeywordAndTags(String keyword, List<Long> tagIds);


}
