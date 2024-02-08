<template>
  <div v-if="answer" class="answerContainer">
    <div class="myListItem">
      <div class="listItem">
        <div>
          <p>
            <span class="nickname title">
              <profileRouter :uid="answer.answerWriter.id" :nickName="answer.answerWriter.nickname" />
            </span>
            &nbsp;
            <span class="time info">
              {{ answer.createdTime[0] }}년 {{ answer.createdTime[1] }}월 {{ answer.createdTime[2] }}일
            </span>
          </p>
        </div>
      </div>
      <div class="answerBox listItem answerContent">
        <AnswerViewer :initialValue="answer.content" :answerId="answer.answerId" />
      </div>
      <div class="editDeleteBox" @click="answerStore.deleteAnswer(answer.answerId)">
        <span v-if="answer.answerWriter.id === userStore.loginUserId" class="deleteText">답변삭제</span>
      </div>
    </div>
    <div class="commentBox">
      <CommentList :comment-list="answer.commentList" />
      <div class="searchBox">
        <div class="midBox">
          <v-form class="commentInputArea stackBox">
            <v-textarea
              variant="solo-filled"
              auto-grow
              rows="1"
              class="searchInput"
              v-model="commentContent"
              clearable
              label="댓글을 작성해주세요"
            >
              <template #append-inner>
                <v-btn type="submit" class="searchBtn" size="medium" @click="createComment">
                  <img src="/searchicon.png" alt="검색아이콘" style="width: 40px; height: auto" />
                </v-btn>
              </template>
            </v-textarea>
          </v-form>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import CommentList from '@/components/comment/CommentList.vue';
import { useAnswerStore } from '@/stores/answerStore';
import { useUserStore } from '@/stores/userStore';
import { ref } from 'vue';
import axios from '@/utils/common-axios';
import AnswerViewer from '@/components/common/AnswerViewer.vue';
import { useRoute } from 'vue-router';
import profileRouter from '@/components/common/profileRouter.vue';

import { useQuestionStore } from '@/stores/questionStore';
const questionStore = useQuestionStore();

const answerStore = useAnswerStore();
const userStore = useUserStore();
const route = useRoute();

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

.answerBox {
  display: flex;
  justify-content: space-between;
}

.commentBox {
  margin-left: 10px;
  padding: 0px;
}

.commentInputArea {
  margin: 0px;
  display: flex;
  justify-content: center;
  align-items: center;
}

.myListItem {
  background-color: white;
  border-radius: 35px;
  padding-top: 20px;
}

.listItem {
  width: 100%;
  height: auto;
  font-size: large;
  color: #575757;
  display: flex;
  align-items: center;
}

.info {
  font-size: small;
}

.nickname {
  color: #62c0a6;
}

.title {
  font-size: large;
  font-weight: 800;
}

.time {
  color: #d9d9d9;
}

.answerContent {
  border: #62c0a6 solid 2px;
  border-radius: 24px;
  padding: 40px;
}

.editDeleteBox {
  display: flex;
  justify-content: flex-end;
  margin-top: 0;
}

.deleteText {
  color: #12a980;
  font-size: small;
  font-weight: 800;
}

.searchBox {
  position: relative;
  right: -18px;
  padding-left: 127px;
  margin: 0px;
}

.midBox {
  padding: 0px;
  margin: 0px;
}

.searchInput {
  padding: 0px;
  margin: 0px;
}

.searchBtn {
  position: relative;
  border-radius: 30px;
}

.stackBox ::v-deep(.v-field) {
  border-radius: 30px;
}
</style>
