<template>
  <v-toolbar>
    <v-btn>
      <!-- 피그마에서는 버튼이 두 개가 아닌 하나인 것 같아서 로고랑 사이트명이랑 하나로 합쳤습니다. 물론 수정해도 무방합니다.-->
      <router-link to="/">
        <span style="display: flex; justify-content: space-between">
          <img src="./de;code_logo_jin.png" alt="로고이미지" height="30px" /><v-toolbar-title
            >de;code</v-toolbar-title
          ></span
        >
      </router-link>
    </v-btn>

    <!-- 처음 버튼을 두 개로 했을 때의 코드 -->
    <!-- <v-btn>
      <router-link to="/">
        <v-toolbar-title>de;code</v-toolbar-title>
      </router-link>
    </v-btn> -->

    <v-btn>
      <router-link to="/question">
        <v-toolbar-items>질문게시판</v-toolbar-items>
      </router-link>
    </v-btn>

    <!-- 상점 nav -->

    <!-- 랭킹 nav -->

    <!-- 마이페이지 nav -->

    <v-spacer></v-spacer>

    <v-btn><router-link to="/login">로그인</router-link></v-btn>
  </v-toolbar>
</template>

<script setup></script>

<style scoped>
a {
  text-decoration: none; /* 밑줄 제거 */
  color: inherit; /* 링크 색상 상속 */
}
</style>
