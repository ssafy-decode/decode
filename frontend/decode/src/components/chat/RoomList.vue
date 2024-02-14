<template>
  <div class="room-list">
    <div v-for="room in rooms" :key="room.id" class="room-item" @click="selectRoom(room)">
      <div class="room-title">{{ room.roomName }}</div>
      <div class="room-description">{{ room.roomDescription }}</div>
    </div>
  </div>
</template>

<script>
import { ref } from 'vue';
import { useStompStore } from '@/utils/StompUtil';
import { useChatStore } from '@/stores/chatStore';

export default {
  name: 'RoomList',
  props: {
    rooms: Array,
  },
  setup(props, context) {
    const stompStore = useStompStore();
    const chatStore = useChatStore();
    const selectRoom = (room) => {
      stompStore.subscribeRoom(room.id);
      chatStore.subChatRoom(room.id);
      context.emit('selectRoom', room);
    };

    return { selectRoom };
  },
};
</script>

<style scoped>
.room-list {
  flex-direction: column;
  height: 89%;
  overflow-y: auto;
  background-color: #f5f5f5; /* 방 리스트 전체 배경색 추가 */
  border-top: 0.5px solid #b1d8cd;
  border-bottom: 1px solid #34a080;
}

.room-item {
  width: 100%;
  height: 50px;
  /* box-sizing: border-box; */
  border-top: 0.5px solid #b1d8cd;
  position: relative;
  background-color: #ffffff; /* 방 항목 배경색 추가 */
  transition: background-color 0.3s ease; /* 배경색 변경 효과 추가 */
}

.room-title {
  width: 100%;
  min-height: 100%; /* 높이를 100%로 설정 */
  display: flex; /* 내부 텍스트를 중앙에 배치하기 위한 flex 설정 */
  align-items: center;
  padding-left: 5px;
  z-index: 1;
  font-size: 15px;
  font-weight: bold;
  text-align: center;
}

.room-description {
  width: 100%;
  height: 100%; /* 높이를 100%로 설정 */
  position: absolute;
  top: 0; /* 상단으로부터의 위치를 0으로 설정 */
  left: 0;
  background-color: #34a080;
  color: white;
  opacity: 0;
  transition: opacity 0.3s ease;
  display: flex; /* 내부 텍스트를 중앙에 배치하기 위한 flex 설정 */
  justify-content: center;
  align-items: center;
  padding: 10px; /* 텍스트와 테두리 사이에 간격을 주기 위한 패딩 설정 */
}

.room-item:hover .room-description {
  opacity: 1;
}

.room-item:hover {
  background-color: rgba(0, 0, 0, 0); /* 마우스를 올리면 방 항목 배경색을 투명하게 */
}
/* 스크롤 디자인 */
.room-list::-webkit-scrollbar {
  width: 10px;
}
.room-list::-webkit-scrollbar-track {
  background: #f1f1f1;
}
.room-list::-webkit-scrollbar-thumb {
  background: #888;
}
.room-list::-webkit-scrollbar-thumb:hover {
  background: #555;
}
</style>
