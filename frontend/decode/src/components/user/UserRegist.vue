<template>
  <div class="pa-5" rounded>
    <v-card
      class="mx-auto px-4 py-8"
      max-width="500"
      style="background-color: #f3f3f3; border-radius: 50px; box-shadow: 0 0px 36px rgba(0, 0, 0, 0.2)"
    >
      <v-form ref="form" @submit.prevent="regist">
        <span style="margin-left: 20px"
          ><img src="../levelselected.png" />&nbsp;&nbsp;&nbsp;<img src="../levelunselected.png"
        /></span>
        <div style="text-align: center">
          <img style="width: 8%" src="./LogoDecode3.png" />
        </div>
        <br />
        <div class="flex-container">
          <v-text-field
            bg-color="#d9d9d9"
            :rules="[writeName]"
            variant="solo"
            class="textfield"
            clearable
            density="compact"
            v-model="name"
            label="이름"
            prepend-inner
            style="margin-right: 5px"
            ><template #prepend-inner>
              <img
                src="./person.png"
                alt="이름 아이콘"
                style="width: auto; height: auto; margin-left: 6px; margin-right: 10px"
              /> </template
          ></v-text-field>
          <v-text-field
            bg-color="#d9d9d9"
            :rules="[writeNickname]"
            variant="solo"
            class="textfield"
            clearable
            density="compact"
            v-model="nickname"
            label="닉네임"
            prepend-inner
            style="margin-left: 5px"
            @blur="checkDuplicateNickname"
            ><template #prepend-inner>
              <img
                src="./pencil.png"
                alt="닉네임 아이콘"
                style="width: auto; height: auto; margin-left: 6px; margin-right: 10px"
              /> </template
          ></v-text-field>
        </div>
        <v-text-field
          bg-color="#d9d9d9"
          :rules="[writeEmail]"
          v-model="email"
          variant="solo"
          class="textfield"
          clearable
          density="compact"
          label="이메일 계정"
          prepend-inner
          @blur="checkDuplicateEmail"
          ><template #prepend-inner>
            <img
              src="./email.png"
              alt="메일 아이콘"
              style="width: auto; height: auto; margin-left: 5px; margin-right: 10px"
            /> </template
        ></v-text-field>
        <!-- 비번 보이고 안 보이게 하기 -->
        <v-text-field
          bg-color="#d9d9d9"
          :rules="[writePassword]"
          v-model="password"
          variant="solo"
          class="textfield"
          clearable
          density="compact"
          label="비밀번호"
          hint="영문, 숫자, 특수문자 조합 8자리 이상"
          prepend-inner
          append-inner
          :type="showPassword ? 'text' : 'password'"
          ><template #prepend-inner>
            <img
              src="./pwd.png"
              alt="비밀번호 아이콘"
              style="width: auto; height: auto; margin-left: 1px; margin-right: 10px"
            />
          </template>
          <template #append-inner>
            <v-icon @click="toggleEye">{{ showPassword ? 'mdi-eye' : 'mdi-eye-off' }}</v-icon>
          </template></v-text-field
        >
        <v-text-field
          bg-color="#d9d9d9"
          :rules="[writePassword2]"
          v-model="password2"
          variant="solo"
          class="textfield"
          clearable
          density="compact"
          label="비밀번호 확인"
          prepend-inner
          append-inner
          :type="showPassword2 ? 'text' : 'password'"
          ><template #prepend-inner>
            <img
              src="./pwd.png"
              alt="비밀번호 아이콘"
              style="width: auto; height: auto; margin-left: 1px; margin-right: 10px"
            />
          </template>
          <template #append-inner>
            <v-icon @click="toggleEye2">{{ showPassword2 ? 'mdi-eye' : 'mdi-eye-off' }}</v-icon>
          </template></v-text-field
        >
        <v-text-field
          bg-color="#d9d9d9"
          :rules="[writeBirthday]"
          variant="solo"
          class="textfield"
          clearable
          density="compact"
          label="생년월일 6자리 ex) 990101"
          v-model="birthday"
          prepend-inner
          ><template #prepend-inner>
            <img
              src="./calendar.png"
              alt="달력 아이콘"
              style="width: auto; height: auto; margin-left: 4px; margin-right: 10px"
            /> </template
        ></v-text-field>
        <v-text-field
          bg-color="#d9d9d9"
          :rules="[writePhone]"
          variant="solo"
          class="textfield"
          clearable
          density="compact"
          label="휴대폰 뒷 네 자리 ex) 1234"
          v-model="phone"
          prepend-inner
          ><template #prepend-inner>
            <img
              src="./phone.png"
              alt="전화 아이콘"
              style="width: auto; height: auto; margin-left: 4px; margin-right: 10px"
            /> </template
        ></v-text-field>
        <span style="display: flex; justify-content: end; margin-right: 20px">
          <router-link to="/techstack"
            ><v-btn
              @click.prevent="regist"
              color="#62C0A6"
              size="large"
              type="submit"
              variant="elevated"
              style="font-weight: bolder; border-radius: 30px; color: #000000"
            >
              확인
            </v-btn></router-link
          >
        </span>
      </v-form>
    </v-card>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { useUserStore } from '@/stores/userStore';
