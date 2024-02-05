<template>
  <div class="pa-5" rounded style="color: #575757; font-weight: bold">
    <v-card
      class="mx-auto px-4 py-8"
      max-width="1036"
      style="text-align: center; background-color: #f3f3f3; border-radius: 31px; border: 15px solid #d9d9d9"
    >
      <v-row>
        <v-col :cols="2">
          {{ userStore.loginUserProfile.nickname }}
          <br />
          <br />
          <!-- <input type="file" accept="image/*" @change="handleFileChange" style="display: none" ref="fileInput" /> -->
          <!-- <img style="width: 70%" :src="'./' + userStore.loginUserProfile.profileImg + '.png'" /> -->
          <!-- 추후 url로 받아오는 걸로 수정 -->
          <br />
          {{ userStore.loginUserProfile.tier }}
          <br />
          ((커마아이콘))
          <!-- 추후 수정 -->
        </v-col>

        <!-- 본인 기술 스택 v-chip들 나열 (소제목은 없애고)-->
        <v-col :cols="1"
          ><v-chip>{{ userTags }}</v-chip> <br />아직 get 없어서
          <!-- <div v-if="userStore.tagIdList.length > 0"> -->
          <v-chip v-for="tag in userTags" :key="tag" label color="primary" class="mr-2 mb-2">{{ tag }}</v-chip>
          <!-- {{ userStore.tagIdList.value }} (값이 없어서 빈칸이 뜨는 중입니다) -->
          <!-- </div> -->
          <!-- <div v-else>선택한 기술 스택이 없습니다.</div> -->
          기술스택 안 뜸</v-col
        >
        <!-- 일단 숫자로라도 뜨나 테스트 -->

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

  <MyProfileWindow v-if="!loading && isFetched" />
</template>

<script setup>
import { onMounted, ref, computed } from 'vue';
import { useUserStore } from '@/stores/userStore';
import AttendanceLog from '@/components/profile/AttendanceLog.vue';
import ExpLog from '@/components/profile/ExpLog.vue';
import { storeToRefs } from 'pinia';
import MyProfileWindow from '@/components/profile/MyProfileWindow.vue';

const userStore = useUserStore();
const { loginUserId: uid } = storeToRefs(userStore);
// const loading = ref(true);
const isFetched = ref(false);

// DB에 수정된 번호를 다시 태그명으로 전환
const tagNum = {
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
const userTags = userStore.tagIdList.map((item) => tagNum[item]); // 아직 미완성

// 배열을 제대로 가져오고는 있음. => 그러나 그 값을 저장하기 직전에 렌더링이 먼저 되어 버림. 순서의 문제.
// 일단 const loading 같은 변수를 두고 T/F로 해서 v-if로 렌더링을 잠시 막는 방법을 생각해보도록 하자.
const loading = computed(() => !isFetched.value);
onMounted(async () => {
  // try {
  await userStore.myProfile();
  isFetched.value = true;
  // } finally {
  //   loading.value = false;
  // }
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
