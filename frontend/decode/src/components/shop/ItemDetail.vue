<script setup>
import axios from '@/utils/common-axios';
import { useUserStore } from '@/stores/userStore';
import { storeToRefs } from 'pinia';
const userStore = useUserStore();
const { handleLoginUserId: uid, handleAccessToken: accessToken } = storeToRefs(userStore);

const props = defineProps({
  detail: Object,
  isSelected: Boolean,
});

const useItem = () => {
  axios
    .post(
      '/item/use',
      {
        itemId: props.detail.itemId,
        userId: uid.value,
        count: 1,
      },
      {
        headers: {
          Authorization: `Bearer ${accessToken.value}`,
        },
      },
    )
    .then((res) => {})
    .catch((err) => {});
};
</script>

<template>
  <div>여긴 아이템 디테일</div>

  <div v-if="isSelected">
    <v-img :src="detail.itemImage" height="200px" />
    <h3>{{ detail.itemName }}</h3>
    <p>{{ detail.itemDetail }}</p>

    <v-btn color="#34a080" @click="useItem">사용하기</v-btn>
  </div>
  <div v-else>
    <h3>아이템을 선택해주세요.</h3>
  </div>
</template>

<style scoped></style>
