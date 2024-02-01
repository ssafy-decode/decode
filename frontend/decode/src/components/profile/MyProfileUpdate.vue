<template>
  <div>
    <div class="pa-5" rounded style="color: #575757; font-weight: bold">
      <v-card
        class="mx-auto px-4 py-8"
        max-width="481"
        style="text-align: center; background-color: #f3f3f3; border-radius: 68px; border: 15px solid #d9d9d9"
      >
        <v-row style="margin-left: 5px">
          <v-col cols="4">
            <img style="width: 70%" src="../default.png" />
            <br />
            <v-btn class="photobtn" color="#62C0A6" type="submit" variant="elevated">사진 변경</v-btn>
          </v-col>
          <v-col cols="8">
            <div style="font-size: 16px">
              <span> 이 &nbsp;&nbsp; 름: {{ userName }}</span>
              <br />
              <br />
              <span>닉 네 임: {{ userNickName }}</span>
              <br />
              <br />
              <span>생년월일: {{ userBirthday }}</span>
              <br />
            </div>
          </v-col>
        </v-row>
        <br />
        <div style="text-align: left; margin-left: 20px">
          <span>이&nbsp;&nbsp; 메&nbsp;&nbsp; 일: {{ userEmail }}</span>
          <br />
          <br />
          <span>휴대폰 뒷자리: {{ userPhone }}</span>
          <br />
          <br />
        </div>
        <br />
        <div>
          기술 스택 변경
          <br />
          {{ userStore.tagIdList.value }} (값이 없어서 빈칸이 뜨는 중입니다)
          <br />
          <router-link to="/updatetechstack"><v-btn>기술 스택 변경</v-btn></router-link>
        </div>
        <br />
        <div>
          비밀번호 변경
          <br />
          <br />
          <v-text-field
            bg-color="#d9d9d9"
            :rules="[writePassword]"
            v-model="password"
            variant="solo"
            class="textfield"
            clearable
            label="비밀번호 변경"
            hint="영문, 숫자, 특수문자 조합 8자리 이상"
            append-inner
            :type="showPassword ? 'text' : 'password'"
            style="margin-right: 20px"
          >
            <template #append-inner>
              <v-icon @click="toggleEye" style="margin-right: 10px">{{
                showPassword ? 'mdi-eye' : 'mdi-eye-off'
              }}</v-icon>
            </template></v-text-field
          >
          <v-text-field
            bg-color="#d9d9d9"
            :rules="[writePassword2]"
            v-model="password2"
            variant="solo"
            class="textfield"
            clearable
            label="비밀번호 확인"
            append-inner
            :type="showPassword2 ? 'text' : 'password'"
            style="margin-right: 20px"
          >
            <template #append-inner>
              <v-icon @click="toggleEye2" style="margin-right: 10px">{{
                showPassword2 ? 'mdi-eye' : 'mdi-eye-off'
              }}</v-icon>
            </template></v-text-field
          >
        </div>
        <v-btn class="updatebtn" @click="updatepwd" color="#62C0A6" type="submit" variant="elevated"
          >변경 내용 저장</v-btn
        >
      </v-card>
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue';
import { useUserStore } from '@/stores/userStore';

const userStore = useUserStore();
const password = ref('');
const password2 = ref('');
const showPassword = ref(false);
const showPassword2 = ref(false);

// 눈 버튼 누르면 비밀번호 가렸다 보였다
const toggleEye = () => {
  showPassword.value = !showPassword.value;
};
const toggleEye2 = () => {
  showPassword2.value = !showPassword2.value;
};

const writePassword = (value) => {
  return !!value || '비밀번호를 입력하세요.';
};

const writePassword2 = (value) => {
  return !!value || '비밀번호 확인을 입력하세요.';
};

// 비밀번호 형식 올바른지
const isPasswordValid = (pwd) => {
  return pwd.length >= 8 && /[!@#$%^&*(),.?":{}|<>]/g.test(pwd);
};

// 이름, 닉네임, 생년월일, 이메일, 전화번호는 변경 불가 (+ 프로필 사진도)
const userNickName = userStore.loginUserProfile.nickname;
const userName = userStore.loginUserName;
const userBirthday = userStore.loginUserBirthday;
const userEmail = userStore.loginUserEmail;
const userPhone = userStore.loginUserPhone;

console.log('닉넴', userNickName);
console.log('이름', userName);
console.log('생일', userBirthday);
console.log('메일', userEmail);
console.log('전번', userPhone);

onMounted(() => {
  userStore.setUser(userStore.loginUserId);
});

const updatepwd = () => {
  if (!isPasswordValid(password.value)) {
    alert('비밀번호는 8자리 이상이며, 특수문자를 포함해야 합니다.');
    return;
  }

  if (password.value !== password2.value) {
    alert('비밀번호가 일치하지 않습니다.');
    return;
  }

  const user = {
    id: userStore.loginUserId,
    password: password.value,
  };

  userStore.updatePwd(user);
};
</script>

<style scoped>
.photobtn {
  height: 34px;
  width: 95px;
  font-size: 15px;
  font-weight: bold;
  border-radius: 34px;
  margin-top: 10px;
  color: #000000;
}

.updatebtn {
  height: 34px;
  width: 376px;
  font-size: 15px;
  font-weight: bold;
  border-radius: 34px;
  margin-top: 10px;
  color: #000000;
}

.textfield :deep(.v-field) {
  border-radius: 10px;
}
</style>
