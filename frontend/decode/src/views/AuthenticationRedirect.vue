<script setup>
import { useRoute } from 'vue-router';
import { storeToRefs } from 'pinia';
import myaxios from '@/utils/common-axios.js';
import { useUserStore } from '@/stores/userStore';

const userStore = useUserStore();
const { accessToken: accessToken, isLoggedIn: isLoggedIn } = storeToRefs(userStore);

const route = useRoute();

const code = route.query.code;

if (code) {
  const res = await myaxios.get('/auth/github?code=' + code);

  if (res.data.status === 'OK') {
    accessToken.value = res.data.data;
    isLoggedIn.value = true;
    console.log(accessToken.value);
    window.opener.postMessage(res.data.data, '*');
    window.close();
  } else {
    console.log('로그인 실패');
  }
}
</script>

<template>
  <div>OAuth2 인증을 완료하셨군요.</div>
  <div>잠시 기다려 주시죠.</div>
</template>

<style scoped></style>
