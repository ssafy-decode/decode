<template>
  <v-card v-show="isAppOpen" :class="{ 'hide-background': !isAppOpen }" :style="dialogStyle">
    <v-tabs v-model="tab" bg-color="#34A080">
      <v-tab value="chat">채팅</v-tab>
      <v-tab value="video">화면 공유</v-tab>
      <v-tab value="my-chat">내 채팅</v-tab>
    </v-tabs>
    <v-container class="tab-body">
      <v-window v-model="tab">
        <v-window-item value="chat">
          <ChatComponent />
        </v-window-item>

        <v-window-item value="video">
          <ScreenShareComponent />
        </v-window-item>

        <v-window-item value="my-chat">
          <ScreenShareComponent />
        </v-window-item>
      </v-window>
    </v-container>
  </v-card>

  <!-- 항상 보이는 버튼 -->
  <v-btn @click="toggleApp" class="fab" fab color="#03A080" dark>
    <v-icon>{{ appIcon }}</v-icon>
  </v-btn>
</template>

<!-- The rest of the script and style -->

<script>
import ChatComponent from './ChatComponent.vue';
import ScreenShareComponent from './ScreenShareComponent.vue';

export default {
  components: {
    ChatComponent,
    ScreenShareComponent,
  },
  data() {
    return {
      tab: 'chat',
      isAppOpen: false,
    };
  },
  computed: {
    dialogStyle() {
      return {
        position: 'fixed',
        bottom: '55px',
        right: '16px',
        width: '300px', // 너비를 300px로 조정
        height: '500px', // 높이를 500px로 조정
        backgroundColor: 'white',
        border: '1px solid #ccc',
        zIndex: '9999',
      };
    },
    appIcon() {
      return this.isAppOpen ? 'mdi-close' : 'mdi-message-outline';
    },
  },
  methods: {
    toggleApp(event) {
      this.isAppOpen = !this.isAppOpen;
    },
  },
};
</script>

<style>
.hide-background {
  background-color: transparent !important;
  border: none !important;
}
.fab {
  position: fixed;
  bottom: 16px;
  right: 16px;
  z-index: 10000;
}
.tab-body {
  padding-right: 0;
}
</style>
