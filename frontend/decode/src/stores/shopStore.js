import { ref } from 'vue';
import { defineStore } from 'pinia';
import router from '@/router';
import axios from '@/utils/common-axios';
import { useUserStore } from '@/stores/userStore';

export const useShopStore = defineStore(
  'shop',
  () => {
    // 다른 store 활용
    const userStore = useUserStore();

    // 배열
    const items = ref([]); // 아이템들
    const searchedItems = ref([]); // 검색된 아이템들
    const products = ref([]); // 상품들
    const searchedProducts = ref([]); // 검색된 상품들

    // 각 개체
    const item = ref(''); // 아이템
    const searchedItem = ref(''); // 검색된 아이템
    const product = ref(''); // 상품
    const searchedProduct = ref(''); // 검색된 상품

    // 토큰 정보
    const accessToken = ref(userStore.accessToken); // 파싱된 토큰 값 (활용 시 앞에 'Bearer '을 붙일 것!)

    // 아이템 전체 목록 가져오기
    // (itemId(숫자), itemCount(숫자), itemName, itemDetail)
    const getItems = (userId) => {
      axios
        .get(`/item/list/${userId}`, {
          withCredentials: true,
        })
        .then((res) => {
          accessToken.value = userStore.parseToken(res);
          const response = res.data;
          if (response.status === 'OK') {
            items.value = response.data;
            console.log('items.value는 제대로 가져오는 중?', items.value);
          }
        })
        .catch((error) => {
          console.error('Error:', error);
        });
    };

    // 상품 전체 목록 가져오기
    // (productId(숫자), productName, productDetail, productPrice(숫자), productType(숫자))
    const getProducts = () => {
      console.log('작동 테스트');
      axios
        .get(`/product/list`, {
          withCredentials: true,
          headers: {
            Authorization: `Bearer ${accessToken.value}`,
          },
        })
        .then((res) => {
          accessToken.value = userStore.parseToken(res);
          const response = res.data;
          if (response.status === 'OK') {
            products.value = response.data;
            console.log('products.value는 제대로 가져오는 중', products.value);
          }
        })
        .catch((error) => {
          console.error('Error:', error);
        });
    };

    // 상품 검색된 이름으로 목록 가져오기
    // (productId(숫자), productName, productDetail, productPrice(숫자), productType(숫자))
    const searchProduct = (name) => {
      axios
        .get(`/product/${name}`, {
          withCredentials: true,
        })
        .then((res) => {
          accessToken.value = userStore.parseToken(res);
          const response = res.data;
          if (response.status === 'OK') {
            searchedProducts.value = response.data;
            console.log('searchedProducts.value는 제대로 가져오는 중?', searchedProducts.value);
          }
        })
        .catch((error) => {
          console.error('Error:', error);
        });
    };

    // 아이템 사용하기
    // item 형태: { itemId: 숫자, userId: 숫자, count: 숫자 }
    const useItem = (item) => {
      axios
        .post(`/item/use`, item, {
          withCredentials: true,
        })
        .then((res) => {
          accessToken.value = userStore.parseToken(res);
          const response = res.data;
          if (response.status === 'OK') {
            alert('아이템이 사용되었습니다.');
          } else {
            alert('아이템 사용에 실패했습니다.');
          }
        })
        .catch((error) => {
          console.error('Error:', error);
        });
    };

    // 상품 구매하기
    // thing 형태: { productId: 숫자, userId: 숫자, count: 숫자 }
    const buyProduct = (thing) => {
      axios
        .post(`/product`, thing, {
          withCredentials: true,
        })
        .then((res) => {
          accessToken.value = userStore.parseToken(res);
          const response = res.data;
          if (response.status === 'OK') {
            alert('구매가 완료되었습니다.');
          } else {
            alert('구매에 실패했습니다.');
          }
        })
        .catch((error) => {
          console.error('Error:', error);
        });
    };

    return {
      // userStore,
      items,
      searchedItems,
      products,
      searchedProducts,
      item,
      searchedItem,
      product,
      searchedProduct,
      getItems,
      getProducts,
      searchProduct,
      useItem,
      buyProduct,
    };
  },
  { persist: { storage: sessionStorage } },
);
