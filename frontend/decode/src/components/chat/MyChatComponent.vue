<template>
  <div class="chat-component">
    <div id="app">
      <my-room-list v-if="!selectedRoom" :rooms="roomList" @selectRoom="selectRoom" ></my-room-list>
      <chat-room v-else :room="selectedRoom" :messages="messages" @goBack="goBack"></chat-room>
    </div>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue';
import CreateRoomDialog from './CreateRoomDialog.vue';
import MyRoomList from './MyRoomList.vue';
import ChatRoom from './ChatRoom.vue';
import { useChatStore } from '@/stores/chatStore.js';
export default {
  name: 'App',
  // props: {
  //   nickname: String,
  //   userId: Number,  
  // },
  components: {
    CreateRoomDialog,
    MyRoomList,
    ChatRoom,
  },
  setup(props) {
    const dialog = ref(false);
    const roomList = ref([]);
    const selectedRoom = ref(null);
    const chatStore = useChatStore();
    const messages = ref([]); // 채팅 내역을 저장할 반응성 데이터

    onMounted(async () => {
      try {
        const rooms = await chatStore.fetchRoomList();
        if (!Array.isArray(rooms)) {
          console.error('fetchRooms API가 배열을 반환하지 않았습니다:', rooms);
        } else {
          roomList.value = rooms;
        }
      } catch (error) {
        console.error('fetchRooms API 호출 중 오류가 발생했습니다:', error);
      }
    });

    const goBack = () => {
      selectedRoom.value = null;
    };

    const selectRoom = async (room) => {
      console.log('selected room', room.id);
      selectedRoom.value = room;
    };

    return {
      dialog,
      roomList,
      selectedRoom,
      messages,
      goBack,
      selectRoom,
    };
  },
};
</script>

<style scoped>


.chat-component {
  display: flex;
  flex-direction: column;
  height: 430px;
}
</style>
