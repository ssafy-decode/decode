<template>
  <div style="text-align: center; margin-top: 8%; margin-bottom: 10%">
    <img style="width: 3%" src="./LogoDecode3.png" />
    <br />
    <br />
    <h1 style="color: #999999">회원님의 계정은 다음과 같습니다.</h1>
    <br />
    <h2 style="color: #34a080; text-decoration: underline">{{ hideEmail(foundEmail) }}</h2>
    <br />
    <br />
    <router-link to="/login"
      ><v-btn
        color="#62C0A6"
        size="x-large"
        type="submit"
        variant="elevated"
        style="border-radius: 30px; font-size: smaller; font-weight: bolder; color: #000000"
        >로그인 화면으로</v-btn
      ></router-link
    >
  </div>
</template>

<script setup>
import { useUserStore } from '@/stores/userStore';

const userStore = useUserStore();

const foundEmail = userStore.foundEmail;

console.log('일단 가져왔는지');
console.log(foundEmail);

const hideEmail = (email) => {
  const idx = email.indexOf('@');
  return email.substring(0, 2) + '*'.repeat(idx - 2) + email.substring(idx);
};

console.log('제대로 잘렸는지');
console.log(hideEmail(foundEmail));
</script>

<style scoped></style>
