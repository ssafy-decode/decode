<template>
  <div class="detailContainer">
    <br />
    <p>질문 번호: {{ question.id }}</p>
    <p>질문 제목: {{ question.title }}</p>
    <p>질문 내용: {{ question.content }}</p>
    <p>질문 작성자 정보: {{ question.questionWriter }}</p>
    <p>질문 태그 목록: {{ question.tagList }}</p>
    <p>질문 생성일: {{ question.createdTime }}</p>
    <p>질문 수정일: {{ question.updatedTime }}</p>
    <p>질문 나도 궁금 수: {{ question.meTooCnt }}</p>
    <br />
    <div class="btnBox">
      <v-btn class="btn" @click="goUpdate()">질문수정</v-btn>
      <v-btn class="btn" @click="deleteQuestion()">질문삭제</v-btn>
    </div>
    <hr />
    <br />
    <div class="answerRelatedBox">
      <v-btn @click="goCreateAnswer()">답변생성</v-btn>
    </div>
    <AnswerList :answer-list="question.answerList" />
  </div>
</template>

<script setup>
import axios from 'axios';
import { onMounted, ref, computed } from 'vue';
import { useQuestionStore } from '@/stores/questionStore';
import { useUserStore } from '@/stores/userStore';
import { useRoute, useRouter, RouterLink } from 'vue-router';
import AnswerList from '@/components/answer/AnswerList.vue';

const questionStore = useQuestionStore();
const userStore = useUserStore();
const router = useRouter();
const route = useRoute();

const questionId = ref(0);
const question = ref({});

const getDetailQuestion = function () {
  axios({
    method: 'get',
    url: `${questionStore.URL}/question/${route.params.id}`,
    headers: {
      'Access-Control-Allow-Origin': '*',
      Authorization: `${userStore.accessToken}`,
    },
  })
    .then((res) => {
      questionId.value = route.params.id;
      question.value = res.data.data;
    })
    .catch((err) => {
      console.log(err);
      console.log('상세 질문 조회 오류');
    });
};

const deleteQuestion = function () {
  if (confirm('질문을 삭제하시겠습니까?')) {
    axios({
      method: 'delete',
      url: `${questionStore.URL}/question/delete/${questionId.value}`,
      headers: {
        'Access-Control-Allow-Origin': '*',
        Authorization: `Bearer ${userStore.accessToken}`,
      },
    })
      .then((res) => {
        router.push({
          path: '/board',
        });
      })
      .catch((err) => {
        console.log(err);
        console.log('질문 삭제 오류');
      });
  } else {
  }
};

const goUpdate = function () {
  questionStore.originalContent = question.value.content;
  router.push({ path: `/question-update/${questionId.value}` });
};

const goCreateAnswer = function () {
  router.push({ path: `/answer-create` });
};

onMounted(() => {
  getDetailQuestion();
});
</script>

<style scoped>
.answerRelatedBox {
  display: flex;
  justify-content: space-between;
  padding: 20px;
}

.answerListBox {
  padding: 20px;
}
.btnBox {
  padding: 20px;
}

.btn {
  margin-left: 10px;
  margin-right: 10px;
}

.detailContainer {
  margin: 40px;
}
</style>
