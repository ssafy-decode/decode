<template>
  <vue-draggable-resizable
    class-name-resizable="my-resizable-class"
    class="custom-component"
    v-if="isComponentVisible"
    :x="0"
    :y="0"
    :z="10000"
    :w="1080"
    :h="640"
    :parent="false"
    :resizable="false"
  >
    <div class="custom-openvidu-modal">
      <div class="upper-container">
        <div class="screen-container">
          <!-- 여기에 화면공유 관련 코드를 넣으세요 -->
          <video ref="videoElement" class="video-element" autoplay playsinline></video>
        </div>

        <div class="chat-container">
          <div class="chat-messages" ref="chatContainer">
            <div v-for="(message, index) in messages" :key="index" :class="messageClass(message)">
              <div class="message-content">
                <div class="nickname">{{ message.username }}</div>
                <p>{{ message.message }}</p>
              </div>
            </div>
          </div>
          <v-text-field
            class="input-field"
            filled
            hide-details
            density="compact"
            variant="outlined"
            v-model="inputMessage"
            placeholder="전달할 내용을 입력하세요."
            @keyup.enter="sendMessage"
            append-icon=""
          >
            <!-- <template v-slot:append>
              <v-btn density="compact" flat :round="false" style="min-width: 24px" @click="sendMessage">
                <v-icon color="#34A080">mdi-send</v-icon>
              </v-btn>
            </template> -->
          </v-text-field>
        </div>
      </div>

      <!-- 비디오 아래에 버튼 3개 추가 -->
      <div class="button-container">
        <v-btn color="#34A080" @click="toggleAudio" title="음소거/해제" v-if="isPublisher">
          <font-awesome-icon v-if="isMuted" :icon="['fas', 'microphone']" />
          <!-- 음소거가 아닐 때 -->
          <font-awesome-icon v-else :icon="['fas', 'microphone-slash']" />
          <!-- 음소거일 때 -->
        </v-btn>
        <v-btn color="#34A080" @click="toggleScreenSharing" title="화면 공유" v-if="isPublisher">
          <font-awesome-icon :icon="['fas', 'desktop']" />
        </v-btn>
        <v-btn color="#34A080" @click="toggleFullScreen" title="전체 화면">
          <font-awesome-icon :icon="['fas', 'expand']" />
        </v-btn>
        <v-btn color="#34A080" @click="exitPage" title="나가기">
          <font-awesome-icon :icon="['fas', 'door-open']" />
        </v-btn>
      </div>
    </div>
  </vue-draggable-resizable>
</template>

<script>
import VueDraggableResizable from 'vue-draggable-resizable';
import 'vue-draggable-resizable/style.css';
import { storeToRefs } from 'pinia';
import OpenviduTest from '@/components/openvidu/OpenviduTest.vue';
import { ref, onMounted, onBeforeUnmount, computed, watchEffect, nextTick, watch } from 'vue';
import { useSessionStore } from '@/stores/sessionStore';
import { useMessageStore } from '@/stores/messageStore';
import { useUserStore } from '@/stores/userStore';

