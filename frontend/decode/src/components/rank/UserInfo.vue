<template>
  <v-container class="user-info">
    <v-row v-if="user">
      <v-col cols="2">
        <v-avatar image="../../default.png" size="100px" />
        <p>{{ user.nickname }}</p>
        <p>{{ user.myRank }}위</p>
        <p style="font-size: 12px; color: gray">(상위 {{ Math.round((user.myRank / user.totalUserCount) * 100) }}%)</p>
      </v-col>
      <v-col cols="2" style="align-self: center">
        <img :src="`../../${user.tier}.png`" width="120px" />
        <p :style="{ fontWeight: 'bold', color: getTierColor(user.tier) }">{{ capitalize(user.tier) }}</p>
      </v-col>
      <v-col cols="8">
        <v-row>
          <v-col cols="3">
            <p>{{ user.answerCount }}</p>
            <p style="color: #999999">답변수</p>
          </v-col>
          <v-col cols="3">
            <p>{{ user.adoptCount }}</p>
            <p style="color: #999999">채택수</p>
          </v-col>
          <v-col cols="3">
            <p>{{ user.followerCount }}</p>
            <p style="color: #999999">팔로워</p>
          </v-col>
          <v-col cols="3">
            <p>{{ user.followCount }}</p>
            <p style="color: #999999">팔로우</p>
          </v-col>
        </v-row>
        <v-row style="margin-bottom: -30px">
          <v-col cols="4" style="color: #575757"> {{ capitalize(user.tier) }}: {{ user.exp }} </v-col>
          <v-col cols="4"></v-col>
          <v-col v-if="rankStore.getNextTier(user.tier)" cols="4" style="color: lightgray">
            {{ capitalize(rankStore.getNextTier(user.tier)) }}까지 -{{ rankStore.getNeedExp(user.tier) - user.exp }}
          </v-col>
        </v-row>
        <v-row justify="center">
          <v-col sm="10">
            <v-progress-linear
              color="#62C0A6"
              height="30px"
              rounded="true"
              :model-value="(user.exp / rankStore.getNeedExp(user.tier)) * 100"
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
import { storeToRefs } from 'pinia';
import { useRankStore } from '@/stores/rankStore';

const rankStore = useRankStore();
const { handleUserRank: user } = storeToRefs(rankStore);

onMounted(() => {});

function getTierColor(tier) {
  if (tier === 'bronze') {
    return '#D7C3BC';
  } else if (tier === 'silver') {
    return '#DCDCDC';
  } else if (tier === 'gold') {
    return '#EDE481';
  } else if (tier === 'platinum') {
    return '#C8EBB9';
  } else if (tier === 'diamond') {
    return '#97D2FF';
  } else if (tier === 'ruby') {
    return '#F4A5C5';
  } else {
    return 'black';
  }
}
const capitalize = (value) => {
  if (!value) return '';
  return value.charAt(0).toUpperCase() + value.slice(1);
};
</script>

<style>
.user-info {
  display: flex;
  margin-top: 20px;
}
</style>
