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
        </v-col>
      </v-row>
      <br /><br />
      <div class="btnBox">
        <div>
          <v-btn v-if="questionWriterId === userStore.loginUserId" @click="goUpdate()">질문수정</v-btn>
          <v-btn v-if="questionWriterId === userStore.loginUserId" @click="deleteQuestion()">질문삭제</v-btn>
        </div>
        <div>
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
import { ref, onMounted } from 'vue';
import { useAnswerStore } from '@/stores/answerStore';
import { useUserStore } from '@/stores/userStore';
import { useRoute, useRouter } from 'vue-router';
import AnswerList from '@/components/answer/AnswerList.vue';
import QuestionViewer from '@/components/common/QuestionViewer.vue';
import profileRouter from '@/components/common/profileRouter.vue';

const answerStore = useAnswerStore();
const userStore = useUserStore();
const router = useRouter();
const route = useRoute();

const questionId = ref(0);
const question = ref({});
const writerNickname = ref('');
const questionWriterId = ref(null);
const isAnswerExist = ref(false);
const questionCreatedTime = ref('');

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
    })
    .catch((err) => {
      console.log(err);
      console.log('상세 질문 조회 오류');
    });
};

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

onMounted(() => {
  getDetailQuestion();
});
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
</style>
