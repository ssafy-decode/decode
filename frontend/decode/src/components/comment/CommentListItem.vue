<template>
  <div v-if="comment">
    <p>댓글 번호: {{ comment.commentId }}</p>
    <p>댓글 작성자: {{ comment.commentWriter.nickname }}</p>
    <p>댓글 내용: {{ comment.content }}</p>
    <p>작성시각: {{ comment.createdTime }}</p>
    <p>수정시각: {{ comment.updatedTime }}</p>
    <br />
    <hr />
    <br />
  </div>
</template>

<script setup>
defineProps({
  comment: Object,
});
</script>

<style scoped></style>
