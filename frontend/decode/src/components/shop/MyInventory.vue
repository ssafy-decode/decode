<script setup>
import InventoryItem from './InventoryItem.vue';
import ItemDetail from './ItemDetail.vue';
import { ref } from 'vue';
import { storeToRefs } from 'pinia';
import { useShopStore } from '@/stores/shopStore';

const shopStore = useShopStore();
const detail = ref({});
const selected = ref(false);
const { myInventory: inventory } = storeToRefs(shopStore);

const changeDetail = (item) => {
  selected.value = true;
  detail.value = item;
};
</script>
<template>
  <v-container style="text-align: center">
    <h1 style="color: #34a080">인벤토리</h1>
    <v-row style="display: flex; align-items: center; margin-bottom: 20px">
      <v-col cols="3" offset="10">
        <router-link to="/shop">
          <v-btn color="#62C0A6" style="height: 38px; width: 99px; border-radius: 34px; font-weight: bold">상점</v-btn>
        </router-link>
      </v-col>
    </v-row>
    <v-card class="item-container">
      <v-row>
        <v-col cols="8">
          <v-row>
            <v-col v-for="(item, index) in inventory" :key="index" cols="2">
              <InventoryItem :item="item" @click="changeDetail(item)" />
            </v-col>
          </v-row>
        </v-col>
        <v-col cols="4">
          <ItemDetail :detail="detail" :isSelected="selected" />
        </v-col>
      </v-row>
    </v-card>
  </v-container>
</template>

<style scoped>
.item-container {
  margin-top: 20px;
  padding: 20px;
  box-shadow: 0 0 5px gray;
  height: 400px;
}
</style>
