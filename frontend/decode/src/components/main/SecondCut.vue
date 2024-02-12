<template>
    <v-row class="flex-container">
      <v-col cols="1"></v-col>
      <v-col cols="5" style="padding: 0; margin: 0">
        <div class="img-box">
            <img class="success page" src="./fail_page.png" alt="성공페이지" />
        </div>
    </v-col>
    <v-col cols="5" style="padding: 0; margin: 0">
        <div class="img-box">
            <p class="text2">
                ChatGPT 4.0 과 <br/>
                ~~~ 사용자로부터 <br/>
                에러에 대한 다양한 <br/> 
                솔루션을 얻을 수 있습니다!
              </p>
            <img class="success people" src="./fail_man.png" alt="성공사람" />
        </div>
      </v-col>
    </v-row>
  </template>
  
  <script setup></script>
  
  <style>
  .test {
      border: solid;
      padding: 0;
      margin: 0;
  }
  .text2 {
      font-size: 2em; /* 텍스트 크기를 크게 설정 */
      font-weight: 900; /* 텍스트를 굵게 설정 */
      color: #23968B; /* 텍스트 색상을 초록색으로 설정 */
  }
  
  .flex-container {
      display: flex;
      margin: 0;
      padding: 0;
      border: solid red;
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
  
  .fail {
    position: absolute;
    animation: changeAnimation 10s infinite;
    animation-delay: 5s;
  }
  
  .success {
    display: flex;
  }
  
  .page {
    width: 80%;
  }
  .people {
    width: 40%;
  }
  </style>
  