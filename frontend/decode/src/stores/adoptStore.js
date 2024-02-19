import { ref, computed } from 'vue';
import { defineStore } from 'pinia';
import axios from '@/utils/common-axios';
import { useUserStore } from './userStore';

const useAdoptStore = defineStore(
  'useAdoptStore',
  () => {
    const userStore = useUserStore();
    const adoptList = ref([]);

    // 현재 질문의 채택 중인 답변 번호의 목록을 불러옴
    const setAdoptList = async (questionId) => {
      await axios.get(`/answer/adopt/${questionId}`).then((res) => {
        adoptList.value = [];
        adoptList.value = res.data.data;
      });
    };

    const addAdopt = async (userId, answerId) => {
      let data = {
        userId,
        answerId,
      };
      await axios
        .post(`/answer/adopt`, data, {
          headers: {
            Authorization: `Bearer ${userStore.accessToken}`,
          },
        })
        .then((res) => {
          alert('답변을 채택하였습니다!');
        })
        .catch((error) => {
          alert('답변 채택 중 오류가 발생했습니다.');
        });
    };

    // // computed
    const handleAdoptList = computed(() => adoptList.value);
    const handleAdoptState = computed(() => isAdopted.value);

    // 반환
    return {
      adoptList,
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
