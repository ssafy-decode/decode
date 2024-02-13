<template>
  <div>
    <QuestionList />
  </div>
</template>

<script setup>
import { onMounted } from 'vue';
import { useQuestionStore } from '@/stores/questionStore';
import QuestionList from '@/components/question/QuestionList.vue';

const questionStore = useQuestionStore();

onMounted(() => {
  questionStore.getQuestions();
});
</script>

<style scoped></style>
