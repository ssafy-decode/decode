<template>
  <v-dialog
    v-model="localDialog"
    persistent
    max-width="500px"
    transition="dialog-bottom-transition"
    :color="'transparent'"
    :retain-focus="false"
    :overlay="true"
    style="z-index: 9999"
  >
    <v-card class="pa-5">
      <v-card-title class="headline d-flex align-center">
        <span class="subtitle-1 font-weight-bold">방 만들기</span>
        <v-spacer></v-spacer>
        <v-btn icon @click="dialog = false">
          <v-icon small>mdi-close</v-icon>
        </v-btn>
      </v-card-title>
      <v-card-text>
        <v-theme-provider theme="dark" class="pa-10">
          <v-card outlined class="mb-5 guide-card">
            <v-card-title class="guide-text guide-title">채팅방 가이드라인</v-card-title>
            <v-card-text class="guide-text">
              · 3일 동안 한 계정으로 채팅방 1개를 만들 수 있습니다. <br />
              · 채팅방 이름 및 설명에 채팅방 성격을 잘 표현해주세요. <br />
              · 채팅방 삭제는 문의를 통해 진행해주세요.
            </v-card-text>
          </v-card>
        </v-theme-provider>
        <v-text-field
          filled
          dense
          v-model="newRoom.roomName"
          label="제목"
          :rules="[(v) => !!v || '제목을 입력해주세요.']"
          required
        ></v-text-field>

        <v-textarea
          filled
          dense
          v-model="newRoom.roomDescription"
          label="설명"
          :rules="[(v) => !!v || '설명을 입력해주세요.']"
          required
        ></v-textarea>
      </v-card-text>
      <v-card-actions class="justify-end">
        <v-btn text color="#34A080" dark @click="createRoom">만들기</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>
<script>
import { ref, watch, toRefs } from 'vue';
import { useStompStore } from '@/utils/StompUtil';
import { useChatStore } from '@/stores/chatStore.js';
import { storeToRefs } from 'pinia';
import { useUserStore } from '@/stores/userStore';
export default {
  name: 'CreateRoomDialog',
  props: {
    dialog: Boolean,
  },
  setup(props, { emit }) {
    const stompStore = useStompStore();
    const { dialog } = toRefs(props);
    const localDialog = ref(false);
    const chatStore = useChatStore();
    const userStore = useUserStore();
    const { handleMyprofile: myProfile, handleLoginUserId: loginUserId } = storeToRefs(userStore);
    const newRoom = ref({
      id: 0,
      roomName: '',
      roomDescription: '',
      creator: 0,
    });

    watch(dialog, (val) => {
      localDialog.value = val;
    });

    watch(localDialog, (val) => {
      if (!val) {
        emit('close');
      }
    });

    const createRoom = async () => {
      if (!newRoom.value.roomName || !newRoom.value.roomDescription) {
        return;
      }
      // stompUtil.subscribeRoom()
      const roomId = await chatStore.createChatRoom(
        newRoom.value.roomName,
        newRoom.value.roomDescription,
        loginUserId.value,
      );
      newRoom.value.id = roomId;
      stompStore.subscribeRoom(roomId);

      emit('create-room', newRoom.value);
      newRoom.value.roomName = '';
      newRoom.value.roomDescription = '';
      localDialog.value = false;
    };

    return {
      localDialog,
      newRoom,
      createRoom,
    };
  },
};
</script>
<style scoped>
.guide-card {
  background-color: rgba(52, 160, 128, 0.1);
}

.guide-text {
  color: rgba(0, 0, 0, 0.6);
  font-size: 0.8rem;
}
.guide-title {
  font-size: 1rem;
  padding-bottom: 0%;
}
</style>
