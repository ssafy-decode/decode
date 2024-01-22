<template>
  <v-sheet class="mx-auto" width="1000">
    <form @submit.prevent="createArticle">
      <!-- <div class="mb-3"> -->
      <!-- <input
          style="color: black"
          type="text"
          class="form-control"
          placeholder="질문의 제목을 입력하세요"
          id="articleTitle"
          v-model.trim="title"
        /> -->
      <v-text-field variant="solo" label="질문 제목"></v-text-field>
      <!-- </div>
      <div class="mb-3"> -->
      <!-- <textarea
          style="color: black"
          type="text"
          class="form-control"
          placeholder="질문 내용과 에러 코드를 입력하세요"
          id="articleContent"
          v-model.trim="content"
        ></textarea> -->
      <!-- </div> -->

      <!-- <v-textarea variant="solo" label="질문 내용 / 에러 코드" auto-grow></v-textarea> -->
      <Editor />

      <v-btn type="submit">질문등록</v-btn>
    </form>
  </v-sheet>
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
