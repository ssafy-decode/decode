<template>
  <v-toolbar>
    <v-btn>
      <router-link to="/"><img src="./de;code_logo_jin.png" alt="로고이미지" height="30px" /></router-link>
    </v-btn>

    <v-btn>
      <router-link to="/"><v-toolbar-title>de;code</v-toolbar-title></router-link>
    </v-btn>

    <v-btn>
      <router-link to="/questionboard"><v-toolbar-items>질문게시판</v-toolbar-items></router-link>
    </v-btn>
    <!-- 임시 비활성화 -->
    <!-- <v-btn>
      <router-link to="/shop"><v-toolbar-items>상점</v-toolbar-items></router-link>
    </v-btn> -->

    <v-spacer></v-spacer>

    <!-- <router-link to="/login"><v-btn>로그인</v-btn></router-link> -->
    <router-link to="/"><v-btn>로그인</v-btn></router-link>
  </v-toolbar>
</template>

<script setup></script>

<style scoped></style>
