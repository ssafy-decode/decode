<template>
  <v-row justify="center">
    <v-card width="1250" style="box-shadow: none; margin-top: 10px; background-color: transparent">
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
                max-width="625"
                style="
                  box-shadow: none;
                  text-align: center;
                  border-radius: 31px;
                  border: 15px solid #d9d9d9;
                  min-height: 450px;
                "
              >
                <div class="list-title">작성한 질문</div>
                <v-list>
                  <v-list-item v-for="(question, questionIndex) in qList" :key="questionIndex">
                    <router-link
                      :to="{ name: 'question-detail', params: { id: question.questionId } }"
                      class="article-link"
                    >
                      <v-list-item-title class="article-title">{{ question.title }}</v-list-item-title>
                    </router-link>
                  </v-list-item>
                </v-list>
              </v-card>
            </v-col>

            <v-col cols="6">
              <v-card
                class="mx-auto px-4 py-8"
                max-width="625"
                style="
                  box-shadow: none;
                  text-align: center;
                  border-radius: 31px;
                  border: 15px solid #d9d9d9;
                  min-height: 450px;
                "
              >
                <div class="list-title">작성한 답변</div>
                <v-list>
                  <v-list-item v-for="(answer, answerIndex) in aList" :key="answerIndex">
                    <router-link
                      :to="{ name: 'question-detail', params: { id: answer.questionId } }"
                      class="article-link"
                    >
                      <v-list-item-title class="article-title">{{ answer.title }}</v-list-item-title>
                    </router-link>
                  </v-list-item>
                </v-list>
              </v-card>
            </v-col>
          </v-row>

          <ul v-else-if="index === 1">
            <v-card
              class="mx-auto px-4 py-8 text-left"
              max-width="1250"
              style="
                box-shadow: none;
                text-align: center;
                background-color: #f3f3f3;
                border-radius: 31px;
                border: 15px solid #d9d9d9;
              "
            >
              <div v-if="followerList.length > 0">
                <v-row>
                  <v-col cols="1"></v-col>
                  <v-col cols="1">티어</v-col>
                  <v-col cols="4">닉네임</v-col>
                  <v-col cols="6">기술 스택</v-col>
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
                  <v-col cols="6">
                    <template v-if="followerTechStacks[follower.id] && followerTechStacks[follower.id].length > 0">
                      <div v-for="tag in followerTechStacks[follower.id]" :key="tag">
                        <v-chip
                          label
                          :style="{ backgroundColor: tagBackGroundColor(tag), color: tagTextColor(tag) }"
                          class="mr-2 mb-2 chips"
                        >
                          {{ tagName[tag] }}</v-chip
                        >
                      </div>
                    </template>
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
              max-width="1250"
              style="
                box-shadow: none;
                text-align: center;
                background-color: #f3f3f3;
                border-radius: 31px;
                border: 15px solid #d9d9d9;
              "
            >
              <div v-if="followingList.length > 0">
                <v-row>
                  <v-col cols="1"></v-col>
                  <v-col cols="1">티어</v-col>
                  <v-col cols="4">닉네임</v-col>
                  <v-col cols="4">기술 스택</v-col>
                </v-row>
                <v-row v-for="(following, followingIdx) in followingList" :key="followingIdx">
                  <span hidden>{{ following.id }}</span>
                  <v-col cols="1"> {{ following.tier }}</v-col>
                  <v-col cols="1">
                    <img style="width: 30px" src="../default.png" />
                    {{ following.profileImg }}
                  </v-col>
                  <v-col cols="4">
                    <profileRouter :uid="following.id" :nickName="following.nickname" />
                  </v-col>
                  <v-col cols="4">
                    <template v-if="followingTechStacks[following.id] && followingTechStacks[following.id].length > 0">
                      <div v-for="tag in followingTechStacks[following.id]" :key="tag">
                        <v-chip
                          label
                          :style="{ backgroundColor: tagBackGroundColor(tag), color: tagTextColor(tag) }"
                          class="mr-2 mb-2 chips"
                        >
                          {{ tagName[tag] }}</v-chip
                        >
                      </div>
                    </template>
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

