import { ref, computed } from 'vue';
import { defineStore } from 'pinia';
import axios from '@/utils/common-axios';
import { useUserStore } from './userStore';

const useRecommendStore = defineStore(
  'useRecommendStore',
  () => {
    const userStore = useUserStore();
    const recommendList = ref([]);

    // 로그인 유저의 추천한 답변 번호의 목록을 불러옴
    const setRecommendList = async (answerId, userId) => {
      await axios.get(`/answer/recommend/${userId}`).then((res) => {
        recommendList.value = [];
        res.data.data.forEach((answer) => {
          recommendList.value.push(answer.id);
        });
      });
    };

    const addRecommend = async (userId, answerId) => {
      console.log('개추 누름');
      let data = {
        userId,
        answerId,
      };
      await axios
        .post(`/answer/recommend`, data, {
          headers: {
            Authorization: `Bearer ${userStore.accessToken}`,
          },
        })
        .then((res) => {
          console.log('개추 눌림');
          console.log('답변 추천');
        })
        .catch((error) => {
          console.error('Recommend error:', error);
        });
    };

    const deleteRecommend = async (answerId) => {
      console.log('추취 누름');
      await axios
        .delete(`/answer/unrecommend/${answerId}`, {
          headers: {
            Authorization: `Bearer ${userStore.accessToken}`,
          },
        })
        .then((res) => {
          console.log('답변 추천 취소');
          console.log('추취 눌림');
        })
        .catch((error) => {
          console.error('deleteRecommend error:', error);
        });
    };

    // // computed
    const handleRecommendList = computed(() => recommendList.value);
    const handleRecommendState = computed(() => isRecommended.value);

    // 반환
    return {
      recommendList,
      setRecommendList,
      addRecommend,
      deleteRecommend,
      handleRecommendList,
      handleRecommendState,
    };
  },
  {
    persist: true,
  },
);

export { useRecommendStore };
