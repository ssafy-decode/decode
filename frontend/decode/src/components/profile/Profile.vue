<template>
  <div class="pa-5" rounded style="color: #575757; font-weight: bold">
    <v-card
      class="mx-auto px-4 py-8"
      max-width="1036"
      style="text-align: center; background-color: #f3f3f3; border-radius: 31px; border: 15px solid #d9d9d9"
    >
      <v-row>
        <v-col :cols="2">
          {{ profileStore.loginUserProfile.nickname }}
          <br />
          <br />
          <!-- <img style="width: 70%" src={{profileStore.loginUserProfile.profileImg}} /> -->
          <br />
          {{ profileStore.loginUserProfile.tier }}
          <br />
          <!-- 커마아이콘 -->
        </v-col>

        <v-col :cols="1"
          ><div v-if="tagStore.tagIdList.length > 0">
            <v-chip v-for="tag in tagStore.tagIdList" :key="tag" label color="primary" class="mr-2 mb-2">
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
        <!-- 본인이 아닐 경우: 팔로우하기/팔로우취소 버튼만 구현하기 -->
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
import AttendanceLog from '@/components/profile/AttendanceLog.vue';
import ExpLog from '@/components/profile/ExpLog.vue';
import { storeToRefs } from 'pinia';
import { useUserStore } from '@/stores/userStore';
import { useProfileStore } from '@/stores/profileStore';
import { useTagStore } from '@/stores/tagStore';

const userStore = useUserStore();
const profileStore = useProfileStore();
const tagStore = useTagStore();
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
