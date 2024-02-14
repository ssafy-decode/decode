<template>
  <v-dialog v-model="localDialog" persistent max-width="300px" style="z-index: 10000">
    <v-card>
      <v-card-title class="headline">화면 공유방을 만드시겠습니까?</v-card-title>

      <v-card-text>
        <v-text-field
          v-model="roomDuration"
          label="방 유지시간(최대 30분)"
          type="number"
          :rules="[(value) => value <= 30 || '방 유지시간은 최대 30분입니다']"
        ></v-text-field>

        <v-text-field
          v-model="roomCapacity"
          label="방 인원 수"
          type="number"
          :rules="[
            (value) => value <= 10 || '방 인원 수는 최대 10명입니다',
            (value) => value >= 1 || '방 인원 수는 최소 1명이어야 합니다',
          ]"
        ></v-text-field>
      </v-card-text>

      <v-card-actions
        ><v-btn color="green darken-1" text @click="handleYes" :disabled="!isValid">예</v-btn>

        <v-btn color="red darken-1" text @click="handleNo">아니오</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
import axios from 'axios';
import { ref, onMounted, computed, watch } from 'vue';
import { OpenVidu } from 'openvidu-browser';
import { useSessionStore } from '@/stores/sessionStore';
import { useMessageStore } from '@/stores/messageStore';
import { useUserStore } from '@/stores/userStore';
import { storeToRefs } from 'pinia';
const APPLICATION_SERVER_URL = 'https://i10a507.p.ssafy.io/decode/openvidu';
// const APPLICATION_SERVER_URL = 'http://localhost:7777/decode/openvidu';
export default {
  props: {
    dialog: Boolean,
  },
  setup(props, { emit }) {
    const sessionStore = useSessionStore(); // store 인스턴스 생성
    const messageStore = useMessageStore();
    const localDialog = ref(props.dialog);
    const roomDuration = ref(0);
    const roomCapacity = ref(0);
    const randomSessionId = computed(() => {
      const characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
      let result = '';
      const charactersLength = characters.length;
      for (let i = 0; i < 10; i++) {
        result += characters.charAt(Math.floor(Math.random() * charactersLength));
      }
      return result;
    });

    const OV = ref(undefined);
    const session = ref(undefined);
    const mainStreamManager = ref(undefined);
    const publisher = ref(undefined);
    const subscribers = ref([]);
    const inputMessage = ref('');
    const messages = ref([]);
    const muted = ref(false);
    const camerOff = ref(false);
    const selectedCamera = ref('');
    const selectedAudio = ref('');
    const userStore = useUserStore();
    const { handleMyprofile: myProfile, handleLoginUserId: loginUserId } = storeToRefs(userStore);
    const myUserName = myProfile.value.nickname;
    const isValid = computed(() => {
      return roomDuration.value >= 1 && roomDuration.value <= 30 && roomCapacity.value >= 1 && roomCapacity.value <= 10;
    });

    watch(
      () => props.dialog,
      (newVal) => {
        localDialog.value = newVal;
      },
    );
    onMounted(() => {
      isValid.value =
        roomDuration.value >= 1 && roomDuration.value <= 30 && roomCapacity.value >= 1 && roomCapacity.value <= 10;
    });

    const handleYes = async () => {
      const screenSharingPublisher = await joinSession();
      localDialog.value = false;
      roomDuration.value = 0;
      roomCapacity.value = 0;
      emit('yesClicked', screenSharingPublisher, randomSessionId);
    };

    const handleNo = () => {
      localDialog.value = false;
      emit('noClicked');
    };

    const joinSession = async () => {
      OV.value = new OpenVidu();
      session.value = OV.value.initSession();
      sessionStore.setOv(randomSessionId, OV.value);

      session.value.on('streamCreated', ({ stream }) => {
        const subscriber = session.value.subscribe(stream);
        subscribers.value.push(subscriber);
      });

      session.value.on('streamDestroyed', ({ stream }) => {
        const index = subscribers.value.indexOf(stream.streamManager, 0);
        if (index >= 0) {
          subscribers.value.splice(index, 1);
        }
      });
      // 채팅 이벤트 수신 처리 함. session.on이 addEventListenr 역할
      session.value.on('signal:chat', (event) => {
        // event.from.connectionId === session.value.connection.connectionId 이건 나와 보낸이가 같으면임
        const messageData = JSON.parse(event.data);
        if (event.from.connectionId === session.value.connection.connectionId) {
          messageData['username'] = '나';
        }
        messageStore.addMessage(randomSessionId, messageData);
      });
      session.value.on('exception', ({ exception }) => {});

      try {
        const token = await getToken(randomSessionId.value);
        await session.value.connect(token, { clientData: myUserName });

        // const stream = await navigator.mediaDevices.getDisplayMedia({ video: true });
        const publisher = await OV.value.initPublisherAsync('publisher', {
          videoSource: 'screen',
          publishAudio: true,
          publishVideo: true,
          mirror: false,
        });
        session.value.publish(publisher);
        // 세션 정보와 유저 이름을 store에 저장 -> sid로 저장해서 v 구분
        sessionStore.setSession(randomSessionId, session.value);
        sessionStore.setMyUserName(randomSessionId, myUserName);
        sessionStore.setPublisher(randomSessionId, publisher);
        return publisher;
      } catch (error) {}
    };

    const leaveSession = () => {
      if (session.value) session.value.disconnect();

      // Empty all properties...
      session.value = undefined;
      mainStreamManager.value = undefined;
      publisher.value = undefined;
      subscribers.value = [];
      OV.value = undefined;

      // Remove beforeunload listener
      window.removeEventListener('beforeunload', leaveSession);
    };

    const getToken = async (sessionId) => {
      const sId = await createSession(sessionId);
      return await createToken(sId);
    };

    const createSession = async (sessionId) => {
      try {
        const response = await axios.post(
          APPLICATION_SERVER_URL + '/api/sessions',
          { customSessionId: sessionId },
          {
            headers: {
              Authorization: `Bearer ${userStore.accessToken}`,
            },
          },
        );
        return response.data;
      } catch (error) {}
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
        throw error;
      }
    };

    const handleMuteBtn = () => {
      if (!publisher.value) return;
      muted.value = !muted.value;
      const muteActivate = document.getElementById('mute-activate');
      if (muted.value) {
        muteActivate.innerText = '음소거 비활성화';
      } else {
        muteActivate.innerText = '음소거 활성화';
      }
      publisher.value.publishAudio(!muted.value);
    };

    return {
      isValid,
      localDialog,
      roomDuration,
      roomCapacity,
      randomSessionId,
      OV,
      session,
      mainStreamManager,
      publisher,
      subscribers,
      myUserName,
      inputMessage,
      messages,
      muted,
      camerOff,
      selectedCamera,
      selectedAudio,
      handleYes,
      handleNo,
      joinSession,
      leaveSession,
      getToken,
      createSession,
      createToken,
      handleMuteBtn,
    };
  },
};
</script>
