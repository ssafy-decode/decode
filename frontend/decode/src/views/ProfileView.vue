<template>
  <Profile :profile="profile" />
  <ProfileWindow :followerList="followerList" :followingList="followingList" :profile="profile" />
</template>

<script setup>
import { onMounted, onBeforeMount } from 'vue';
import { useRoute } from 'vue-router';
import { useProfileStore } from '@/stores/profileStore';
import { useFollowStore } from '@/stores/followStore';
import ProfileWindow from '@/components/profile/ProfileWindow.vue';
import Profile from '@/components/profile/Profile.vue';
import { storeToRefs } from 'pinia';

const route = useRoute();
const uid = route.params.id;

const followStore = useFollowStore();
const profileStore = useProfileStore();

const { setFollowerList, setFollowingList } = followStore;
const { setUserProfile, setAList, setQList } = profileStore;

const { handleFollowerList: followerList, handleFollowingList: followingList } = storeToRefs(followStore);
const { handleUserProfile: profile } = storeToRefs(profileStore);

onBeforeMount(() => {
  setFollowerList(uid);
  setFollowingList(uid);
  setUserProfile(uid);
  setAList(uid);
  setQList(uid);
});
</script>

<style scoped></style>
