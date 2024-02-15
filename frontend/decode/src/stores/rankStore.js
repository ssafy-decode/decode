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
    const userRank = ref({});

    const needExp = {
      bronze: 100,
      silver: 250,
      gold: 450,
      platinum: 700,
      diamond: 1000,
      rubi: 1500,
    };

    // 함수
    // 경험치순 모든 회원 목록 조회
    const getRank = async () => {
      await axios.get(`/rank`).then((res) => {
        userStore.accessToken = userStore.parseToken(res);
        if (res.data.status === 'OK') {
          rankList.value = res.data.data;
          rankList.value.sort((a, b) => b.exp - a.exp);
        }
      });
    };

    const getUserRank = async (userId) => {
      await axios.get(`/rank/${userId}`).then((res) => {
        userStore.accessToken = userStore.parseToken(res);
        if (res.data.status === 'OK') {
          userRank.value = res.data.data;
        }
      });
    };

    const getNeedExp = (tier) => {
      return needExp[tier];
    };

    const getNextTier = (tier) => {
      const nextTier = ref();
      switch (tier) {
        case 'bronze':
          nextTier.value = 'silver';
          break;
        case 'silver':
          nextTier.value = 'gold';
          break;
        case 'gold':
          nextTier.value = 'platinum';
          break;
      }
      return nextTier.value;
    };

    // computed
    const handleRank = computed(() => rankList.value);
    const handleUserRank = computed(() => userRank.value);

    // 반환
    return { rankList, getRank, getNeedExp, handleRank, needExp, getNextTier, getUserRank, userRank, handleUserRank };
  },
  {
    persist: {
      storage: sessionStorage,
    },
  },
);

export { useRankStore };
