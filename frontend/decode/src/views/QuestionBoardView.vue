<template>
  <div>
    <h1 style="text-align: center">질문게시판</h1>
    <br />
    <QuestionList />
  </div>
</template>

<script setup>
import { onMounted } from 'vue';
import { useQuestionStore } from '@/stores/questionStore';
import QuestionList from '@/components/question/QuestionList.vue';

const store = useQuestionStore();

onMounted(() => {
  store.getQuestions();
});
</script>

<style scoped></style>
