<template>
  <v-container style="text-align: center">
    <h2 style="color: #34a080">인벤토리</h2>
    <br />
    <br />
    <v-row style="display: flex; align-items: center; margin-left: 500px; margin-right: 30px">
      <v-col>
        <v-text-field
          v-model="searchKeyword"
          variant="plain"
          label="아이템을 입력해주세요"
          class="search-field"
          bg-color="fff"
          clearable
          append-inner
        >
          <template #append-inner>
            <v-btn class="searchBtn" size="medium" @click="productNameSearch(name)">
              <img src="./searchicon.png" alt="검색아이콘" style="width: 40px; height: auto" />
            </v-btn>
          </template>
        </v-text-field>
      </v-col>
      <v-col>
        <router-link to="/shop">
          <v-btn color="#62C0A6" style="height: 38px; width: 99px; border-radius: 34px; font-weight: bold">상점</v-btn>
        </router-link>
      </v-col>
    </v-row>
    <br />

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
              <v-col v-for="product in getFilteredProducts(1)" :key="product.productId">
                <ProductComponent :product="product" />
              </v-col>
            </v-row>
          </v-window-item>

          <v-window-item value="consumption-item">
            <v-row>
              <v-col v-for="product in getFilteredProducts(2)" :key="product.productId">
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
      // storeName: '상점 이름',
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
          name: `아이템 ${i}`,
          description: `아이템 설명 ${i}`,
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
.search-field {
  width: 705px;
  height: 60px;
  margin-right: 10px;
  background-color: white;
  border-radius: 31px;
  border: 1px solid #898989;
  padding-left: 20px;
  padding-right: 20px;
}

.searchBtn {
  position: relative;
  bottom: 10px;
  border-radius: 30px;
}
</style>
