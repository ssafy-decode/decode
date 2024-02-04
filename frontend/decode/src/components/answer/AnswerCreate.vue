<template>
  <v-card class="mx-auto d-flex justify-space-between py-5 px-3" width="1550" height="750">
    <v-sheet class="px-5" width="600" style="border: 2px solid black">
      <br />
      <p>질문 번호: {{ questionId }}</p>
      <p>질문 제목: {{ questionTitle }}</p>
      <p>질문 내용: {{ questionContent }}</p>
      <p>질문 작성자: {{ questionWriter.nickname }}</p>
      <p>질문 태그 목록: {{ questionTagList }}</p>
      <p>질문 생성일: {{ questionCreatedTime }}</p>
      <p>질문 수정일: {{ questionUpdatedTime }}</p>
      <p>질문 나도 궁금 수: {{ questionMeTooCnt }}</p>
      <br />
    </v-sheet>
    <v-sheet class="mx-auto px-5" width="900" style="border: 2px solid black">
      <h3>답변 작성 칸</h3>
      <br />
      <form @submit.prevent="createAnswer">
        <MyEditor @editor-content-updated="updateEditorContent" />
        <div id="btnBox">
          <v-btn id="submitBtn" type="submit">답변등록</v-btn>
        </div>
      </form>
    </v-sheet>
  </v-card>
</template>

<script setup>
import MyEditor from '@/components/common/MyEditor.vue';
import { useUserStore } from '@/stores/userStore';
import { useAnswerStore } from '@/stores/answerStore';
import { useQuestionStore } from '@/stores/questionStore';
import { useRoute, useRouter } from 'vue-router';
import { ref, onMounted } from 'vue';
import axios from '@/utils/common-axios';

const userStore = useUserStore();
const answserStore = useAnswerStore();
const questionStore = useQuestionStore();
const route = useRoute();
const router = useRouter();

const questionInfo = ref({});
const questionTitle = ref('');
const questionContent = ref('');
const questionWriter = ref({});
const questionTagList = ref([]);
const questionCreatedTime = ref('');
const questionUpdatedTime = ref('');
const questionMeTooCnt = ref(null);

// questionId, userId, content 필요
const questionId = answserStore.questionId;
const userId = userStore.loginUserId;

const answerContent = ref('');

const updateEditorContent = function (content) {
  answerContent.value = content;
};

const getDetailQuestion = function () {
  axios({
    method: 'get',
    url: `/question/${questionId}`,
    headers: {
      Authorization: `${userStore.accessToken}`,
    },
  })
    .then((res) => {
      questionInfo.value = res.data.data;
      questionTitle.value = questionInfo.value.title;
      questionContent.value = questionInfo.value.content;
      questionWriter.value = questionInfo.value.questionWriter;
      questionTagList.value = questionInfo.value.tagList;
      questionCreatedTime.value = questionInfo.value.createdTime;
      questionUpdatedTime.value = questionInfo.value.updatedTime;
      questionMeTooCnt.value = questionInfo.value.meTooCnt;
    })
    .catch((err) => {
      console.log(err);
      console.log('상세 질문 조회 오류');
    });
};

onMounted(() => {
  getDetailQuestion();
});

const createAnswer = function () {
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
      console.log('답변 생성 완료');
      router.push({ path: `/board/${questionId}` });
    })
    .catch((err) => {
      console.log(data)
      console.log(err);
      console.log('답변 생성 오류');
    });
};
</script>

<style scoped>
#btnBox {
  position: relative;
}

#submitBtn {
  position: absolute;
  right: 10px;
  top: 5px;
  border-radius: 40px;
  background-color: #62c0a6;
}
</style>
