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
import { ref, computed, watch, nextTick } from 'vue';
import { useSessionStore } from '@/stores/sessionStore';
import { useMessageStore } from '@/stores/messageStore';
export default {
  components: {
    VueDraggableResizable,
    OpenviduTest,
  },
  props: ['roomSessionId'],
  setup(props) {
    const inputMessage = ref('');
    const isComponentVisible = ref(true);
    const sessionStore = useSessionStore();
    const messageStore = useMessageStore();
    const messages = computed(() => messageStore.messages[props.roomSessionId] || []);
    const session = ref(null);
    const username = ref(null);
    const OV = ref(null);
    const publisher = ref(null);
    const chatContainer = ref(null);

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
    };
  },

  data() {
    return {
      isMuted: false, // 음소거 상태를 저장하는 속성
      isScreenSharing: true,
    };
  },
  async mounted() {
    console.log(123);
    this.session = await this.sessionStore.getSessionById(this.roomSessionId)['session'];
    this.username = await this.sessionStore.getMyUserNameById(this.roomSessionId)['myUserName'];
    this.OV = await this.sessionStore.getOvById(this.roomSessionId);
    this.publisher = await this.sessionStore.getPublisherById(this.roomSessionId);
    console.log(this.session);
    console.log(this.username);
    console.log(this.publisher);
    console.log(this.OV);

    if (this.publisher && this.publisher.stream) {
      this.$refs.videoElement.srcObject = this.publisher.stream.mediaStream;
    } else {
      console.log('publisher or publisher.stream is not defined yet');
    }
  },
  beforeDestroy() {
    if (this.$refs.videoElement) {
      this.$refs.videoElement.srcObject = null;
    }
  },
  methods: {
    sendMessage(event) {
      event.preventDefault();
      if (this.inputMessage.trim()) {
        this.session.signal({
          data: JSON.stringify({
            username: this.username,
            message: this.inputMessage,
          }),
          type: 'chat',
        });
        this.inputMessage = '';
        this.$nextTick(() => {
          setTimeout(() => {
            const container = this.$refs.chatContainer;
            container.scrollTop = container.scrollHeight;
          }, 100); // 0초 후에 실행, 즉 가능한 한 빠르게 실행되지만 렌더링은 기다림
        });
      }
    },
    showComponent() {
      this.isComponentVisible.value = !this.isComponentVisible.value;
    },
    toggleComponent() {
      this.isComponentVisible.value = !this.isComponentVisible.value;
    },

    toggleAudio() {
      if (this.publisher) {
        this.isMuted = !this.isMuted; // 음소거 상태를 반전시킵니다.
        const isAudioActive = this.publisher.stream.audioActive;
        this.publisher.publishAudio(!isAudioActive); // 오디오 상태를 반전시킵니다.
      }
    },
    async toggleScreenSharing() {
      // publisher가 존재하지 않는 경우, 화면 공유를 시작합니다.
      if (!this.publisher) {
        this.publisher = await this.OV.initPublisherAsync('publisher', {
          videoSource: 'screen',
          publishAudio: true,
          publishVideo: true,
          mirror: false,
        });
        await this.session.publish(this.publisher);
      } else {
        // publisher가 존재하는 경우, 기존 화면 공유를 종료하고 새 화면을 공유합니다.
        this.session.unpublish(this.publisher);
        this.publisher = await this.OV.initPublisherAsync('publisher', {
          videoSource: 'screen',
          publishAudio: true,
          publishVideo: true,
          mirror: false,
        });
        await this.session.publish(this.publisher);
      }
      this.$refs.videoElement.srcObject = this.publisher.stream.mediaStream;
    },
    toggleFullScreen() {
      const videoElement = this.$refs.videoElement;
      if (!document.fullscreenElement) {
        if (videoElement.requestFullscreen) {
          videoElement.requestFullscreen();
        } else if (videoElement.mozRequestFullScreen) {
          videoElement.mozRequestFullScreen();
        } else if (videoElement.webkitRequestFullscreen) {
          videoElement.webkitRequestFullscreen();
        } else if (videoElement.msRequestFullscreen) {
          videoElement.msRequestFullscreen();
        }
      } else {
        if (document.exitFullscreen) {
          document.exitFullscreen();
        } else if (document.mozCancelFullScreen) {
          document.mozCancelFullScreen();
        } else if (document.webkitExitFullscreen) {
          document.webkitExitFullscreen();
        } else if (document.msExitFullscreen) {
          document.msExitFullscreen();
        }
      }
    },
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
