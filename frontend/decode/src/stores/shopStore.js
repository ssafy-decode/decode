import { ref, computed } from 'vue';
import { defineStore } from 'pinia';
import axios from '@/utils/common-axios';

const useShopStore = defineStore('useShopStore', () => {
  const products = ref([]);
  const myItems = ref([]);

  const fetchProducts = async () => {
    await axios
      .get('/product/list')
      .then((res) => {
        products.value = res.data.data;
      })
      .catch((err) => {});
  };

  const fetchInventory = async (uid) => {
    await axios
      .get(`/item/list/${uid}`)
      .then((res) => {
        myItems.value = res.data.data;
      })
      .catch((err) => {});
  };

  const allProducts = computed(() => products.value);
  const myInventory = computed(() => myItems.value);

  return {
    products,
    fetchProducts,
    allProducts,
    myItems,
    fetchInventory,
    myInventory,
  };
});

export { useShopStore };
