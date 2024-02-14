<template>
  <v-container class="main">
    <div class="user-info-container">
      <UserInfo />
    </div>
    <br />
    <!-- <Search></Search> -->
    <RankingTable />
  </v-container>
</template>

<script setup>
import { ref, onMounted, onBeforeMount } from 'vue';
import { useProfileStore } from '@/stores/profileStore';
import { useUserStore } from '@/stores/userStore';
import { useRankStore } from '@/stores/rankStore';
import UserInfo from '@/components/rank/UserInfo.vue';
import RankingTable from '@/components/rank/RankingTable.vue';

const userStore = useUserStore();
const profileStore = useProfileStore();
const rankStore = useRankStore();

onBeforeMount(() => {
  profileStore.setUserProfile(userStore.loginUserId);
  rankStore.getUserRank(userStore.loginUserId);
});
</script>

<style>
.main {
  text-align: center;
  /* justify-content: center; */
  /* margin: 0 auto; */
}
.user-info-container {
  border-radius: 15px;
  border: 5px solid #cfcfcf;
  background-color: #ffffff;
  max-width: 1250px;
  margin: auto;
}

.rank-list-container {
  border-radius: 15px;
  border: 1px solid #ccc;
  background-color: #ffffff;
  max-width: 1250px;
  margin: auto;
  padding: 10px; /* 간격 설정 */
}

.user-info {
  display: flex;
  margin-top: 20px;
}
.rounded-border {
  border-radius: 15px;
  border: 1px solid #ccc;
}
</style>