export default {
  components: {
    VueDraggableResizable,
    OpenviduTest,
  },
  props: ['roomSessionId', 'subscribers'],

  setup(props, context) {
    const inputMessage = ref('');
    const isComponentVisible = ref(true);
    const userStore = useUserStore();
    const sessionStore = useSessionStore();
    const messageStore = useMessageStore();
    const messages = computed(() => messageStore.messages[props.roomSessionId] || []);
    const session = ref(null);
    const username = ref(null);
    const OV = ref(null);
    const publisher = ref(null);
    const subscriber = ref(null);
    const chatContainer = ref(null);
    const isMuted = ref(false);
    const isScreenSharing = ref(true);
    const { handleMyprofile: myProfile } = storeToRefs(userStore);

    const videoElement = ref(null); // videoElement를 선언합니다.
    const isPublisher = ref(false);

    watch(
      () => publisher.value,
      (newPublisher) => {
        isPublisher.value = newPublisher != null;
      },
    );
    const sendMessage = (event) => {
      event.preventDefault();
      if (inputMessage.value.trim()) {
        session.value.signal({
          data: JSON.stringify({
            username: username.value,
            message: inputMessage.value,
          }),
          type: 'chat',
        });
        inputMessage.value = '';
        nextTick(() => {
          setTimeout(() => {
            const container = chatContainer.value;
            container.scrollTop = container.scrollHeight;
          }, 100);
        });
      }
    };

    const toggleAudio = () => {
      if (publisher.value) {
        isMuted.value = !isMuted.value; // 음소거 상태를 반전시킵니다.
        const isAudioActive = publisher.value.stream.audioActive;
        publisher.value.publishAudio(!isAudioActive); // 오디오 상태를 반전시킵니다.
      }
    };
    // 비디오 엘리먼트를 업데이트하는 함수를 정의합니다.
    const updateVideoElement = () => {
      if (subscriber.value && subscriber.value.stream) {
        videoElement.value.srcObject = subscriber.value.stream.mediaStream;
      } else if (publisher.value && publisher.value.stream) {
        videoElement.value.srcObject = publisher.value.stream.mediaStream;
      } else {
      }
    };
    onMounted(async () => {
      const rsId = props.roomSessionId;
      session.value = await sessionStore.getSessionById(rsId)['session'];
      subscriber.value = await sessionStore.getSubscriberById(rsId);

      username.value = await sessionStore.getMyUserNameById(rsId);
      OV.value = await sessionStore.getOvById(rsId);
      publisher.value = await sessionStore.getPublisherById(rsId);
      // 2초 뒤에 비디오 엘리먼트를 업데이트합니다.
      setTimeout(updateVideoElement, 2000);
    });
    // publisher와 subscriber의 변화를 감지하고, 변화가 감지되면 비디오 엘리먼트를 업데이트합니다.

    onBeforeUnmount(() => {
      if (videoElement.value) {
        videoElement.value.srcObject = null;
      }
      // 세션을 종료하고 상태를 업데이트합니다.
      exitPage();
    });
    const toggleFullScreen = () => {
      if (!document.fullscreenElement) {
        if (videoElement.value.requestFullscreen) {
          videoElement.value.requestFullscreen();
        } else if (videoElement.value.mozRequestFullScreen) {
          /* Firefox */
          videoElement.value.mozRequestFullScreen();
        } else if (videoElement.value.webkitRequestFullscreen) {
          /* Chrome, Safari & Opera */
          videoElement.value.webkitRequestFullscreen();
        } else if (videoElement.value.msRequestFullscreen) {
          /* IE/Edge */
          videoElement.value.msRequestFullscreen();
        }
      } else {
        if (document.exitFullscreen) {
          document.exitFullscreen();
        } else if (document.mozCancelFullScreen) {
          /* Firefox */
          document.mozCancelFullScreen();
        } else if (document.webkitExitFullscreen) {
          /* Chrome, Safari & Opera */
          document.webkitExitFullscreen();
        } else if (document.msExitFullscreen) {
          /* IE/Edge */
          document.msExitFullscreen();
        }
      }
    };
    const toggleScreenSharing = () => {
      if (isScreenSharing.value) {
        navigator.mediaDevices.getDisplayMedia({ video: true }).then((stream) => {
          const videoTrack = stream.getVideoTracks()[0];
          publisher.value.replaceTrack(videoTrack);
        });
      } else {
        publisher.value.replaceTrack(publisher.value.stream.videoStream);
      }
      isScreenSharing.value = !isScreenSharing.value;
    };

    const exitPage = () => {
      // 유효성 체크
      if (session.value && session.value.connection && publisher.value && publisher.value.stream) {
        // 세션을 종료하고 상태를 업데이트합니다.
        session.value.disconnect();
        session.value = null;
        publisher.value = null;
        subscriber.value = null;
      } else {
      }

      sessionStore.exitSession(props.roomSessionId);
      // 나가기 이벤트를 발생시킵니다.
      context.emit('exit');
    };

    const messageClass = (message) => {
      return message.username === '나' ? 'my-message' : 'other-message';
    };

    return {
      isPublisher,
      inputMessage,
      messages,
      isComponentVisible,
      sessionStore,
      messageStore,
      session,
      username,
      chatContainer,
      OV,
      isMuted,
      isScreenSharing,
      videoElement,
      exitPage,
      toggleScreenSharing,
      toggleFullScreen,
      sendMessage,
      toggleAudio,
      messageClass,
      // 나머지 메서드도 반환 객체에 포함시켜 주세요
    };
  },
};
</script>

