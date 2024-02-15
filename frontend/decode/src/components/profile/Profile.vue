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
        <v-col :cols="2" v-if="profile">
          <v-avatar image="../../default.png" size="100px" />
          <div>{{ profile.nickname }}</div>
          <div style="display: flex; margin-left: 60px">
            <p :style="{ fontWeight: 'bold', color: getTierColor(profile.tier) }">{{ capitalize(profile.tier) }}</p>
            <img :src="`../../${profile.tier}.png`" width="30px" />
          </div>
          <div v-if="isMyProfile">
            <router-link to="/checkpwd" v-if="!oauth">
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

        <v-col :cols="2" style="margin: auto 0">
          <div v-if="editing">
            <v-combobox
              v-if="editing"
              variant="solo"
              class="combo"
              bg-color="#d9d9d9"
              v-model="selectedTagNames"
              :items="items"
              placeholder="ex) java, spring boot, sql"
              label="기술 스택"
              multiple
              chips
              clearable
            ></v-combobox>
          </div>
          <div v-else-if="selectedTags.length > 0">
            <div
              v-for="(tag, index) in showAllTags ? selectedTags : selectedTags.slice(0, 3)"
              :key="index"
              style="display: block"
            >
              <v-chip
                :clearable="editing"
                :key="tag"
                label
                :style="{ backgroundColor: tagBackGroundColor(tag), color: tagTextColor(tag) }"
                class="mr-2 mb-2 chips"
              >
                {{ tagName[tag] }}
              </v-chip>
            </div>
            <button v-if="selectedTags.length > 3" @click="showAllTags = !showAllTags">
              <img src="../plus.png" width="30px" />
            </button>
          </div>
          <div v-else>
            <div>선택한 기술 스택이 없습니다.</div>
          </div>
          <button v-if="isMyProfile" @click="toggleEdit" style="float: right">
            <img v-if="!editing" src="../gear.png" width="40px" />
            <img v-else src="../save.png" width="30px" />
          </button>
        </v-col>

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
import { ref, defineProps, onMounted, watch } from 'vue';
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
const { handleAccessToken: accessToken, handleIsOAuth: oauth } = storeToRefs(userStore);
const { setTagNumList } = tagStore;
const { handleTags: StoreTagIdList } = storeToRefs(tagStore);

const props = defineProps({
  profile: Object,
  isMyProfile: Boolean,
  isFollowing: Boolean,
  tagIdList: Array,
});

const showAllTags = ref(false);
const editing = ref(false);
const selectedTags = ref([]);
const selectedTagNames = ref([]);
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

watch(
  StoreTagIdList,
  () => {
    selectedTags.value = [];
    selectedTagNames.value = [];
    StoreTagIdList.value.forEach((tag) => {
      selectedTags.value.push(tag);
      selectedTagNames.value.push(tagName[tag]);
    });
  },
  {
    immediate: true,
    deep: true,
  },
);

const toggleEdit = () => {
  if (editing.value) {
    selectedTags.value = [];
    items.value.forEach((item, index) => {
      if (selectedTagNames.value.includes(item)) {
        selectedTags.value.push(index + 1);
      }
    });
    const user = {
      userId: userStore.loginUserId,
      tagIdList: selectedTags.value,
    };
    updateTechStack(user, accessToken.value);
  }
  editing.value = !editing.value;
};

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
