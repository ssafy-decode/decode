<script setup>
const props = defineProps({
  detail: Object,
  isSelected: Boolean,
});
</script>

<template>
  <div>여긴 아이템 디테일</div>

  <div v-if="isSelected">
    <v-img :src="detail.itemImage" height="200px" />
    <h3>{{ detail.itemName }}</h3>
    <p>{{ detail.itemDetail }}</p>
  </div>
  <div v-else>
    <h3>아이템을 선택해주세요.</h3>
  </div>
</template>

<style scoped></style>
