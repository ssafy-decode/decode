<template>
  <v-card color="#f3f3f3" elevation="16" width="70%" class="card mx-auto px-5 py-5">
    <v-row>
      <v-col :cols="12">
        <div class="myListItem">
          <div class="listItem">
            <img class="img" src="/questionIcon2.png" alt="질문아이콘" />
            <div>
              <p class="title">{{ question.title }}</p>
            </div>
          </div>
        </div>
      </v-col>
    </v-row>
    <br />
    <div class="myListItem contentBox">
      <v-row>
        <v-col :cols="12">
          <div class="listItem writerBox">
            <span class="nickname title">
              <profileRouter :uid="questionWriterId" :nickName="writerNickname" />
            </span>
            &nbsp; &nbsp;
            <span class="time info">
              {{ questionCreatedTime[0] }}년 {{ questionCreatedTime[1] }}월 {{ questionCreatedTime[2] }}일
            </span>
          </div>
        </v-col>
      </v-row>
      <v-row>
        <v-col :cols="12">
          <QuestionViewer :initialValue="question.content" />
          <div class="tagList">
            <span>&lt;질문 태그&gt;</span>
            <div v-for="(tag, index) in numToStr" :key="index"># {{ tag }} - {{ versions[index] }}</div>
          </div>
        </v-col>
      </v-row>
      <br /><br />
      <div class="btnBox">
        <div>
          <v-btn v-if="questionWriterId === userStore.loginUserId" @click="goUpdate()">질문수정</v-btn>
          <v-btn v-if="questionWriterId === userStore.loginUserId" @click="deleteQuestion()">질문삭제</v-btn>
        </div>
        <div class="cntBox">
          <div class="cnt">
            <v-btn v-if="isMeTooed" @click="deleteMeToo(questionId)">나도궁금해요 취소</v-btn>
            <v-btn v-else @click="addMeToo(userStore.loginUserId, questionId)">나도궁금해요</v-btn>
            <!-- {{ meTooCnt }} -->
          </div>
          <div class="cnt">
            <v-btn v-if="isBookmarked" @click="deleteBookmark(questionId)">북마크 취소</v-btn>
            <v-btn v-else @click="addBookmark(userStore.loginUserId, questionId)">북마크</v-btn>
            <!-- {{ bookmarkCnt }} -->
          </div>
          <v-btn @click="goCreateAnswer()">답변달기</v-btn>
        </div>
      </div>
    </div>
    <br />
    <br />
    <div v-if="isAnswerExist" class="myListItem">
      <AnswerList :answer-list="question.answerList" />
    </div>
  </v-card>
</template>

<script setup>
import axios from '@/utils/common-axios';
import { ref, onMounted, watch } from 'vue';
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

// const bookmarkCnt = ref(0);
// const meTooCnt = ref(0);

const numToStr = ref([]);
const versions = ref([]);

const getDetailQuestion = function () {
  axios({
    method: 'get',
    url: `/question/${route.params.id}`,
  })
    .then((res) => {
      questionId.value = route.params.id;
      question.value = res.data.data;
      isAnswerExist.value = question.value.answerList.length > 0;
      writerNickname.value = question.value.questionWriter.nickname;
      questionWriterId.value = question.value.questionWriter.id;
      questionCreatedTime.value = question.value.createdTime;
      // bookmarkCnt.value = question.value.bookmarkCnt;
      // meTooCnt.value = question.value.meTooCnt;
      question.value.tagList.forEach((item) => {
        numToStr.value.push(questionStore.reverseItems[item.tagId]);
        versions.value.push(item.version);
      });
    })
    .catch((err) => {
      console.log(err);
      console.log('상세 질문 조회 오류');
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
        console.log('삭제됨');
        router.push({
          path: '/board',
        });
      })
      .catch((err) => {
        console.log(err);
        console.log('질문 삭제 오류');
      });
  } else {
  }
};

const goUpdate = function () {
  router.push({ path: `/question-update/${questionId.value}` });
};

const goCreateAnswer = function () {
  answerStore.questionId = questionId.value;
  router.push({ path: `/answer-create` });
};
</script>

<style scoped>
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
  margin: 100px 40px;
}

.btnBox {
  display: flex;
  justify-content: space-between;
}

.img {
  width: 75px;
  height: 75px;
  margin: 5px 10px 5px 5px;
}

.myListItem {
  border-radius: 35px;
  padding-bottom: 10px;
  background-color: white;
}

.contentBox {
  padding: 25px 40px 25px;
}

.writerBox {
  align-items: flex-end;
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

.tagList {
  margin-top: 40px;
  color: #575757;
  font-size: small;
  font-weight: 600;
}

.cntBox {
  display: flex;
  justify-content: center;
}

.cnt {
  display: flex;
  flex-direction: column;
  align-items: center;
}
</style>
