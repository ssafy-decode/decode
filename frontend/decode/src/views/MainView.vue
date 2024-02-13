<template>
  <div class="container">
    <FirstCut :style="{ opacity: opacities.first }"/>
    <SecondCut :style="{ opacity: opacities.second }"/>
    <ThirtCut :style="{ opacity: opacities.third }"/>
    <FourthCut :style="{ opacity: opacities.fourth }"/>
  </div>
</template>

<script setup>
import { onMounted, onUnmounted, reactive } from 'vue'
import FirstCut from '@/components/main/FirstCut.vue';
import SecondCut from '@/components/main/SecondCut.vue';
import ThirtCut from '@/components/main/ThirdCut.vue';
import FourthCut from '@/components/main/FourthCut.vue';

let opacities = reactive({
  first: 1,
  second: 0,
  third: 0,
  fourth: 0
})

const handleScroll = () => {
  const scrollTop = window.pageYOffset || document.documentElement.scrollTop
  const cutHeight = window.innerHeight - 64
  const halfCutHeight = cutHeight / 2

  opacities.first = Math.max(0, 1 - scrollTop / halfCutHeight)
  opacities.second = Math.max(0, 1 - Math.abs(scrollTop - cutHeight) / halfCutHeight)
  opacities.third = Math.max(0, 1 - Math.abs(scrollTop - 2 * cutHeight) / halfCutHeight)
  opacities.fourth = Math.max(0, 1 - Math.abs(scrollTop - 3 * cutHeight) / halfCutHeight)
}

onMounted(() => {
  window.addEventListener('scroll', handleScroll)
})

onUnmounted(() => {
  window.removeEventListener('scroll', handleScroll)
})

</script>

<style scoped>
.container {
  padding: 0;
  margin: 64px 0 0 0;
  width: 100vw;
}
.container > * {
  height: calc(100vh - 64px); 
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
