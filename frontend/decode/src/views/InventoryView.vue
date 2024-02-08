<template>
  <MyInventory></MyInventory>
</template>

<script setup>
import MyInventory from '@/components/shop/MyInventory.vue';
import { useShopStore } from '@/stores/shopStore.js';
import { useUserStore } from '@/stores/userStore.js';
import { storeToRefs } from 'pinia';

const { loginUserId: uid } = storeToRefs(useUserStore());
const shopStore = useShopStore();
const { fetchInventory } = shopStore;
fetchInventory(uid.value);
</script>

<style scoped></style>
