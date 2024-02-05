<template>
  <v-container style="text-align: center">
    <h2>상점</h2>
    <br />
    <v-row>
      <v-col>
        <v-row>
          <v-text-field
            v-model="searchKeyword"
            label="상품을 입력해주세요."
            append-icon="mdi-magnify"
            @click:append="productNameSearch"
          ></v-text-field>
        </v-row>
      </v-col>
      <v-col cols="2">
        <router-link to="/inventory"><v-btn color="primary">인벤토리</v-btn></router-link>
      </v-col>
    </v-row>

    <v-card>
      <!-- <v-tabs v-model="selectedTab" @click="onTabChange"> -->
      <v-tabs v-model="selectedTab">
        <v-tab value="all-product">전체</v-tab>
        <v-tab value="wearing-item">착장아이템</v-tab>
        <v-tab value="consumption-item">소비아이템</v-tab>
      </v-tabs>

      <v-card-text>
        <v-window v-model="selectedTab">
          <v-window-item value="all-product">
            <v-row>
              <v-col v-for="product in getFilteredProducts(0)" :key="product.productId">
                <ProductComponent :product="product" />
              </v-col>
            </v-row>
          </v-window-item>
          <v-window-item value="wearing-item">
            <v-row>
              <v-col
                v-if="getFilteredProducts(1).length > 0"
                v-for="product in getFilteredProducts(1)"
                :key="product.productId"
              >
                <ProductComponent :product="product" />
              </v-col>
              <v-col v-else v-for="product in getFilteredProducts(1)" :key="product.productId">
                해당 상품이 없습니다.
              </v-col>
            </v-row>
          </v-window-item>

          <v-window-item value="consumption-item">
            <v-row>
              <v-col
                v-if="getFilteredProducts(2).length > 0"
                v-for="product in getFilteredProducts(2)"
                :key="product.productId"
              >
                <ProductComponent :product="product" />
              </v-col>
              <v-col v-else v-for="product in getFilteredProducts(2)" :key="product.productId">
                해당 상품이 없습니다.
              </v-col>
            </v-row>
          </v-window-item>
        </v-window>
      </v-card-text>
    </v-card>
  </v-container>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useShopStore } from '@/stores/shopStore';
import ProductComponent from './ProductComponent.vue';

const shopStore = useShopStore();

const products = ref([]); // 전체 상품 목록
const searchedProducts = ref([]); // 검색한 상품 목록
const searchKeyword = ref(''); // 검색어
const selectedTab = ref('all-product'); // 선택된 탭

const getFilteredProducts = (category) => {
  const filteredProducts = products.value;

  if (category === 0) {
    if (!filteredProducts) {
      console.log('해당 상품이 없습니다.');
    }
    return filteredProducts; // 전체 탭 => 모든 상품 반환
  } else {
    if (!filteredProducts) {
      console.log('해당 상품이 없습니다.');
    }
    return filteredProducts.filter((product) => product.productType === category); // 카테고리 상품만 반환
  }
};

const productNameSearch = () => {
  // 상품 검색
  shopStore.searchProduct(searchKeyword.value);
  searchedProducts.value = shopStore.searchedProducts.value;
  return searchedProducts;
};

onMounted(async () => {
  await shopStore.getProducts(); // 전체 상품 목록 가져오기
  products.value = shopStore.products.value;
});

// export default {
//   setup() {
//     const shopStore = useShopStore();
//     const searchedProducts = ref([]);
//     const searchKeyword = ref('');
//     const selectedTab = ref('all-product');

//     const generateProducts = () => {
//       // Use the computed property or direct access to the store's products
//       return shopStore.products;
//     };

//     const productNameSearch = () => {
//       console.log('검색 버튼 클릭됨');
//       shopStore.searchProduct(searchKeyword.value);
//     };

//     const getFilteredProducts = (category) => {
//       if (category === 0) {
//         return generateProducts(); // 전체 탭일 경우 모든 상품 반환
//       } else {
//         return generateProducts().filter((product) => product.category === category);
//       }
//     };

//     const onTabChange = () => {
//       console.log(selectedTab.value);
//       // 여기에서 원하는 동작 수행
//     };

//     onMounted(() => {
//       shopStore.getProducts(); // 전체 상품 목록 가져오기
//     });

//     return {
//       searchedProducts,
//       searchKeyword,
//       selectedTab,
//       generateProducts,
//       productNameSearch,
//       getFilteredProducts,
//       onTabChange,
//     };
//   },
//   components: {
//     ProductComponent,
//   },
// };

// export default {
//   data() {
//     return {
//       shopStore: useShopStore(),
//       searchedProducts: [],
//       // storeName: '상점 이름',
//       searchKeyword: '',
//       selectedTab: 'all-product', // 초기값 설정
//       tabs: ['전체', '착장아이템', '소비아이템'],
//       // allProducts: this.generateProducts(),
//     };
//   },
//   methods: {
//     generateProducts() {
//       const products = [];
//       useShopStore().getProducts();
//       products = useShopStore().products;
//       // for (let i = 1; i <= 15; i++) {
//       //   const category = Math.floor(Math.random() * 2 + 1); // Random category (1, 2)

//       //   const product = {
//       //     id: i,
//       //     name: `상품 ${i}`,
//       //     description: `상품 설명 ${i}`,
//       //     image: `path/to/image${i}.jpg`,
//       //     category: category,
//       //   };
//       //   console.log(product);
//       //   products.push(product);
//       // }
//       return products;
//     },
//     productNameSearch() {
//       console.log('검색 버튼 클릭됨');
//       // Add your search logic here
//       shopStore.searchProduct(this.searchKeyword);
//     },
//     getFilteredProducts(category) {
//       if (category === 0) {
//         return this.allProducts; // 전체 탭일 경우 모든 상품 반환
//       } else {
//         return this.allProducts.filter((product) => product.category === category);
//       }
//     },
//     onTabChange() {
//       console.log(this.selectedTab);
//       // 여기에서 원하는 동작 수행
//     },
//   },
//   setup() {
//     //   const shopStore = useShopStore();
//     //   const searchedProducts = ref([]);
//     // const productNameSearch = () => {
//     //   // 상품 검색

//     // };
//     onMounted(() => {
//       shopStore.getProducts(); // 전체 상품 목록 가져오기
//     });
//     return {
//       searchedProducts,
//       // productNameSearch,
//     };
//   },
//   components: {
//     ProductComponent,
//   },
// };
</script>

<style scoped>
/* 필요한 스타일이 있다면 추가 */
</style>
