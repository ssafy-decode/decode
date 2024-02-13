<template>
  <v-row>
    <v-col cols="2"></v-col>
    <v-col cols="4" class="flex-container">
      <div class="img-box">
        <img class="page-img" src="./fail_page.png" alt="성공페이지" />
      </div>
    </v-col>
    <v-col cols="4" style="padding: 0; margin: 0">
      <div class="img-box">
        <p class="head-text">
          de;code Community <br/>
        </p>
        <p class="title-text">
          자유로운 질의응답 <br/>
        </p>
        <p class="sub-text">
          ChatGPT 4.0 와 모든 사용자로부터 <br />
          에러에 대한 다양한 솔루션을 얻을 수 있습니다.
        </p>
      </div>
    </v-col>
    <v-col cols="2"></v-col>
  </v-row>
</template>

<script setup></script>

<style scoped>
.flex-container {
  display: flex;
  margin: 0;
  padding: 0;
}
.head-text {
  font-size: 25px;
  color: #23968B;
  font-weight: 900;
  text-align: left;
  width: 100%;
  padding-left: 20px
}
.title-text {
  font-size: 35px;
  font-weight: 900;
  text-align: left;
  color: black;
  width: 100%;
  padding-left: 20px
}
.sub-text {
  font-size: 25px;
  font-weight: 400;
  color: #7c7c7c;
  text-align: left;
  width: 100%;
  padding-left: 20px
}
.img-box {
  flex-direction: column;
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  margin: 0;
  padding: 0;
}

.text-box {
  flex-direction: column;
  height: 100%;
  display: flex;
  justify-content: center;
  margin: 0;
  padding: 0;
}

</style>
