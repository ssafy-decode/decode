<script setup>
import { useUserStore } from '@/stores/userStore';
import { storeToRefs } from 'pinia';
import { ref, defineProps } from 'vue';
const userStore = useUserStore();
const { loginUserId: uid, loginUserProfile: profile } = storeToRefs(userStore);
const props = defineProps({
  product: Object,
});

const data = {
  userId: uid.value,
  productId: props.product.productId,
};

const showModal = ref(false);

const closeModal = () => {
  showModal.value = false;
};

const buyProduct = () => {
  showModal.value = true;
};
</script>
<template>
  <div class="product-box">
    <div class="product-image">
      <img :src="product.productImage" alt="product.productName" />
    </div>
    <div class="product-info">
      <div class="product-info1">
        <h3>{{ product.productName }}</h3>
        <h4>{{ product.productPrice }}</h4>
      </div>
      <div class="product-info2">
        <p>{{ product.productDetail }}</p>
      </div>
      <div style="margin-top: 10px" v-if="profile">
        보유 포인트: {{ profile.point }}&nbsp;&nbsp; 보유 코인:
        {{ profile.coin }}
      </div>
      <div class="product-buy-btn">
        <v-btn color="#34a080" @click="buyProduct">구매</v-btn>
      </div>
    </div>
  </div>

  <div v-if="showModal" class="modal">
    <div class="modal-content">
      <span class="close" @click="closeModal">&times;</span>
      <h1>상품 미리 보기</h1>
      <div class="modal-product-image">
        <img :src="product.productImage" alt="product.productName" />
      </div>
      <div class="modal-product-info">
        <h2>{{ product.productName }}</h2>
        <div class="modal-product-info1">
          <p>{{ product.productDetail }}</p>
        </div>
        <h4>{{ product.productPrice }}</h4>
        <div class="modal-product-buy-btn">
          <v-btn color="#34a080">구매하기</v-btn>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.modal {
  display: block;
  position: fixed;
  z-index: 1;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
}
.modal-content {
  border-radius: 10px;
  background-color: #fefefe;
  margin: 5% auto;
  padding: 20px;
  border: 1px solid #34a080;
  box-shadow: 0 0 5px #34a080;
  width: 50%;
  height: 80%;
}

.close {
  color: #aaa;
  float: right;
  font-size: 28px;
  font-weight: bold;
}

.close:hover,
.close:focus {
  color: black;
  text-decoration: none;
  cursor: pointer;
}
.modal-product-image {
  text-align: center;
  margin-top: 20px;
}
.product-box {
  border: 1px groove #34a080;
  padding: 30px;
  margin: 10px;
  width: 300px;
  height: 500px;
  text-align: center;
}
.product-info1 {
  display: flex;
  justify-content: space-between;
}
.product-info2 {
  margin-top: 15px;
  height: 100px;
}
.product-buy-btn {
  margin-top: 20px;
}
.modal-product-buy-btn {
  margin-top: 100px;
}
.modal-product-info1 {
  margin-top: 20px;
  height: 100px;
}
</style>
