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
          <!-- {{ userStore.loginUserProfile.nickname }} -->닉네임
          <br />
          <br />
          <!-- <input type="file" accept="image/*" @change="handleFileChange" style="display: none" ref="fileInput" /> -->
          <!-- <img style="width: 70%" :src="'./' + userStore.loginUserProfile.profileImg + '.png'" /> -->
          프사
          <br />
          <!-- {{ userStore.loginUserProfile.tier }} -->티어
          <br />
          ((커마아이콘))
        </v-col>

        <v-col :cols="1"
          ><div v-if="userStore.tagIdList.length > 0">
            <!-- <v-chip v-for="tag in userStore.tagIdList" :key="tag" label color="primary" class="mr-2 mb-2">
              {{ tagName[tag] }}</v-chip
            > -->기술 스택 목록
          </div>
          <div v-else><br /><br />기술<br />스택<br />없음</div></v-col
        >

        <v-col :cols="6">
          출석 스트릭
          <!-- <AttendanceLog :uid="uid" /> -->그 사람의 출석 로그
        </v-col>

        <v-col :cols="3"> <!-- <ExpLog :uid="uid" /> -->그 사람의 경험치 로그 </v-col>
      </v-row>

      <div class="buttons">
        <span>
          <v-btn
            class="btn"
            @class="handleFollow"
            color="#62C0A6"
            size="x-large"
            type="submit"
            variant="elevated"
            style="color: #000000"
          >
            팔로우하기 (팔로우취소도 구현하기)
          </v-btn>
        </span>
      </div>
    </v-card>
  </div>

  <!-- 로딩 끝나고 데이터 가져왔다면 하단 탭들 렌더링 -->
  <OtherProfileWindow v-if="!loading && isFetched" />
</template>

<script setup>
import { ref, computed, onBeforeMount } from 'vue';
import { useUserStore } from '@/stores/userStore';
// import AttendanceLog from '@/components/profile/AttendanceLog.vue';
// import ExpLog from '@/components/profile/ExpLog.vue';
// import { storeToRefs } from 'pinia';
import OtherProfileWindow from '@/components/rank/OtherProfileWindow.vue';

const userStore = useUserStore();
// const { loginUserId: uid } = storeToRefs(userStore);

// userId는 랭킹/질문게시판에서 response로 받아와서 들고 올 수 있음. hidden으로 숨기고 그걸로 돌리기

// DB에 수정된 번호를 다시 태그명으로 전환 (아직 작성중)
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

const handleFollow = () => {
  // 팔로우하기/팔로우취소 전환 구현하기
};

const isFetched = ref(false); // data를 가져오고 나서 렌더링하도록
const loading = computed(() => !isFetched.value); // 가져오기 전까지는 로딩 상태로
onBeforeMount(async () => {
  await userStore.myProfile();
  await userStore.getTagNumList(userStore.loginUserId);
  setTimeout(() => {
    isFetched.value = true; // 가져오면 렌더링
  }, 1000); // 1초 후에
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
