import { ref, computed } from 'vue';
import { defineStore } from 'pinia';
import axios from '@/utils/common-axios';

const useShopStore = defineStore('useShopStore', () => {
  const products = ref([]);

  const fetchProducts = async () => {
    await axios
      .get('/product/list')
      .then((res) => {
        // if (res.data.data.length === 0) {
        //   for (let i = 0; i < 10; i++) {
        //     products.value.push({
        //       productId: i,
        //       productName: '상품' + i,
        //       productPrice: i * 1000,
        //       productDetail: '상품 상세 설명' + i,
        //       productImage: 'https://via.placeholder.com/150',
        //       productType: 0,
        //     });
        //   }
        // } else products.value = res.data.data;
        for (let i = 0; i < 11; i++) {
          products.value.push({
            productId: i,
            productName: '상품' + i,
            productPrice: i * 1000,
            productDetail: '상품 상세 설명' + i,
            productImage: 'https://via.placeholder.com/200',
            productType: 0,
          });
        }
      })
      .catch((err) => {
        console.log(err);
      });
  };

  const allProducts = computed(() => products.value);

  return {
    products,
    fetchProducts,
    allProducts,
  };
});

export { useShopStore };
