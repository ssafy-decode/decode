<template>
  <div class="pa-5" rounded style="color: #575757; font-weight: bold">
    <v-card
      class="mx-auto px-4 py-8"
      max-width="1036"
      style="text-align: center; background-color: #f3f3f3; border-radius: 31px; border: 15px solid #d9d9d9"
    >
      <v-row>
        <v-col :cols="2">
          <!-- <img style="width: 70%" :src="'./' + userStore.loginUserProfile.profileImg + '.png'" /> -->
          <img style="width: 70%" src="./default.png" />
          <br />
          랭킹 등수
          <br />
          상위 몇 %
        </v-col>
        <v-col :cols="2">
          <br />
          <img style="width: 40%" src="./커마아이콘샘플.png" />
          <br />
          <!-- {{ userStore.loginUserProfile.tier }} -->티어
        </v-col>
        <v-col :cols="2">
          <br />
          <!-- {{ userStore.loginUserProfile.nickname }} -->닉넴
          <br />
          보유 포인트: 000p
        </v-col>
        <v-col :cols="2">
          <br />
          팔로워 / 팔로잉
          <br />
          100 / 140
          <br />
          <br />
          답변 수: 00개
          <br />
          채택 수: 00개
        </v-col>
        <v-col :cols="4">
          <br />
          내 랭크
          <br />
          (현재 티어)(막대그래프으으으으)(다음 티어)
          <br />
          <br />
          현재 티어명 &nbsp;&nbsp; 000 / 000 &nbsp;&nbsp; 다음 티어명
          <br />
          다음 {티어}까지 000!
        </v-col>
      </v-row>
    </v-card>

    <div>검색창 넣기</div>

    <div>랭킹 목록 넣기</div>
  </div>
</template>

<script setup>
// import { onMounted, ref } from 'vue';
// import { useUserStore } from '@/stores/userStore';

// const userStore = useUserStore();
</script>

<style scoped></style>
