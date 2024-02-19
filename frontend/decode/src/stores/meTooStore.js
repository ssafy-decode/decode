import { ref, computed } from 'vue';
import { defineStore } from 'pinia';
import axios from '@/utils/common-axios';
import { useUserStore } from './userStore';

const useMeTooStore = defineStore(
  'useMeTooStore',
  () => {
    const userStore = useUserStore();

    const meTooList = ref([]);
    const isMeTooed = ref(false);

    // 로그인 유저의 "나도궁금해요!" 중인 질문 번호의 목록을 불러옴
    const setMeTooList = async (questionId, userId) => {
      await axios.get(`/metoo/${userId}`).then((res) => {
        meTooList.value = [];
        isMeTooed.value = false;
        res.data.data.forEach((question) => {
          meTooList.value.push(question.id);
          if (parseInt(questionId) === parseInt(question.id)) {
            isMeTooed.value = true;
          }
        });
      });
    };

    const addMeToo = async (userId, questionId) => {
      let data = {
        userId,
        questionId,
      };
      await axios
        .post(`/metoo`, data, {
          headers: {
            Authorization: `Bearer ${userStore.accessToken}`,
          },
        })
        .then((res) => {
          isMeTooed.value = true;
          alert('나도 궁금해요!에 추가했습니다!');
        })
        .catch((error) => {
          alert('나도 궁금해요! 추가에 실패했습니다.');
        });
    };

    const deleteMeToo = async (questionId) => {
      await axios
        .delete(`/metoo/${questionId}`, {
          headers: {
            Authorization: `Bearer ${userStore.accessToken}`,
          },
        })
        .then((res) => {
          isMeTooed.value = false;
          alert('나도 궁금해요!에서 제외되었습니다.');
        })
        .catch((error) => {
          alert('나도 궁금해요! 취소 실패');
        });
    };

    // // computed
    const handleMeTooList = computed(() => meTooList.value);
    const handleMeTooState = computed(() => isMeTooed.value);

    // 반환
    return {
      meTooList,
      isMeTooed,
      setMeTooList,
      addMeToo,
      deleteMeToo,
      handleMeTooList,
      handleMeTooState,
    };
  },
  {
    persist: true,
  },
);

export { useMeTooStore };
