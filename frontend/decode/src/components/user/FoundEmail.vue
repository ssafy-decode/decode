<template>
  <div style="text-align: center; margin-top: 8%; margin-bottom: 10%">
    <img style="width: 3%" src="./LogoDecode3.png" />
    <br />
    <h2 style="color: #999999; font-size: 25px; margin-top: 5px">회원님의 계정은 다음과 같습니다.</h2>
    <h2 style="color: #34a080; text-decoration: underline; font-size: 30px; margin-top: 10px">
      {{ hideEmail(foundEmail) }}
    </h2>
    <br />
    <router-link to="/login"
      ><v-btn
        color="#62C0A6"
        size="x-large"
        type="submit"
        variant="elevated"
        style="width: 231px; border-radius: 34px; font-size: smaller; font-weight: bold; color: #000000"
        >로그인 화면으로</v-btn
      ></router-link
    >
  </div>
</template>

<script setup>
import { useUserStore } from '@/stores/userStore';

const userStore = useUserStore();

const foundEmail = userStore.foundEmail;

const hideEmail = (email) => {
  const idx = email.indexOf('@');
  return email.substring(0, 2) + '*'.repeat(idx - 2) + email.substring(idx);
};
</script>

<style scoped></style>
