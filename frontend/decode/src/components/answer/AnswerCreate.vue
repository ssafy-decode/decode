<template>
  <v-card class="card mx-auto" width="75%" height="70%">
    <v-sheet class="cardLeft">
      <v-row>
        <v-col :cols="12">
          <div class="myListItem">
            <div class="listItem">
              <img class="img" src="/questionIcon2.png" alt="질문아이콘" />
              <div>
                <p class="title">
                  {{ questionTitle }}
                </p>
              </div>
            </div>
          </div>
        </v-col>
      </v-row>
      <v-row>
        <v-col :cols="12">
          <div class="contentBox">
            <QuestionViewer :initialValue="questionContent" />
          </div>
        </v-col>
      </v-row>
    </v-sheet>
    <v-sheet class="cardRight">
      <div class="answerTitle">답변 작성 중...</div>
      <form @submit.prevent="createAnswer">
        <div style="background-color: white">
          <AnswerEditor @editor-content-updated="updateEditorContent" />
        </div>
        <div id="btnBox">
          <v-btn class="submitBtn" type="submit">답변등록</v-btn>
        </div>
      </form>
    </v-sheet>
  </v-card>
</template>

<script setup>
import AnswerEditor from '@/components/common/AnswerEditor.vue';
import { useUserStore } from '@/stores/userStore';
import { useQuestionStore } from '@/stores/questionStore';
import { useAnswerStore } from '@/stores/answerStore';
import { useRouter, useRoute } from 'vue-router';
import { ref, onMounted } from 'vue';
import axios from '@/utils/common-axios';
import QuestionViewer from '@/components/common/QuestionViewer.vue';

const userStore = useUserStore();
const questionStore = useQuestionStore();
const answserStore = useAnswerStore();
const router = useRouter();
const route = useRoute();

const questionInfo = ref({});
const questionTitle = ref('');
const questionContent = ref('');
const questionWriter = ref({});
const questionTagList = ref([]);
const questionCreatedTime = ref('');
const questionUpdatedTime = ref('');
const questionMeTooCnt = ref(null);

const questionId = answserStore.questionId;
const userId = userStore.loginUserId;

const answerContent = ref('');

const updateEditorContent = function (content) {
  answerContent.value = content;
};

const getDetailQuestion = function () {
  axios({
    method: 'get',
    url: `/question/${route.params.id}`,
    headers: {
      Authorization: `${userStore.accessToken}`,
    },
  })
    .then((res) => {
      questionStore.detailQuestion = res.data.data;
      questionInfo.value = questionStore.detailQuestion;
      questionTitle.value = questionInfo.value.title;
      questionContent.value = questionInfo.value.content;
      questionWriter.value = questionInfo.value.questionWriter;
      questionTagList.value = questionInfo.value.tagList;
      questionCreatedTime.value = questionInfo.value.createdTime;
      questionUpdatedTime.value = questionInfo.value.updatedTime;
      questionMeTooCnt.value = questionInfo.value.meTooCnt;
    })
    .catch((err) => {
      router.push({ name: 'mainview' });
      alert('접근 권한이 없습니다.');
    });
};

onMounted(() => {
  getDetailQuestion();
});

const createAnswer = function () {
  if (answerContent.value.trim()) {
    let data = {
      questionId: parseInt(questionId),
      userId: parseInt(userId),
      content: answerContent.value,
    };
    axios({
      method: 'post',
      url: `/answer`,
      data: data,
      headers: {
        Authorization: `Bearer ${userStore.accessToken}`,
      },
    })
      .then((res) => {
        router.push({ path: `/board/${questionId}` });
      })
      .catch((err) => {});
  } else {
    alert('내용을 입력해주세요.');
  }
};
</script>

<style scoped>
.submitBtn {
  position: absolute;
  right: 0px;
  top: 20px;
  border-radius: 34px;
  background-color: #62c0a6;
  font-weight: 800;
  font-size: small;
  box-shadow: 0px 0px 5px rgba(0, 0, 0, 0.4);
}

#btnBox {
  position: relative;
}

.card {
  box-shadow: 0px 0px 20px rgba(0, 0, 0, 0.4);
  background-color: none;
  display: flex;
  justify-content: space-between;
  border-top-left-radius: 50px;
  border-bottom-left-radius: 50px;
  border-bottom-right-radius: 50px;
}

.cardLeft {
  width: 40%;
  border-top-left-radius: 50px;
  border-bottom-left-radius: 50px;
  padding: 50px 30px 70px;
  background-color: #f4f4f4;
}
.cardRight {
  width: 60%;
  border-bottom-right-radius: 50px;
  padding: 50px 30px 70px;
  background-color: #d9d9d9;
}

input {
  border-left: 1px solid black;
}

span {
  margin: 5px;
}

::-webkit-scrollbar {
  width: 15px;
}

::-webkit-scrollbar-thumb {
  background: #b0b0b0;
  border: solid 2px #e6e6e6;
  border-radius: 5px;
}

::-webkit-scrollbar-track {
  background-color: #e6e6e6;
}

.myListItem {
  border-radius: 35px;
  background-color: white;
}

.listItem {
  /* width: 100%; */
  height: auto;
  font-size: large;
  color: #575757;
  display: flex;
  align-items: center;
}

.title {
  font-size: medium;
  font-weight: 800;
  width: 400px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.img {
  width: 50px;
  height: 50px;
  margin: 5px 10px 5px 5px;
}

.contentBox {
  padding: 25px 30px 25px;
  background-color: white;
  border-radius: 25px;
  min-height: 515px;
  height: auto;
}

.answerTitle {
  margin-bottom: 20px;
  font-size: larger;
  font-weight: bolder;
  color: #575757;
}
</style>
