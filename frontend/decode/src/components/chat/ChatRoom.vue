<template>
  <div class="header-container">
    <v-row style="padding: 0; margin: 0; height: 100%">
      <v-col cols="8" style="padding: 0; margin: 0; height: 100%">
        <div class="room-title">
          {{ room.roomName }}
        </div>
      </v-col>
      <v-col cols="2" style="padding: 0; margin: 0; height: 100%">
        <div class="etc-button" @click="openDialog">
          <v-icon color="#34A080">mdi-video-vintage</v-icon>
        </div>
      </v-col>
      <v-col cols="2" style="padding: 0; margin: 0; height: 100%">
        <div class="etc-button" @click="goBack">
          <v-icon small color="#ccc">mdi-close</v-icon>
        </div>
      </v-col>
    </v-row>
  </div>

  <div class="chat-container">
    <div class="chat-messages" ref="chatContainer">
      <div v-for="(message, id) in messages" :key="id" :class="messageClass(message)">
        <div class="message-content">
          <div class="nickname">{{ message.nickName }}</div>
          <p v-html="message.text"></p>
        </div>
      </div>
    </div>
    <div>
      <v-text-field
        class="input-field"
        filled
        variant="underlined"
        v-model="newMessage"
        placeholder="메시지를 입력하세요"
        @keyup.enter="sendMessage"
        append-icon=""
      >
      </v-text-field>
    </div>

    <OpenviduDialog
      v-model="dialog"
      :publisher="publisher"
      :roomSessionId="roomSessionId"
      @yesClicked="handleYes"
      @noClicked="handleNo"
    />
    <OpenviduModal
      v-if="showOpenviduModal"
      :publisher="publisher"
      :roomSessionId="rsId"
      :subscribers="subscribers"
      @exit="handleExit"
    ></OpenviduModal>
  </div>
  <v-dialog v-model="errorDialog" max-width="400">
    <v-card>
      <v-card-title style="color: #34a080">Error</v-card-title>
      <v-card-text>세션이 만료됐어요. 다음번엔 빨리 오세요 :></v-card-text>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn color="primary" text @click="errorDialog = false">Okay</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>
