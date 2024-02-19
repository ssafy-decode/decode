<template>
  <v-card v-show="isAppOpen" :class="{ 'hide-background': !isAppOpen }" :style="dialogStyle">
    <v-tabs v-model="tab" bg-color="#34A080">
      <v-tab value="chat">채팅</v-tab>
      <v-tab value="my-chat">내 채팅</v-tab>
    </v-tabs>
    <v-container class="tab-body" bg-color="#34A080">
      <v-window v-model="tab">
        <v-window-item value="chat">
          <ChatComponent />
        </v-window-item>

        <v-window-item value="my-chat" :key="myChatKey">
          <MyChatComponent />
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
import { ref, computed, watch } from 'vue';
import ChatComponent from './ChatComponent.vue';
import MyChatComponent from './MyChatComponent.vue';
import { storeToRefs } from 'pinia';

export default {
  components: {
    ChatComponent,
    MyChatComponent,
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
      borderRadius: '15px',
      zIndex: '9999',
    }));

    const appIcon = computed(() => (isAppOpen.value ? 'mdi-close' : 'mdi-message-outline'));

    const toggleApp = () => {
      isAppOpen.value = !isAppOpen.value;
    };
    // EventBus.$on('registerFetchRooms', (newFetchRooms) => {
    //   fetchRooms = newFetchRooms;
    // });
    const myChatKey = ref(Date.now()); // 초기값 설정

    watch(tab, (newTab) => {
      if (newTab === 'my-chat') {
        // 탭이 '내 채팅'으로 변경될 때마다 myChatKey 값을 변경하여 MyChatComponent를 새로 렌더링합니다.
        myChatKey.value = Date.now();
      }
    });
    return {
      tab,
      isAppOpen,
      dialogStyle,
      appIcon,
      myChatKey,
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
  padding: 0;
  margin: 0;
}
</style>
