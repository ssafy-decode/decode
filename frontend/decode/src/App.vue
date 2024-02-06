<template>
  <div class="app">
    <NavBar />
    <br />
    <router-view></router-view>
    <br />
    <Chat></Chat>
    <FooterBar />
  </div>
</template>

<script setup>
import FooterBar from '@/components/common/FooterBar.vue';
import NavBar from '@/components/common/NavBar.vue';
import Chat from '@/components/chat/Chat.vue';
</script>

<style>
.app {
  width: 100%;
  height: 100%;

  background-image: url('../public/background.png');
  background-repeat: no-repeat;
  background-size: cover;
  background-position: center;
  background-attachment: fixed;
}
</style>
