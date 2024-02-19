import { ref, computed } from 'vue';
import { defineStore } from 'pinia';
import axios from '@/utils/common-axios';
import { useUserStore } from './userStore';
const useChatStore = defineStore('useChatStore', () => {
  const userStore = useUserStore();
  const createChatRoom = async (roomName, roomDescription, creator) => {
    try {
      const res = await axios.post(
        '/chat/room',
        {
          roomName: roomName,
          roomDescription: roomDescription,
          creator: creator,
        },
        {
          headers: {
            Authorization: `Bearer ${userStore.accessToken}`,
          },
        },
      );
      return res.data.data;
    } catch (err) {}
  };
  const deleteChatRoom = async (roomId, userId) => {
    try {
      const res = await axios.post(
        `/chat/room/sub/delete`,
        {
          roomId: roomId,
          userId: userId,
        },
        {
          headers: {
            Authorization: `Bearer ${userStore.accessToken}`,
          },
        },
      );
      return res.data;
    } catch (err) {}
  };
  const subChatRoom = async (roomId) => {
    try {
      const res = await axios.post(
        `/chat/room/${roomId}/sub`,
        {},
        {
          headers: {
            Authorization: `Bearer ${userStore.accessToken}`,
          },
        },
      );
      return res.data;
    } catch (err) {}
  };
  const getSubRoomList = async () => {
    try {
      const res = await axios.get('/chat/rooms/sub', {
        headers: {
          Authorization: `Bearer ${userStore.accessToken}`,
        },
      });
      return res.data.data;
    } catch (err) {}
  };

  const getRoomList = async () => {
    try {
      const res = await axios.get('/chat/rooms');
      return res.data.data;
    } catch (err) {}
  };

  const fetchChatHistory = async (roomId) => {
    try {
      const res = await axios.get(`/chat/room/${roomId}/messages`);
      return res.data.data;
    } catch (err) {}
  };

  const fetchRoomList = async () => {
    try {
      const roomList = await getRoomList(); // getRoom은 chatStore에서 방 정보를 가져오는 메소드로 가정합니다.
      if (roomList) {
      } else {
      }
      return roomList;
    } catch (error) {}
  };
  const fetchMyRoomList = async () => {
    try {
      const roomList = await getSubRoomList(); // getRoom은 chatStore에서 방 정보를 가져오는 메소드로 가정합니다.
      if (roomList) {
      } else {
      }
      return roomList;
    } catch (error) {}
  };
  return {
    deleteChatRoom,
    createChatRoom,
    fetchRoomList,
    fetchChatHistory,
    subChatRoom,
    getSubRoomList,
    fetchMyRoomList,
  };
});

export { useChatStore };
