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

        <div style="display: flex; justify-content: end; margin-right: 50px">
          <span style="color: #34a080; font-size: x-small"
            ><a href="/findemail" style="text-decoration: none; color: #34a080">이메일 찾기</a>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <a href="/findpwd" style="text-decoration: none; color: #34a080">비밀번호 찾기</a></span
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
                  <router-link to="/regist/1">
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
        <GithubLoginBtn />
        <!-- 깃허브로 로그인하기 버튼 -->
      </v-form>
    </v-card>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { useUserStore } from '@/stores/userStore';
import GithubLoginBtn from '@/components/user/GithubLoginBtn.vue';

const userStore = useUserStore();

const form = ref(null);

const email = ref('');
const password = ref('');
const showPassword = ref(false);

// 필수 입력란 비어있을 때 빨간 경고
const writeEmail = (value) => {
  return !!value;
};

const writePassword = (value) => {
  return !!value;
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

.textfield :deep(label) {
  color: #ffffff;
}

.textfield :deep(.v-field) {
  border-radius: 55px;
}
</style>
