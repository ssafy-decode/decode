<template>
  <v-row justify="center">
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
                ? `${item} ${followStore.followerList.length}`
                : index === 2
                  ? `${item} ${followStore.followingList.length}`
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
                질문 &nbsp;&nbsp;{{ profileStore.qListLength }}개 <br /><br />
                <div v-for="(question, questionIndex) in profileStore.qList" :key="questionIndex">
                  {{ questionIndex + 1 }} &nbsp; {{ question.questionId }} &nbsp;
                  {{ question.title }}
                </div>
                <br /><br />
                이 사람이 올린 질문
              </v-card>
            </v-col>

            <v-col cols="6">
              <v-card
                class="mx-auto px-4 py-8"
                max-width="518"
                style="text-align: center; background-color: #f3f3f3; border-radius: 31px; border: 15px solid #d9d9d9"
              >
                답변 &nbsp;&nbsp;{{ profileStore.aListLength }}개 <br /><br />
                <div v-for="(answer, answerIndex) in profileStore.aList" :key="answerIndex">
                  {{ answerIndex + 1 }} &nbsp; {{ answer.questionId }} &nbsp;
                  {{ answer.title }}
                </div>
                <br /><br />
                이 사람이 답변 작성한 질문
              </v-card></v-col
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
                  <span hidden>{{ follower.id }}</span>
                  <!-- 티어 나타내는 커마 아이콘 -->
                  {{ follower.tier }} &nbsp;
                  {{ follower.rank !== null ? `${follower.rank}위` : '순위없음' }}
                  <!-- 프로필 사진-->
                  <!-- <img style="width: 30px" src={{follower.profileImg}} /> -->
                  {{ follower.profileImg }} &nbsp; {{ follower.nickname }} &nbsp;
                  <!-- 선택한 기술 스택들 -->
                  <div v-if="follower.userTagList && follower.userTagList.length > 0">
                    <v-chip
                      v-for="(tag, tagIndex) in follower.userTagList"
                      :key="tagIndex"
                      label
                      color="primary"
                      class="mr-2 mb-2"
                    >
                      {{ tagName[tag] }}
                    </v-chip>
                  </div>
                  <div v-else>기술 스택 없음</div>
                  &nbsp;&nbsp;&nbsp;
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
                  <span hidden>{{ following.id }}</span>
                  <!-- 티어 나타내는 커마 아이콘 -->
                  {{ following.tier }} &nbsp;
                  {{ following.rank !== null ? `${following.rank}위` : '순위없음' }}
                  <!-- 프로필 사진 (보류) -->
                  <!-- 일단 default.png로 임시 통일 -->
                  <img style="width: 30px" src="../default.png" />
                  {{ following.profileImg }} &nbsp; {{ following.nickname }} &nbsp;
                  <!-- 선택한 기술 스택들 -->
                  <div v-if="following.userTagList && following.userTagList.length > 0">
                    <v-chip
                      v-for="(tagId, tagIndex) in following.userTagList"
                      :key="tagIndex"
                      label
                      color="primary"
                      class="mr-2 mb-2"
                    >
                      {{ tagName[tagId] }}
                    </v-chip>
                  </div>
                  <div v-else>기술 스택 없음</div>
                  &nbsp;&nbsp;&nbsp;
                  <v-btn @click="unfollowBtn(following.id)">팔로우 취소</v-btn>
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
import { ref } from 'vue';
import { useFollowStore } from '@/stores/followStore';
import { useProfileStore } from '@/stores/profileStore';

const tab = ref(0);
const items = [`${profileStore.loginUserProfile.nickname}의 질문 / 답변`, '팔로워', '팔로잉'];
const followStore = useFollowStore();
const profileStore = useProfileStore();

// 팔로우 취소
const unfollowBtn = (id) => {
  followStore.unFollow(id);
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
