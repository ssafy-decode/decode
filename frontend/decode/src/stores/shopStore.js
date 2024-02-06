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

        // for (let i = 0; i < 11; i++) {
        //   products.value.push({
        //     productId: i,
        //     productName: '상품' + i,
        //     productPrice: i * 1000,
        //     productDetail: '상품 상세 설명' + i,
        //     productImage: 'https://via.placeholder.com/200',
        //     productType: 0,
        //   });
        // }
      })
      .catch((err) => {
        console.log(err);
      });
  };

  const fetchInventory = async (uid) => {
    await axios
      .get(`/item/list/${uid}`)
      .then((res) => {
        myItems.value = res.data.data;
      })
      .catch((err) => {
        console.log(err);
      });
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
