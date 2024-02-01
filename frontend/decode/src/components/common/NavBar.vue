<template>
  <v-toolbar color="transparent">
    <v-btn>
      <router-link to="/">
        <span style="display: flex; justify-content: space-between">
          <img src="../de;code_logo_jin.png" alt="로고이미지" height="30px" /><v-toolbar-title
            ><span class="decode" style="font-size: x-large; font-weight: bold">de;code</span></v-toolbar-title
          ></span
        >
      </router-link>
    </v-btn>

    &nbsp;&nbsp;|&nbsp;&nbsp;

    <v-btn>
      <router-link to="/board">
        <v-toolbar-items>질문게시판</v-toolbar-items>
      </router-link>
    </v-btn>

    <v-btn v-show="userStore.isLoggedIn">
      <router-link to="/shop">
        <v-toolbar-items>상점</v-toolbar-items>
      </router-link>
    </v-btn>

    <v-btn v-show="userStore.isLoggedIn">
      <router-link to="/rank">
        <v-toolbar-items>랭킹</v-toolbar-items>
      </router-link>
    </v-btn>

    <v-btn v-show="userStore.isLoggedIn">
      <router-link to="/mypage">
        <v-toolbar-items>마이페이지</v-toolbar-items>
      </router-link>
    </v-btn>

    <v-spacer></v-spacer>
    <v-btn id="loginBtn" v-if="userStore.isLoggedIn" @click="logout">로그아웃</v-btn>
    <v-btn id="loginBtn" v-else><router-link to="/login">로그인</router-link></v-btn>
  </v-toolbar>
</template>

<script setup>
import { useUserStore } from '@/stores/userStore';

const userStore = useUserStore();

const { setLogout: logout } = userStore;
</script>

<style scoped>
.decode {
  font-family: titlefont;
}

a {
  text-decoration: none;
  color: inherit;
}

#loginBtn {
  background-color: #62c0a6;
  margin-right: 20px;
  border-radius: 40px;
}
</style>
