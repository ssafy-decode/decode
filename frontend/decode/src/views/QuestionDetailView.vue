<template>
  <div>
    <h1>Question Detail View</h1>
    <br />
    <p>질문 제목: {{ question.title }}</p>
    <p>질문 내용: {{ question.content }}</p>
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

const store = useQuestionStore();
const router = useRouter();
const route = useRoute();

// store와 axios 통해 데이터를 불러옴
const question = ref({});
const questionId = ref(0);

const getDetailQuestion = function () {
  axios({
    method: 'get',
    url: `${store.API_URL}/question/${route.params.id}`,
    headers: {
      'Access-Control-Allow-Origin': '*', // 이 부분을 추가하여 CORS 정책 우회
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
