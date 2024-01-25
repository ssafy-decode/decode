<template>
  <v-sheet class="mx-auto createBox" width="1000">
    <v-form @submit.prevent="createQuestion">
      <v-text-field variant="solo" label="질문 제목" v-model.trim="questionTitle"></v-text-field>
      <v-container>
        <v-row class="d-flex justify-end">
          <v-col cols="12" sm="6" md="4">
            <v-combobox
              variant="solo"
              class="stackBox"
              bg-color="fff"
              v-model="select"
              :items="items"
              placeholder="ex) java, spring boot, sql"
              label="기술 스택"
              multiple
              chips
              clearable
            ></v-combobox>
          </v-col>
        </v-row>
      </v-container>
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
const userStore = useUserStore();
const router = useRouter();
// 넘겨줄 데이터
const questionTitle = ref('');
const questionContent = ref('');
const questionWriterId = ref(null); // 로그인된 사용자 id 가져와서 넣어야함
const tagId = ref(null);
const version = ref('');

const select = ref([]);
const items = ref([
  'python',
  'java',
  'C++',
  'javascript',
  'django',
  'spring',
  'spring boot',
  'kotlin',
  'sql',
  'react',
  'vue',
  'C#',
]);

const updateEditorContent = function (content) {
  questionContent.value = content;
};

////////////////////////////  개발중  //////////////////////////////

const createQuestion = function () {
  let data = {
    title: questionTitle.value,
    content: questionContent.value,
    questionWriterId: questionWriterId.value,
    tags: [{ tagId: tagId.value, version: version.value }],
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
