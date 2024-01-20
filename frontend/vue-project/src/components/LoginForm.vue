<template>
  <div class="pa-5" rounded>
    <v-card class="mx-auto px-4 py-8" max-width="344">
      <v-form v-model="form" onsubmit.prevent="onSubmit">
        <br />
        <div style="text-align: center">
          <img style="width: 10%" src="./LogoDecode3.png" />
          <h2 style="color: #999999">로그인</h2>
        </div>
        <br />

        <v-text-field v-model="email" :rules="[required]" label="이메일을 입력하세요." prepend-inner>
          <template #prepend-inner>
            <img
              src="./person.png"
              alt="이메일 아이콘"
              style="width: auto; height: auto; margin-left: 1px; margin-right: 10px"
            />
          </template>
        </v-text-field>

        <div class="main">
          <v-text-field
            v-model="password"
            :rules="[required]"
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
          <span style="color: #34a080; font-size: x-small">이메일 찾기 | 비밀번호 찾기</span>
        </div>
        <br />

        <!-- 추후 계정/비번 찾기 만들면 그 때 link 연결 -->
        <div style="text-align: center">
          <span>
            <!-- 로그인 조건 만족/불만족 시 alert 띄우기 -->
            <router-link to="/">
              <v-row>
                <v-col cols="8">
                  <v-btn
                    @click="login"
                    :loading="loading"
                    color="success"
                    size="large"
                    type="submit"
                    variant="elevated"
                    style="width: 100%; border-radius: 30px; font-size: smaller"
                  >
                    로그인
                  </v-btn>
                </v-col>
                <v-col cols="4">
                  <router-link to="/regist">
                    <v-btn
                      :loading="loading"
                      color="success"
                      size="large"
                      type="submit"
                      variant="elevated"
                      style="width: 100%; border-radius: 30px; font-size: smaller"
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
                <!-- 추가된 코드: 아이콘 이미지와 버튼 텍스트를 감싸는 v-col -->
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
// import { useUserStore } from '@/stores/userStore';

// const userStore = useUserStore();

const email = ref('');
const password = ref('');
const showPassword = ref(false);

const kakaoButtonColor = ref('#d9d9d9');
const googleButtonColor = ref('#d9d9d9');

const kakaoMouseEnter = () => {
  kakaoButtonColor.value = '#fae300';
};

const googleMouseEnter = () => {
  googleButtonColor.value = '#ffffff';
};

const kakaomouseLeave = () => {
  kakaoButtonColor.value = '#d9d9d9';
};

const googlemouseLeave = () => {
  googleButtonColor.value = '#d9d9d9';
};

const toggleEye = () => {
  showPassword.value = !showPassword.value;
};

const login = () => {
  const user = {
    userId: email.value,
    userPwd: password.value,
  };

  // try {
  //   const result = await userStore.setLoginUser(user);

  //   if (result.success) {
  //     console.log('Login result:', result.data);
  //   } else {
  //     console.log('Login failed:', result.error);
  //   }
  // } catch (error) {
  //   console.error('Login error:', error);
  // }

  alert('로그인되었습니다.');
};
</script>

<style scoped>
.kakaobtn:hover {
  background-color: #fae300;
}

.googlebtn:hover {
  background-color: #ffffff;
}
</style>
