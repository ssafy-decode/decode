<template>
  <v-card color="#f3f3f3" elevation="16" max-width="60%" class="card mx-auto px-5 py-5">
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
    <p>질문 번호: {{ question.id }}</p>
    <p>질문 내용: {{ question.content }}</p>
    <p>질문 작성자 정보: {{ question.questionWriter }}</p>
    <p>질문 태그 목록: {{ question.tagList }}</p>
    <p>질문 생성일: {{ question.createdTime }}</p>
    <p>질문 수정일: {{ question.updatedTime }}</p>
    <p>질문 나도 궁금 수: {{ question.meTooCnt }}</p>
    <br />

    <div class="btnBox">
      <v-btn class="btn" @click="goUpdate()">질문수정</v-btn>
      <v-btn class="btn" @click="deleteQuestion()">질문삭제</v-btn>
    </div>

    <hr />
    <br />

    <div class="answerRelatedBox">
      <v-btn @click="goCreateAnswer()">답변생성</v-btn>
    </div>

    <AnswerList :answer-list="question.answerList" />
  </v-card>
</template>

<script setup>
import axios from '@/utils/common-axios';
import { onMounted, ref } from 'vue';
import { useQuestionStore } from '@/stores/questionStore';
import { useAnswerStore } from '@/stores/answerStore';
import { useUserStore } from '@/stores/userStore';
import { useRoute, useRouter } from 'vue-router';
import AnswerList from '@/components/answer/AnswerList.vue';

const questionStore = useQuestionStore();
const answerStore = useAnswerStore();
const userStore = useUserStore();
const router = useRouter();
const route = useRoute();

const questionId = ref(0);
const question = ref({});

const getDetailQuestion = function () {
  axios({
    method: 'get',
    url: `/question/${route.params.id}`,
  })
    .then((res) => {
      questionId.value = route.params.id;
      question.value = res.data.data;
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
  questionStore.originalContent = question.value.content;
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
.card {
  border-top-left-radius: 50px;
  border-bottom-left-radius: 50px;
  border-bottom-right-radius: 50px;
  padding-bottom: 20px;
  margin: 40px;
}
.answerRelatedBox {
  display: flex;
  justify-content: space-between;
  padding: 20px;
}

.answerListBox {
  padding: 20px;
}
.btnBox {
  padding: 20px;
}

.btn {
  margin-left: 10px;
  margin-right: 10px;
}

.img {
  width: 75px;
  height: 75px;
  margin: 5px 10px 5px 5px;
}
.metooImg {
  width: 60px;
  height: 70px;
  margin-right: 5px;
}
.answerCountImg {
  margin-right: 10px;
  height: 45px;
}

.myListItem {
  background-color: white;
  border-radius: 45px;
}
.myListItem2 {
  background-color: white;
  border-radius: 45px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.myListItem:hover {
  box-shadow: 0px 0px 20px rgba(0, 0, 0, 0.4);
  transition: 0.3s;
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
</style>
