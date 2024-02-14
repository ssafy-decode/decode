<template>
  <v-toolbar>
    <router-link to="/">
      <v-btn>
        <span style="display: flex; justify-content: space-between">
          <img src="../de;code_logo_jin.png" alt="로고이미지" height="30px" /><v-toolbar-title>
            <span class="decode" style="font-size: x-large; font-weight: bold"> de;code </span>
          </v-toolbar-title></span
        >
      </v-btn>
    </router-link>

    &nbsp;&nbsp;|&nbsp;&nbsp;

    <router-link to="/board">
      <v-btn>
        <v-toolbar-items class="font-weight-bold">질문게시판</v-toolbar-items>
      </v-btn>
    </router-link>

    <!-- <router-link to="/shop">
      <v-btn v-show="userStore.isLoggedIn">
        <v-toolbar-items class="font-weight-bold">상점</v-toolbar-items>
      </v-btn>
    </router-link> -->

    <router-link to="/rank">
      <v-btn v-show="userStore.isLoggedIn">
        <v-toolbar-items class="font-weight-bold">랭킹</v-toolbar-items>
      </v-btn>
    </router-link>
    <router-link :to="'/profile/' + uid">
      <v-btn v-show="userStore.isLoggedIn">
        <v-toolbar-items class="font-weight-bold">마이페이지</v-toolbar-items>
      </v-btn>
    </router-link>

    <v-spacer></v-spacer>
    <v-btn id="logoutBtn" v-if="userStore.isLoggedIn" @click="logout">로그아웃</v-btn>
    <router-link to="/login" v-else>
      <v-btn id="loginBtn"> 로그인 </v-btn>
    </router-link>
  </v-toolbar>
</template>

<script setup>
import { useUserStore } from '@/stores/userStore';
import { storeToRefs } from 'pinia';

const userStore = useUserStore();

const { setLogout: logout } = userStore;
const { handleLoginUserId: uid } = storeToRefs(userStore);
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
  border-radius: 34px;
  font-weight: 800;
}

#logoutBtn {
  color: #077f5b;
  font-weight: 800;
  background-color: #ffffff;
  border: 2px solid #62c0a6;
  border-radius: 34px;
  margin-right: 20px;
}
</style>
