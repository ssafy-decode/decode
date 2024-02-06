<template>
  <v-container class="itemshop">
    <ExchangeTab />
    <br />
    <ShopTab />
  </v-container>

  <!-- <OpenviduModal></OpenviduModal> -->
</template>

<script setup>
import ExchangeTab from '@/components/shop/ExchangeTab.vue';
import ShopTab from '@/components/shop/ShopTab.vue';
import OpenviduModal from '@/components/chat/OpenviduModal.vue';
import { useShopStore } from '@/stores/shopStore.js';
const shopStore = useShopStore();
const { fetchProducts } = shopStore;
fetchProducts();
</script>
<style scoped>
.custom-openvidu-modal {
  background-color: red;
  width: 100%;
  height: 100%;
}
</style>
