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
        .then((res) => {})
        .catch((error) => {});
    };

    const deleteRecommend = async (answerId) => {
      await axios
        .delete(`/answer/unrecommend/${answerId}`, {
          headers: {
            Authorization: `Bearer ${userStore.accessToken}`,
          },
        })
        .then((res) => {})
        .catch((error) => {});
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
