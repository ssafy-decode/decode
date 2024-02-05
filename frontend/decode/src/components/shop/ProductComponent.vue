<template>
  <v-card v-if="product && product.category !== undefined">
    <!-- <v-card-title>{{ product.name }}</v-card-title>
    <v-card-subtitle>{{ product.description }}</v-card-subtitle> -->
    <!-- 이상해져서 일단 주석 처리 -->
    <v-card-title>{{ product.productName }}</v-card-title>
    <v-card-subtitle>{{ product.productDetail }}</v-card-subtitle>
    <!-- Add more details or customize as needed -->
  </v-card>
</template>

<script setup>
const { props } = defineProps(['product']);

// import { ref, onMounted } from 'vue';
// import { useShopStore } from '@/stores/shopStore';

// export default {
//   props: {
//     product: {
//       type: Object,
//       required: true,
//     },
//   },
//   setup(props) {
//     const shopStore = useShopStore();
//     const products = ref([]);
//     onMounted(() => {
//       shopStore.getProducts(); // 전체 상품 목록 가져오기
//     });
//     return {
//       products,
//     };
//   },
// };
</script>

<style scoped>
/* Add styling if needed */
</style>
