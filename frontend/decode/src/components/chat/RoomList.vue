<template>
  <div class="room-list">
    <div 
      v-for="room in rooms" 
      :key="room.id" 
      class="room-item" 
      @click="selectRoom(room)"
    >
      <div class="room-title">{{ room.roomName }}</div>
      <div class="room-creator">{{ room.creator }}</div>
      <div class="room-description">{{ room.roomDescription }}</div>
    </div>
  </div>
</template>

<script>
import { ref } from 'vue';
import {useStompStore} from '@/utils/StompUtil';
export default {
  name: 'RoomList',
  props: {
    rooms: Array,
  },
  setup(props, context) {
    const stompStore =  useStompStore();
    const selectRoom = (room) => {
      console.log("selected room", room.id)
      stompStore.subscribeRoom(room.id);

      context.emit('selectRoom', room);
    }

    return { selectRoom };
  }
};
</script>


<style scoped>
.room-list {
  display: flex;
  flex-direction: column;
  max-height: 300px;
  overflow-y: auto;
}

.room-item {
  width: 90%;
  min-height: 60px;  /* 최소 높이를 설정합니다. */
  box-sizing: border-box;
  border: 1px solid #34A080;
  margin: 10px;
  position: relative;
  padding: 10px;
}

.room-title {
  z-index: 1;
}

.room-description {
  width: 100%;
  position: absolute;
  top: 0;
  left: 0;
  background-color: rgba(0, 0, 0, 0.6);
  color: black;
  transform: translateY(100%);
  transition: transform 0.3s ease;
}

.room-item:hover .room-description {
  transform: translateY(0);
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
