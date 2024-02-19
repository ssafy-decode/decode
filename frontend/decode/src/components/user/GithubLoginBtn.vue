<template>
  <!-- 깃허브 계정으로 로그인 -->
  <div style="text-align: center">
    <v-btn
      class="githubbtn"
      style="color: #ffffff"
      :style="{ backgroundColor: githubButtonColor }"
      @click="authenticate"
    >
      <div class="icons">
        <img class="githubicon" src="./githubicon.png" alt="깃허브 아이콘" />
        <span class="texts">깃허브 계정으로 로그인하기</span>
      </div>
    </v-btn>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import router from '@/router';

// hover 전 색깔
const githubButtonColor = ref('#000000');

// 로딩 창으로 이동 + 팝업창 띄우기
const authenticate = () => {
  router.push(`/loading`);
  window.open(`https://i10a507.p.ssafy.io/decode/oauth2/authorization/github`, `_blank`, `width=500, height=800`);
};
</script>

<style scoped>
.githubbtn {
  margin: 0 20px;
  height: 54px;
  width: 349px;
  margin-bottom: 15px;
  border-radius: 10px;
  justify-content: left;
}

.githubicon {
  width: 39px;
  height: 38px;
  margin-right: 10px;
}

.icons {
  display: flex;
  align-items: center;
}

.texts {
  text-align: left;
  flex: 1;
}

.githubbtn:hover {
  background-color: #000000;
  color: #ffffff;
}
</style>
