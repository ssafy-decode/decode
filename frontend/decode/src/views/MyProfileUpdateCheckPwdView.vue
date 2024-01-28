<template>
  <div style="text-align: center; margin-top: 4%; margin-bottom: 10%">
    <img style="width: 3%" src="./LogoDecode3.png" />
    <br />
    <h2 style="color: #575757; font-size: 35px; margin-top: 5px">회원정보 변경</h2>
    <br />
    <div class="pa-5" rounded>
      <v-card
        class="mx-auto px-4 py-8"
        max-width="418"
        style="
          background-color: #f3f3f3;
          border-radius: 31px;
          box-shadow: 0 0px 36px rgba(0, 0, 0, 0.2);
          border: 15px solid #d9d9d9;
        "
      >
        <h3 style="color: #575757; font-size: 25px">비밀번호를 입력해주세요</h3>
        <br />
        <div class="main">
          <v-text-field
            variant="solo"
            class="textfield"
            bg-color="#d9d9d9"
            v-model="password"
            :rules="[writePassword]"
            :type="showPassword ? 'text' : 'password'"
            label="비밀번호 확인"
            append-inner
          >
            <template #append-inner>
              <v-icon @click="toggleEye" style="margin-right: 5px">{{
                showPassword ? 'mdi-eye' : 'mdi-eye-off'
              }}</v-icon>
            </template>
          </v-text-field>
        </div>
        <!-- <br /> -->
        <div class="buttons">
          <span>
            <router-link to="/profile">
              <v-btn
                class="btn"
                @click="confirm"
                color="#62C0A6"
                size="x-large"
                type="submit"
                variant="elevated"
                style="color: #000000"
              >
                다음
              </v-btn>
            </router-link>
          </span>
        </div>
      </v-card>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { useUserStore } from '@/stores/userStore';

const userStore = useUserStore();

const password = ref('');
const showPassword = ref(false);

// 눈 버튼으로 비밀번호 가리고 숨기고
const toggleEye = () => {
  showPassword.value = !showPassword.value;
};

const confirm = () => {
  // 추후 작성
};
</script>

<style scoped>
.buttons {
  text-align: end;
  margin: 0 20px;
}
.btn {
  width: 110px;
  height: 28px;
  border-radius: 34px;
  font-size: smaller;
  font-weight: bold;
}

.textfield {
  margin: 0 20px;
  margin-bottom: 5px;
}

.textfield :deep(label) {
  color: #747474;
}

.textfield :deep(.v-field) {
  border-radius: 10px;
}
</style>
