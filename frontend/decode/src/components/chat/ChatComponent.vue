<template>
  <div class="chat-component">
    <CreateRoomDialog :dialog="dialog" @close="dialog = false" @create-room="addRoom" />

    <CreateRoomButton @open="dialog = true" />

    <div id="app" style="margin: 0; padding: 0; height: 430px">
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

      <room-list v-if="!selectedRoom" :rooms="roomList" @selectRoom="selectRoom"></room-list>
      <chat-room v-else :room="selectedRoom" :messages="messages" @goBack="goBack"></chat-room>
    </div>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue';
import CreateRoomDialog from './CreateRoomDialog.vue';
import RoomList from './RoomList.vue';
import ChatRoom from './ChatRoom.vue';
import { useChatStore } from '@/stores/chatStore.js';
export default {
  name: 'App',

  components: {
    CreateRoomDialog,
    RoomList,
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
        } else {
          roomList.value = rooms;
        }
      } catch (error) {}
    });

    const goBack = () => {
      selectedRoom.value = null;
    };

    const addRoom = (room) => {
      roomList.value.push({ id: room.id, roomName: room.roomName, roomDescription: room.roomDescription });
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
      addRoom,
      selectRoom,
    };
  },
};
</script>

<style scoped>
.custom-icon-btn {
  margin-top: -5px;
  margin-left: -5px; /* 원하는 만큼 왼쪽으로 이동 */
  font-size: 18px; /* 아이콘 크기 조정 */
  color: #34a080; /* 아이콘 버튼의 색상 */
}

.chat-component {
  display: flex;
  flex-direction: column;
  height: 430px;
}
</style>
