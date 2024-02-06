<template>
  <div class="flex-container">
    <div class="box wrapper text-h3 font-weight-black">
      <p id="hate">이 에러를<br /><br />당신이 싫어합니다</p>
      <p id="like">이 코드를<br /><br />당신이 좋아합니다</p>
    </div>

    <div class="box wrapper">
      <img id="hate" src="../public/error.png" alt="에러이미지" />
      <img id="like" src="./public/code.png" alt="코드이미지" />
    </div>
    <img src="./error.png" alt="에러이미지1" />
    <img src="../error.png" alt="에러이미지2" />
    <img src="./public/error.png" alt="에러이미지3" />
    <img src="../public/error.png" alt="에러이미지4" />
    <img src="error.png" alt="에러이미지5" />
    <img src="@/../public/error.png" alt="에러이미지6" />
    <img src="@/../error.png" alt="에러이미지7" />
    <img src="@/public/error.png" alt="에러이미지8" />
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
  animation: changeAnimation 6s infinite;
}

#like {
  position: absolute;
  opacity: 0;
  animation: changeAnimation 6s infinite;
  animation-delay: 3s;
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
