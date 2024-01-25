<template>
  <div class="pa-5" rounded>
    <v-card
      class="mx-auto px-4 py-8"
      max-width="344"
      style="background-color: #f3f3f3; border-radius: 50px; box-shadow: 0 0px 36px rgba(0, 0, 0, 0.2)"
    >
      <v-form v-model="form" onsubmit.prevent="onSubmit">
        <br />
        <div style="text-align: center">
          <img style="width: 10%" src="./LogoDecode3.png" />
          <h2 style="color: #999999">로그인</h2>
        </div>
        <br />

        <v-text-field
          variant="solo"
          class="textfield"
          bg-color="#d9d9d9"
          v-model="email"
          :rules="[writeEmail]"
          label="이메일을 입력하세요."
          prepend-inner
        >
          <template #prepend-inner>
            <img
              src="./person.png"
              alt="이메일 아이콘"
              style="width: auto; height: auto; margin-left: 6px; margin-right: 10px"
            />
          </template>
        </v-text-field>

        <div class="main">
          <v-text-field
            variant="solo"
            class="textfield"
            bg-color="#d9d9d9"
            v-model="password"
            :rules="[writePassword]"
            :type="showPassword ? 'text' : 'password'"
            label="비밀번호를 입력하세요."
            prepend-inner
            append-inner
          >
            <template #prepend-inner>
              <img
                src="./pwd.png"
                alt="비밀번호 아이콘"
                style="width: auto; height: auto; margin-left: 1px; margin-right: 10px"
              />
            </template>
            <template #append-inner>
              <v-icon @click="toggleEye">{{ showPassword ? 'mdi-eye' : 'mdi-eye-off' }}</v-icon>
            </template>
          </v-text-field>
        </div>

        <div style="display: flex; justify-content: end; margin-right: 15px">
          <span style="color: #34a080; font-size: x-small"
            ><a href="/email" style="text-decoration: none; color: #34a080">이메일 찾기</a> |
            <a href="/password" style="text-decoration: none; color: #34a080">비밀번호 찾기</a></span
          >
          <!-- 추후 비번 찾기 만들면 그 때 link 연결 -->
        </div>
        <br />

        <div style="text-align: center">
          <span>
            <router-link to="/">
              <v-row>
                <v-col cols="8">
                  <v-btn
                    @click="login"
                    color="#62C0A6"
                    size="large"
                    type="submit"
                    variant="elevated"
                    style="width: 100%; border-radius: 30px; font-size: smaller; font-weight: bolder; color: #000000"
                  >
                    로그인
                  </v-btn>
                </v-col>
                <v-col cols="4">
                  <router-link to="/regist">
                    <v-btn
                      color="#34A080"
                      size="large"
                      type="submit"
                      variant="elevated"
                      style="width: 100%; border-radius: 30px; font-size: smaller; font-weight: bolder; color: #000000"
                    >
                      회원가입
                    </v-btn>
                  </router-link>
                </v-col>
              </v-row>
            </router-link>
          </span>
        </div>

        <br />
        <div style="text-align: center">
          <router-link to="/loading"
            ><v-btn
              class="kakaobtn"
              style="width: 300px; margin-bottom: 5px"
              :style="{ backgroundColor: kakaoButtonColor }"
              @mouseenter="kakaoMouseEnter"
              @mouseleave="kakaomouseLeave"
              ><v-row>
                <v-col cols="2">
                  <img src="./kakao.png" alt="카카오 아이콘" style="width: 24px; height: 24px" />
                </v-col>
                <v-col cols="10" style="font-size: small">카카오 계정으로 로그인하기</v-col>
              </v-row></v-btn
            ></router-link
          >

          <router-link to="/loading"
            ><v-btn
              class="googlebtn"
              style="width: 300px; margin-top: 5px"
              :style="{ backgroundColor: googleButtonColor }"
              @mouseenter="googleMouseEnter"
              @mouseleave="googlemouseLeave"
              ><v-row>
                <v-col cols="2">
                  <img src="./google.png" alt="구글 아이콘" style="width: 24px; height: 24px" />
                </v-col>
                <v-col cols="10" style="font-size: small">Google 계정으로 로그인하기</v-col>
              </v-row></v-btn
            ></router-link
          >
        </div>
      </v-form>
    </v-card>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { useUserStore } from '@/stores/userStore';

const userStore = useUserStore();

const form = ref(null);

const email = ref('');
const password = ref('');
const showPassword = ref(false);

// 필수 입력란 비어있을 때 빨간 경고
const writeEmail = (value) => {
  return !!value || '이메일을 입력하세요.';
};

const writePassword = (value) => {
  return !!value || '비밀번호를 입력하세요.';
};

// hover 전 색깔
const kakaoButtonColor = ref('#d9d9d9');
const googleButtonColor = ref('#d9d9d9');

// hover 후 색깔
const kakaoMouseEnter = () => {
  kakaoButtonColor.value = '#fae300';
};

const googleMouseEnter = () => {
  googleButtonColor.value = '#ffffff';
};

// 커서 나가면 원래 색깔로
const kakaomouseLeave = () => {
  kakaoButtonColor.value = '#d9d9d9';
};

const googlemouseLeave = () => {
  googleButtonColor.value = '#d9d9d9';
};

// 눈 버튼으로 비밀번호 가리고 숨기고
const toggleEye = () => {
  showPassword.value = !showPassword.value;
};

// 로그인 버튼 누르면 실행
const login = async () => {
  const user = {
    email: email.value,
    password: password.value,
  };
  try {
    const result = await userStore.setLoginUser(user);

    if (result.success) {
      console.log('Login result:', result.data);
    } else {
      console.log('Login failed:', result.error);
    }
  } catch (error) {
    console.error('Login error:', error);
  }
};
</script>

<style scoped>
.textfield :deep(label) {
  color: #ffffff;
}

.textfield :deep(.v-field) {
  border-radius: 30px;
}

.kakaobtn:hover {
  background-color: #fae300;
}

.googlebtn:hover {
  background-color: #ffffff;
}
</style>
