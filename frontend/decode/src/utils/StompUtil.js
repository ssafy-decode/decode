import Stomp from 'stompjs';
import SockJS from 'sockjs-client';
export default {
  name: 'StompUtil',
  data() {
    return {
      userName: '',
      message: '',
      recvList: [],
    };
  },
  methods: {
    sendMessage(e) {
      if (e.keyCode === 13 && this.userName !== '' && this.message !== '') {
        this.send();
        this.message = '';
      }
    },
    send() {
      console.log('Send message:' + this.message);
      if (this.stompClient && this.stompClient.connected) {
        const msg = {
          userId: this.userId,
          messge: this.message,
        };
        this.stompClient.send('/app/chat/message', JSON.stringify(msg), {});
      }
    },
    connect(roomId) {
      const serverURL = process.env.VUE_APP_BACKEND_URL;
      let socket = new SockJS(serverURL);
      this.stompClient = Stomp.over(socket);
      console.log(`소켓 연결을 시도합니다. 서버 주소: ${serverURL}`);
      this.stompClient.connect(
        {},
        (frame) => {
          this.connected = true;
          console.log('소켓 연결 성공', frame);
          this.stompClient.subscribe(`/topic/chat/room/${roomId}`, (res) => {
            this.recvList.push(JSON.parse(res.body));
          });
        },
        (error) => {
          // 소켓 연결 실패
          console.log('소켓 연결 실패', error);
          this.connected = false;
        },
      );
    },
    unsubscribe() {
      if (this.stompClient) {
        this.stompClient.disconnect();
        this.connected = false;
      }
    },
  },
};
