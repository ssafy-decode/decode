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
          userName: this.userName,
          content: this.message,
        };
        this.stompClient.send('/app/chat/message', JSON.stringify(msg), {});
      }
    },
    connect() {
      const serverURL = 'http://70.12.247.49/chattings';
      let socket = new SockJS(serverURL);
      this.stompClient = Stomp.over(socket);
      console.log(`소켓 연결을 시도합니다. 서버 주소: ${serverURL}`);
      this.stompClient.connect(
        {},
        (frame) => {
          this.connected = true;
          console.log('소켓 연결 성공', frame);
          // 서버의 메시지 전송 endpoint를 구독합니다.
          // 이런형태를 pub sub 구조라고 합니다.
          this.stompClient.subscribe('/topic/chat/room', (res) => {
            console.log('구독으로 받은 메시지 입니다.', res.body);

            // 받은 데이터를 json으로 파싱하고 리스트에 넣어줍니다.
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
  },
};
