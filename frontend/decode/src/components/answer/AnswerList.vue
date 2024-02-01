<template>
  <div>
    <AnswerListItem v-for="answer in answerList" :key="answer.answerId" :answer="answer" />
  </div>
</template>

<script setup>
import AnswerListItem from '@/components/answer/AnswerListItem.vue';

defineProps({
  answerList: Array,
});
</script>

<style scoped></style>
