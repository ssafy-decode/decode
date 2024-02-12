<template>
  <div class="app">
    <NavBar :class="$route.name === 'mainview'? 'static-header':'non-static-header' " />
    <div style="min-height: 1100px">
      <router-view></router-view>
      <br />
      <Chat></Chat>
    </div>
    <FooterBar />
  </div>
</template>

<script setup>
import FooterBar from '@/components/common/FooterBar.vue';
import NavBar from '@/components/common/NavBar.vue';
import Chat from '@/components/chat/Chat.vue';
</script>

<style>

.non-static-header{
  background-color: white;
  width: 100vw;
}
.static-header{
  position:fixed;
  top:0;
  background-color: white;
  width: 100vw;
  z-index: 9999;
}
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
