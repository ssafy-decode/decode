<template>
  <div class="flex-container">
    <v-row>
      <v-col cols="2"></v-col>
      <v-col cols="4" style="padding: 0; margin: 0">
        <div class="text-box">
          <p class="explain-text">
            자유롭게 
            질문하고 답변하는 <br />
            IT 개발자 커뮤니티
          </p>
          <br />
          <p class="sub-text">
            코드를 공유하고 에러를 해결해보세요!
          </p>
        </div>
      </v-col>
      <v-col cols="4" style="padding: 0; margin: 0">
        <div class="img-box">
          <img class="page-img" src="./success_page.png" alt="성공페이지" />
        </div>
      </v-col>
      <v-col cols="2"></v-col>
    </v-row>
  </div>
</template>

<script setup></script>

<style>

.flex-container {
  display: flex;
  margin: 0;
  padding: 0;
}
.explain-text {
  font-size: 40px;
  font-weight: bold;
  color: #23968B;
  text-align: left;
  width: 100%;
}
.sub-text {
  font-size: 20px;
  font-weight: 400;
  color: #7c7c7c;
  text-align: left;
  width: 100%;
}
.text-box {
  flex-direction: column;
  height: 100%;
  display: flex;

  justify-content: center;

  margin: 0;
  padding: 0;
}
.img-box {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  margin: 0;
  padding: 0;
}

.page-img {
  width: 100%;
}
</style>
