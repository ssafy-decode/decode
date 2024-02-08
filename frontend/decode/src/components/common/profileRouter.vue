<script setup>
import { defineProps } from 'vue';
import { useRouter } from 'vue-router';
const router = useRouter();

const props = defineProps({
  uid: Number,
  nickName: String,
});

const goToProfile = () => {
  router.push({ name: 'userProfile', params: { id: props.uid } });
};
</script>

<template>
  <div class="profile-link" @click="goToProfile">{{ nickName }}</div>
</template>

<style scoped>
.profile-link {
  cursor: pointer;
  color: #34a080;
  font-weight: bold;
  margin: 0px;
  padding: 0px;
  display: inline;
}
.profile-link:hover {
  text-decoration: underline;
}
</style>
