<script setup>
import { defineProps } from 'vue';
defineProps({
  uid: Number,
  nickName: String,
});
</script>

<template>
  <RouterLink :to="{ name: 'userProfile', params: { id: uid } }"> {{ nickName }}</RouterLink>
</template>

<style scoped></style>
