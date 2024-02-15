<template>
  <v-card color="#f3f3f3" elevation="16" width="70%" class="card mx-auto py-5">
    <div class="title-container">
      <div class="title-img-container">
        <img class="title-img" src="/questionIcon2.png" alt="질문아이콘" />
      </div>
      <div class="title-text-container">
        <p class="title">{{ question.title }}</p>
      </div>
    </div>

    <div class="question-container">
      <div class="question-info-container">
        <div class="listItem writerBox">
          <span class="nickname title">
            <profileRouter :uid="questionWriterId" :nickName="writerNickname" />
          </span>
          <span class="time info">
            {{ questionCreatedTime[0] }}년 {{ questionCreatedTime[1] }}월 {{ questionCreatedTime[2] }}일
          </span>
        </div>
        <div class="edit-btnBox">
          <div class="edit-btn" v-if="questionWriterId === userStore.loginUserId" @click="goUpdate()">
            <span class="edit-text">질문수정</span>
          </div>
          <div class="edit-btn" v-if="questionWriterId === userStore.loginUserId" @click="deleteQuestion()">
            <span class="edit-text">질문삭제</span>
          </div>
        </div>
      </div>
      <div class="question-content-container">
        <QuestionViewer style="margin-left: 10px; margin-right: 10px" :initialValue="question.content" />
        <div class="tagList">
          <span>&lt;질문 태그&gt;</span>
          <div v-for="(tag, index) in numToStr" :key="index"># {{ tag }} - {{ versions[index] }}</div>
        </div>
      </div>
      <div class="question-btn-container">
        <div class="btnBox">
          <v-btn
            v-if="isMeTooed"
            @click="deleteMeToo(questionId)"
            style="background-color: #ffffff; border: 2px solid #62c0a6; color: #575757"
            >나도궁금해요 취소</v-btn
          >
          <v-btn v-else @click="addMeToo(userStore.loginUserId, questionId)">나도궁금해요</v-btn>
          <v-btn
            v-if="isBookmarked"
            @click="deleteBookmark(questionId)"
            style="background-color: #ffffff; border: 2px solid #62c0a6; color: #575757"
            >북마크 취소</v-btn
          >
          <v-btn v-else @click="addBookmark(userStore.loginUserId, questionId)">북마크</v-btn>
          <v-btn @click="goCreateAnswer()">답변달기</v-btn>
        </div>
      </div>
    </div>

    <div v-if="isAnswerExist" class="answer-list-container">
      <AnswerList :answer-list="question.answerList" />
    </div>
  </v-card>
</template>

<script setup>
import axios from '@/utils/common-axios';
import { ref, onMounted, watchEffect } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { storeToRefs } from 'pinia';
import AnswerList from '@/components/answer/AnswerList.vue';
import QuestionViewer from '@/components/common/QuestionViewer.vue';
import profileRouter from '@/components/common/profileRouter.vue';

import { useQuestionStore } from '@/stores/questionStore';
import { useAnswerStore } from '@/stores/answerStore';
import { useUserStore } from '@/stores/userStore';
import { useBookmarkStore } from '@/stores/bookmarkStore';
import { useMeTooStore } from '@/stores/meTooStore';

const questionStore = useQuestionStore();
const answerStore = useAnswerStore();
const userStore = useUserStore();
const bookmarkStore = useBookmarkStore();
const meTooStore = useMeTooStore();

const router = useRouter();
const route = useRoute();

const questionId = ref(0);
const question = ref({});
const writerNickname = ref('');
const questionWriterId = ref(null);
const isAnswerExist = ref(false);
const questionCreatedTime = ref('');

const numToStr = ref([]);
const versions = ref([]);

const getDetailQuestion = function () {
  axios({
    method: 'get',
    url: `/question/${route.params.id}`,
  })
    .then((res) => {
      questionId.value = route.params.id;
      questionStore.detailQuestion = res.data.data;
      question.value = questionStore.detailQuestion;
      questionStore.originalContent = question.value.content;
      isAnswerExist.value = question.value.answerList.length > 0;
      writerNickname.value = question.value.questionWriter.nickname;
      questionWriterId.value = question.value.questionWriter.id;
      questionCreatedTime.value = question.value.createdTime;
      question.value.tagList.forEach((item) => {
        numToStr.value.push(questionStore.reverseItems[item.tagId]);
        versions.value.push(item.version);
      });
      /////////////////////////수정중////////////////////////////
      // bookmarkCnt.value = question.value.bookmarkCnt;
      // meTooCnt.value = question.value.meTooCnt;
      /////////////////////////수정중////////////////////////////
    })
    .catch((err) => {
      router.push({ name: 'mainview' });
      alert('접근 권한이 없습니다.');
    });
};

