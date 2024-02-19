<template>
  <div class="error-container">
    <div style="font-size: 60px; font-weight: bold">404 ERROR</div>
    <div style="font-size: 25px; font-weight: bold; margin-bottom: 20px; color: #34a080">Page Not Found</div>
    <router-link to="/"
      ><v-btn size="large" style="background-color: #62c0a6; border-radius: 34px; font-weight: bold"
        >홈으로</v-btn
      ></router-link
    >
  </div>
</template>
<script setup></script>
<style scoped>
.error-container {
  background-color: transparent;
  text-align: center;
  position: fixed;
  top: 50%; /* 화면 세로 중앙으로 이동 */
  left: 50%; /* 화면 가로 중앙으로 이동 */
  transform: translate(-50%, -50%); /* 화면 정중앙으로 이동 */
}
</style>
