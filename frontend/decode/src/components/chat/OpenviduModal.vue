<template>
  <vue-draggable-resizable
    class-name-resizable="my-resizable-class"
    class="custom-component"
    v-if="isComponentVisible"
    :x="0"
    :y="0"
    :z="10000"
    :w="1000"
    :h="500"
    :parent="false"
    :resizable="false"
  >
    <div class="custom-openvidu-modal">
      <div class="screen-container">
        <!-- 여기에 화면공유 관련 코드를 넣으세요 -->
        <video ref="videoElement" class="video-element" autoplay playsinline></video>

        <!-- 비디오 아래에 버튼 3개 추가 -->
        <div class="button-container">
          <v-btn color="#34A080" @click="toggleAudio" title="음소거/해제">
            <font-awesome-icon v-if="isMuted" :icon="['fas', 'microphone']" />
            <!-- 음소거가 아닐 때 -->
            <font-awesome-icon v-else :icon="['fas', 'microphone-slash']" />
            <!-- 음소거일 때 -->
          </v-btn>
          <v-btn color="#34A080" @click="toggleScreenSharing" title="화면 공유">
            <font-awesome-icon v-if="publisher.value" :icon="['fas', 'desktop']" />
          </v-btn>
          <v-btn color="#34A080" @click="toggleFullScreen" title="전체 화면">
            <font-awesome-icon :icon="['fas', 'expand']" />
          </v-btn>
          <v-btn color="#34A080" @click="exitPage" title="나가기">
            <font-awesome-icon :icon="['fas', 'door-open']" />
          </v-btn>
        </div>
      </div>
      <div class="chat-container">
        <div class="chat-messages" ref="chatContainer">
          <div v-for="(message, index) in messages" :key="index">
            <div class="message-content">
              <div class="nickname">{{ message.username }}</div>
              <p>{{ message.message }}</p>
            </div>
          </div>
        </div>
        <v-text-field
          class="input-field"
          filled
          variant="underlined"
          v-model="inputMessage"
          placeholder="전달할 내용을 입력하세요."
          @keyup.enter="sendMessage"
          append-icon=""
        >
          <template v-slot:append>
            <v-btn density="compact" flat :round="false" style="min-width: 24px" @click="sendMessage">
              <v-icon color="#34A080">mdi-send</v-icon>
            </v-btn>
          </template>
        </v-text-field>
      </div>
    </div>
  </vue-draggable-resizable>
</template>

<script>
import VueDraggableResizable from 'vue-draggable-resizable';
import 'vue-draggable-resizable/style.css';
import OpenviduTest from '@/components/openvidu/OpenviduTest.vue';
import { ref, onMounted, onBeforeUnmount, computed, watchEffect, nextTick, watch } from 'vue';
import { useSessionStore } from '@/stores/sessionStore';
import { useMessageStore } from '@/stores/messageStore';
export default {
  components: {
    VueDraggableResizable,
    OpenviduTest,
  },
  props: ['roomSessionId', 'subscribers'],

  setup(props, context) {
    const inputMessage = ref('');
    const isComponentVisible = ref(true);
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
    const videoElement = ref(null); // videoElement를 선언합니다.

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
        console.log('Neither publisher nor subscriber stream is defined yet');
      }
    };
    onMounted(async () => {
      const rsId = props.roomSessionId;
      session.value = await sessionStore.getSessionById(rsId)['session'];
      subscriber.value = await sessionStore.getSubscriberById(rsId);
      console.log(subscriber.value);

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
      // 세션을 종료하고 상태를 업데이트합니다.
      sessionStore.exitSession(props.roomSessionId);
      // 나가기 이벤트를 발생시킵니다.
      context.emit('exit');
    };

    return {
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
      // 나머지 메서드도 반환 객체에 포함시켜 주세요
    };
  },
};
</script>

<style scoped>
.custom-component {
  border-radius: 3%;
  margin: 0;
  padding: 0;
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
  border-radius: 3%;
  display: flex;
  justify-content: space-between;
  width: 100%;
  height: 100%;
  background-color: white;
}
.button-container {
  display: flex;
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
  max-width: 790px;
  max-height: 450px;
  height: auto;
}

.screen-container {
  width: 800px;
  max-height: 500px;
}

.chat-container {
  width: 200px;
  height: 100%; /* 채팅창 컨테이너의 높이를 100%로 조정 */
  display: flex;
  flex-direction: column;
  justify-content: flex-end; /* 입력 구간을 아래로 */
}

.chat-messages {
  flex: 1;
  overflow-y: auto;
  max-height: calc(100% - 70px); /* 입력 구간의 높이를 고려하여 채팅창 리스트의 최대 높이를 조정 */
  /* margin-bottom: 20px; 입력 구간과의 간격을 주기 위해 margin-bottom 추가 */
}

.input-field {
  flex: none;
  height: 60px; /* 입력 구간의 높이를 증가 */
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
