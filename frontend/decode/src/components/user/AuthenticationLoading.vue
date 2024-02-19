<template>
  <div style="text-align: center; margin-top: 10%; margin-bottom: 10%">
    <video src="../loading.mp4" width="80" height="80" autoplay muted loop></video>
    <br />
    <h2 style="font-size: 30px; margin-top: 20px; color: #999999">인증 중입니다.</h2>
    <h2 style="font-size: 30px; color: #999999">잠시만 기다려주세요.</h2>
    <br />
    <h2 style="font-family: titlelightfont; font-size: 30px; color: #34a080">de;code</h2>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router';
import { useUserStore } from '@/stores/userStore';
import { storeToRefs } from 'pinia';
import { parseJwt } from '@/utils/jwtParser';

const userStore = useUserStore();

const { setLoginUserId, setToken, setMyProfile, setOAuth } = userStore;
const { isLoggedIn: isLoggedIn } = storeToRefs(userStore);
const router = useRouter();

window.addEventListener('message', (event) => {
  const token = event.data;
  if (!token) {
    alert('로그인에 실패했습니다.');
    router.push('/login');
  }
  isLoggedIn.value = true;
  setToken(token);
  const uid = parseJwt(token)['userId'];
  setLoginUserId(uid);
  setMyProfile();
  setOAuth();

  router.push('/');
});
</script>

<style scoped></style>
