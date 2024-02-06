<template>
  <!-- <MyProfile /> -->
  <!-- <MyProfileWindow /> -->
  <div>123</div>
</template>

<script setup>
import { ref, computed, onBeforeMount } from 'vue';
import { useUserStore } from '@/stores/userStore';
import AttendanceLog from '@/components/profile/AttendanceLog.vue';
import ExpLog from '@/components/profile/ExpLog.vue';
import { storeToRefs } from 'pinia';
import MyProfileWindow from '@/components/profile/MyProfileWindow.vue';
import MyProfile from '@/components/profile/MyProfile.vue';

const userStore = useUserStore();
const { loginUserId: uid } = storeToRefs(userStore);

// DB에 수정된 번호를 다시 태그명으로 전환
const tagName = {
  1: 'python',
  2: 'java',
  3: 'C++',
  4: 'javascript',
  5: 'django',
  6: 'spring',
  7: 'spring boot',
  8: 'kotlin',
  9: 'sql',
  10: 'react',
  11: 'vue',
  12: 'C#',
};

const followerProfiles = ref([]); // 팔로워 목록에 rank 포함한 새 배열
const followingProfiles = ref([]); // 팔로잉 목록에 rank 포함한 새 배열

// 경험치순 순위 목록의 회원번호와 팔로워/팔로잉 목록 회원번호 대조
// const matchId = (list, profiles) => {
//   list.forEach((user) => {
//     const userRankListIdx = userStore.rankList.findIndex((rank) => rank.id === user.id);
//     if (userRankListIdx !== -1) {
//       user.rank = userRankListIdx + 1;
//       profiles.value.push(user);
//     } else {
//       user.rank = null;
//     }
//   });
// };

onBeforeMount(async () => {
  const loginUserId = await userStore.loginUserId;
  // console.log(loginUserId);
  // const myProfile = await userStore.myProfile();
  // await userStore.setTagNumList(loginUserId);

  try {
    await userStore.setQList(loginUserId);
    console.log('qList : ', userStore.qList.value);
  } catch (error) {
    console.error('Error setting question list:', error);
  }
  // let qListLength = qList.length;
  // const aList = userStore.getAList(loginUserId);
  // let aListLength = aList.length;

  // userStore.getRank();
  // const followerList = await userStore.getFollowerList(loginUserId);
  // const followingList = await userStore.getFollowingList(loginUserId);

  // matchId(followerList, followerProfiles);
  // matchId(followingList, followingProfiles);
  // console.log('qList : ', qList);
  // console.log('aList : ', aList);
  // console.log('tagNumList : ', tagNumList);
  // console.log('myProfile : ', myProfile);
  // console.log('followerList : ', followerList);
  // console.log('followingList : ', followingList);
});
</script>

<style scoped>
.buttons {
  text-align: end;
}
.btn {
  width: 110px;
  height: 28px;
  border-radius: 34px;
  font-size: smaller;
  font-weight: bold;
}
</style>