<script>
import { ref, onMounted, nextTick, watchEffect, onUnmounted, onUpdated } from 'vue';
import OpenviduDialog from './OpenviduDialog.vue';
import OpenviduModal from '@/components/chat/OpenviduModal.vue';
import { useChatStore } from '@/stores/chatStore.js';
import { useStompStore } from '@/utils/StompUtil';
import { storeToRefs } from 'pinia';
import { useUserStore } from '@/stores/userStore';
import { useSessionStore } from '@/stores/sessionStore';
import { useMessageStore } from '@/stores/messageStore';
import { OpenVidu } from 'openvidu-browser';
import axios from 'axios';
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
    const APPLICATION_SERVER_URL = 'https://i10a507.p.ssafy.io/decode/openvidu';
    // const APPLICATION_SERVER_URL = 'http://localhost:7777/decode/openvidu';
    const errorDialog = ref(false);
    const errorMessage = ref('세션이 만료됐어요. 다음번엔 빨리 오세요 :>');
    const OV = ref(undefined);
    const session = ref(undefined);
    const publisher = ref(undefined);
    const subscribers = ref([]);
    const userStore = useUserStore();
    const chatStore = useChatStore();
    const rsId = ref('');
    const showOpenviduModal = ref(false);
    const newMessage = ref('');
    const dialog = ref(false);
    const messages = ref([]);
    const chatContainer = ref(null);
    const stompStore = useStompStore();
    const sessionStore = useSessionStore();
    const messageStore = useMessageStore();
    const { handleMyprofile: myProfile, handleLoginUserId: loginUserId } = storeToRefs(userStore);
    const myUserName = myProfile.value.nickname;
    watchEffect(() => {
      if (stompStore.messages[props.room.id]) {
        const newMessage = stompStore.messages[props.room.id][stompStore.messages[props.room.id].length - 1];
        const lastMessage = messages.value[messages.value.length - 1];
        if (newMessage && (!lastMessage || newMessage.id !== lastMessage.id)) {
          messages.value.push(newMessage);
          nextTick(() => {
            chatContainer.value.scrollTop = chatContainer.value.scrollHeight;

            // 새로운 메시지에 이벤트 리스너 추가
            const messageContainers = chatContainer.value.querySelectorAll('.message-content');
            const lastMessageContainer = messageContainers[messageContainers.length - 1];
            const button = lastMessageContainer.querySelector('.join-session');
            if (button) {
              button.addEventListener('click', () => {
                const sessionElement = lastMessageContainer.querySelector('.session-id');
                if (sessionElement) {
                  const sessionId = String(sessionElement.textContent);

                  if (sessionId) {
                    joinSession(sessionId);
                  }
                }
              });
            }
          });
        }
      }
    });
    onUpdated(() => {
      const messageContainers = document.querySelectorAll('.message-content');
      messageContainers.forEach((messageContainer) => {
        const button = messageContainer.querySelector('.join-session');
        if (button) {
          button.addEventListener('click', () => {
            const sessionElement = messageContainer.querySelector('.session-id');
            if (sessionElement) {
              const sessionId = String(sessionElement.textContent);
              if (sessionId) {
                joinSession(sessionId);
              }
            }
          });
        }
      });
    });
    const joinSession = async (sessionId) => {
      OV.value = new OpenVidu();
      session.value = OV.value.initSession();
      sessionStore.setOv(sessionId, OV.value);

      session.value.on('streamCreated', ({ stream }) => {
        const subscriber = session.value.subscribe(stream);
        sessionStore.setSubscriber(sessionId, subscriber);
        subscribers.value.push(subscriber);
      });

      session.value.on('streamDestroyed', ({ stream }) => {
        const index = subscribers.value.indexOf(stream.streamManager, 0);
        if (index >= 0) {
          subscribers.value.splice(index, 1);
        }
      });
      session.value.on('signal:chat', (event) => {
        const messageData = JSON.parse(event.data);
        if (event.from.connectionId === session.value.connection.connectionId) {
          messageData['username'] = '나';
        }
        messageStore.addMessage(sessionId, messageData);
      });

      session.value.on('exception', ({ exception }) => {});

      try {
        const token = await getToken(sessionId);
        await session.value.connect(token, { clientData: myUserName });

        // 세션 정보와 유저 이름을 store에 저장
        sessionStore.setSession(sessionId, session.value);
        sessionStore.setMyUserName(sessionId, myUserName);
        rsId.value = sessionId;

        showOpenviduModal.value = true;
      } catch (error) {}
    };
    const getToken = async (sessionId) => {
      return await createToken(sessionId);
    };

    const createToken = async (sessionId) => {
      try {
        const response = await axios.post(
          APPLICATION_SERVER_URL + `/api/sessions/${sessionId}/connections`,
          {},
          {
            headers: {
              Authorization: `Bearer ${userStore.accessToken}`,
            },
          },
        );
        return response.data;
      } catch (error) {
        // HTTP 상태 코드가 500인 경우 alert 띄우기
        if (error.response && error.response.status === 500) {
          errorMessage.value = '세션이 만료되었습니다. 다음번엔 빨리오세요~';
          errorDialog.value = true;
        }
        throw error;
      }
    };

    onMounted(async () => {
      try {
        const roomId = props.room.id;
        const chatHistory = await chatStore.fetchChatHistory(roomId);
        if (!Array.isArray(chatHistory)) {
        } else {
          messages.value = chatHistory;
        }
      } catch (error) {}

      nextTick(() => {
        chatContainer.value.scrollTop = chatContainer.value.scrollHeight;
      });
    });
    onUnmounted(() => {
      // 구독 취소
      if (stompStore.subscriptions[props.room.id]) {
        stompStore.subscriptions[props.room.id].unsubscribe();
      }
      if (session.value) {
        sessionStore.exitSession(rsId.value);
      }
    });
    const openDialog = () => {
      dialog.value = true;
    };

    const handleYes = (screenSharingPublisher, roomSessionId) => {
      publisher.value = screenSharingPublisher;
      rsId.value = roomSessionId;
      showOpenviduModal.value = true;
      dialog.value = false;
      // 여기서 버튼을 보내면 될 듯
      stompStore.sendMessage(
        loginUserId.value,
        myProfile.value.nickname,
        `화면 공유방에 참여하시겠습니까? <span class="session-id" hidden>${String(roomSessionId.value)}</span>
        <button class="join-session" style="color: blue">참가하기</button>`,
        props.room.id,
      );

      nextTick(() => {
        chatContainer.value.scrollTop = chatContainer.value.scrollHeight;
      });
    };
    const handleNo = () => {
      dialog.value = false;
    };
    const handleExit = () => {
      showOpenviduModal.value = false;
      // 여기에 나머지 처리를 추가 가능
    };
    const goBack = () => {
      if (stompStore[props.room.id]) {
        stompStore.subscriptions[props.room.id].unsubscribe();
      }
      emit('goBack');
    };

    const sendMessage = () => {
      if (newMessage.value !== '') {
        // messages.value.push({ nickName: '나', text: newMessage.value });
        stompStore.sendMessage(loginUserId.value, myProfile.value.nickname, newMessage.value, props.room.id);
        newMessage.value = '';
        nextTick(() => {
          chatContainer.value.scrollTop = chatContainer.value.scrollHeight;
        });
      }
    };

    const messageClass = (message) => {
      return message.nickName === myProfile.value.nickname ? 'my-message' : 'other-message';
    };

    return {
      rsId,
      showOpenviduModal,
      newMessage,
      publisher,
      dialog,
      errorDialog,
      errorMessage,
      messages,
      openDialog,
      handleYes,
      handleNo,
      handleExit,
      goBack,
      sendMessage,
      messageClass,
      joinSession,
      chatContainer,
    };
  },
};
</script>

