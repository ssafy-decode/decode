<template>
  <div>
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

<style scoped></style>
