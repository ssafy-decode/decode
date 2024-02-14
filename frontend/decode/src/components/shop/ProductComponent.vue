<script setup>
import { useUserStore } from '@/stores/userStore';
import { storeToRefs } from 'pinia';
import { ref, defineProps } from 'vue';
import axios from '@/utils/common-axios';
const userStore = useUserStore();
const { loginUserId: uid, loginUserProfile: profile, accessToken: accessToken } = storeToRefs(userStore);
const props = defineProps({
  product: Object,
});
const count = ref(0);

const data = {
  userId: uid.value,
  productId: props.product.productId,
  count: 0,
};

const showModal = ref(false);

const closeModal = () => {
  showModal.value = false;
};

const openModal = () => {
  showModal.value = true;
};

const buyProduct = async () => {
  data.count = count.value;
  await axios
    .post('/product', data, {
      headers: {
        Authorization: `Bearer ${accessToken.value}`,
      },
    })
    .then((res) => {
      alert('구매가 완료되었습니다.');
      closeModal();
    })
    .catch((err) => {
      alert('돈이 없으시네요.');
    });
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
      </div>
      <div class="product-info2">
        <p>{{ product.productDetail }}</p>
      </div>
      <div class="product-info3">
        <div class="price-container">
          <h5>{{ product.productPrice }}</h5>
          &nbsp;
          <img src="./coin.png" width="25px" height="25px" />
        </div>
      </div>
      <div class="mycurrency">
        보유 포인트: {{ profile.point }}&nbsp;&nbsp; 보유 코인:
        {{ profile.coin }}
      </div>
      <div class="product-buy-btn">
        <v-btn color="#34a080" @click="openModal">구매</v-btn>
      </div>
    </div>
  </div>

  <div v-if="showModal" class="modal">
    <div class="modal-content">
      <span class="close" @click="closeModal">&times;</span>
      <h1>상품 미리 보기</h1>
      <!-- 여기 미리보기 컴포넌트 대신 들어가야함 -->
      <div class="modal-product-image">
        <img :src="product.productImage" alt="product.productName" />
      </div>
      <div class="modal-product-info">
        <h2>{{ product.productName }}</h2>
        <div class="modal-product-info1">
          <p>{{ product.productDetail }}</p>
        </div>
        <h4>{{ product.productPrice }}</h4>

        <div class="modal-product-count">
          <h4>수량</h4>
          <input style="border: 1px solid gray; margin-left: 5px" type="number" v-model="count" />
        </div>

        <div class="modal-product-buy-btn">
          <v-btn color="#34a080" @click="buyProduct">구매하기</v-btn>
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
}
.product-info2 {
  margin-top: 15px;
  height: 100px;
}
.prodcut-info3 {
  display: flex;
  justify-content: center;
  align-items: center;
}
.price-container {
  align-items: center;
  display: flex;
  justify-content: center;
}

.currency-icon {
  margin-left: 5px;
}
.product-buy-btn {
  margin-top: 5px;
}
.modal-product-buy-btn {
  margin-top: 20px;
}
.modal-product-info1 {
  margin-top: 20px;
  height: 100px;
}
.modal-product-count {
  margin-top: 20px;
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>
