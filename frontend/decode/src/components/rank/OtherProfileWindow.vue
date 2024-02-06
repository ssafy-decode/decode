<template>
  <v-row justify="center">
    (아직 해당 회원 id로 수정 안 함)
    <v-card width="1036" style="box-shadow: none; margin-top: 10px; background-color: transparent">
      <v-tabs v-model="tab" background-color="transparent" grow>
        <v-tab
          v-for="(item, index) in items"
          :key="index"
          :value="index"
          style="font-size: 16px; font-weight: bold; flex: 1"
        >
          {{
            index === 0
              ? `${item}`
              : index === 1
                ? `${item} ${userStore.followerList.length}`
                : index === 2
                  ? `${item} ${userStore.followingList.length}`
                  : item
          }}
        </v-tab>
      </v-tabs>

      <v-window v-model="tab">
        <v-window-item
          v-for="(item, index) in items"
          :key="index"
          :value="index"
          :class="{ 'v-window-item-active': tab === index }"
        >
          <!-- 0번 탭 누를 시 -->
          <v-row v-if="index === 0">
            <v-col cols="6">
              <v-card
                class="mx-auto px-4 py-8"
                max-width="518"
                style="text-align: center; background-color: #f3f3f3; border-radius: 31px; border: 15px solid #d9d9d9"
              >
                질문 &nbsp;&nbsp;{{ userStore.qListLength }}개 <br /><br />
                <div v-for="(question, questionIndex) in userStore.qList" :key="questionIndex">
                  {{ questionIndex + 1 }} &nbsp; {{ question.questionId }} &nbsp;
                  {{ question.title }}
                </div>
                <br /><br />
                이 사람이 올린 질문
              </v-card>
            </v-col>

            <v-col cols="6">
              <v-card
                v-if="index === 0"
                class="mx-auto px-4 py-8"
                max-width="518"
                style="text-align: center; background-color: #f3f3f3; border-radius: 31px; border: 15px solid #d9d9d9"
              >
                답변 &nbsp;&nbsp;{{ userStore.aListLength }}개 <br /><br />
                <div v-for="(answer, answerIndex) in userStore.aList" :key="answerIndex">
                  {{ answerIndex + 1 }} &nbsp; {{ answer.questionId }} &nbsp;
                  {{ answer.title }}
                </div>
                <br /><br />
                이 사람이 답변 작성한 질문</v-card
              ></v-col
            ></v-row
          >

          <ul v-else-if="index === 1">
            <!-- 1번 탭 누를 시 -->
            <v-card
              class="mx-auto px-4 py-8"
              max-width="1036"
              style="text-align: center; background-color: #f3f3f3; border-radius: 31px; border: 15px solid #d9d9d9"
            >
              <v-row v-if="followerProfiles.length > 0">
                <v-col v-for="(follower, followerIdx) in followerProfiles" :key="followerIdx">
                  {{ followerIdx + 1 }} &nbsp;
                  <!-- 티어 나타내는 커마 아이콘 -->
                  {{ follower.tier }} &nbsp;
                  {{ follower.rank !== null ? `${follower.rank}위` : '순위없음' }}
                  <!-- 프로필 사진 (보류)-->
                  <!-- 일단 default.png로 임시 통일 -->
                  <img style="width: 30px" src="../default.png" />
                  {{ follower.profileImg }} &nbsp; {{ follower.nickname }} &nbsp;
                  <!-- 선택한 기술 스택들 (여기선 data 아직 안 뽑아옴) (userStore의 tagIdList를 기술명으로 전환해야) -->
                  {{ follower.tagIdLisit }} &nbsp;&nbsp;&nbsp;
                </v-col>
              </v-row>
              <v-row v-else>
                <v-col>아싸시네요.</v-col>
              </v-row>
            </v-card>
          </ul>

          <ul v-else-if="index === 2">
            <!-- 2번 탭 누를 시 -->
            <v-card
              class="mx-auto px-4 py-8"
              max-width="1036"
              style="text-align: center; background-color: #f3f3f3; border-radius: 31px; border: 15px solid #d9d9d9"
            >
              <v-row v-if="followingProfiles.length > 0">
                <v-col v-for="(following, followingIdx) in followingProfiles" :key="followingIdx">
                  {{ followingIdx + 1 }} &nbsp;
                  <!-- 티어 나타내는 커마 아이콘 -->
                  {{ following.tier }} &nbsp;
                  {{ following.rank !== null ? `${following.rank}위` : '순위없음' }}
                  <!-- 프로필 사진 (보류) -->
                  <!-- 일단 default.png로 임시 통일 -->
                  <img style="width: 30px" src="../default.png" />
                  {{ following.profileImg }} &nbsp; {{ following.nickname }} &nbsp;
                  <!-- 선택한 기술 스택들 (여기선 data 아직 안 뽑아옴) (userStore의 tagIdList를 기술명으로 전환해야)-->
                  {{ following.userTagList }} &nbsp;&nbsp;&nbsp; <v-btn @click="unfollowBtn">팔로우 취소</v-btn>
                </v-col>
              </v-row>
              <v-row v-else>
                <v-col>친구가 없으시네요.</v-col>
              </v-row>
            </v-card>
          </ul>
        </v-window-item>
      </v-window>
    </v-card>
  </v-row>
</template>

<script setup>
import { onBeforeMount, ref } from 'vue';
import { useUserStore } from '@/stores/userStore';

const userStore = useUserStore();
const tab = ref(0);
const items = [`${userStore.loginUserProfile.nickname}의 질문 / 답변`, '팔로워', '팔로잉'];

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

// const userTags = userStore.tagIdList.map((item) => tagNum[item]);

const followerProfiles = ref([]); // 팔로워 목록에 rank 포함한 새 배열
const followingProfiles = ref([]); // 팔로잉 목록에 rank 포함한 새 배열

// 경험치순 순위 목록의 회원번호와 팔로워/팔로잉 목록 회원번호 대조
const matchId = (list, profiles) => {
  list.forEach((user) => {
    const userRankListIdx = userStore.rankList.findIndex((rank) => rank.id === user.id);
    if (userRankListIdx !== -1) {
      user.rank = userRankListIdx + 1;
      profiles.value.push(user);
    } else {
      user.rank = null;
    }
  });
};

onBeforeMount(() => {
  userStore.setQList(userStore.loginUserId);
  userStore.setAList(userStore.loginUserId);
  userStore.getRank();
  userStore.setFollowerList(userStore.loginUserId);
  userStore.setFollowingList(userStore.loginUserId);
  matchId(userStore.followerList, followerProfiles);
  matchId(userStore.followingList, followingProfiles);
});

// 팔로우 취소
const unfollowBtn = () => {
  // 아직 작성 안 함
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

.v-window-item-active {
  display: block;
}
</style>
