package com.decode.web.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Setter
@Getter
@Table(name = "chatRoom")
@NoArgsConstructor
@ToString
public class ChatRoomEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roomName;
    private String roomDescription;

    private Long creator;            // 채팅방 생성자


    @OneToMany(mappedBy = "chatRoomEntity", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<ChatSubRoomEntity> chatSubRoomList = new ArrayList<>();
    @OneToMany(mappedBy = "chatRoomEntity", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<ChatEntity> messageList = new ArrayList<>();


}
