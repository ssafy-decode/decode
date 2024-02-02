<template>
  <div>
    <div class="commentBox" v-if="comment">
      <div>
        <p>댓글 번호: {{ comment.commentId }}</p>
        <p>댓글 작성자: {{ comment.commentWriter.nickname }}</p>
        <p>댓글 내용: {{ comment.content }}</p>
        <p>작성시각: {{ comment.createdTime }}</p>
        <p>수정시각: {{ comment.updatedTime }}</p>
        <br />
      </div>
      <div class="btnBox">
        <v-btn class="btn" @click="commentStore.updateComment(comment.commentId)">댓글수정</v-btn>
        <v-btn class="btn" @click="commentStore.deleteComment(comment.commentId)">댓글삭제</v-btn>
      </div>
    </div>
    <hr />
    <br />
  </div>
</template>

<script setup>
import { useCommentStore } from '@/stores/commentStore';

const commentStore = useCommentStore();

defineProps({
  comment: Object,
});
</script>

<style scoped>
.btn {
  margin-left: 10px;
  margin-right: 10px;
}

.commentBox {
  display: flex;
  justify-content: space-between;
}
</style>
