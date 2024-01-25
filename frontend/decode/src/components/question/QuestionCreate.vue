<template>
  <v-sheet class="mx-auto createBox" width="1000">
    <v-form @submit.prevent="createQuestion">
      <v-text-field variant="solo" label="질문 제목" v-model.trim="questionTitle"></v-text-field>
      <MyEditor @editor-content-updated="updateEditorContent" />
      <div id="btnBox">
        <v-btn id="submitBtn" type="submit">질문등록</v-btn>
      </div>
    </v-form>
  </v-sheet>
</template>

<script setup>
import MyEditor from '@/components/common/MyEditor.vue';
import { ref } from 'vue';
import { useQuestionStore } from '@/stores/questionStore';
import { useUserStore } from '@/stores/userStore';
import axios from 'axios';
import { useRouter } from 'vue-router';

const questionStore = useQuestionStore();
const router = useRouter();
// 넘겨줄 데이터
const questionTitle = ref('');
const questionContent = ref('');
const questionWriterId = ref(17); // 로그인된 사용자 id 가져와서 넣어야함
const tagId = ref(0); // 임시 태그 id
const version = ref('string'); // 임시 태그 버전
// tags라는 array 안에 tagId와 version의 정보가 json 형태로 원소로 들어감

const userStore = useUserStore();

const updateEditorContent = function (content) {
  questionContent.value = content;
};

////////////////////////////  개발중  //////////////////////////////

const createQuestion = function () {
  let data = {
    title: questionTitle.value,
    content: questionContent.value,
    questionWriterId: questionWriterId.value,
    tags: [
      { tagId: tagId.value, version: version.value },
      { tagId: 1, version: '99.99.99' },
    ],
  };

  console.log(data);

  axios({
    method: 'post',
    url: `${questionStore.API_URL}/question`,
    data: data,
    headers: {
      'Content-Type': 'application/json',
      'Access-Control-Allow-Origin': '*', // 이 부분을 추가하여 CORS 정책 우회
      Authorization: userStore.accessToken, // 만약 인증이 필요하다면 주석 해제
    },
  })
    .then((res) => {
      console.log(res);
      console.log('질문 생성 완료');
      router.push({ name: 'questionview' });
    })
    .catch((err) => {
      console.log(err);
      console.log('질문 생성 오류');
    });
};
////////////////////////////  개발중  //////////////////////////////
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

.createBox {
  margin-bottom: 150px;
}
</style>
