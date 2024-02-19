<template>
  <div class="chat-component">
    <div id="app" style="margin: 0; padding: 0; height: 476px">
      <div style="display: flex; align-items: center; background-color: #edebeb; padding: 0; margin: 0">
        <v-text-field
          v-if="!selectedRoom"
          v-model="roomname"
          variant="outlined"
          rounded
          density="compact"
          placeholder="채팅방을 검색하세요."
          append-inner-icon="mdi-magnify"
          @click:append="nickNameSearch"
          hide-details
          style="border: none; padding: 3px"
          bg-color="white"
        >
          <template v-slot:append>
            <!-- 텍스트 필드 내에 버튼 추가 -->
            <v-btn
              v-if="!selectedRoom"
              density="compact"
              icon="mdi-plus"
              @click="dialog = true"
              class="custom-icon-btn"
              style="padding: 0; margin: 0"
            ></v-btn>
          </template>
        </v-text-field>
      </div>

      <my-room-list
        v-if="!selectedRoom"
        :rooms="roomList"
        @selectRoom="selectRoom"
        @deleteRoom="handleDeleteRoom"
      ></my-room-list>
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
    const fetchRooms = async () => {
      try {
        const rooms = await chatStore.fetchMyRoomList();
        if (!Array.isArray(rooms)) {
        } else {
          roomList.value = rooms;
        }
      } catch (error) {}
    };
    onMounted(fetchRooms);
    // 방 목록을 불러오는 메서드를 이벤트 버스에 등록합니다.
    // EventBus.$emit('registerFetchRooms', fetchRooms);
    const goBack = () => {
      selectedRoom.value = null;
    };
    const handleDeleteRoom = (room) => {
      const index = roomList.value.findIndex((r) => r.id === room.id);
      if (index > -1) {
        roomList.value.splice(index, 1);
      }
    };
    const selectRoom = async (room) => {
      selectedRoom.value = room;
    };

    return {
      dialog,
      roomList,
      selectedRoom,
      messages,
      goBack,
      selectRoom,
      handleDeleteRoom,
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
