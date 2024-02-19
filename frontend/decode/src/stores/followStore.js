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
    const isFollow = ref(false);

    // 함수
    const setFollowerList = async (userid) => {
      await axios
        .get(`/followerlist/${userid}`)
        .then((res) => {
          followerList.value = [];
          res.data.data.forEach((follower) => {
            followerList.value.push(follower);
          });
        })
        .catch((error) => {
          if (error.response && error.response.status === 401) {
            router.push({ name: 'mainview' });
            alert('접근 권한이 없습니다.');
          }
        });
    };

    const setFollowingList = async (userid) => {
      await axios
        .get(`/followinglist/${userid}`)
        .then((res) => {
          followingList.value = [];
          res.data.data.forEach((following) => {
            followingList.value.push(following);
          });
        })
        .catch((error) => {
          if (error.response && error.response.status === 401) {
            router.push({ name: 'mainview' });
            alert('접근 권한이 없습니다.');
          }
        });
    };

    const follow = async (userid, token) => {
      await axios
        .post(
          `/follow/${userid}`,
          {},
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          },
        )
        .then((res) => {
          isFollow.value = true;
        })
        .catch((error) => {
          alert('팔로우에 실패했습니다.');
        });
    };
    const setFollowState = (value) => {
      isFollow.value = value;
    };

    const getFollowState = async (userid, token) => {
      await axios
        .get(`/isfollow/${userid}`, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        })
        .then((res) => {
          setFollowState(res.data.data);
        });
    };

    // 팔로우하고 있는 특정 회원을 팔로우취소
    const unFollow = async (userid, token) => {
      await axios
        .delete(`/follow/${userid}`, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        })
        .then((res) => {
          isFollow.value = false;
        })
        .catch((error) => {
          alert('팔로우 취소에 실패했습니다.');
        });
    };

    // computed
    const handleFollowerList = computed(() => followerList.value);
    const handleFollowingList = computed(() => followingList.value);
    const handleFollowState = computed(() => isFollow.value);

    // 반환
    return {
      followerList,
      followingList,
      isFollow,
      follow,
      unFollow,
      setFollowerList,
      setFollowingList,
      getFollowState,
      handleFollowerList,
      handleFollowingList,
      handleFollowState,
    };
  },
  {
    persist: true,
  },
);

export { useFollowStore };
