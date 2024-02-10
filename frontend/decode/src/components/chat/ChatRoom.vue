<template>
  <div class="back-button" @click="goBack">
    <v-icon small color="primary">mdi-close</v-icon>
  </div>

  <div class="chat-container">
    <div class="chat-messages" ref="chatContainer">
      <div v-for="(message, id) in messages" :key="id" :class="messageClass(message)">
        <div class="message-content">
          <div class="nickname">{{ message.nickName }}</div>
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
import { ref, onMounted, nextTick, watchEffect, onUnmounted } from 'vue';
import OpenviduDialog from './OpenviduDialog.vue';
import OpenviduModal from '@/components/chat/OpenviduModal.vue';
import { useChatStore } from '@/stores/chatStore.js';
import { useStompStore } from '@/utils/StompUtil';

export default {
  name: 'App',
  components: {
    OpenviduDialog,
    OpenviduModal,
  },
  props: {
    room: Object,
  },
  setup(props, { emit }) {
    const chatStore = useChatStore();
    const rommSessionId = ref('');
    const showOpenviduModal = ref(false);
    const newMessage = ref('');
    const publisher = ref(null);
    const dialog = ref(false);
    const messages = ref([]);
    const chatContainer = ref(null);
    const stompStore = useStompStore();
    watchEffect(() => {
      if (stompStore.messages[props.room.id]) {
        const newMessage = stompStore.messages[props.room.id][stompStore.messages[props.room.id].length - 1];
        if (newMessage) {
          messages.value.push(newMessage);
        }
      }
    });
    onMounted(async () => {
      try {
        const roomId = props.room.id;
        const chatHistory = await chatStore.fetchChatHistory(roomId);
        console.log(chatHistory, ' 반환됨.');
        if (!Array.isArray(chatHistory)) {
          console.error('빈 배열 반환:', chatHistory);
        } else {
          messages.value = chatHistory;
        }
      } catch (error) {
        console.error('fetchChatHistory API 호출 중 오류', error);
      }

      nextTick(() => {
        chatContainer.value.scrollTop = chatContainer.value.scrollHeight;
      });
    });
    onUnmounted(() => {
      // 구독 취소
      if (stompStore[props.room.id]) {
        stompStore.subscriptions[props.room.id].unsubscribe();
      }
    });
    const openDialog = () => {
      console.log('click');
      dialog.value = true;
    };

    const handleYes = (screenSharingPublisher, roomSessionId) => {
      console.log('Yes 버튼 클릭');
      publisher.value = screenSharingPublisher;
      rommSessionId.value = roomSessionId;
      console.log('chat room ', publisher.value);
      showOpenviduModal.value = true;
      dialog.value = false;
    };

    const handleNo = () => {
      console.log('No 버튼 클릭');
      dialog.value = false;
    };

    const goBack = () => {
      stompStore.subscriptions[props.room.id].unsubscribe();
      emit('goBack');
    };

    const sendMessage = () => {
      if (newMessage.value !== '') {
        messages.value.push({ nickName: '나', text: newMessage.value });
        stompStore.sendMessage(13, '제제제', newMessage.value, props.room.id);
        newMessage.value = '';
        nextTick(() => {
          chatContainer.value.scrollTop = chatContainer.value.scrollHeight;
        });
      }
    };

    const messageClass = (message) => {
      return message.nickName === '나' ? 'my-message' : 'other-message';
    };

    return {
      rommSessionId,
      showOpenviduModal,
      newMessage,
      publisher,
      dialog,
      messages,
      openDialog,
      handleYes,
      handleNo,
      goBack,
      sendMessage,
      messageClass,
      chatContainer,
    };
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
