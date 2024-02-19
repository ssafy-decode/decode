<template>
  <div style="text-align: center; margin-top: 8%; margin-bottom: 10%">
    <div class="pa-5" rounded>
      <v-card
        class="mx-auto px-4 py-8"
        max-width="418"
        style="background-color: #f3f3f3; border-radius: 68px; box-shadow: 0 0px 36px rgba(0, 0, 0, 0.2)"
      >
        <img style="width: 13%" src="./LogoDecode3.png" />
        <h2 style="color: #898989; font-size: 30px">회원정보 변경</h2>
        <h3 style="font-size: 20px; color: #34a080; margin-top: 5px; margin-bottom: 40px">비밀번호를 입력해주세요</h3>
        <div class="main">
          <v-text-field
            variant="solo"
            class="textfield"
            bg-color="#d9d9d9"
            v-model="password"
            :rules="[writePassword]"
            :type="showPassword ? 'text' : 'password'"
            label="비밀번호"
            prepend-inner
            append-inner
            @keyup.enter="confirmpwd"
          >
            <!-- 마우스 클릭 말고도 엔터키를 누르는 것으로도 비번 확인이 작동되도록 수정 -->
            <template #prepend-inner>
              <img
                src="./pwd.png"
                alt="비밀번호 아이콘"
                style="width: auto; height: auto; margin-left: 1px; margin-right: 10px"
              />
            </template>
            <template #append-inner>
              <v-icon @click="toggleEye" style="margin-right: 5px">{{
                showPassword ? 'mdi-eye' : 'mdi-eye-off'
              }}</v-icon>
            </template>
          </v-text-field>
        </div>

        <div class="buttons">
          <span>
            <v-btn
              class="btn"
              @click="confirmpwd"
              color="#62C0A6"
              size="x-large"
              type="submit"
              variant="elevated"
              style="color: #000000; margin-top: 20px; margin-bottom: 5px"
            >
              다음
            </v-btn>
          </span>
        </div>
      </v-card>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { useUserStore } from '@/stores/userStore';
import { useProfileStore } from '@/stores/profileStore';
import { storeToRefs } from 'pinia';

const userStore = useUserStore();
const profileStore = useProfileStore();

const { checkPwd } = profileStore;
const { handleAccessToken: accessToken } = storeToRefs(userStore);

const password = ref('');
const showPassword = ref(false);

// 눈 버튼으로 비밀번호 가리고 숨기고
const toggleEye = () => {
  showPassword.value = !showPassword.value;
};

const writePassword = (value) => {
  return !!value || '비밀번호를 입력하세요.';
};

const confirmpwd = () => {
  const confirmuserpwd = {
    password: password.value,
  };
  checkPwd(confirmuserpwd, accessToken.value);
};
</script>

<style scoped>
.buttons {
  text-align: flex-end;
  margin: 0 20px;
}
.btn {
  width: 95px;
  height: 58px;
  border-radius: 34px;
  font-size: 15px;
  font-weight: bold;
}

.textfield {
  margin: 0 20px;
  margin-bottom: 5px;
  height: 63px;
}

.textfield :deep(label) {
  color: #ffffff;
}

.textfield :deep(.v-field) {
  border-radius: 55px;
}
</style>
