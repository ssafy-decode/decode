<template>
  <div class="app">
    <!-- 일부 화면에는 navbar가 없기 때문에 추후 app.vue에서 navbar를 없애고 있는 화면들에 직접 넣는 방식으로 할 지 상의해봅시다.-->
    <NavBar />
    <br />
    <router-view></router-view>
    <br />
    <FooterBar />
  </div>
</template>

<script setup>
import FooterBar from '@/components/common/FooterBar.vue';
import NavBar from '@/components/common/NavBar.vue';
</script>

<style>
/* 전역 적용을 위해 scoped를 지움 */
.app {
  width: 100%;
  height: 100%;

  /* 배경이미지는 최소 1920*960으로 제작해야 문제가 안생김 */
  background-image: url('../public/background.png');
  background-repeat: no-repeat; /* 배경이미지X */
  background-size: cover; /* 요소를 비율에 맞게 커버 */
  background-position: center; /* 이미지를 요소의 정가운데로 처리 */
  background-attachment: fixed; /* 스크롤바 움직일때 이미지가 따라다님 */
}
/* 배경 이미지가 잘리기 때문에 모든 router-view에 있는 v-sheet, v-app 태그를 div로 수정해야 함 */
</style>
