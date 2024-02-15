<template>
  <div v-if="answer" class="answer-container">
    <div class="myListItem">
      <div class="listItem">
        <div class="answer-info-container">
          <div class="answer-img-container">
            <p class="answer-img"> A </p>
          </div>
          <span class="answer-nickname">
            <profileRouter :uid="answer.answerWriter.id" :nickName="answer.answerWriter.nickname" />
          </span>
          &nbsp;
          <span class="answer-time">
            {{ answer.createdTime[0] }}년 {{ answer.createdTime[1] }}월 {{ answer.createdTime[2] }}일
          </span>
          <v-btn
            v-if="recommendedAnswerList[props.answer.answerId]"
            @click="unRecommend()"
            class="ma-2"
            variant="text"
            icon="mdi-thumb-up"
            color="#34a080"
          ></v-btn>
          <v-btn v-else @click="recommend()" class="ma-2" variant="text" icon="mdi-thumb-up" color="#c2c2c2"></v-btn>
        </div>
        <div class="answer-delete-container">
          <div v-if="answer.answerWriter.id === userStore.loginUserId" class="editDeleteBox" @click="answerStore.deleteAnswer(answer.answerId)">
            <span  class="deleteText">답변삭제</span>
          </div>
        </div>
      </div>
      <div class="answerBox listItem answerContent">
        <AnswerViewer :initialValue="answer.content" :answerId="answer.answerId" />
      </div>
    </div>
    <div class="comment-list-container">
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
              hide-details
              label="댓글을 작성해주세요"
            >
              <template #append-inner>
                <v-btn type="submit" class="searchBtn" size="medium" @click="createComment">
                  <img src="/searchicon.png" alt="등록아이콘" style="width: 40px; height: auto" />
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
import { ref, onMounted, watchEffect } from 'vue';
import axios from '@/utils/common-axios';
import AnswerViewer from '@/components/common/AnswerViewer.vue';
import { useRoute } from 'vue-router';
import profileRouter from '@/components/common/profileRouter.vue';

// const recommendStore = useRecommendStore();
const answerStore = useAnswerStore();
const userStore = useUserStore();
const route = useRoute();

const props = defineProps({
  answer: Object,
  recommendedAnswerList: Object,
});

import { storeToRefs } from 'pinia';
import { useRecommendStore } from '@/stores/recommendStore';
const recommendStore = useRecommendStore();
const { setRecommendList, addRecommend, deleteRecommend } = recommendStore;
const { handleRecommendList: recommendList } = storeToRefs(recommendStore);

onMounted(() => {
  setRecommendList(props.answer.answerId, userStore.loginUserId);
});

watchEffect(() => {
  if (props.answer && props.recommendedAnswerList) {
    recommendList.value.forEach(function (a_id) {
      props.recommendedAnswerList[a_id] = true;
    });
  }
});

const recommend = function () {
  addRecommend(userStore.loginUserId, props.answer.answerId);
  props.recommendedAnswerList[props.answer.answerId] = true;
};

const unRecommend = function () {
  deleteRecommend(props.answer.answerId);
  props.recommendedAnswerList[props.answer.answerId] = false;
};

const commentContent = ref('');

const createComment = function () {
  if (commentContent.value.trim()) {
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
        location.reload();
      })
      .catch((err) => {
        console.log(err);
        console.log('댓글 생성 오류');
      });
  } else {
    alert('댓글 내용을 입력해주세요.');
  }
};
</script>

<style scoped>
.answer-img-container{
  height: 40px;
  width: 40px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
}
.answer-img{
  width: 80%;
  height: 80%;
  border: solid #12a980 2px;
  background-color: white;
  border-radius: 50%;
  text-align: center;
  font-weight: bold;
  color: #12a980;
}
.answer-container{
  width: 95%;
}
.answer-info-container{
  height: 40px;
  margin-left: 15px;
  display: flex;
  align-items: center;
}
.answerBox {
  display: flex;
  justify-content: space-between;
}


.commentInputArea {
  margin: 0px;
  display: flex;
  justify-content: center;
  align-items: center;
}

.myListItem {
  height: fit-content;
  border-radius: 35px;
  display: flex;
  flex-direction: column;
}

.listItem {
  width: 100%;
  height: auto;
  font-size: large;
  color: #575757;
  display: flex;
  align-items: center;
  margin: 0px;
}

.info {
  font-size: small;
}

.answer-nickname {
  font-size: 15px;
  color: #62c0a6;
}

.title {
  font-size: large;
  font-weight: 800;
}

.answer-time {
  color: #d9d9d9;
  font-size: 10px;
  font-weight: bold;
}
.answer-delete-container{
  flex-grow: 1;
  display: flex;
  align-items: end;
  justify-content: end;
}
.answerContent {
  border: #62c0a6 solid 2px;
  background-color: white;
  border-radius: 24px;
  padding: 20px;
  margin: 0;
}

.editDeleteBox {
  width: fit-content;
  border: solid black;
  display: flex;
  align-items: end;
  justify-content: end;
}

.deleteText {
  color: #12a980;
  font-size: 15px;
  font-weight: 800;
  cursor: pointer;
}

.searchBox {
  display: flex;
  justify-content: end;
  margin-top: 5px;
  margin-bottom: 5px;
  padding: 0;
}

.midBox {
  padding: 0px;
  margin: 0px;
  width: 70%;
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
