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
          <img style="width: 70%" :src="'./' + userStore.loginUserProfile.profileImg + '.png'" />
          <br />
          {{ userStore.loginUserProfile.tier }}
          <br />
          <img style="width: 40%" src="./커마아이콘샘플.png" />
          <!-- 추후 수정 -->
        </v-col>
        <v-col :cols="2"> 기술스택 </v-col>
        <v-col :cols="5">
          <AttendanceLog :uid="uid"></AttendanceLog>
        </v-col>

        <v-col :cols="3">
          <ExpLog></ExpLog>
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

  <v-row justify="center">
    <v-card width="1036" style="box-shadow: none; margin-top: 10px; background-color: transparent">
      <v-tabs v-model="tab" background-color="transparent" grow>
        <v-tab v-for="(item, index) in items" :key="index" :value="index" style="font-size: 16px; font-weight: bold">
          {{
            index === 0
              ? `${item}`
              : index === 1
                ? `${item} ${followerList.length}`
                : index === 2
                  ? `${item} ${followingList.length}`
                  : item
          }}
        </v-tab>
      </v-tabs>

      <v-window v-model="tab">
        <v-window-item v-for="(item, index) in items" :key="index" :value="index">
          <v-card flat style="background-color: transparent">
            <v-card-text style="text-align: center; font-size: 15px">
              <v-row v-if="index === 0">
                <v-col cols="6"
                  >질문 &nbsp;&nbsp;{{ questionList.length }}개 <br /><br />
                  <!-- 테스트용 질문 목록 -->
                  <div v-for="(question, questionIndex) in testquestionList" :key="questionIndex">
                    {{ question.text }}
                  </div>
                  <br /><br />
                  이 사람이 올린 질문</v-col
                >
                <v-col cols="6"
                  >답변 &nbsp;&nbsp;{{ answerList.length }}개 <br /><br />
                  <!-- 테스트용 답변 목록 -->
                  <div v-for="(answer, answerIndex) in testanswerList" :key="answerIndex">
                    {{ answer.text }}
                  </div>
                  <br /><br />
                  이 사람이 답변 작성한 질문</v-col
                >
              </v-row>
              <ul v-else-if="index === 1">
                <div>팔로워 목록 (테스트용)</div>
                <!-- 티어 나타내는 커마 아이콘 -->
                {{
                  followerList.tier
                }}
                <!-- 순위 (여기선 data 아직 안 뽑아옴) -->
                <!-- 프로필 사진 -->
                {{
                  followerList.profileImg
                }}
                <!-- 닉네임 -->
                {{
                  followerList.nickname
                }}
                <!-- 선택한 기술 스택들 (여기선 data 아직 안 뽑아옴) -->
                <!-- 팔로워 목록에는 버튼 없음? -->
                <!-- 일단 잘 뜨나 테스트용 -->
                <v-row v-for="(follower, followerIndex) in testfollowerList" :key="followerIndex">
                  <v-col>
                    <!-- 티어 나타내는 커마 아이콘 -->
                    {{ follower.tier }}
                    <!-- 순위 (여기선 data 아직 안 뽑아옴) -->
                    {{ follower.rank + '위' }}
                    <!-- 프로필 사진 -->
                    <img style="width: 40px" :src="'../' + follower.profileImg" />
                    <!-- 닉네임 -->
                    {{ follower.text }}
                    <!-- 선택한 기술 스택들 (여기선 data 아직 안 뽑아옴) -->
                    <v-chip>{{ follower.tag }}</v-chip>
                    <!-- 팔로워 목록에는 버튼 없음? -->
                  </v-col>
                </v-row>
              </ul>
              <ul v-else-if="index === 2">
                <div>팔로잉 목록 (테스트용)</div>
                <!-- 티어 나타내는 커마 아이콘 -->
                {{
                  followingList.tier
                }}
                <!-- 순위 (여기선 data 아직 안 뽑아옴) -->
                <!-- 프로필 사진 -->
                {{
                  followingList.profileImg
                }}
                <!-- 닉네임 -->
                {{
                  followingList.nickname
                }}
                <!-- 선택한 기술 스택들 (여기선 data 아직 안 뽑아옴) -->
                <!-- 팔로잉 목록에는 팔로우 취소 버튼 있음? -->
                <v-btn>팔로우 취소</v-btn>
                <!-- 일단 잘 뜨나 테스트용 -->
                <v-row v-for="(following, followingIndex) in testfollowingList" :key="followingIndex">
                  <v-col>
                    <!-- 티어 나타내는 커마 아이콘 -->
                    {{ following.tier }}
                    <!-- 순위 (여기선 data 아직 안 뽑아옴) -->
                    {{ following.rank + '위' }}
                    <!-- 프로필 사진 -->
                    <img style="width: 40px" :src="'./' + following.profileImg" />
                    <!-- 닉네임 -->
                    {{ following.text }}
                    <!-- 선택한 기술 스택들 (여기선 data 아직 안 뽑아옴) -->
                    <v-chip>{{ following.tag }}</v-chip>
                    <!-- 팔로잉 목록에는 팔로우 취소 버튼 있음? -->
                    <v-btn>팔로우 취소</v-btn>
                  </v-col>
                </v-row>
              </ul>
            </v-card-text>
          </v-card>
        </v-window-item>
      </v-window>
    </v-card>
  </v-row>
</template>

<script setup>
import { onMounted, ref } from 'vue';
import { useUserStore } from '@/stores/userStore';
import AttendanceLog from './AttendanceLog.vue';
import ExpLog from './ExpLog.vue';
import { storeToRefs } from 'pinia';

const userStore = useUserStore();

const tab = ref(0);
const items = [`${userStore.loginUserProfile.nickname}의 질문 / 답변`, '팔로워', '팔로잉'];
const questionList = ref([]);
const answerList = ref([]);
const followerList = ref([]);
const followingList = ref([]);

const { loginUserId: uid } = storeToRefs(userStore);

// 테스트용으로 임시 만든 배열
const testquestionList = ref([]);
const testanswerList = ref([]);
const testfollowerList = ref([]);
const testfollowingList = ref([]);

onMounted(async () => {
  await userStore.myProfile();
  await userStore.setUser(userStore.loginUserId);
  await userStore.setFollowerList(userStore.loginUserId);
  await userStore.setFollowingList(userStore.loginUserId);

  //테스트용
  testquestionList.value = [
    { id: 1, text: 'Q [Temp Tag] 테스트용 질문1입니다.' },
    { id: 2, text: 'Q [Temp Tag] 테스트용 질문2입니다.' },
    { id: 3, text: 'Q [Temp Tag] 테스트용 질문3입니다.' },
  ];

  //테스트용
  testanswerList.value = [
    { id: 1, text: 'Q [Temp Tag] 테스트용 답변1입니다.' },
    { id: 2, text: 'Q [Temp Tag] 테스트용 답변2입니다.' },
    { id: 3, text: 'Q [Temp Tag] 테스트용 답변3입니다.' },
  ];

  //테스트용
  testfollowerList.value = [
    { tier: `bronze`, rank: 100, profileImg: `default.png`, tag: `python` },
    { tier: `bronze`, rank: 100, profileImg: `default.png`, tag: `python` },
    { tier: `bronze`, rank: 100, profileImg: `default.png`, tag: `python` },
  ];

  //테스트용
  testfollowingList.value = [
    { tier: `bronze`, rank: 100, profileImg: `default.png`, tag: `python` },
    { tier: `bronze`, rank: 100, profileImg: `default.png`, tag: `python` },
    { tier: `bronze`, rank: 100, profileImg: `default.png`, tag: `python` },
  ];
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
