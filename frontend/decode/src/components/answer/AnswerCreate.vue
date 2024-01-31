<template>
  <v-card class="mx-auto d-flex justify-space-between py-5 px-3" width="1550" height="750">
    <v-sheet width="600" style="border: 2px solid black">왼쪽 질문 상세 조회 부분</v-sheet>
    <v-sheet class="mx-auto" width="900" style="border: 2px solid black">
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
import { ref } from 'vue';
import axios from 'axios';

const userStore = useUserStore();
const answserStore = useAnswerStore();

// questionId, userId, content 필요
const questionId = answserStore.questionId;
const userId = userStore.loginUserId;

const answerContent = ref('');
const updateEditorContent = function (content) {
  answerContent.value = content;
};

const createAnswer = function () {
  let data = {
    questionId: parseInt(questionId),
    userId: userStore.loginUserId,
    content: answerContent.value,
  };

  axios({
    method: 'post',
    url: `${userStore.URL}/answer`,
    data: data,
    headers: {
      'Content-Type': 'application/json',
      'Access-Control-Allow-Origin': '*',
      Authorization: `Bearer ${userStore.accessToken}`,
    },
  })
    .then((res) => {
      console.log('답변 생성 완료');
      router.push({ name: 'questionview' });
    })
    .catch((err) => {
      console.log(err);
      console.log('data입니다 :', data);
      console.log(userStore.accessToken);
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
