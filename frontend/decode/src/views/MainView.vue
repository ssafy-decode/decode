<template>
  <div class="container">
    <FirstCut/>
    <SecondCut/>
    <ThirtCut/>
    <FourthCut/>
  </div>
</template>

<script setup>
import FirstCut from '@/components/main/FirstCut.vue';
import SecondCut from '@/components/main/SecondCut.vue';
import ThirtCut from '@/components/main/ThirdCut.vue';
import FourthCut from '@/components/main/FourthCut.vue';


</script>

<style scoped>
.container {
  padding: 0;
  margin: 0;
  width: 100vw;
}
.container > * {
  height: 90vh; /* Nav바의 높이가 64px라고 가정함 */
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
