import { ref, computed } from 'vue';
import { defineStore } from 'pinia';
import axios from '@/utils/common-axios';
import { useUserStore } from './userStore';

const useFollowStore = defineStore(
  'useFollowStore',
  () => {
    // 스토어
    const userStore = useUserStore();

    // 배열
    const followerList = ref([]); // 팔로워 목록
    const followingList = ref([]); // 팔로잉 목록

    // T/F
    const isFollow = ref(false); // 로그인 유저가 그 대상을 팔로우했는지 여부 T/F

    // 함수
    // 특정 회원을 팔로우하기
    const toFollow = async (userid) => {
      await axios
        .post(`/follow/${userid}`, {
          headers: {
            Authorization: `Bearer ${userStore.accessToken.value}`,
          },
        })
        .then((res) => {
          userStore.accessToken.value = userStore.parseToken(res);
          const response = res.data;
          if (res.data.status !== 'BAD_REQUEST') {
            if (userid === loginUserId) {
              alert('본인을 팔로우할 수 없습니다.');
              return;
            }
            isFollowOrNot(userid); // 팔로우 여부 확인 (T/F)
            if (isFollow.value) {
              alert('이미 팔로우하고 있습니다.');
              return;
            } else {
              isFollow.value = true;
            }
          }
        });
    };

    // 팔로우 여부 확인 (T/F)
    const isFollowOrNot = async (userid) => {
      await axios
        .get(`/isfollow/${userid}`, {
          headers: {
            Authorization: `Bearer ${userStore.accessToken.value}`,
          },
        })
        .then((res) => {
          userStore.accessToken.value = userStore.parseToken(res);
          isFollow.value = res.data;
        });
    };

    // 팔로우하고 있는 특정 회원을 팔로우취소
    const unFollow = async (userid) => {
      await axios
        .delete(`/follow/${userid}`, {
          headers: {
            Authorization: `Bearer ${userStore.accessToken.value}`,
          },
        })
        .then((res) => {
          userStore.accessToken.value = userStore.parseToken(res);
          isFollowOrNot(userid); // 팔로우 여부 확인 (T/F)
          if (res.data.status !== 'BAD_REQUEST') {
            if (isFollow.value) {
              isFollow.value = false;
            } else {
              alert('팔로우를 취소할 수 없습니다.');
              return;
            }
          }
        });
    };

    // computed
    const handleFollowers = computed(() => followerList.value);
    const handleFollowings = computed(() => followingList.value);

    // 반환
    return {
      followerList,
      followingList,
      isFollow,
      toFollow,
      isFollowOrNot,
      unFollow,
      handleFollowers,
      handleFollowings,
    };
  },
  {
    persist: {
      storage: sessionStorage,
    },
  },
);

export { useFollowStore };
