<template>
  <div style="text-align: center; margin-top: 10%; margin-bottom: 10%">
    <video src="../loading.mp4" width="80" height="80" autoplay muted loop></video>
    <br />
    <br />
    <h2 style="color: #999999">인증 중입니다.</h2>
    <h2 style="color: #999999">잠시만 기다려주세요.</h2>
    <br />
    <h2 style="color: #34a080">de;code</h2>
    <!-- 인증 완료 시 alert를 띄워서 기술 스택 창으로 이동하게끔 할 것 (일단 보류) -->
    <br />
    <router-link to="/techstack"
      ><btn @click="next">기술 스택 창으로 (화면 이동이 불가능해서 만든 임시 버튼: 구현 후에는 삭제)</btn></router-link
    >
  </div>
</template>

<script setup>
const next = () => {
  alert('소셜 인증이 완료되었습니다.\n다음 단계로 넘어갑니다.');
};
</script>

<style scoped></style>
