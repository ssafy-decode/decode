<template>
  <div class="comment-list-container">
    <CommentListItem v-for="comment in commentList" :key="comment.commentId" :comment="comment" />
  </div>
</template>

<script setup>
import CommentListItem from '@/components/comment/CommentListItem.vue';
defineProps({
  commentList: Array,
});
</script>

<style scoped>
.comment-list-container {
  width: 100%;
  padding: 0px;
  display: flex;
  flex-direction: column;
  align-items: flex-end;
  justify-content: flex-end;
}
</style>
