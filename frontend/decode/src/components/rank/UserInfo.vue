<template>
  <v-container class="user-info">
    {{ profile }}
    <v-row v-if="userStore && profile">
      <v-col cols="2">
        <br />
        <img width="70px" src="../../default.png" />
        <br />
        <br />
        <div class="text-center">
          랭킹 <br />
          상위 {{ rankPercentile }}%
        </div>
      </v-col>

      <v-col cols="2" v-if="profile.tier">
        <br />
        <div class="text-center">
          <img width="70px;" src="../../커마아이콘샘플.png" /> <br />
          {{ profile.tier }}
        </div>
      </v-col>

      <v-col
        cols="2"
        v-if="profile.nickname !== undefined && profile.point !== undefined"
      >
        <br />
        <div class="text-center">
          {{ profile.nickname }} <br />
          보유 포인트: {{ profile.point }}p
        </div>
      </v-col>

      <!-- <v-col cols="2">
        <br />
        <div class="text-center">팔로워 / 팔로잉</div>
        <div class="text-center">{{ profile.followerList.length }} / {{ profile.followingList.length }}</div>
        <br />
        <div class="text-center">
          답변 수: {{ profile.aListLength }}개
          <br />
          채택 수: 0개
        </div>
      </v-col> -->

      <!-- <v-col cols="4" v-if="profile.exp">
        <div class="text-center">내 랭크</div>
        <div>((경험치 그래프))</div>
        <br />
        <div>
          현재 티어명 &nbsp;&nbsp; {{ profile.exp }} / {{ neededExp }} &nbsp;&nbsp; 다음 티어명
        </div>
        <div class="text-center">다음 {티어}까지 {{ moreExp }}!</div>
      </v-col> -->
    </v-row>
  </v-container>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useUserStore } from '@/stores/userStore';
import { useProfileStore } from '@/stores/profileStore';
import { storeToRefs } from 'pinia';


const userStore = useUserStore();
const profileStore = useProfileStore();
const rankPercentile = ref(0);
const neededExp = ref(0);
const moreExp = ref(0);
const { handleUserProfile: profile } = storeToRefs(profileStore);
console.log(profile.value)
onMounted(() => {
  const showProfile = async () => {
    rankPercentile.value = countRankPercent();
  };
  showProfile();
  console.log("profile: " + profile.value);
  // neededExp.value = nextTierExp();
  // moreExp.value = neededExp.value - userStore.loginUserProfile.exp; // 다음 티어까지 필요한 경험치 차액
});

const countRankPercent = () => {
  // 상위 몇 % 계산
  return 50; // 임의로
};

const nextTierExp = () => {
  // 다음 티어까지 필요한 총 경험치
  return 100; //임의로
};
</script>

<style>
.user-info {
  display: flex;
  margin-top: 20px;
}
</style>
