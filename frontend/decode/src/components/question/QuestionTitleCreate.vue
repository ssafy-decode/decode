<template>
  <div>
    <v-sheet class="mx-auto createBox" width="1000">
      <h1>제목 생성을 위한 Error 입력 창</h1>
      <br />
      <v-form @submit.prevent="createQuestionTitle">
        <v-text-field disabled="true" variant="solo" label="제목 자동 생성 전.."></v-text-field>
        <ErrorEditor @editor-content-updated="updateEditorContent" />
        <br />
        <div id="btnBox">
          <v-btn id="submitBtn" type="submit">질문 제목 생성</v-btn>
        </div>
      </v-form>
    </v-sheet>
  </div>
</template>

<script setup>
import ErrorEditor from '@/components/common/ErrorEditor.vue';
import { useQuestionStore } from '@/stores/questionStore';
import { useUserStore } from '@/stores/userStore';
import { useRouter } from 'vue-router';
import { ref } from 'vue';
import axios from 'axios';

const questionStore = useQuestionStore();
const userStore = useUserStore();
const router = useRouter();

// 넘겨줄 데이터
const inputContent = ref('');
// 생성될 데이터

const updateEditorContent = function (content) {
  inputContent.value = content;
};

////////////////////////////  개발중  //////////////////////////////

const createQuestionTitle = function () {
  let data = {
    content: inputContent.value,
  };

  console.log(data);

  axios({
    method: 'post',
    url: `${questionStore.URL}/gpt`,
    data: data,
    headers: {
      'Content-Type': 'application/json',
      'Access-Control-Allow-Origin': '*', // 이 부분을 추가하여 CORS 정책 우회
      Authorization: `Bearer ${userStore.accessToken}`, // 만약 인증이 필요하다면 주석 해제
    },
  })
    .then((res) => {
      console.log('질문 제목 생성 완료');
      console.log(res.data);
      // 토큰이 없는 것인지 모르지만
      // 500 서버에러 발생
      questionStore.gptTagIds.value = res.data.data.titles;
      questionStore.gptTitles.value = res.data.data.tagIds;
      // 디버깅용
      console.log('gptTitles:', questionStore.gptTitles.value);
      console.log('gptTagIds:', questionStore.gptTagIds.value);
      router.push({ name: 'question-create' });
    })
    .catch((err) => {
      console.log('질문 생성 오류');
      console.log(err);
      // 디버깅용
      questionStore.gptTitles.value = [
        "Missing module './date.js' in ReactJS project",
        "Compilation error: Unable to find './date.js' module",
        "Error: './date.js' not found in ReactJS project",
      ];
      questionStore.gptTagIds.value = ['java', 'spring'];
      console.log('gptTitles:', questionStore.gptTitles.value);
      console.log('gptTagIds:', questionStore.gptTagIds.value);
      router.push({
        path: '/question-create',
      });
    });
};
////////////////////////////  개발중  //////////////////////////////
</script>

<style scoped>
#btnBox {
  position: relative;
}

#submitBtn {
  position: absolute;
  right: 10px;
  top: 5px;
  border-radius: 40px;
  background-color: #62c0a6;
}

.createBox {
  margin-bottom: 150px;
}
</style>
