<template>
  <div class="pa-5" rounded style="color: #575757; font-weight: bold">
    <v-card
      class="mx-auto px-4 py-8"
      max-width="1036"
      style="text-align: center; background-color: #f3f3f3; border-radius: 31px; border: 15px solid #d9d9d9"
    >
      <v-row>
        <v-col :cols="2">
          <!-- 프로필 사진, 커마 아이콘 추후 수정-->
          {{ userStore.loginUserProfile.nickname }}
          <br />
          <br />
          <!-- <input type="file" accept="image/*" @change="handleFileChange" style="display: none" ref="fileInput" /> -->
          <!-- <img style="width: 70%" :src="'./' + userStore.loginUserProfile.profileImg + '.png'" /> -->
          <img style="width: 70%" src="../default.png" />
          <br />
          {{ userStore.loginUserProfile.tier }}
          <br />
          ((커마아이콘))
        </v-col>

        <v-col :cols="1"
          ><div v-if="userStore.tagIdList.length > 0">
            <v-chip v-for="tag in userStore.tagIdList" :key="tag" label color="primary" class="mr-2 mb-2">
              {{ tagName[tag] }}</v-chip
            >
          </div>
          <div v-else><br /><br />기술<br />스택<br />없음</div></v-col
        >

        <v-col :cols="6">
          출석 스트릭
          <AttendanceLog :uid="uid" />
        </v-col>

        <v-col :cols="3">
          <ExpLog :uid="uid" />
        </v-col>
      </v-row>

      <div class="buttons">
        <span>
          <router-link to="/inventory">
            <v-btn class="btn" color="#62C0A6" size="x-large" type="submit" variant="elevated" style="color: #000000">
              내 아이템
            </v-btn>
          </router-link>
          &nbsp;
          <router-link to="/checkpwd">
            <v-btn class="btn" color="#62C0A6" size="x-large" type="submit" variant="elevated" style="color: #000000">
              회원정보 수정
            </v-btn>
          </router-link>
        </span>
      </div>
    </v-card>
  </div>
</template>

<script setup>
import { ref, computed, onBeforeMount } from 'vue';
import { useUserStore } from '@/stores/userStore';
import AttendanceLog from '@/components/profile/AttendanceLog.vue';
import ExpLog from '@/components/profile/ExpLog.vue';
import { storeToRefs } from 'pinia';
import MyProfileWindow from '@/components/profile/MyProfileWindow.vue';

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

// const isFetched = ref(false); // data를 가져오고 나서 렌더링하도록
// const loading = computed(() => !isFetched.value); // 가져오기 전까지는 로딩 상태로
// onBeforeMount(async () => {
//   await userStore.myProfile();
//   await userStore.getTagNumList(userStore.loginUserId);
//   userStore.setQList(userStore.loginUserId);
//   const qList = userStore.qList;
//   const qListLength = userStore.qListLength;
//   userStore.setAList(userStore.loginUserId);
//   const aList = userStore.aList;
//   const aListLength = userStore.aListLength;
//   userStore.getRank();
//   userStore.setFollowerList(userStore.loginUserId);
//   userStore.setFollowingList(userStore.loginUserId);
//   matchId(userStore.followerList, followerProfiles);
//   matchId(userStore.followingList, followingProfiles);
//   setTimeout(() => {
//     isFetched.value = true; // 가져오면 렌더링
//   }, 1000); // 1초 후에
// });
//
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
