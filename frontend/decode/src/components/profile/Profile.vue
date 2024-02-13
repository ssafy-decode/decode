<template>
  <div class="pa-5" rounded style="color: #575757; font-weight: bold">
    <v-card
      class="mx-auto px-4 py-8"
      max-width="1250"
      style="
        box-shadow: none;
        text-align: center;
        background-color: #f3f3f3;
        border-radius: 31px;
        border: 15px solid #d9d9d9;
      "
    >
      <v-row>
        <v-col :cols="2">
          <v-avatar image="../../default.png" size="100px" />
          <div>{{ profile.nickname }}</div>
          <div style="display: flex; margin-left: 60px">
            <p :style="{ fontWeight: 'bold', color: getTierColor(profile.tier) }">{{ capitalize(profile.tier) }}</p>
            <img :src="`../../${profile.tier}.png`" width="30px" />
          </div>
          <div v-if="isMyProfile">
            <router-link to="/inventory">
              <v-btn class="btn" color="#62C0A6" size="x-large" type="submit" variant="elevated"> 내 아이템 </v-btn>
            </router-link>
            <router-link to="/checkpwd">
              <v-btn class="btn" color="#62C0A6" size="x-large" type="submit" variant="elevated"> 회원정보 수정 </v-btn>
            </router-link>
          </div>
          <div v-else>
            <v-btn
              v-if="isFollowing"
              class="btn"
              color="#62C0A6"
              size="x-large"
              type="submit"
              variant="elevated"
              @click="unfollowById(profile.id)"
            >
              팔로우 취소
            </v-btn>
            <v-btn
              v-else
              class="btn"
              color="#62C0A6"
              size="x-large"
              type="submit"
              variant="elevated"
              @click="followById(profile.id)"
            >
              팔로우
            </v-btn>
          </div>
        </v-col>

        <v-col :cols="2"
          ><div>
            <div>
              <template v-if="editing">
                <v-combobox
                  v-if="editing"
                  variant="solo"
                  class="combo"
                  bg-color="#d9d9d9"
                  v-model="selectedTags"
                  :items="items"
                  placeholder="ex) java, spring boot, sql"
                  label="기술 스택"
                  multiple
                  chips
                  clearable
                ></v-combobox>
              </template>
              <template v-else-if="tagIdList.length > 0">
                <div v-for="tag in tagIdList" :key="tag" style="display: block">
                  <v-chip :clearable="editing" :key="tag" label color="primary" class="mr-2 mb-2 chips">
                    {{ tagName[tag] }}</v-chip
                  >
                </div>
              </template>
              <template v-else>
                <div>선택한 기술 스택이 없습니다.</div>
              </template>
            </div>
            <br />
            <v-btn
              v-if="isMyProfile"
              @click="toggleEdit"
              class="tagbtn"
              color="#62C0A6"
              type="submit"
              variant="elevated"
              >{{ editing ? '기술 스택 변경 저장' : '기술 스택 변경' }}</v-btn
            >
          </div></v-col
        >

        <v-col :cols="5">
          출석 스트릭
          <AttendanceLog />
        </v-col>

        <v-col :cols="3">
          <ExpLog />
        </v-col>
      </v-row>
    </v-card>
  </div>
</template>

<script setup>
import AttendanceLog from '@/components/profile/AttendanceLog.vue';
import ExpLog from '@/components/profile/ExpLog.vue';
import { ref, defineProps, onBeforeMount } from 'vue';
import { storeToRefs } from 'pinia';
import { useUserStore } from '@/stores/userStore';
import { useProfileStore } from '@/stores/profileStore';
import { useTagStore } from '@/stores/tagStore';
import { useFollowStore } from '@/stores/followStore';

const userStore = useUserStore();
const profileStore = useProfileStore();
const tagStore = useTagStore();
const followStore = useFollowStore();

const { updateTechStack } = profileStore;
const { unFollow, follow } = followStore;
const { handleTags: tagIdList } = storeToRefs(tagStore);
// const { handleSelectedTags: selectedTags } = storeToRefs(profileStore);
const { handleAccessToken: accessToken } = storeToRefs(userStore);
const { setTagNumList } = tagStore;

const props = defineProps({
  profile: Object,
  isMyProfile: Boolean,
  isFollowing: Boolean,
  tagIdList: Array,
  selectedTags: Array,
});

const selectedTags = ref([]);

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

const editing = ref(false);
const items = ref([
  'python',
  'java',
  'C++',
  'javascript',
  'django',
  'spring',
  'spring boot',
  'kotlin',
  'sql',
  'react',
  'vue',
  'C#',
]);

// 기술 스택 목록 변경
const toggleEdit = () => {
  if (editing.value) {
    const user = {
      userId: userStore.loginUserId,
      tagIdList: selectedTags.value,
    };
    updateTechStack(user, accessToken.value);
  }
  editing.value = !editing.value;
  setTagNumList(userStore.loginUserId);
  selectedTags.value = tagIdList.value.map((tag) => tagName[tag]);
};

onBeforeMount(() => {
  setTagNumList(userStore.loginUserId);
  selectedTags.value = tagIdList.value.map((tag) => tagName[tag]);
});

const followById = (id) => {
  follow(id, accessToken.value);
};
const unfollowById = (id) => {
  unFollow(id, accessToken.value);
};

function getTierColor(tier) {
  if (tier === 'bronze') {
    return '#D7C3BC';
  } else if (tier === 'silver') {
    return '#DCDCDC';
  } else if (tier === 'gold') {
    return '#EDE481';
  } else if (tier === 'platinum') {
    return '#C8EBB9';
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
.chips {
  border-radius: 31px;
  background-color: #9dd0ff;
  color: #447cb0;
  font-weight: bold;
}
.btn {
  color: #000000;
  width: 110px;
  height: 28px;
  border-radius: 34px;
  font-size: smaller;
  font-weight: bold;
  margin: 10px;
}
</style>
