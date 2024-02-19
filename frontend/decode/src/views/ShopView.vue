<template>
  <v-container class="itemshop">
    <ExchangeTab :profile="profile" />
    <br />
    <br />
    <ShopTab :profile="profile" />
  </v-container>

  <!-- <OpenviduModal></OpenviduModal> -->
</template>

<script setup>
import { ref, onBeforeMount } from 'vue';
import { storeToRefs } from 'pinia';
import ExchangeTab from '@/components/shop/ExchangeTab.vue';
import ShopTab from '@/components/shop/ShopTab.vue';
import { useUserStore } from '@/stores/userStore';
import { useProfileStore } from '@/stores/profileStore';
import { useShopStore } from '@/stores/shopStore.js';

const userStore = useUserStore();
const profileStore = useProfileStore();
const shopStore = useShopStore();
const { setUserProfile } = profileStore;
const { handleUserProfile: profile } = storeToRefs(profileStore);
const { fetchProducts } = shopStore;
fetchProducts();
onBeforeMount(async () => {
  await setUserProfile(userStore.loginUserId);
});
</script>
<style scoped>
.custom-openvidu-modal {
  background-color: red;
  width: 100%;
  height: 100%;
}
</style>
