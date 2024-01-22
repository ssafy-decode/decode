<template>
  <div>
    <NavBar />
    <br />
    <router-view class="temp"></router-view>
    <FooterBar />
  </div>
</template>

<script setup>
import FooterBar from '@/components/common/FooterBar.vue';
import NavBar from '@/components/common/NavBar.vue';
</script>

<style scoped></style>
