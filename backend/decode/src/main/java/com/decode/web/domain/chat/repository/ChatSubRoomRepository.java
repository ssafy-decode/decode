package com.decode.web.domain.chat.repository;

import com.decode.web.entity.ChatSubRoomEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatSubRoomRepository extends JpaRepository<ChatSubRoomEntity, Long> {

    List<ChatSubRoomEntity> findByUserId(Long userId);

    Optional<ChatSubRoomEntity> findByChatRoomEntityIdAndUserId(Long id, Long userId);

}
