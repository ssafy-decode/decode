<template>
  <div style="text-align: center; margin-top: 8%; margin-bottom: 10%">
    <img style="width: 3%" src="./LogoDecode3.png" />
    <br />
    <h2 style="font-size: 25px; margin-top: 10px; color: #999999">임시 비밀번호를 전송했습니다.</h2>
    <h2 style="font-size: 25px; color: #999999">이메일을 확인해주세요.</h2>
    <br />
    <router-link to="/login"
      ><v-btn
        color="#62C0A6"
        size="x-large"
        type="submit"
        variant="elevated"
        style="
          width: 231px;
          margin-top: 10px;
          border-radius: 34px;
          font-size: smaller;
          font-weight: bold;
          color: #000000;
        "
        >로그인 화면으로</v-btn
      ></router-link
    >
  </div>
</template>

<script setup></script>

<style scoped></style>