const { setBookmarkList, addBookmark, deleteBookmark } = bookmarkStore;
const { handleBookmarkList: bookmarkList, handleBookmarkState: isBookmarked } = storeToRefs(bookmarkStore);

const { setMeTooList, addMeToo, deleteMeToo } = meTooStore;
const { handleMeTooList: meTooList, handleMeTooState: isMeTooed } = storeToRefs(meTooStore);

onMounted(() => {
  getDetailQuestion();
  setBookmarkList(route.params.id, userStore.loginUserId);
  setMeTooList(route.params.id, userStore.loginUserId);
});

// const meToo = function () {
//   meTooStore.addMeToo(userStore.loginUserId, questionId);
//   meTooCnt.value++;
// };

// const bookmark = function () {
//   bookmarkStore.addBookmark(userStore.loginUserId, questionId);
//   bookmarkCnt.value++;
// };

// const cancelMeToo = function () {
//   meTooStore.deleteMeToo(questionId);
//   meTooCnt.value--;
// };

// const cancelBookmark = function () {
//   bookmarkStore.deleteBookmark(questionId);
//   bookmarkCnt.value--;
// };

const deleteQuestion = function () {
  if (confirm('질문을 삭제하시겠습니까?')) {
    axios({
      method: 'delete',
      url: `/question/delete/${questionId.value}`,
      headers: {
        Authorization: `Bearer ${userStore.accessToken}`,
      },
    })
      .then((res) => {
        router.push({
          path: '/board',
        });
      })
      .catch((err) => {});
  } else {
  }
};

const goUpdate = function () {
  router.push({ path: `/question-update/${questionId.value}` });
};

const goCreateAnswer = function () {
  answerStore.questionId = questionId.value;
  router.push({ path: `/answer-create/${questionId.value}` });
};

// watchEffect(() => {
// if (props.answer && props.recommendedAnswerList) {
//   recommendList.value.forEach(function (a_id) {
//     props.recommendedAnswerList[a_id] = true;
//   });
// }
// });
</script>

<style scoped>
.title-container {
  height: fit-content;
  width: 90%;
  background-color: #fff;
  box-shadow: 0px 0px 5px rgba(0, 0, 0, 0.4);
  border-radius: 50px;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 0;
  margin: 0;
}
.title-img-container {
  height: 100%;
  width: 70px;
  display: flex;
  justify-content: center;
  align-items: center;
}
.title-text-container {
  height: 100%;
  flex-grow: 1;
  display: flex;
  align-items: center;
}
.title-img {
  height: 60px;
  width: 60px;
}
.question-container {
  width: 90%;
  padding-top: 10px;
  padding-left: 20px;
  padding-right: 20px;
  margin-top: 10px;
  background-color: white;
  border-radius: 35px;
  display: flex;
  flex-direction: column;
  box-shadow: 0px 0px 5px rgba(0, 0, 0, 0.4);
}
.question-info-container {
  width: 100%;
  border-bottom: solid 0.5px #ccc;
  display: flex;
}
.question-content-container {
  width: 100%;
}
.question-btn-container {
  width: 100%;
  margin-top: 5px;
  margin-bottom: 5px;
  border-top: solid 0.5px #ccc;
  display: flex;
  justify-content: flex-end;
}
.btnBox {
  display: flex;
  justify-content: space-between;
}
.edit-btnBox {
  width: fit-content;
  display: flex;
  align-items: flex-end;
  justify-content: flex-end;
}
.edit-btn {
  margin-left: 5px;
  margin-right: 5px;
}
.edit-text {
  color: #aaa;
  font-size: 15px;
  cursor: pointer;
  white-space: nowrap;
}
.answer-list-container {
  display: flex;
  margin-top: 10px;
  width: 90%;
}

button {
  background-color: #62c0a6;
  border-radius: 35px;
  font-size: small;
  font-weight: 800;
  padding: 0 10 0;
  margin: 5px;
  box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.4);
}

.card {
  border-top-left-radius: 50px;
  border-bottom-left-radius: 50px;
  border-bottom-right-radius: 50px;
  margin-top: 10px;
  padding: 0;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
}

.contentBox {
  padding: 25px 40px 25px;
}

.writerBox {
  align-items: flex-end;
  margin-top: 10px;
}

.listItem {
  width: 100%;
  height: auto;
  font-size: large;
  color: #575757;
  display: flex;
  align-items: center;
  margin-left: 10px;
}

.info {
  font-size: small;
}

.nickname {
  color: #62c0a6;
}

.title {
  font-size: large;
  font-weight: bolder;
  color: #575757;
  margin-right: 20px;
}

.time {
  color: #d9d9d9;
  font-weight: bold;
}

.tagList {
  margin-top: 40px;
  margin-left: 10px;
  margin-right: 10px;
  color: #575757;
  font-size: small;
  font-weight: bold;
}
</style>
