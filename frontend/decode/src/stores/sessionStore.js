import { defineStore } from 'pinia';
export const useSessionStore = defineStore({
  id: 'session',
  state: () => ({
    sessions: {}, // 각 세션의 정보를 저장할 state
  }),
  actions: {
    exitSession(sessionId) {
      if (this.sessions[sessionId]) {
        // 세션에서 사용자를 제거합니다.
        this.sessions[sessionId].session.disconnect();
        // 세션 상태를 업데이트합니다.
        this.sessions[sessionId] = {
          session: null,
          myUserName: null,
          OV: null,
          publisher: null,
          subscriber: null,
        };
      }
    },
    // 세션 정보를 설정하는 action
    setSession(sessionId, session) {
      if (!this.sessions[sessionId]) {
        this.sessions[sessionId] = {
          session: null,
          myUserName: null,
          OV: null,
          publisher: null,
          subscriber: null,
        };
      }
      this.sessions[sessionId].session = session;
    },
    // 유저 이름을 설정하는 action
    setMyUserName(sessionId, username) {
      if (!this.sessions[sessionId]) {
        this.sessions[sessionId] = {
          session: null,
          myUserName: null,
          OV: null,
          publisher: null,
          subscriber: null,
        };
      }
      this.sessions[sessionId].myUserName = username;
    },
    // OV 설정하는 action
    setOv(sessionId, ov) {
      if (!this.sessions[sessionId]) {
        this.sessions[sessionId] = {
          session: null,
          myUserName: null,
          OV: null,
          publisher: null,
          subscriber: null,
        };
      }
      this.sessions[sessionId].OV = ov;
    },
    // OV 설정하는 action
    setPublisher(sessionId, publisher) {
      if (!this.sessions[sessionId]) {
        this.sessions[sessionId] = {
          session: null,
          myUserName: null,
          OV: null,
          publisher: null,
          subscriber: null,
        };
      }
      this.sessions[sessionId].publisher = publisher;
    },
    // 구독자 설정하는 action
    setSubscriber(sessionId, subscriber) {
      if (!this.sessions[sessionId]) {
        this.sessions[sessionId] = {
          session: null,
          myUserName: null,
          OV: null,
          publisher: null,
          subscriber: null,
        };
      }
      this.sessions[sessionId].subscriber = subscriber;
    },
  },
  getters: {
    // 세션 정보를 가져오는 getter
    getSessionById: (state) => (sessionId) => {
      return state.sessions[sessionId];
    },
    // 유저 이름을 가져오는 getter
    getMyUserNameById: (state) => (sessionId) => {
      return state.sessions[sessionId]?.myUserName;
    },
    // OV 가져오는 getters
    getOvById: (state) => (sessionId) => {
      return state.sessions[sessionId]?.OV;
    },
    // publisher 가져오는 getters
    getPublisherById: (state) => (sessionId) => {
      return state.sessions[sessionId]?.publisher;
    },

    // subscriber 가져오는 getters
    getSubscriberById: (state) => (sessionId) => {
      return state.sessions[sessionId]?.subscriber;
    },
  },
});
