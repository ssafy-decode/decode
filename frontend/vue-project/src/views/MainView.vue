<template>
  <h1>MainView</h1>
  <div class="flex-container">
    <div class="box wrapper text-h3 font-weight-black">
      <p id="hate">이 에러를<br /><br />당신이 싫어합니다</p>
      <p id="like">이 코드를<br /><br />당신이 좋아합니다</p>
    </div>

    <div class="box wrapper">
      <img id="hate" src="./error.png" alt="에러이미지" />
      <img id="like" src="./code.png" alt="굿이미지" />
    </div>
  </div>
</template>

<script setup></script>

<style scoped>
@keyframes changeAnimation {
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

.box {
  height: 500px;
  width: 50%;
}

.wrapper {
  position: relative;
  color: #34a080;
}

#hate {
  position: absolute;
  animation: changeAnimation 10s infinite;
}

#like {
  position: absolute;
  opacity: 0;
  animation: changeAnimation 10s infinite;
  animation-delay: 5s;
}
.flex-container {
  display: flex;
  justify-content: space-between;
  height: 800px;
}

p {
  top: 250px;
  right: 200px;
  text-align: right;
}

img {
  width: 90%;
}
</style>
