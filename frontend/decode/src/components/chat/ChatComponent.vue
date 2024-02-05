<template>
  <div class="chat-component">
    <CreateRoomDialog :dialog="dialog" @close="dialog = false" @create-room="addRoom"/>
    
    <CreateRoomButton @open="dialog = true"/>

    <div id="app">
        <v-text-field v-if="!selectedRoom"
          v-model="nickname"
          placeholder="채팅방을 검색하세요."
          append-inner-icon="mdi-magnify"
          @click:append="nickNameSearch"
          variant="underlined"
      >

      <template v-slot:append> <!-- 텍스트 필드 내에 버튼 추가 -->
          <v-btn v-if="!selectedRoom" density="compact" icon="mdi-plus" @click="dialog = true" class="custom-icon-btn"></v-btn> 
      </template>
      </v-text-field>

      <room-list v-if="!selectedRoom" :rooms="roomList" @selectRoom="selectRoom"></room-list>
      <chat-room v-else :room="selectedRoom" @goBack="goBack"></chat-room>
  </div>
  </div>
</template>

<script>
import CreateRoomDialog from './CreateRoomDialog.vue';
import RoomList from './RoomList.vue';
import ChatRoom from './ChatRoom.vue'
export default {
  name: 'App',
  components: {
    CreateRoomDialog,
    RoomList,
    ChatRoom
  },
  data() {
    return {
      dialog: false,
      roomList: [],  // 방 리스트
      selectedRoom: null,
    };
  },
  methods: {
    goBack() {
      this.selectedRoom = null;
    },
    addRoom(room) {
      this.roomList.push({ id: room.title, name: room.description });
    },
    selectRoom(room) {
      console.log("selected room", room)
      this.selectedRoom = room;
    }
  },
};
</script>

<style scoped>

.custom-icon-btn {
  margin-top: -5px;
  margin-left: -5px; /* 원하는 만큼 왼쪽으로 이동 */
  font-size: 18px; /* 아이콘 크기 조정 */
  color: #34A080; /* 아이콘 버튼의 색상 */
  /* 다른 스타일 속성들... */
}

.chat-component {
  display: flex;
  flex-direction: column;
  height: 430px;
}
</style>