<style scoped>
.custom-component {
  border-radius: 5px;
  margin: 0;
  padding: 5px;
  background-color: #34a080;
  position: fixed;
  left: 10px;
  top: 10px;
  scrollbar-width: none;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}
.custom-component::-webkit-scrollbar {
  display: none; /* Chrome, Safari and Opera */
}
.custom-openvidu-modal {
  padding: 5px;
  border-radius: 3px;
  display: flex;
  flex-direction: column;
  width: 100%;
  height: 100%;
  background-color: #f1f1f1;
}
.upper-container {
  width: 100%;
  height: 90%;
  padding: 0;
  margin: 0;
  display: flex;
  align-items: center;
}
.button-container {
  display: flex;
  width: 100%;
  height: 10%;
  margin: 0;
  padding: 0;
  align-items: center;
  justify-content: center; /* 버튼들을 가운데 정렬 */
  gap: 20px; /* 버튼 사이의 간격을 20px로 설정 */
}
.button-container .v-btn {
  background-color: white; /* 배경색을 흰색으로 설정 */
  border: 2px solid #34a080; /* 테두리를 초록색으로 설정 */
  border-radius: 50%; /* 모양을 동그랗게 설정 */
  color: black; /* 아이콘 색상을 검정색으로 설정 */
}
.video-element {
  padding: 1px;
  width: auto;
  max-width: 840px;
  height: 100%;
  border: solid 0.5px #34a080;
  border-radius: 3px;
}

.screen-container {
  width: 80%;
  max-width: 840px;
  height: 100%;
  margin: 1px;
  background-color: black;
  border-radius: 3px;
}

.chat-container {
  padding: 0;
  margin: 0;
  width: 20%;
  height: 100%; /* 채팅창 컨테이너의 높이를 100%로 조정 */
  background-color: white;
  display: flex;
  flex-direction: column;
  justify-content: flex-end; /* 입력 구간을 아래로 */
  border-radius: 5px;
}

.chat-messages {
  flex: 1;
  padding: 1px;
  overflow-y: auto;
  height: 90%; /* 입력 구간의 높이를 고려하여 채팅창 리스트의 최대 높이를 조정 */
  border: solid 0.5px #ccc;
  background-color: #f1f1f1;
  border-radius: 5px;
  /* margin-bottom: 20px; 입력 구간과의 간격을 주기 위해 margin-bottom 추가 */
}

.input-field {
  flex: none;
  margin: 5px;
  height: 10%; /* 입력 구간의 높이를 증가 */
}
.chat-list {
  width: 100%;
  height: calc(100% - 30px); /* Chat-write의 높이를 제외한 나머지 높이 */
  overflow-y: auto;
}

.chat-write {
  width: 100%;
  height: 50px;
  position: absolute;
  bottom: 0;
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

/* 스크롤바 전체의 너비를 설정 */
.chat-messages::-webkit-scrollbar {
  width: 8px;
}

/* 스크롤바의 배경색을 설정 */
.chat-messages::-webkit-scrollbar-track {
  background: #f1f1f1;
}

/* 스크롤바의 색상을 설정 */
.chat-messages::-webkit-scrollbar-thumb {
  background: #888;
}

/* 스크롤바에 마우스를 올렸을 때의 색상을 설정 */
.chat-messages::-webkit-scrollbar-thumb:hover {
  background: #555;
}
</style>
