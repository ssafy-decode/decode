<template>
  <div v-if="answer">
    <p>답변 번호: {{ answer.answerId }}</p>
    <p>작성자: {{ answer.answerWriter.nickname }}</p>
    <p>답변 내용: {{ answer.content }}</p>
    <p>작성시각: {{ answer.createdTime }}</p>
    <p>수정시각: {{ answer.updatedTime }}</p>
    <br />
    <hr />
    <CommentList :comment-list="answer.commentList" />
  </div>
</template>

<script setup>
import CommentList from '@/components/comment/CommentList.vue';

defineProps({
  answer: Object,
});
</script>

<style scoped>
div {
  margin: 20px;
}
</style>
