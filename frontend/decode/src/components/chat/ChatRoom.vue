<template>
  <div class="back-button" @click="goBack">
    <v-icon small color="primary">mdi-close</v-icon>
  </div>

  <div class="chat-container">
    <div class="chat-messages" ref="chatContainer">
      <div v-for="(message, index) in messages" :key="index" :class="messageClass(message)">
        <div class="message-content">
          <div class="nickname">{{ message.user }}</div>
          <p>{{ message.text }}</p>
        </div>
      </div>
    </div>
    <v-text-field
      class="input-field"
      filled
      variant="underlined"
      v-model="newMessage"
      placeholder="메시지를 입력하세요"
      @keyup.enter="sendMessage"
      append-icon=""
    >
      <template v-slot:append>
        <v-btn density="compact" flat :round="false" @click="openDialog" style="min-width: 24px">
          <v-icon color="#34A080">mdi-video-vintage</v-icon>
        </v-btn>
      </template>
    </v-text-field>

    <OpenviduDialog
      v-model="dialog"
      :publisher="publisher"
      :roomSessionId="roomSessionId"
      @yesClicked="handleYes"
      @noClicked="handleNo"
    />
    <OpenviduModal v-if="showOpenviduModal" :publisher="publisher" :roomSessionId="roomSessionId"></OpenviduModal>
  </div>
</template>
<script>
import OpenviduDialog from './OpenviduDialog.vue';
import OpenviduModal from '@/components/chat/OpenviduModal.vue';
export default {
  components: {
    OpenviduDialog,
    OpenviduModal,
  },
  props: {
    room: Object,
  },
  data() {
    return {
      rommSessionId: '',
      showOpenviduModal: false,
      newMessage: '',
      publisher: null, // screenSharingPublisher를 저장할 데이터
      dialog: false, // 모달 보이고/안 보이고를 제어하는 데이터
      messages: [
        { user: '나', text: '안녕하세요!' },
        { user: 'bot', text: '안녕하세요, 어떻게 도와드릴까요?' },
        { user: '132', text: '안녕하세요, 어떻게 도와드릴까요?' },
      ],
    };
  },
  mounted() {
    this.$nextTick(() => {
      this.$refs.chatContainer.scrollTop = this.$refs.chatContainer.scrollHeight;
    });
  },
  methods: {
    // OpenviduDialog 사용할 메소드들
    openDialog() {
      console.log('click');
      this.dialog = true;
    },
    handleYes(screenSharingPublisher, roomSessionId) {
      console.log('Yes 버튼 클릭');
      this.publisher = screenSharingPublisher; // screenSharingPublisher 저장
      this.roomSessionId = roomSessionId;
      console.log('chat room ', this.publisher);
      this.showOpenviduModal = true;
      this.dialog = false;
    },
    handleNo() {
      console.log('No 버튼 클릭');
      this.dialog = false;
    },
    goBack() {
      this.$emit('goBack');
    },
    sendMessage() {
      if (this.newMessage !== '') {
        this.messages.push({ user: '나', text: this.newMessage });
        this.newMessage = '';
        this.$nextTick(() => {
          this.$refs.chatContainer.scrollTop = this.$refs.chatContainer.scrollHeight;
        });
      }
    },
    messageClass(message) {
      return message.user === '나' ? 'my-message' : 'other-message';
    },
  },
};
</script>

<style scoped>
.back-button {
  position: absolute;
  top: 0;
  right: 25px;
  cursor: pointer;
  z-index: 1;
}
.chat-container {
  max-width: 100%;
  margin: 0;
  padding: 0;
  height: 400px;
  position: relative;
}

.chat-messages {
  max-width: 100%;
  height: calc(400px - 40px); /* 입력 영역 높이를 제외한 높이 */
  overflow-y: auto;
  padding: 1px;
  position: relative;
}
.input-area {
  width: 100%;
  position: absolute;
  bottom: 0; /* 바닥에 붙입니다 */
  padding: 0 10px; /* 좌우 패딩을 추가하여 필드가 너무 가까이 붙지 않게 합니다 */
}

.input-field {
  margin-top: 10px; /* 입력 필드 위로 마진을 추가했습니다 */
  padding-top: 0;
  height: 30px; /* 높이를 조금 더 늘립니다 */
  font-size: 14px; /* 폰트 크기를 조금 더 늘립니다 */
}
/* 스크롤 디자인 */
.chat-messages::-webkit-scrollbar {
  width: 10px;
}
.chat-messages::-webkit-scrollbar-track {
  background: #f1f1f1;
}
.chat-messages::-webkit-scrollbar-thumb {
  background: #888;
}
.chat-messages::-webkit-scrollbar-thumb:hover {
  background: #555;
}

/* 메시지 디자인 */
.my-message .message-content,
.other-message .message-content {
  margin-top: 10px;
  margin-bottom: 0;
}

.my-message .message-content {
  text-align: right;
}

.my-message p,
.other-message p {
  font-size: 12px;
  display: inline-block;
  background-color: #aed581;
  padding: 10px;
  border-radius: 10px;
  word-break: break-word; /* 줄바꿈을 추가합니다 */
}

.other-message .message-content {
  text-align: left;
}

.other-message p {
  display: inline-block;
  background-color: #cfd8dc;
  padding: 10px;
  border-radius: 10px;
}

.nickname {
  font-weight: bold;
}
</style>
