<template>
  <v-row class="flex-container">
    <v-col cols="2"></v-col>
    <v-col cols="4" style="padding: 0; margin: 0">
      <div class="img-box">
        <p class="text">
          전문가를 팔로우하고<br/>
          화면공유와 채팅을 통해<br/>
          1:1 답변을 받아보세요.
        </p>
      </div>
    </v-col>
    <v-col cols="4" style="padding: 0; margin: 0">
      <div class="img-box">
        <img class="page" src="./expector.png" alt="전문가" />
      </div>
    </v-col>
    <v-col cols="2"></v-col>
  </v-row>
</template>

<script setup></script>

<style>
.test {
  border: solid;
  padding: 0;
  margin: 0;
}
.text {
  font-size: 2.5em; /* 텍스트 크기를 크게 설정 */
  font-weight: 900; /* 텍스트를 굵게 설정 */
  color: #23968b; /* 텍스트 색상을 초록색으로 설정 */
}

.flex-container {
  display: flex;
  margin: 0;
  padding: 0;
}
.img-box {
  flex-direction: column;
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  margin: 0;
  padding: 0;
}

.expector-img {
  position: absolute;
  animation: changeAnimation 10s infinite;
  animation-delay: 5s;
}

.success {
  display: flex;
}

.page {
  width: 80%;
}
.people {
  width: 40%;
}
</style>
