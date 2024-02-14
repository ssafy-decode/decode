<template>
  <div v-if="answer" class="answerContainer">
    <div class="myListItem">
      <div class="listItem">
        <div>
          <span class="nickname title">
            <profileRouter :uid="answer.answerWriter.id" :nickName="answer.answerWriter.nickname" />
          </span>
          &nbsp;
          <span class="time info">
            {{ answer.createdTime[0] }}년 {{ answer.createdTime[1] }}월 {{ answer.createdTime[2] }}일
          </span>
          <!-- 답변 채택 버튼 -->
          <v-btn
            v-if="adoptedAnswerList[props.answer.answerId]"
            icon="mdi-check-decagram"
            :ripple="false"
            variant="text"
            color="#34a080"
          ></v-btn>
          <v-btn
            v-else-if="userStore.loginUserId == questionStore.detailQuestion.questionWriter.id"
            @click="adopt()"
            icon="mdi-check-decagram-outline"
            variant="text"
            color="#c2c2c2"
          ></v-btn>

          <!-- 답변 추천 버튼 -->
          <v-btn
            v-if="recommendedAnswerList[props.answer.answerId]"
            @click="unRecommend()"
            variant="text"
            icon="mdi-thumb-up"
            color="#34a080"
          ></v-btn>
          <v-btn v-else @click="recommend()" variant="text" icon="mdi-thumb-up" color="#c2c2c2"></v-btn>
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
import { useQuestionStore } from '@/stores/questionStore';
import { useUserStore } from '@/stores/userStore';
import { ref, onMounted, watchEffect } from 'vue';
import axios from '@/utils/common-axios';
import AnswerViewer from '@/components/common/AnswerViewer.vue';
import { useRoute } from 'vue-router';
import profileRouter from '@/components/common/profileRouter.vue';

const answerStore = useAnswerStore();
const questionStore = useQuestionStore();
const userStore = useUserStore();
const route = useRoute();

const props = defineProps({
  answer: Object,
  recommendedAnswerList: Object,
  adoptedAnswerList: Object,
});

import { storeToRefs } from 'pinia';
import { useRecommendStore } from '@/stores/recommendStore';
const recommendStore = useRecommendStore();
const { setRecommendList, addRecommend, deleteRecommend } = recommendStore;
const { handleRecommendList: recommendList } = storeToRefs(recommendStore);

import { useAdoptStore } from '@/stores/adoptStore';
const adoptStore = useAdoptStore();
const { setAdoptList, addAdopt } = adoptStore;
const { handleAdoptList: adoptList } = storeToRefs(adoptStore);
const isAdopted = ref(false);

onMounted(() => {
  setRecommendList(props.answer.answerId, userStore.loginUserId);
  setAdoptList(route.params.id);
  // if (adoptList.value.includes(props.answer.answerId)) {
  //   isAdopted.value = true;
  // }
});

watchEffect(() => {
  if (props.answer && props.recommendedAnswerList) {
    recommendList.value.forEach(function (a_id) {
      props.recommendedAnswerList[a_id] = true;
    });
  }

  if (props.answer && props.adoptedAnswerList) {
    adoptList.value.forEach(function (a_id) {
      props.adoptedAnswerList[a_id] = true;
    });
  }

  // if (props.adoptList) {
  //   if (props.adoptList.includes(props.answer.answerId)) {
  //     isAdopted.value = true;
  //   }
  // }
});

const recommend = function () {
  addRecommend(userStore.loginUserId, props.answer.answerId);
  props.recommendedAnswerList[props.answer.answerId] = true;
};

const adopt = function () {
  if (confirm('이 답변을 채택하시겠습니까?')) {
    addAdopt(userStore.loginUserId, props.answer.answerId);
    props.adoptedAnswerList[props.answer.answerId] = true;
  }
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
        location.reload();
      })
      .catch((err) => {});
  } else {
    alert('댓글 내용을 입력해주세요.');
  }
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
  padding-top: 10px;
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

.nickname {
  color: #62c0a6;
}

.title {
  font-size: large;
  font-weight: 800;
}

.time {
  color: #d9d9d9;
  font-weight: bold;
}

.answerContent {
  border: #62c0a6 solid 2px;
  border-radius: 24px;
  padding: 20px;
}

.editDeleteBox {
  display: flex;
  justify-content: flex-end;
  margin-top: 15px;
}

.deleteText {
  color: #12a980;
  font-size: 15px;
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
  margin-top: 20px;
  margin-right: 20px;
  margin-bottom: 20px;
}

.searchBtn {
  position: relative;
  border-radius: 30px;
}

.stackBox ::v-deep(.v-field) {
  border-radius: 30px;
}
</style>
