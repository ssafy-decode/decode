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
    <div class="myListItem contentBox">
      <v-row>
        <v-col :cols="12">
          <div class="listItem writerBox">
            <span class="nickname title">{{ writerNickname }}</span>
            &nbsp; &nbsp;
            <span class="time info">{{ question.createdTime }}</span>
          </div>
        </v-col>
      </v-row>
      <v-row>
        <v-col :cols="12">
          <div class="listItem title">{{ question.content }}</div>
        </v-col>
      </v-row>
      <br /><br />
      <div class="btnBox">
        <div>
          <v-btn @click="goUpdate()">질문수정</v-btn>
          <v-btn @click="deleteQuestion()">질문삭제</v-btn>
        </div>
        <div>
          <!-- 나도 궁금 -->
          <!-- 누르면 나도 궁금 되도록 -->

          <!-- <span>{{ question.meTooCnt }}</span>
          &nbsp; &nbsp; -->

          <!-- 북마크 카운트수 불러올 수 있도록? -->
          <!-- 누르면 북마크 되도록 -->

          <!-- <span>{{ question.meTooCnt }}</span>
          &nbsp; &nbsp; -->

          <v-btn @click="goCreateAnswer()">답변달기</v-btn>
        </div>
      </div>
    </div>
    <br />
    <br />
    <div class="myListItem">
      <AnswerList :answer-list="question.answerList" />
    </div>
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
const writerNickname = ref('');

const getDetailQuestion = function () {
  axios({
    method: 'get',
    url: `/question/${route.params.id}`,
  })
    .then((res) => {
      questionId.value = route.params.id;
      question.value = res.data.data;
      writerNickname.value = question.value.questionWriter.nickname;
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
button {
  background-color: #62c0a6;
  border-radius: 35px;
  font-size: small;
  padding: 0 10 0;
  margin: 5px;
}

.card {
  border-top-left-radius: 50px;
  border-bottom-left-radius: 50px;
  border-bottom-right-radius: 50px;
  margin: 40 px;
}
.answerRelatedBox {
  display: flex;
  justify-content: flex-end;
}

.answerListBox {
  padding: 20px;
}
.btnBox {
  display: flex;
  justify-content: space-between;
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
  border-radius: 35px;
  padding-bottom: 10px;
}
.myListItem2 {
  background-color: white;
  border-radius: 35px;
  display: flex;
  justify-content: space-between;
  align-items: center;
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
