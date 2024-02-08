<template>
  <v-container class="user-info">
    <v-row v-if="userStore && user">
      <v-col cols="2">
        <v-avatar size="100">
          <img src="../../default.png" alt="Profile" />
        </v-avatar>
        <p>{{ user.nickname}}</p>
        <p>{{ user.myRank }}위 (상위 {{Math.round(user.myRank/user.totalUserCount*100)}}%)</p>
      </v-col>
      <v-col cols="2" style="align-self: center;">
        <img :src="`../../${user.tier}.png`" width="100px" />
      </v-col>
      <v-col cols="8">
        <v-row>
          <v-col cols="3">
            <p> {{ user.answerCount }} </p>
            <p> 답변수 </p>
          </v-col>
          <v-col cols="3">
            <p> {{ user.adoptCount }} </p>
            <p> 채택수 </p>
          </v-col>
          <v-col cols="3">
            <p> {{ user.followerCount }} </p>
            <p> 팔로워 </p>
          </v-col>
          <v-col cols="3">
            <p> {{ user.followCount }} </p>
            <p> 팔로우 </p>
          </v-col>
        </v-row>
        <v-row style="margin-bottom: -30px;">
          <v-col cols="4" style="color: #575757;">
            {{ user.tier }}: {{ user.exp }}
          </v-col>
          <v-col cols="4"></v-col>
          <v-col cols="4" style="color: lightgray">
            {{ rankStore.getNextTier(user.tier) }}: {{ rankStore.getNeedExp(user.tier) }}
          </v-col>
        </v-row>
        <v-row justify="center">
          <v-col sm="10">
            <v-progress-linear
            color= #62C0A6 
            height="30px"
            rounded="true"
            :model-value="user.exp / rankStore.getNeedExp(user.tier) * 100"
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
import { useRankStore } from '@/stores/rankStore';


const userStore = useUserStore();
const profileStore = useProfileStore();
const rankStore = useRankStore();
const { handleUserRank: user } = storeToRefs(rankStore);


onMounted(() => {

});


</script>

<style>
.user-info {
  display: flex;
  margin-top: 20px;
}
</style>
