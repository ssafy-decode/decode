import { ref, computed } from 'vue';
import { defineStore } from 'pinia';
import axios from '@/utils/common-axios';

const useChatStore = defineStore('useChatStore', () => {
  const createChatRoom = async (roomName, roomDescription, creator) => {
    try {
      const res = await axios.post('/chat/room', {
        roomName: roomName,
        roomDescription: roomDescription,
        creator: creator,
      });
      console.log(res.data.data);
      return res.data.data;
    } catch (err) {
      console.log(err);
    }
  };

  const getRoomList = async () => {
    try {
      const res = await axios.get('/chat/rooms');
      console.log(res.data.data);
      return res.data.data;
    } catch (err) {
      console.log(err);
    }
  };

  const fetchChatHistory = async (roomId) => {
    try {
      const res = await axios.get(`/chat/room/${roomId}/messages`);
      console.log(res.data.data);
      return res.data.data;
    } catch (err) {
      console.log(err);
    }
  };

  const fetchRoomList = async () => {
    try {
      const roomList = await getRoomList(); // getRoom은 chatStore에서 방 정보를 가져오는 메소드로 가정합니다.
      console.log(roomList)
      if (roomList) {
        console.log('방 리스트를 성공적으로 가져왔습니다:', roomList);
      } else {
        console.log('존재하는 방들이 없습니다.');
      }
      return roomList
    } catch (error) {
      console.error('방 정보를 가져오는 중 오류가 발생했습니다:', error);
    }
  };

  return { createChatRoom, fetchRoomList,fetchChatHistory };
});

export { useChatStore };
