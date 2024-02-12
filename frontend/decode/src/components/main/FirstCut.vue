<template>
  <v-row class="flex-container">
    <v-col cols="1"></v-col>
    <v-col cols="5" style="padding: 0; margin: 0">
      <div class="img-box">
        <p class="title-text">
          코드를 공유하고 
          <br/> 
          해결해보세요!
        </p>
        <img class="success people" src="./success_man.png" alt="성공사람" />
      </div>
    </v-col>
    <v-col cols="5" style="padding: 0; margin: 0">
      <div class="img-box">
        <img class="success page" src="./success_page.png" alt="성공페이지" />
      </div>
    </v-col>
  </v-row>
</template>

<script setup></script>

<style>
@keyframes changeAnimation1 {
  0% {
    opacity: 0;
  }
  7% {
    opacity: 1;
  }
  50% {
    opacity: 1;
  }
  65% {
    opacity: 0;
  }
  100% {
    opacity: 0;
  }
}
@keyframes changeAnimation {
  0%,
  20% {
    opacity: 0;
  }
  30%,
  70% {
    opacity: 1;
  }
  80%,
  100% {
    opacity: 0;
  }
}
.test {
    border: solid;
    padding: 0;
    margin: 0;
}
.title-text {
    font-size: 3em; /* 텍스트 크기를 크게 설정 */
    font-weight: 900; /* 텍스트를 굵게 설정 */
    color: #23968B; /* 텍스트 색상을 초록색으로 설정 */
}

.flex-container {
    display: flex;
    margin: 0;
    padding: 0;
    border: solid red;
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

.fail {
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
