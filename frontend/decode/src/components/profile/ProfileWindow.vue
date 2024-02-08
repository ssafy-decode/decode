<template>
  <v-row justify="center">
    <v-card width="1036" style="box-shadow: none; margin-top: 10px; background-color: transparent">
      <v-tabs v-model="tab" background-color="transparent" grow>
        <v-tab
          v-for="(item, index) in 3"
          :key="index"
          :value="index"
          style="font-size: 16px; font-weight: bold; flex: 1"
        >
          {{
            index === 0
              ? profile.nickname + '의 질문 / 답변'
              : index === 1
                ? '팔로워 ' + followerList.length
                : index === 2
                  ? '팔로잉 ' + followingList.length
                  : item
          }}
        </v-tab>
      </v-tabs>

      <v-window v-model="tab">
        <v-window-item
          v-for="(item, index) in 3"
          :key="index"
          :value="index"
          :class="{ 'v-window-item-active': tab === index }"
        >
          <v-row v-if="index === 0">
            <v-col cols="6">
              <v-card
                class="mx-auto px-4 py-8"
                max-width="518"
                style="text-align: center; background-color: #f3f3f3; border-radius: 31px; border: 15px solid #d9d9d9"
              >
                질문 &nbsp;&nbsp;{{ qList.length }}개 <br /><br />
                <div v-for="(question, questionIndex) in qList" :key="questionIndex">
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
                답변 &nbsp;&nbsp;{{ aList.length }}개 <br /><br />
                <div v-for="(answer, answerIndex) in aList" :key="answerIndex">
                  {{ answerIndex + 1 }} &nbsp; {{ answer.questionId }} &nbsp;
                  {{ answer.title }}
                </div>
                <br /><br />
                이 사람이 답변 작성한 질문
              </v-card></v-col
            ></v-row
          >

          <ul v-else-if="index === 1">
            <v-card
              class="mx-auto px-4 py-8 text-left"
              max-width="1036"
              style="text-align: center; background-color: #f3f3f3; border-radius: 31px; border: 15px solid #d9d9d9"
            >
              <div v-if="followerList.length > 0">
                <v-row>
                  <v-col cols="1"></v-col>
                  <v-col cols="1">티어</v-col>
                  <v-col cols="4">닉네임</v-col>
                </v-row>
                <v-row v-for="(follower, followerIdx) in followerList" :key="followerIdx">
                  <span hidden>{{ follower.id }}</span>
                  <v-col cols="1"> {{ follower.tier }}</v-col>
                  <v-col cols="1">
                    <img style="width: 30px" src="../default.png" />
                    {{ follower.profileImg }}
                  </v-col>
                  <v-col cols="4">
                    <profileRouter :uid="follower.id" :nickName="follower.nickname" />
                  </v-col>
                </v-row>
              </div>
              <v-row v-else>
                <v-col>아싸시네요.</v-col>
              </v-row>
            </v-card>
          </ul>

          <ul v-else-if="index === 2">
            <v-card
              class="mx-auto px-4 py-8 text-left"
              max-width="1036"
              style="text-align: center; background-color: #f3f3f3; border-radius: 31px; border: 15px solid #d9d9d9"
            >
              <div v-if="followingList.length > 0">
                <v-row>
                  <v-col cols="1"></v-col>
                  <v-col cols="1">티어</v-col>
                  <v-col cols="4">닉네임</v-col>
                </v-row>
                <v-row v-for="(following, followingIdx) in followingList" :key="followingIdx">
                  <span hidden>{{ following.id }}</span>
                  <v-col cols="1">
                    <img style="width: 30px" src="../default.png" />
                    {{ following.profileImg }}
                  </v-col>
                  <v-col cols="1"> {{ following.tier }}</v-col>
                  <v-col cols="4">
                    <profileRouter :uid="following.id" :nickName="following.nickname" />
                  </v-col>
                  <v-col cols="2" offset="4">
                    <v-btn @click="unfollowById(following.id, followingIdx)">팔로우 취소</v-btn>
                  </v-col>
                </v-row>
              </div>
              <v-col v-else>
                <v-col>친구가 없으시네요.</v-col>
              </v-col>
            </v-card>
          </ul>
        </v-window-item>
      </v-window>
    </v-card>
  </v-row>
</template>

<script setup>
import profileRouter from '@/components/common/profileRouter.vue';

import { ref, defineProps } from 'vue';
import { useFollowStore } from '@/stores/followStore';
import { useProfileStore } from '@/stores/profileStore';
import { useUserStore } from '@/stores/userStore';
import { storeToRefs } from 'pinia';

const props = defineProps({
  followerList: Array,
  followingList: Array,
  profile: Object,
});

const tab = ref(0);
const profileStore = useProfileStore();
const followStore = useFollowStore();
const userStore = useUserStore();

const { handleQuestions: qList, handleAnswers: aList } = storeToRefs(profileStore);
const { handleAccessToken: accessToken } = storeToRefs(userStore);

const { unFollow } = followStore;

const unfollowById = (id, index) => {
  props.followingList.splice(index, 1);
  unFollow(id, accessToken.value);
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