<style scoped>
.header-container {
  border: solid 0.5px #ccc;
  width: 100%;
  height: 40px;
  padding: 0;
  margin: 0;
}
.room-title {
  height: 100%;
  margin: 0;
  margin-left: 5px;
  padding: 0;
  display: flex;
  align-items: center;
}
.etc-button {
  height: 100%;
  border: solid 0.5px #ccc;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
}
.chat-container {
  background-color: #f5f5f5;
  max-width: 100%;
  margin: 0;
  padding: 0;
  height: 330px;
}

.chat-messages {
  max-width: 100%;
  height: 330px; /* 입력 영역 높이를 제외한 높이 */
  overflow-y: auto;
  padding: 1px;
}
.input-area {
  width: 100%;
  position: absolute;
  bottom: 0; /* 바닥에 붙입니다 */
  padding: 0 10px; /* 좌우 패딩을 추가하여 필드가 너무 가까이 붙지 않게 합니다 */
}

.input-field {
  margin: 0; /* 입력 필드 위로 마진을 추가했습니다 */
  padding: 0;
  padding-left: 5px;
  width: 80%;
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
  margin: 5px;
}

.my-message .message-content {
  text-align: right;
}

.my-message p,
.other-message p {
  font-size: 12px;
  display: inline-block;
  background-color: #93d5d1;
  padding: 5px;
  border-radius: 5px;
  max-width: 65%;
  word-break: break-word; /* 줄바꿈을 추가합니다 */
}

.other-message .message-content {
  text-align: left;
}

.other-message p {
  display: inline-block;
  background-color: #d3eeec;
  padding: 5px;
  border-radius: 5px;
}

.nickname {
  font-size: 13px;
}
</style>
