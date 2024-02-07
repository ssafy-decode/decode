<template>
  <v-container class="user-info">
    <v-row v-if="userStore && profile">
      <v-col cols="2">
        <v-avatar size="100">
          <img src="../../default.png" alt="Profile" />
        </v-avatar>
        <p>{{ profile.nickname}}</p>
        <p>100위(상위 10%)</p>
      </v-col>
      <v-col cols="2">
        <img src="../../커마아이콘샘플.png" width="50px" />
      </v-col>
      <v-col cols="8">
        <v-row>
          <v-col cols="2">
            <p> 100 </p>
            <p> 답변수 </p>
          </v-col>
          <v-col cols="2">
            <p> 100 </p>
            <p> 채택수 </p>
          </v-col>
          <v-col cols="2">
            <p> 100 </p>
            <p> 팔로워 </p>
          </v-col>
          <v-col cols="2">
            <p> 100 </p>
            <p> 팔로우 </p>
          </v-col>
        </v-row>
        <v-row justify="center">
          <v-col sm="10">
            <v-progress-linear
            color="green" 
            height="20"
            :model-value="profile.exp / 180 *100"
            >
          </v-progress-linear>
          </v-col>
        </v-row>
      </v-col>
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
  neededExp.value = nextTierExp();
  moreExp.value = neededExp.value - profile.exp; // 다음 티어까지 필요한 경험치 차액
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
