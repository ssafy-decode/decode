package com.decode.web.domain.store.service;

import com.decode.web.domain.store.dto.ItemDto;
import com.decode.web.domain.store.dto.ItemUseDto;
import com.decode.web.domain.store.repository.ItemRepository;
import com.decode.web.entity.ItemEntity;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl {

    private final ItemRepository itemRepository;

    public List<ItemDto> getItemByUserId(Long userId) {
        return itemRepository.findByUserInfoId(userId)
                .stream()
                .map(ItemEntity::toDto)
                .collect(Collectors.toList());
    }

    public Integer useItem(ItemUseDto itemUseDto) throws BadRequestException {
        ItemEntity item = itemRepository.findByItemIdAndUserId(itemUseDto.getItemId(),
                        itemUseDto.getUserId())
                .orElseThrow(() -> new BadRequestException("아이템 사용 불가"));
        item.buy(itemUseDto.getCount());
        return item.getProductCount();
    }
}
