import { ref, computed } from 'vue';
import { defineStore } from 'pinia';
import axios from '@/utils/common-axios';
import { useUserStore } from './userStore';

const useAdoptStore = defineStore(
  'useAdoptStore',
  () => {
    const userStore = useUserStore();

    const adoptList = ref([]);
    const isAdopted = ref(false);

    // 로그인 유저의 채택 중인 답변 번호의 목록을 불러옴
    const setAdoptList = async (questionId, userId) => {
      await axios.get(`/answer/adopt/${userId}`).then((res) => {
        adoptList.value = [];
        isAdopted.value = false;
        res.data.data.forEach((question) => {
          adoptList.value.push(question.id);
          if (parseInt(questionId) === parseInt(question.id)) {
            isAdopted.value = true;
          }
        });
      });
    };

    const addAdopt = async (userId, questionId) => {
      let data = {
        userId,
        questionId,
      };
      await axios
        .post(`/answer/adopt`, data, {
          headers: {
            Authorization: `Bearer ${userStore.accessToken}`,
          },
        })
        .then((res) => {
          isAdopted.value = true;
          alert('답변을 채택하였습니다!');
        })
        .catch((error) => {
          console.error('Adopt error:', error);
          alert('답변 채택 중 오류가 발생했습니다.');
        });
    };

    // // computed
    const handleAdoptList = computed(() => adoptList.value);
    const handleAdoptState = computed(() => isAdopted.value);

    // 반환
    return {
      adoptList,
      isAdopted,
      setAdoptList,
      addAdopt,
      handleAdoptList,
      handleAdoptState,
    };
  },
  {
    persist: true,
  },
);

export { useAdoptStore };
