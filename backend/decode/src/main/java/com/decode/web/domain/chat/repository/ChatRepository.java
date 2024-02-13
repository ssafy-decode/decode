package com.decode.web.domain.chat.repository;

import com.decode.web.entity.ChatEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends JpaRepository<ChatEntity, Long> {

    List<ChatEntity> findAllByChatRoomEntity_Id(Long roomId);

    List<ChatEntity> findTop100ByChatRoomEntity_IdOrderByCreatedTimeAsc(Long roomId);
}
