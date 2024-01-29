<template>
  <div>
    <div>
      <p>질문번호: {{ question.id }}</p>
      <p @click="goDetail(question.id)">질문제목: {{ question.title }}</p>
      <p>질문태그: {{ question.tagIds }}</p>
      <p>작성자 정보: {{ question.writer }}</p>
      <p>최초생성일자: {{ question.createdTime }}</p>
      <p>답변개수: {{ question.answerCnt }}</p>
      <p>나도궁금수: {{ question.meTooCnt }}</p>
      <br />
      <hr />
      <br />
    </div>
  </div>
</template>

<script setup>
import { useQuestionStore } from '@/stores/questionStore';
import { useRouter } from 'vue-router';

const router = useRouter();
const questionStore = useQuestionStore();

const goDetail = function (questionId) {
  questionStore.questionId = questionId;
  router.push({ path: `/question/${questionId}` });
};

defineProps({
  question: Object,
});
</script>

<style scoped></style>
