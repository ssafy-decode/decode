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
      <v-form @submit.prevent="createComment" class="commentInputArea">
        <v-textarea v-model="commentContent" clearable label="댓글을 작성해주세요"></v-textarea>
        <v-btn class="btn" type="submit">댓글등록</v-btn>
      </v-form>
    </div>
  </div>
</template>

<script setup>
import CommentList from '@/components/comment/CommentList.vue';
import { useAnswerStore } from '@/stores/answerStore';
import { useCommentStore } from '@/stores/commentStore';
import { useUserStore } from '@/stores/userStore';
import { useRouter } from 'vue-router';
import { ref } from 'vue';
import axios from '@/utils/common-axios';

const router = useRouter();

const answerStore = useAnswerStore();
const commentStore = useCommentStore();
const userStore = useUserStore();

const props = defineProps({
  answer: Object,
});

const commentContent = ref('');

const createComment = function () {
  let data = {
    content: commentContent.value,
    userId: userStore.loginUserId,
    answerId: props.answer.answerId,
  };

  console.log('data내용!!!!', data);

  axios({
    method: 'post',
    url: `/comment`,
    data: data,
    headers: {
      Authorization: `Bearer ${userStore.accessToken}`,
    },
  })
    .then((res) => {
      console.log('댓글 생성됨');
      router.go(0);
    })
    .catch((err) => {
      console.log(err);
      console.log('댓글 생성 오류');
    });
};
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
