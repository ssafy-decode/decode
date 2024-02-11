<script setup>
import ProductComponent from './ProductComponent.vue';
import { ref } from 'vue';
import { storeToRefs } from 'pinia';
import { useShopStore } from '@/stores/shopStore';
const shopStore = useShopStore();
const { allProducts } = storeToRefs(shopStore);
</script>
<template>
  <v-container style="text-align: center">
    <h1><span style="color: #34a080">de;code Store</span></h1>
    <br />
    <v-row>
      <v-col cols="2" offset="10">
        <router-link to="/inventory"><v-btn color="#34a080">인벤토리</v-btn></router-link>
      </v-col>
    </v-row>
    <v-card class="product-container">
      <v-row>
        <v-col v-for="(product, index) in allProducts" :key="index" cols="4">
          <ProductComponent :profile="profile" :product="product" />
        </v-col>
      </v-row>
    </v-card>
  </v-container>
</template>

<style scoped>
.product-container {
  margin-top: 20px;
  padding: 20px;
  box-shadow: 0 0 5px gray;
}
</style>
