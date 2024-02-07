import { ref, computed } from 'vue';
import { defineStore } from 'pinia';
import axios from '@/utils/common-axios';
import { useUserStore } from './userStore';
import { storeToRefs } from 'pinia';
const useFollowStore = defineStore(
  'useFollowStore',
  () => {
    // 배열
    const followerList = ref([]); // 팔로워 목록
    const followingList = ref([]); // 팔로잉 목록

    // T/F
    const isFollow = ref(false); // 로그인 유저가 그 대상을 팔로우했는지 여부 T/F

    // 함수
    const setFollowerList = async (userid) => {
      await axios.get(`/followerlist/${userid}`).then((res) => {
        followerList.value = [];
        res.data.data.forEach((follower) => {
          followerList.value.push(follower);
        });
      });
    };

    const setFollowingList = async (userid) => {
      await axios.get(`/followinglist/${userid}`).then((res) => {
        followingList.value = [];
        res.data.data.forEach((following) => {
          followingList.value.push(following);
        });
      });
    };

    // 특정 회원을 팔로우하기
    // const toFollow = async (userid) => {
    //   await axios
    //     .post(`/follow/${userid}`, {
    //       headers: {
    //         Authorization: `Bearer ${userStore.accessToken.value}`,
    //       },
    //     })
    //     .then((res) => {
    //       userStore.accessToken.value = userStore.parseToken(res);
    //       const response = res.data;
    //       if (res.data.status !== 'BAD_REQUEST') {
    //         if (userid === loginUserId) {
    //           alert('본인을 팔로우할 수 없습니다.');
    //           return;
    //         }
    //         isFollowOrNot(userid); // 팔로우 여부 확인 (T/F)
    //         if (isFollow.value) {
    //           alert('이미 팔로우하고 있습니다.');
    //           return;
    //         } else {
    //           isFollow.value = true;
    //         }
    //       }
    //     });
    // };

    // 팔로우 여부 확인 (T/F)
    // const isFollowOrNot = async (userid) => {
    //   await axios
    //     .get(`/isfollow/${userid}`, {
    //       headers: {
    //         Authorization: `Bearer ${userStore.accessToken.value}`,
    //       },
    //     })
    //     .then((res) => {
    //       userStore.accessToken.value = userStore.parseToken(res);
    //       isFollow.value = res.data;
    //     });
    // };

    // 팔로우하고 있는 특정 회원을 팔로우취소
    const unFollow = async (userid, token) => {
      await axios
        .delete(`/follow/${userid}`, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        })
        .then((res) => {})
        .catch((error) => {
          console.error('unFollow error:', error);
          alert('팔로우 취소에 실패했습니다.');
        });
    };

    // computed
    const handleFollowerList = computed(() => followerList.value);
    const handleFollowingList = computed(() => followingList.value);

    // 반환
    return {
      followerList,
      followingList,
      isFollow,

      unFollow,
      setFollowerList,
      setFollowingList,
      handleFollowerList,
      handleFollowingList,
    };
  },
  {
    persist: true,
  },
);

export { useFollowStore };