import axios from 'axios';
const URL = process.env.VUE_APP_BACKEND_URL;

const userStore = useUserStore();

const form = ref(null);

const name = ref('');
const nickname = ref('');
const email = ref('');
const password = ref('');
const password2 = ref('');
const showPassword = ref(false);
const showPassword2 = ref(false);
const birthday = ref('');
const phone = ref('');

// 눈 버튼 누르면 비밀번호 가렸다 보였다
const toggleEye = () => {
  showPassword.value = !showPassword.value;
};
const toggleEye2 = () => {
  showPassword2.value = !showPassword2.value;
};

// 필수 입력란에 입력 없을 경우 빨간 경고
const writeName = (value) => {
  return !!value || '이름을 입력하세요.';
};

const writeNickname = (value) => {
  return !!value || '닉네임을 입력하세요.';
};

const writeEmail = (value) => {
  return !!value || '이메일을 입력하세요.';
};

const writePassword = (value) => {
  return !!value || '비밀번호를 입력하세요.';
};

const writePassword2 = (value) => {
  return !!value || '비밀번호 확인을 입력하세요.';
};

const writeBirthday = (value) => {
  return !!value || '생년월일을 입력하세요.';
};

const writePhone = (value) => {
  return !!value || '전화번호를 입력하세요.';
};

// 이메일 형식 올바른지
const isEmailValid = (email) => {
  const re =
    /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

  return re.test(String(email).toLowerCase());
};

// 비밀번호 형식 올바른지
const isPasswordValid = (pwd) => {
  return pwd.length >= 8 && /[!@#$%^&*(),.?":{}|<>]/g.test(pwd);
};

// 생년월일 형식 올바른지
const isBirthdayValid = (birthday) => {
  return birthday.length == 6;
};

// 전화번호 형식 올바른지
const isPhoneValid = (phone) => {
  return phone.length == 4;
};

// 이메일 중복체크
const checkDuplicateEmail = async () => {
  console.log(email);
  console.log(email.value);
  try {
    const res = await axios.get(`${URL}/email?keyword=${email.value}`, {
      withCredentials: true,
      headers: {
        'Access-Control-Allow-Origin': '/',
        'Access-Control-Allow-Credentials': 'true',
      },
    });

    const isDuplicate = res.data.data;
    if (!isDuplicate) {
      alert('이미 존재하는 이메일입니다.');
    }
  } catch (error) {
    console.error('Error checking duplicate email:', error);
  }
};

// 닉네임 중복체크
const checkDuplicateNickname = async () => {
  console.log(nickname);
  console.log(nickname.value);
  try {
    const res = await axios.get(`${URL}/nickname?keyword=${nickname.value}`, {
      withCredentials: true,
      headers: {
        'Access-Control-Allow-Origin': `/`,
        'Access-Control-Allow-Credentials': 'true',
      },
    });

    const isDuplicate = res.data.data;
    if (!isDuplicate) {
      alert('이미 존재하는 닉네임입니다.');
    }
  } catch (error) {
    console.error('Error checking duplicate nickname:', error);
  }
};

// 회원가입 버튼 누르면 전체 정보 올바른지 확인
const regist = () => {
  try {
    if (
      name.value === '' ||
      nickname.value === '' ||
      email.value === '' ||
      password.value === '' ||
      password2.value === '' ||
      birthday.value === '' ||
      phone.value === ''
    ) {
      alert('필수 정보를 모두 입력해주세요.');
      return;
    }

    if (password.value !== password2.value) {
      alert('비밀번호가 일치하지 않습니다.');
      return;
    }

    if (!isPasswordValid(password.value)) {
      alert('비밀번호는 8자리 이상이며, 특수문자를 포함해야 합니다.');
      return;
    }

    if (!isEmailValid(email.value)) {
      alert('올바른 이메일 형식을 입력해주세요.');
      return;
    }

    if (!isBirthdayValid(birthday.value)) {
      alert('생년월일을 6자리로 입력해주세요.');
      return;
    }

    if (!isPhoneValid(phone.value)) {
      alert('전화번호를 4자리로 입력해주세요.');
      return;
    }

    const user = {
      name: name.value,
      nickname: nickname.value,
      email: email.value,
      password: password.value,
      birth: birthday.value,
      phoneNumber: phone.value,
    };

    userStore.createUser(user);
  } catch (error) {
    console.error('Error:', error);
  }
};
</script>

<style scoped>
.flex-container {
  display: flex;
  justify-content: space-between;
}

.textfield >>> label {
  color: #ffffff;
}

.textfield ::v-deep(.v-field) {
  border-radius: 30px;
}
</style>
