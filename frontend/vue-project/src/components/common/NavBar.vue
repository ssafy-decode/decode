<template>
  <v-toolbar>
    <v-btn>
      <router-link to="/">
        <img src="./de;code_logo_jin.png" alt="로고이미지" height="30px" />
      </router-link>
    </v-btn>

    <v-btn>
      <router-link to="/">
        <v-toolbar-title>de;code</v-toolbar-title>
      </router-link>
    </v-btn>

    <v-btn>
      <router-link to="/questionboard">
        <v-toolbar-items>질문게시판</v-toolbar-items>
      </router-link>
    </v-btn>

    <v-btn>
      <v-toolbar-items>상점</v-toolbar-items>
    </v-btn>

    <v-btn>
      <router-link to="/">
        <v-toolbar-items>test_btn</v-toolbar-items>
      </router-link>
    </v-btn>

    <v-spacer></v-spacer>

    <v-btn>로그인</v-btn>
  </v-toolbar>
</template>

<script setup></script>

<style scoped>
a {
  text-decoration: none; /* 밑줄 제거 */
  color: inherit; /* 링크 색상 상속 */
}
</style>
