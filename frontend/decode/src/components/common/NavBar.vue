<template>
  <v-toolbar color="transparent">
    <v-btn>
      <!-- 피그마에서는 버튼이 두 개가 아닌 하나인 것 같아서 로고랑 사이트명이랑 하나로 합쳤습니다. 물론 수정해도 무방합니다.-->
      <router-link to="/">
        <span style="display: flex; justify-content: space-between">
          <img src="./de;code_logo_jin.png" alt="로고이미지" height="30px" /><v-toolbar-title
            ><span class="decode" style="font-size: x-large; font-weight: bold">de;code</span></v-toolbar-title
          ></span
        >
      </router-link>
    </v-btn>

    &nbsp;&nbsp;|&nbsp;&nbsp;

    <v-btn>
      <router-link to="/question">
        <v-toolbar-items>질문게시판</v-toolbar-items>
      </router-link>
    </v-btn>

    <!-- 상점 nav -->

    <!-- 랭킹 nav -->

    <!-- 마이페이지 nav -->

    <v-spacer></v-spacer>
    <v-btn id="loginBtn" v-if="userStore.isLoggedIn" @click="logout">로그아웃</v-btn>
    <v-btn id="loginBtn" v-else><router-link to="/login">로그인</router-link></v-btn>
  </v-toolbar>
</template>

<script setup>
// import { ref } from 'vue';
import { useUserStore } from '@/stores/userStore';

const userStore = useUserStore();

const logout = () => {
  userStore.setLogout();
};
</script>

<style scoped>
.decode {
  font-family: titlefont; /* 타이틀만 오아고딕체 */
}

a {
  text-decoration: none; /* 밑줄 제거 */
  color: inherit; /* 링크 색상 상속 */
}

#loginBtn {
  background-color: #62c0a6;
  margin-right: 20px;
  border-radius: 40px;
}
</style>
