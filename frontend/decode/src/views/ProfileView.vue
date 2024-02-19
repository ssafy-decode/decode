<template>
  <Profile :profile="profile" :isMyProfile="isMyProfile" :isFollowing="isFollowing" :tagIdList="tagIdList" />
  <ProfileWindow
    :followerList="followerList"
    :followingList="followingList"
    :profile="profile"
    :isMyProfile="isMyProfile"
  />
</template>

<script setup>
import { ref, watch } from 'vue';
import { useRoute } from 'vue-router';
import { useUserStore } from '@/stores/userStore';
import { useProfileStore } from '@/stores/profileStore';
import { useFollowStore } from '@/stores/followStore';
import { useTagStore } from '@/stores/tagStore';
import ProfileWindow from '@/components/profile/ProfileWindow.vue';
import Profile from '@/components/profile/Profile.vue';
import { storeToRefs } from 'pinia';

const route = useRoute();
const uid = route.params.id;

const userStore = useUserStore();
const followStore = useFollowStore();
const profileStore = useProfileStore();
const tagStore = useTagStore();

const { setFollowerList, setFollowingList, getFollowState } = followStore;
const { setUserProfile, setAList, setQList } = profileStore;
const { setTagNumList } = tagStore;

const {
  handleFollowerList: followerList,
  handleFollowingList: followingList,
  handleFollowState: isFollowing,
} = storeToRefs(followStore);
const { handleUserProfile: profile, handleSelectedTags: selectedTags } = storeToRefs(profileStore);
const { handleLoginUserId: loginUserId, handleAccessToken: accessToken } = storeToRefs(userStore);
const { handleTags: tagIdList } = storeToRefs(tagStore);

const isMyProfile = ref(false);

watch(
  route,
  () => {
    const newUid = route.params.id;
    setFollowerList(newUid);
    setFollowingList(newUid);
    setUserProfile(newUid);
    setAList(newUid);
    setQList(newUid);
    setTagNumList(newUid);

    if (newUid == loginUserId.value) {
      isMyProfile.value = true;
    } else {
      isMyProfile.value = false;
      getFollowState(newUid, accessToken.value);
    }
  },
  {
    deep: true,
    immediate: true,
  },
);
</script>

<style scoped></style>
