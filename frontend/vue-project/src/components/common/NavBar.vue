<template>
  <div>
    <v-container fluid class="bg-surface-variant">
      <v-row class="d-flex align-center text-center">
        <v-text style="width: 7%"> 아이콘 </v-text>
        <v-text style="width: 7%"> DE;CODE </v-text>
        <v-text style="width: 3%"> | </v-text>
        <v-text style="width: 7%"> 기능 </v-text>
        <v-text style="width: 7%"> 기능 </v-text>
        <v-text style="width: 7%"> 기능 </v-text>
        <v-text-field class="mx-3" label="검색어를 입력하세요" :rules="rules" hide-details="auto"></v-text-field>
        <v-btn style="width: 7%" class="mx-3 text-black">로그인</v-btn>
      </v-row>
    </v-container>
  </div>
</template>

<script setup></script>

<style scoped></style>
