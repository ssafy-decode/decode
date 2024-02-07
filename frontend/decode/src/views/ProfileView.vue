<template>
  <Profile />
  <ProfileWindow :profile="handleUserProfile" />
  <!-- id 받아와서 넘기면 그 사람 프로필로 이동 -->
</template>

<script setup>
import { ref } from 'vue';
import ProfileWindow from '@/components/profile/ProfileWindow.vue';
import Profile from '@/components/profile/Profile.vue';
import { useUserStore } from '@/stores/userStore';
import { useProfileStore } from '@/stores/profileStore';
import { useTagStore } from '@/stores/tagStore';
import { useRankStore } from '@/stores/rankStore';

const userStore = useUserStore();
const profileStore = useProfileStore();
const tagStore = useTagStore();
const rankStore = useRankStore();
const { setUserProfile, setQList, setAList, setFollowerList, setFollowingList } = profileStore;
const { setTagNumList } = tagStore;
const { getRank } = rankStore;
// const {handleUserProfile} = storeREft~~

const followerProfiles = ref([]); // 팔로워 목록에 rank 포함한 새 배열
const followingProfiles = ref([]); // 팔로잉 목록에 rank 포함한 새 배열
// 경험치순 순위 목록의 회원번호와 팔로워/팔로잉 목록 회원번호 대조
const matchId = (list, profiles) => {
  list.forEach((user) => {
    const userRankListIdx = rankStore.rankList.findIndex((rank) => rank.id === user.id);
    if (userRankListIdx !== -1) {
      user.rank = userRankListIdx + 1;
      profiles.value.push(user);
    } else {
      user.rank = null;
    }
  });
};

// 추후 로그인 여부에 따라 집어넣는 id 변경해주기
const loginUserId = userStore.loginUserId;
// myProfile();
setUserProfile(loginUserId);
setTagNumList(loginUserId);
setQList(loginUserId);
setAList(loginUserId);
getRank();
const followerList = setFollowerList(loginUserId);
const followingList = setFollowingList(loginUserId);
matchId(followerList, followerProfiles);
matchId(followingList, followingProfiles);
</script>

<style scoped></style>
