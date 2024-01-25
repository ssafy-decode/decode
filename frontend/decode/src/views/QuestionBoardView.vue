<template>
  <div>
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
