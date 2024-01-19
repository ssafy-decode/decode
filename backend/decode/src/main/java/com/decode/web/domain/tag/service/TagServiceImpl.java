package com.decode.web.domain.tag.service;

import com.decode.web.domain.tag.dto.TagDto;
import com.decode.web.domain.tag.mapper.TagMapper;
import com.decode.web.domain.tag.repository.TagRepository;
import com.decode.web.entity.TagEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    @Override
    public boolean addTag(TagDto tagName) {
        TagEntity tagEntity = tagMapper.toEntity(tagName);
        try {
            tagRepository.save(tagEntity);
            return true;
        } catch (Exception e) {
            return false;
        }

    }
}
