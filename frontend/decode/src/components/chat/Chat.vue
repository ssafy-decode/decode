<template>
  <v-card v-show="isAppOpen" :class="{ 'hide-background': !isAppOpen }" :style="dialogStyle">
    <v-tabs v-model="tab" bg-color="#34A080">
      <v-tab value="chat">채팅</v-tab>
      <v-tab value="my-chat">내 채팅</v-tab>
    </v-tabs>
    <v-container class="tab-body">
      <v-window v-model="tab">
        <v-window-item value="chat">
          <ChatComponent />
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
import { ref, computed } from 'vue';
import ChatComponent from './ChatComponent.vue';
import ScreenShareComponent from './ScreenShareComponent.vue';


export default {
  components: {
    ChatComponent,
    ScreenShareComponent,
  },
  setup() {
    const tab = ref('chat');
    const isAppOpen = ref(false);
    const dialogStyle = computed(() => ({
      position: 'fixed',
      bottom: '55px',
      right: '16px',
      width: '300px',
      height: '500px',
      backgroundColor: 'white',
      border: '1px solid #ccc',
      zIndex: '9999',
    }));

    const appIcon = computed(() => (isAppOpen.value ? 'mdi-close' : 'mdi-message-outline'));

    const toggleApp = () => {
      isAppOpen.value = !isAppOpen.value;
    };

    return {
      tab,
      isAppOpen,
      dialogStyle,
      appIcon,
      toggleApp,
    };
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
