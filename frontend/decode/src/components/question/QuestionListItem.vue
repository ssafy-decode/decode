<template>
  <div>
    <div>
      <p>question id: {{ question.id }}</p>
      <p @click="goDetail(question.id)">question title: {{ question.title }}</p>
      <p>question tags: {{ question.tagIds }}</p>
      <br />
      <hr />
      <br />
    </div>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router';

const router = useRouter();

const goDetail = function (questionId) {
  console.log(questionId);
  router.push({ path: `/question/${questionId}` });
};

defineProps({
  question: Object,
});
</script>

<style scoped></style>
