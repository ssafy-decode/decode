<template>
  <v-sheet rounded>
    <v-card class="mx-auto px-6 py-8" max-width="344">
      <v-form v-model="form" onsubmit.prevent="onSubmit">
        <img src="./LogoDecode3.png" />
        <h1 style="text-align: center">로그인</h1>

        <br />

        <v-text-field
          v-model="email"
          :readonly="loading"
          :rules="[required]"
          class="mb-2"
          clearable
          label="이메일을 입력하세요."
        ></v-text-field>

        <v-text-field
          v-model="password"
          :readonly="loading"
          :rules="[required]"
          clearable
          label="비밀번호를 입력하세요."
        ></v-text-field>

        <span>이메일 찾기 | 비밀번호 찾기</span>

        <br />
        <br />

        <span>
          <v-btn :disabled="!form" :loading="loading" color="success" size="large" type="submit" variant="elevated">
            로그인
          </v-btn>
          &nbsp;
          <!-- <v-btn :disabled="!form" :loading="loading" color="success" size="large" type="submit" variant="elevated">
            회원가입
          </v-btn> -->
        </span>

        <br />
      </v-form>
    </v-card>
  </v-sheet>
</template>

<script setup>
// import { ref } from 'vue';
// import { useUserStore } from '@/stores/userStore';

// const userStore = useUserStore();

// const id = ref('');
// const password = ref('');

// const login = async () => {
//   const user = {
//     userId: id.value,
//     userPwd: password.value,
//   };

//   try {
//     const result = await userStore.setLoginUser(user);

//     if (result.success) {
//       console.log('Login result:', result.data);
//     } else {
//       console.log('Login failed:', result.error);
//     }
//   } catch (error) {
//     console.error('Login error:', error);
//   }
// };
</script>

<style scoped>
/* .btn {
  display: inline-block;
  padding: 8px 12px;
  font-size: 15px;
  cursor: pointer;
  text-align: center;
  text-decoration: none;
  outline: none;
  color: #fff;
  background-color: #04aa6d;
  border: none;
  border-radius: 15px;
  box-shadow: 0 5px #999;
}

.btn:hover {
  background-color: #3e8e41;
}

.btn:active {
  background-color: #3e8e41;
  box-shadow: 0 3px #666;
  transform: translateY(4px);
} */

/* #emailview {

} */
</style>
