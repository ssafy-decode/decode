import { ref, computed } from 'vue';
import { defineStore } from 'pinia';
import axios from '@/utils/common-axios';
import { useUserStore } from './userStore';

const useTagStore = defineStore(
  'useTagStore',
  () => {
    // 스토어
    const userStore = useUserStore();

    // 배열
    const tagIdList = ref([]); // 선택한 기술 태그 번호 목록
    const tagNum = {
      // 태그명 & 태그번호 매칭
      python: 1,
      java: 2,
      'C++': 3,
      javascript: 4,
      django: 5,
      spring: 6,
      'spring boot': 7,
      kotlin: 8,
      sql: 9,
      react: 10,
      vue: 11,
      'C#': 12,
    };

    // 함수
    // 특정 회원 선호 기술 스택 (번호) 조회
    const setTagNumList = async (userid) => {
      await axios
        .get(`/tag/${userid}`)
        .then((res) => {
          userStore.accessToken = userStore.parseToken(res);
          if (res.data.status === 'OK') {
            tagIdList.value = res.data.data.tagIdList;
          }
        })
        .catch((error) => {
          if (error.response && error.response.status === 401) {
            router.push({ name: 'mainview' });
            alert('접근 권한이 없습니다.');
          }
        });
    };

    // computed
    const handleTags = computed(() => tagIdList.value);

    // 반환
    return { tagNum, tagIdList, setTagNumList, handleTags };
  },
  {
    persist: {
      storage: sessionStorage,
    },
  },
);

export { useTagStore };