import { ref, defineProps, onBeforeMount } from 'vue';
import { useFollowStore } from '@/stores/followStore';
import { useProfileStore } from '@/stores/profileStore';
import { useUserStore } from '@/stores/userStore';
import { storeToRefs } from 'pinia';
import { useTagStore } from '@/stores/tagStore';

const props = defineProps({
  followerList: Array,
  followingList: Array,
  profile: Object,
});

const tab = ref(0);
const profileStore = useProfileStore();
const followStore = useFollowStore();
const userStore = useUserStore();
const tagStore = useTagStore();

const { handleQuestions: qList, handleAnswers: aList } = storeToRefs(profileStore);
const { handleAccessToken: accessToken } = storeToRefs(userStore);

const { unFollow } = followStore;
const { setTagNumList } = tagStore;

const unfollowById = (id, index) => {
  props.followingList.splice(index, 1);
  unFollow(id, accessToken.value);
};

const followerTechStacks = ref({});
const followingTechStacks = ref({});

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

// 기술 태그 배경색
const tagBackGroundColor = (tag) => {
  switch (tag) {
    case 1:
      return '#9DD0FF';
    case 2:
      return '#FF9D9D';
    case 3:
      return '#FFDE9D';
    case 4:
      return '#D49DFF';
    case 5:
      return '#FFCC9F';
    case 6:
      return '#CEFAD0';
    case 7:
      return '#1FD655';
    case 8:
      return '#FF6865';
    case 9:
      return '#73A5C6';
    case 10:
      return '#FFB7CE';
    case 11:
      return '#E8D3C9';
    case 12:
      return '#B65FCF';

    default:
      return 'primary'; // Default color
  }
};

// 기술 태그 글자색
const tagTextColor = (tag) => {
  switch (tag) {
    case 1:
      return '#447CB0';
    case 2:
      return '#B54F4F';
    case 3:
      return '#BC8533';
    case 4:
      return '#9330B5';
    case 5:
      return '#FF6600';
    case 6:
      return '#008631';
    case 7:
      return '#073B3A';
    case 8:
      return '#450003';
    case 9:
      return '#051650';
    case 10:
      return '#9E4244';
    case 11:
      return '#CC5404';
    case 12:
      return '#66023C';

    default:
      return 'primary'; // Default color
  }
};

onBeforeMount(() => {
  props.followerList.forEach((follower) => {
    getFollowerTechStacks(follower.id);
  });
  props.followingList.forEach((following) => {
    getFollowingTechStacks(following.id);
  });
});

// 팔로워/팔로잉 목록 기술 스택 목록 조회
const getFollowerTechStacks = async (followerId) => {
  await setTagNumList(followerId);
  const tagIds = tagStore.tagIdList?.value || [];
  const tagNames = tagIds.map((tagId) => tagName[tagId]);
  followerTechStacks.value[followerId] = tagNames;
};
const getFollowingTechStacks = async (followingId) => {
  await setTagNumList(followingId);
  const tagIds = tagStore.tagIdList?.value || [];
  const tagNames = tagIds.map((tagId) => tagName[tagId]);
  followingTechStacks.value[followingId] = tagNames;
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
.article-container {
  margin-bottom: 20px;
}

.article-link {
  text-decoration: none;
}
.article-title {
  font-size: 16px;
  font-weight: bold;
  color: #333;
  cursor: pointer;
  text-align: left;
}

.article-title:hover {
  color: #62c0a6; /* hover 시 색상 변경 */
}

.list-title {
  font-size: 24px;
  font-weight: bold;
  color: #62c0a6; /* 리스트 제목 색상 변경 */
  margin-bottom: 20px;
}
</style>
