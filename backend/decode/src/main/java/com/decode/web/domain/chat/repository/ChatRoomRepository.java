package com.decode.web.domain.chat.repository;

import com.decode.web.entity.ChatEntity;
import com.decode.web.entity.ChatRoomEntity;
import com.decode.web.entity.UserProfileEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoomEntity, Long> {
}
