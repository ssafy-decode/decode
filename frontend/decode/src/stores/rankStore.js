import { ref, computed } from 'vue';
import { defineStore } from 'pinia';
import router from '@/router';
import axios from '@/utils/common-axios';
import { useUserStore } from './userStore';

const useRankStore = defineStore(
  'useRankStore',
  () => {
    // 스토어
    const userStore = useUserStore();

    // 배열
    const rankList = ref([]); // 랭킹 목록

    // 함수
    // 경험치순 모든 회원 목록 조회
    const getRank = async () => {
      await axios.get(`/rank`).then((res) => {
        userStore.accessToken.value = userStore.parseToken(res);
        if (res.data.status === 'OK') {
          rankList.value = res.data.data;
        }
      });
    };

    // computed
    const handleRank = computed(() => rankList.value);

    // 반환
    return { rankList, getRank, handleRank };
  },
  {
    persist: {
      storage: sessionStorage,
    },
  },
);

export { useRankStore };
