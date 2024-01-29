<template>
  <div>
    <h1>Question Detail View</h1>
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
    <hr />
    <br />
    <h3>답변관련</h3>
    <p>질문 답변 목록: {{ question.answerList }}</p>
    <br />
    <div>
      <AnswerList />
    </div>
  </div>
</template>

<script setup>
import axios from 'axios';
import { onMounted, ref, computed } from 'vue';
import { useQuestionStore } from '@/stores/questionStore';
import { useRoute, useRouter, RouterLink } from 'vue-router';
import AnswerList from '@/components/answer/AnswerList.vue';

const questionStore = useQuestionStore();
const router = useRouter();
const route = useRoute();

// store와 axios 통해 데이터를 불러옴

const questionId = ref(0);

const question = ref({});

const getDetailQuestion = function () {
  axios({
    method: 'get',
    url: `${questionStore.URL}/question/${route.params.id}`,
    headers: {
      'Access-Control-Allow-Origin': '*', // 이 부분을 추가하여 CORS 정책 우회
      Authorization: `${questionStore.accessToken}`, // 만약 인증이 필요하다면 주석 해제
    },
  })
    .then((res) => {
      console.log(res.data.data);
      question.value = res.data.data;
    })
    .catch((err) => {
      console.log(err);
      console.log('상세 질문 조회 오류');
    });
};

// 수정 필요
onMounted(() => {
  getDetailQuestion();
});
</script>

<style scoped></style>
