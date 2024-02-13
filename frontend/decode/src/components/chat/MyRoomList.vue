<template>
  <div class="room-list">
    <div v-for="room in rooms" :key="room.id" class="room-item">
      <div class="room-content" @click="selectRoom(room)">
        <div class="room-title">{{ room.roomName }}</div>
        <div class="room-description">{{ room.roomDescription }}</div>
      </div>
      <button class="delete-button" @click.stop="deleteRoom(room)">삭제</button>
    </div>
  </div>
</template>

<script>
import { ref } from 'vue';
import { useStompStore } from '@/utils/StompUtil';
export default {
  name: 'RoomList',
  props: {
    rooms: Array,
  },
  setup(props, context) {
    const stompStore = useStompStore();
    const selectRoom = (room) => {
      stompStore.subscribeRoom(room.id);
      context.emit('selectRoom', room);
    };

    const deleteRoom = (room) => {
      // TODO: DB에서 해당 방을 삭제하는 API 호출
      console.log(`Deleting room: ${room.roomName}`);
    };

    return { selectRoom, deleteRoom };
  },
};
</script>

<style scoped>
.room-list {
  display: flex;
  flex-direction: column;
  max-height: 450px;
  overflow-y: auto;
  background-color: #f5f5f5; /* 방 리스트 전체 배경색 추가 */
}

.room-item {
  width: 90%;
  min-height: 60px;
  box-sizing: border-box;
  border: 1px solid #34a080;
  margin: 10px;
  position: relative;
  padding: 10px;
  background-color: #ffffff; /* 방 항목 배경색 추가 */
  transition: background-color 0.3s ease; /* 배경색 변경 효과 추가 */
  display: flex; /* 기존 스타일에 추가 */
  align-items: center; /* 기존 스타일에 추가 */
}
.room-content {
  position: relative;
  flex-grow: 1;
  z-index: 1;
}
.room-content:hover .room-description {
  opacity: 1;
}

.room-content:hover {
  background-color: rgba(0, 0, 0, 0);
}
.room-title {
  z-index: 1;
  font-size: 1.2em;
  font-weight: bold;
  flex-grow: 1; /* 기존 스타일에 추가 */
}

.room-description {
  width: 100%;
  height: 100%; /* 높이를 100%로 설정 */
  position: absolute;
  top: 0; /* 상단으로부터의 위치를 0으로 설정 */
  left: 0;
  background-color: #f1f1f1;
  color: black;
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
.delete-button {
  margin-left: auto; /* 왼쪽에 자동 마진을 주어 오른쪽에 정렬 */
  background-color: #34a080;
  color: white;
  border: none;
  padding: 5px 10px;
  cursor: pointer;
  margin: 0 0 0 auto; /* 기존 스타일에 추가 */
}
</style>
