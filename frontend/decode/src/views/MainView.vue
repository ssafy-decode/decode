<template>
  <div class="container">
    <FirstCut/>
    <SecondCut/>
    <ThirtCut/>
  </div>
</template>

<script setup>
import FirstCut from '@/components/main/FirstCut.vue';
import SecondCut from '@/components/main/SecondCut.vue';
import ThirtCut from '@/components/main/ThirdCut.vue';


</script>

<style scoped>
.container {
  border: solid red;
  padding: 0;
  margin: 0;
  width: 100vw;
}
.container > * {
  border: solid blue;
  height: calc(100vh - 64px); /* Nav바의 높이가 64px라고 가정함 */
  width: 100vw;
}
.box {
  height: 500px;
  width: 50%;
}

.wrapper {
  position: relative;
  color: #34a080;
}
</style>
