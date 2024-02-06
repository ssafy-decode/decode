import { defineStore } from 'pinia';

export const useMessageStore = defineStore({
  id: 'message',
  state: () => ({
    messages: {}, // 각 채팅방의 메시지를 저장할 객체
  }),
  actions: {
    // 특정 채팅방에 메시지를 추가하는 action
    addMessage(sessionId, message) {
      if (!this.messages[sessionId]) {
        this.messages[sessionId] = [];
      }
      this.messages[sessionId].push(message);
    },
  },
});
