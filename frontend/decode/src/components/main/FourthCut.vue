<template>
  <v-row class="flex-container">
    <v-col cols="2"></v-col>
    <v-col cols="4" style="padding: 0; margin: 0">
      <div class="img-box">
        <img class="page-img" src="./rank.png" alt="전문가" />
      </div>
    </v-col>
    <v-col cols="4" style="padding: 0; margin: 0">
      <div class="text-box">
        <p class="head-text">
          de;code Arena <br/>
        </p>
        <p class="title-text">
          Ranking & Tier System <br/>
        </p>
        <p class="sub-text">
          질문과 답변을 통해 경험치를 획득하고<br />
          랭킹을 높여보세요.
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
}
.title-text {
  font-size: 35px;
  font-weight: 900;
  text-align: left;
  color: black;
  width: 100%;
}
.sub-text {
  font-size: 25px;
  font-weight: 400;
  color: #7c7c7c;
  text-align: left;
  width: 100%;
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
