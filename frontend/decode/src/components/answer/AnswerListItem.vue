<template>
  <div>
    <div class="answerBox" v-if="answer">
      <div>
        <p>답변 번호: {{ answer.answerId }}</p>
        <p>작성자: {{ answer.answerWriter.nickname }}</p>
        <p>답변 내용: {{ answer.content }}</p>
        <p>작성시각: {{ answer.createdTime }}</p>
        <p>수정시각: {{ answer.updatedTime }}</p>
        <br />
      </div>
      <div class="btnBox">
        <v-btn class="btn" @click="answerStore.updateAnswer(answer.answerId)">답변수정</v-btn>
        <v-btn class="btn" @click="answerStore.deleteAnswer(answer.answerId)">답변삭제</v-btn>
      </div>
    </div>
    <br />
    <hr />
    <div class="commentBox">
      <CommentList :comment-list="answer.commentList" />
      <v-form class="commentInputArea" onsubmit.prevent="onSubmit">
        <v-textarea v-model="commentContent" clearable label="댓글을 작성해주세요"></v-textarea>
        <v-btn class="btn" type="submit" @click="commentStore.createComment(data)">댓글등록</v-btn>
      </v-form>
    </div>
  </div>
</template>

<script setup>
import CommentList from '@/components/comment/CommentList.vue';
import { useAnswerStore } from '@/stores/answerStore';
import { useCommentStore } from '@/stores/commentStore';
import { ref } from 'vue';

const answerStore = useAnswerStore();
const commentStore = useCommentStore();

const commentContent = ref('');
const data = ref({});

const props = defineProps({
  answer: Object,
});
</script>

<style scoped>
div {
  margin: 20px;
}

.btn {
  margin-left: 10px;
  margin-right: 10px;
}

.answerBox {
  display: flex;
  justify-content: space-between;
}

.commentBox {
  margin-left: 50px;
}

.commentInputArea {
  margin: 0px;
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>
