<template>
  <div class="app">
    <NavBar />
    <br />
<<<<<<< HEAD
    <router-view></router-view>
    <br />
    <Chat v-if="isLoggedIn"></Chat>
=======
    <div style="min-height: 1100px">
      <router-view></router-view>
      <br />
      <Chat></Chat>
    </div>
>>>>>>> 84644f8cf2593b512bfee8b03404d03bccc62f76
    <FooterBar />
  </div>
</template>

<script setup>
import FooterBar from '@/components/common/FooterBar.vue';
import NavBar from '@/components/common/NavBar.vue';

/*
  처음 client가 접속했을 때 socket에 연결하는 작업.
  컴퍼넌트 생명 주기를 통해 socket 상태 관리
*/
import Chat from '@/components/chat/Chat.vue';
import { ref, onMounted, onUnmounted, watch } from 'vue';
import { useStompStore } from '@/utils/StompUtil';
import { useUserStore } from './stores/userStore';
const stompStore = useStompStore();
const userStore = useUserStore();
const isLoggedIn = ref(userStore.isLoggedIn); // 초기 값 설정
watch(
  () => userStore.isLoggedIn,
  (newVal) => {
    // userStore.isLoggedIn 변경을 감지하여 isLoggedIn 업데이트
    isLoggedIn.value = newVal;
  },
);
onMounted(() => {
  stompStore.connect();
});

onUnmounted(() => {
  stompStore.disconnect();
});
</script>

<style>
.app {
  width: 100%;
  height: 100%;

  background-image: url('../public/background.png');
  background-repeat: no-repeat;
  background-size: cover;
  background-position: center;
  background-attachment: fixed;
}
</style>
