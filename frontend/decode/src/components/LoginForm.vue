<template>
  <div class="pa-5" rounded>
    <v-card
      class="mx-auto px-4 py-8"
      max-width="418"
      style="background-color: #f3f3f3; border-radius: 68px; box-shadow: 0 0px 36px rgba(0, 0, 0, 0.2)"
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
              <v-icon @click="toggleEye" style="margin-right: 10px">{{
                showPassword ? 'mdi-eye' : 'mdi-eye-off'
              }}</v-icon>
            </template>
          </v-text-field>
        </div>

        <div style="display: flex; justify-content: end; margin-right: 50px">
          <span style="color: #34a080; font-size: x-small"
            ><a href="/email" style="text-decoration: none; color: #34a080">이메일 찾기</a>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <a href="/password" style="text-decoration: none; color: #34a080">비밀번호 찾기</a></span
          >
        </div>
        <br />

        <div class="buttons">
          <span>
            <router-link to="/">
              <v-row>
                <v-col cols="78">
                  <v-btn
                    class="loginbtn"
                    @click="login"
                    color="#62C0A6"
                    size="x-large"
                    type="submit"
                    variant="elevated"
                    style="color: #000000"
                  >
                    로그인
                  </v-btn>
                </v-col>
                <v-col cols="5">
                  <router-link to="/regist">
                    <v-btn
                      class="registbtn"
                      color="#34A080"
                      size="x-large"
                      type="submit"
                      variant="elevated"
                      style="color: #000000"
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
              :style="{ backgroundColor: kakaoButtonColor }"
              @mouseenter="kakaoMouseEnter"
              @mouseleave="kakaomouseLeave"
              ><div class="icons">
                <img class="kakaoicon" src="./kakao.png" alt="카카오 아이콘" />
                <span class="texts">카카오 계정으로 로그인하기</span>
              </div>
            </v-btn></router-link
          >

          <router-link to="/loading"
            ><v-btn
              class="googlebtn"
              :style="{ backgroundColor: googleButtonColor }"
              @mouseenter="googleMouseEnter"
              @mouseleave="googlemouseLeave"
              ><div class="icons">
                <img class="googleicon" src="./google.png" alt="구글 아이콘" />
                <span>Google 계정으로 로그인하기</span>
              </div></v-btn
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
  console.log(user);

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
.textfield {
  margin: 0 20px;
  margin-bottom: 5px;
  height: 63px;
}
.buttons {
  text-align: center;
  margin: 0 20px;
}

.loginbtn,
.registbtn {
  width: 100%;
  border-radius: 30px;
  font-size: smaller;
  font-weight: bold;
}

.kakaobtn,
.googlebtn {
  margin: 0 20px;
  height: 54px;
  width: 349px;
  margin-bottom: 15px;
  border-radius: 10px;
  justify-content: left;
}

.kakaoicon {
  width: 39px;
  height: 38px;
  margin-right: 10px;
}

.googleicon {
  width: 31px;
  height: 31px;
  margin-right: 15px;
}

.icons {
  display: flex;
  align-items: center;
}

.texts {
  text-align: left;
  flex: 1;
}

.textfield :deep(label) {
  color: #ffffff;
}

.textfield :deep(.v-field) {
  border-radius: 55px;
}

.kakaobtn:hover {
  background-color: #fae300;
}

.googlebtn:hover {
  background-color: #ffffff;
}
</style>
