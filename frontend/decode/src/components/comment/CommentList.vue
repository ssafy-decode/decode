<template>
  <div>
    <CommentListItem v-for="comment in commentList" :key="comment.commentId" :comment="comment" />
  </div>
</template>

<script setup>
import CommentListItem from '@/components/comment/CommentListItem.vue';
defineProps({
  commentList: Array,
});
</script>

<style scoped></style>
