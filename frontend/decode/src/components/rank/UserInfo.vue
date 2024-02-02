<template>
  <v-container class="user-info">
    <v-row v-if="userStore && userStore.loginUserProfile">
      <!-- 유저 랭킹 등수 -->
      <v-col cols="2">
        <!-- <v-card-title class="text-center">랭킹</v-card-title> -->
        <br />
        <img width="70px" src="../../default.png" />
        <br />
        <br />
        <div class="text-center">
          랭킹 <br />
          상위 {{ rankPercentile }}%
        </div>
      </v-col>

      <!-- 유저 티어 -->
      <v-col cols="2" v-if="userStore.loginUserProfile.tier">
        <!-- <v-card-title class="text-center">티어</v-card-title> -->
        <br />
        <div class="text-center">
          <img width="70px;" src="../../커마아이콘샘플.png" /> <br />
          {{ userStore.loginUserProfile.tier }}
        </div>
      </v-col>

      <!-- 유저 닉네임 및 포인트 -->
      <v-col
        cols="2"
        v-if="userStore.loginUserProfile.nickname !== undefined && userStore.loginUserProfile.point !== undefined"
      >
        <br />
        <!-- <v-card-title class="text-center">닉네임 & 포인트</v-card-title> -->
        <div class="text-center">
          {{ userStore.loginUserProfile.nickname }} <br />
          보유 포인트: {{ userStore.loginUserProfile.point }}p
        </div>
      </v-col>

      <v-col cols="2">
        <br />
        <div class="text-center">팔로워 / 팔로잉</div>
        <div class="text-center">{{ userStore.followerList.length }} / {{ userStore.followingList.length }}</div>
        <br />
        <!-- <v-card-title class="text-center">답변 수 & 채택 수</v-card-title> -->
        <div class="text-center">
          답변 수: {{ userStore.aListLength }}개
          <br />
          채택 수: 0개
          <!-- {{ userAnswerCount }} 답변, {{ userSelectionCount }} 채택 -->
        </div>
      </v-col>

      <v-col cols="4" v-if="userStore.loginUserProfile.exp">
        <!-- 내랭크 경험치 현황 -->
        <div class="text-center">내 랭크</div>
        <div>((경험치 그래프))</div>
        <br />
        <div>
          현재 티어명 &nbsp;&nbsp; {{ userStore.loginUserProfile.exp }} / {{ neededExp }} &nbsp;&nbsp; 다음 티어명
        </div>
        <div class="text-center">다음 {티어}까지 {{ moreExp }}!</div>
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useUserStore } from '@/stores/userStore';

const userStore = useUserStore();
const rankPercentile = ref(0);
const neededExp = ref(0);
const moreExp = ref(0);

onMounted(() => {
  const showProfile = async () => {
    await userStore.myProfile();
    await userStore.setAList(userStore.loginUserId);
    rankPercentile.value = countRankPercent();
  };
  showProfile();

  neededExp.value = nextTierExp();
  moreExp.value = neededExp.value - userStore.loginUserProfile.exp; // 다음 티어까지 필요한 경험치 차액
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
