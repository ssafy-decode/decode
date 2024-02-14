import { defineStore } from 'pinia';
import { Client } from '@stomp/stompjs';

export const useStompStore = defineStore({
  id: 'stompStore',
  state: () => ({
    stompClient: null,
    connected: false,
    messages: {}, // 각 방의 메시지 리스트를 저장하는 객체
    subscriptions: {}, // 각 방의 구독 객체를 저장하는 객체
  }),
  actions: {
    connect() {
      const stompClient = new Client({
        brokerURL: 'wss://i10a507.p.ssafy.io/decode/chattings',
        // brokerURL: 'ws://localhost:7777/decode/chattings',
        connectHeaders: {},
        onConnect: (frame) => {
          this.connected = true;
        },
        onStompError: (error) => {
          this.connected = false;
        },
      });
      stompClient.activate();
      this.stompClient = stompClient;
    },
    subscribeRoom(roomId) {
      if (this.stompClient && this.connected) {
        this.subscriptions[roomId] = this.stompClient.subscribe(`/topic/chat/room/${roomId}`, (res) => {
          // 해당 방의 메시지 리스트에 메시지 추가
          if (!this.messages[roomId]) {
            this.messages[roomId] = [];
          }
          this.messages[roomId].push(JSON.parse(res.body));
        });
      }
    },
    sendMessage(userId, nickName, text, roomId) {
      if (userId !== '' && text !== '' && roomId !== '') {
        if (this.stompClient && this.connected) {
          const msg = {
            userId: userId,
            nickName: nickName,
            text: text,
            roomId: roomId,
          };
          this.stompClient.publish({ destination: '/app/chat/message', body: JSON.stringify(msg) });
        }
      }
    },
    unsubscribe() {
      if (this.subscriptions[roomId]) {
        // 특정 방의 구독 취소
        this.subscriptions[roomId].unsubscribe();
        delete this.subscriptions[roomId];
      }
    },
    unsubscribeAll() {
      // 모든 방의 구독 취소
      for (let roomId in this.subscriptions) {
        this.subscriptions[roomId].unsubscribe();
      }
      this.subscriptions = {};
      if (this.stompClient) {
        this.stompClient.deactivate();
        this.connected = false;
      }
    },
  },
});
