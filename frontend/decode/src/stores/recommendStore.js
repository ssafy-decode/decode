import { ref, computed } from 'vue';
import { defineStore } from 'pinia';
import axios from '@/utils/common-axios';
import { useUserStore } from './userStore';

const useRecommendStore = defineStore(
  'useRecommendStore',
  () => {
    const userStore = useUserStore();

    const recommendList = ref([]);
    const isRecommended = ref(false);

    // 로그인 유저의 추천한 답변 번호의 목록을 불러옴
    const setRecommendList = async (questionId, userId) => {
      await axios.get(`/answer/recommend/${userId}`).then((res) => {
        recommendList.value = [];
        isRecommended.value = false;
        res.data.data.forEach((question) => {
          recommendList.value.push(question.id);
          if (parseInt(questionId) === parseInt(question.id)) {
            isRecommended.value = true;
          }
        });
      });
    };

    const addRecommend = async (userId, questionId) => {
      let data = {
        userId,
        questionId,
      };
      await axios
        .post(`/answer/recommend`, data, {
          headers: {
            Authorization: `Bearer ${userStore.accessToken}`,
          },
        })
        .then((res) => {
          isRecommended.value = true;
        })
        .catch((error) => {
          console.error('Recommend error:', error);
        });
    };

    const deleteRecommend = async (questionId) => {
      await axios
        .delete(`/answer/unrecommend/${questionId}`, {
          headers: {
            Authorization: `Bearer ${userStore.accessToken}`,
          },
        })
        .then((res) => {
          isRecommended.value = false;
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
      isRecommended,
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
