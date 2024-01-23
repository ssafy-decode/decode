<template>
  <!-- <div> -->
  <v-card class="mx-auto d-flex justify-space-between py-5 px-3" width="1550" height="auto">
    <!-- 왼쪽 질문 상세 조회 부분 -->
    <v-sheet width="600" style="border: 2px solid black">왼쪽 질문 상세 조회 부분</v-sheet>

    <!-- 오른쪽 답변 생성 부분 -->
    <v-sheet class="mx-auto" width="900" style="border: 2px solid black">
      <h5>답변 작성 중...</h5>
      <br />
      <form @submit.prevent="createArticle">
        <!-- <div class="mb-3"> -->
        <!-- <textarea
          style="color: black"
          type="text"
          class="form-control"
          placeholder="질문 내용과 에러 코드를 입력하세요"
          id="articleContent"
          v-model.trim="content"
        ></textarea> -->
        <Editor />
        <v-btn type="submit">답변등록</v-btn>
      </form>
    </v-sheet>
  </v-card>
  <!-- </div> -->
</template>

<script setup>
import Editor from '../common/Editor.vue';
// import { ref } from 'vue';
// import axios from 'axios';
// import { useUvasStore } from '@/stores/uvas';
// import { useRouter } from 'vue-router';

// const title = ref(null);
// const content = ref(null);
// const store = useUvasStore();
// const router = useRouter();

// const createArticle = function () {
//   axios({
//     method: 'post',
//     url: `${store.API_URL}/articles/`,
//     data: {
//       title: title.value,
//       content: content.value,
//     },
//     headers: {
//       Authorization: `Token ${store.token}`,
//     },
//   })
//     .then((res) => {
//       console.log(res);
//       router.push({ name: 'article' });
//     })
//     .catch((err) => {
//       console.log(err);
//       console.log('게시글 생성 오류');
//     });
// };
</script>

<style scoped></style>
