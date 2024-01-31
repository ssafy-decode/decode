<template>
  <v-container>
    <h2>{{ storeName }}</h2>

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
      <v-tabs v-model="selectedTab" @click="onTabChange">
        <v-tab value="all-product">전체</v-tab>
        <v-tab value="wearing-item">착장아이템</v-tab>
        <v-tab value="consumption-item">소비아이템</v-tab>
      </v-tabs>

      <v-card-text>
        <v-window v-model="selectedTab">
          <v-window-item value="all-product">
            <v-row>
              <v-col v-for="product in getFilteredProducts(0)" :key="product.id">
                <ProductComponent :product="product" />
              </v-col>
            </v-row>
          </v-window-item>
          <v-window-item value="wearing-item">
            <v-row>
              <v-col v-for="product in getFilteredProducts(1)" :key="product.id">
                <ProductComponent :product="product" />
              </v-col>
            </v-row>
          </v-window-item>

          <v-window-item value="consumption-item">
            <v-row>
              <v-col v-for="product in getFilteredProducts(2)" :key="product.id">
                <ProductComponent :product="product" />
              </v-col>
            </v-row>
          </v-window-item>
        </v-window>
      </v-card-text>
    </v-card>
  </v-container>
</template>

<script>
import ProductComponent from './ProductComponent.vue';

export default {
  data() {
    return {
      storeName: '상점 이름',
      searchKeyword: '',
      selectedTab: 'all-product', // 초기값 설정
      tabs: ['전체', '착장아이템', '소비아이템'],
      allProducts: this.generateProducts(),
    };
  },
  methods: {
    generateProducts() {
      const products = [];
      for (let i = 1; i <= 15; i++) {
        const category = Math.floor(Math.random() * 2 + 1); // Random category (1, 2)

        const product = {
          id: i,
          name: `상품 ${i}`,
          description: `상품 설명 ${i}`,
          image: `path/to/image${i}.jpg`,
          category: category,
        };
        console.log(product);
        products.push(product);
      }
      return products;
    },
    productNameSearch() {
      console.log('검색 버튼 클릭됨');
      // Add your search logic here
    },
    goToInventory() {
      console.log('인벤토리로 이동');
      // Add your logic to navigate to inventory
    },
    getFilteredProducts(category) {
      if (category === 0) {
        return this.allProducts; // 전체 탭일 경우 모든 상품 반환
      } else {
        return this.allProducts.filter((product) => product.category === category);
      }
    },
    onTabChange() {
      console.log(this.selectedTab);
      // 여기에서 원하는 동작 수행
    },
  },
  components: {
    ProductComponent,
  },
};
</script>

<style scoped>
/* 필요한 스타일이 있다면 추가 */
</style>
