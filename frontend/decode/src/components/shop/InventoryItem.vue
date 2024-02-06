<script setup>
import { useUserStore } from '@/stores/userStore';
import { storeToRefs } from 'pinia';
import { ref, defineProps } from 'vue';
import axios from '@/utils/common-axios';
const userStore = useUserStore();
const { accessToken: accessToken } = storeToRefs(userStore);
const props = defineProps({
  item: Object,
});
</script>

<template>
  <v-card class="item-card">
    <v-img :src="item.itemImage" height="80px" />
  </v-card>
</template>

<style scoped>
.item-card {
  margin: 10px;
  padding: 10px;
  width: 100px;
  height: 100px;
  box-shadow: 0 0 5px #34a080;
}
</style>
