<script setup>
import { useRoute } from 'vue-router';
import myaxios from '@/utils/common-axios.js';

const route = useRoute();
const code = route.query.code;

if (code) {
  const res = await myaxios.get('/auth/github?code=' + code);

  if (res.data.status === 'OK') {
    window.opener.postMessage(res.data.data, '*');
    window.close();
  }
}
</script>

<template>
  <div>OAuth2 인증을 완료하셨군요.</div>
  <div>잠시 기다려 주시죠.</div>
</template>

<style scoped></style>
