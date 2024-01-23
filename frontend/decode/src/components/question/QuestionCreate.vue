<template>
  <v-sheet class="mx-auto" width="1000">
    <form @submit.prevent="createQuestion">
      <v-text-field variant="solo" label="질문 제목" v-model.trim="questionTitle"></v-text-field>
      <MyEditor @editor-content-updated="updateEditorContent" />
      <div id="btnBox">
        <v-btn id="submitBtn" type="submit">질문등록</v-btn>
      </div>
    </form>
  </v-sheet>
</template>

<script setup>
import MyEditor from '@/components/common/MyEditor.vue';
import { ref } from 'vue';
import { useQuestionStore } from '@/stores/questionStore';
import axios from 'axios';
import { useRouter } from 'vue-router';

const questionTitle = ref('');
const questionContent = ref('');
const questionStore = useQuestionStore();
const router = useRouter();

const updateEditorContent = function (content) {
  questionContent.value = content;
};

////////////////////////////  test 중  //////////////////////////////

const createQuestion = function () {
  console.log(questionTitle.value);
  console.log(questionContent.value);
  axios({
    method: 'post',
    url: `${questionStore.API_URL}/question`,
    data: {
      title: questionTitle.value,
      content: questionContent.value,
    },
    // headers: {
    //   Authorization: `${questionStore.token}`,
    // },
  })
    .then((res) => {
      console.log(res);
      // 일단 임시로 메인페이지 가도록 설정했음
      router.push({ name: 'mainview' });
    })
    .catch((err) => {
      console.log(err);
      console.log('질문 생성 오류');
    });
};
////////////////////////////  test 중  //////////////////////////////
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
</style>
