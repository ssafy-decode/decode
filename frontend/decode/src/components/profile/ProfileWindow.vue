<template>
  <v-row justify="center">
    <v-card width="1250" style="box-shadow: none; margin-top: 10px; background-color: transparent">
      <v-tabs v-model="tab" background-color="transparent" grow>
        <v-tab
          v-if="profile"
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
              style="box-shadow: none; text-align: center; border-radius: 31px; border: 15px solid #d9d9d9"
            >
              <div v-if="followerList.length > 0">
                <v-row v-for="(follower, followerIdx) in followerList" :key="followerIdx">
                  <span hidden>{{ follower.id }}</span>
                  <v-col cols="1">
                    <div style="display: flex; margin-left: 60px">
                      <img :src="`../../${follower.tier}.png`" width="30px" />
                      <p :style="{ fontWeight: 'bold', color: getTierColor(follower.tier) }">
                        {{ capitalize(follower.tier) }}
                      </p>
                    </div>
                  </v-col>
                  <v-col cols="1"> </v-col>
                  <v-col cols="3">
                    <img style="width: 30px" src="../default.png" />
                    {{ follower.profileImg }}
                    <profileRouter :uid="follower.id" :nickName="follower.nickname" />
                  </v-col>
                  <v-col cols="5">
                    <v-chip
                      v-if="followerTechStacks[follower.id]"
                      v-for="tag in showAllTags1[follower.id]
                        ? followerTechStacks[follower.id]
                        : followerTechStacks[follower.id].slice(0, 3)"
                      :key="tag"
                      label
                      :style="{ backgroundColor: tagBackGroundColor(tag), color: tagTextColor(tag) }"
                      class="mb-2 mr-2 chips"
                    >
                      {{ tagName[tag] }}
                    </v-chip>
                    <button
                      v-if="followerTechStacks[follower.id] && followerTechStacks[follower.id].length > 3"
                      @click="toggleTags1(follower.id)"
                    >
                      <img src="../plus.png" width="30px" />
                    </button>
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
              style="box-shadow: none; text-align: center; border-radius: 31px; border: 15px solid #d9d9d9"
            >
              <div v-if="followingList.length > 0">
                <v-row v-for="(following, followingIdx) in followingList" :key="followingIdx">
                  <span hidden>{{ following.id }}</span>
                  <v-col cols="1">
                    <div style="display: flex; margin-left: 60px">
                      <img :src="`../../${following.tier}.png`" width="30px" />
                      <p :style="{ fontWeight: 'bold', color: getTierColor(following.tier) }">
                        {{ capitalize(following.tier) }}
                      </p>
                    </div>
                  </v-col>
                  <v-col cols="1"> </v-col>
                  <v-col cols="3">
                    <img style="width: 30px" src="../default.png" />
                    {{ following.profileImg }}
                    <profileRouter :uid="following.id" :nickName="following.nickname" />
                  </v-col>
                  <v-col cols="5">
                    <v-chip
                      v-if="followingTechStacks[following.id]"
                      v-for="tag in showAllTags2[following.id]
                        ? followingTechStacks[following.id]
                        : followingTechStacks[following.id].slice(0, 3)"
                      :key="tag"
                      label
                      :style="{ backgroundColor: tagBackGroundColor(tag), color: tagTextColor(tag) }"
                      class="mb-2 mr-2 chips"
                    >
                      {{ tagName[tag] }}
                    </v-chip>
                    <button
                      v-if="followingTechStacks[following.id] && followingTechStacks[following.id].length > 3"
                      @click="toggleTags2(following.id)"
                    >
                      <img src="../plus.png" width="30px" />
                    </button>
                  </v-col>
                  <v-col cols="2" v-if="isMyProfile">
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

import { ref, defineProps, onBeforeMount, watch } from 'vue';
import { useFollowStore } from '@/stores/followStore';
import { useProfileStore } from '@/stores/profileStore';
import { useUserStore } from '@/stores/userStore';
import { storeToRefs } from 'pinia';
import axios from '@/utils/common-axios';

const props = defineProps({
  followerList: Array,
  followingList: Array,
  profile: Object,
  isMyProfile: Boolean,
});

const tab = ref(0);
const profileStore = useProfileStore();
const followStore = useFollowStore();
const userStore = useUserStore();

const { handleQuestions: qList, handleAnswers: aList } = storeToRefs(profileStore);
const { handleAccessToken: accessToken } = storeToRefs(userStore);

const { unFollow } = followStore;

const unfollowById = (id, index) => {
  if (props.isMyProfile) {
    props.followingList.splice(index, 1);
  }
  unFollow(id, accessToken.value);
};

const followerTechStacks = ref({});
const followingTechStacks = ref({});

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

const showAllTags1 = ref({});
const showAllTags2 = ref({});
watch(
  props,
  () => {
    props.followerList.forEach((follower) => {
      axios.get('/tag/' + follower.id).then((res) => {
        followerTechStacks.value[follower.id] = res.data.data.tagIdList;
        showAllTags1.value[follower.id] = false;
      });
    });
    props.followingList.forEach((following) => {
      axios.get('/tag/' + following.id).then((res) => {
        followingTechStacks.value[following.id] = res.data.data.tagIdList;
        showAllTags2.value[following.id] = false;
      });
    });
  },
  {
    deep: true,
    immediate: true,
  },
);

const toggleTags1 = (id) => {
  showAllTags1.value[id] = !showAllTags1.value[id];
};
const toggleTags2 = (id) => {
  showAllTags2.value[id] = !showAllTags2.value[id];
};
function getTierColor(tier) {
  if (tier === 'bronze') {
    return '#D7C3BC';
  } else if (tier === 'silver') {
    return '#DCDCDC';
  } else if (tier === 'gold') {
    return '#EDE481';
  } else if (tier === 'platinum') {
    return '#A5D8D8';
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

<style scoped>
.buttons {
  text-align: flex-end;
}
.chips {
  border-radius: 31px;
  background-color: #9dd0ff;
  color: #447cb0;
  font-weight: bold;
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
