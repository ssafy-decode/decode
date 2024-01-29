<template>
  <v-sheet class="mx-auto createBox" width="1000">
    <v-form @submit.prevent="createQuestion">
      <v-text-field variant="solo" label="질문 제목" v-model.trim="questionTitle"></v-text-field>
      <v-container>
        <template v-for="(tag, index) in tagId" :key="index">
          <v-row class="d-flex justify-end">
            <v-col cols="12" sm="6" md="4">
              <!-- <v-text-field variant="solo" label="관련 태그" v-model.trim="tagId"></v-text-field> -->
              <v-text-field
                variant="solo"
                class="stackBox"
                bg-color="fff"
                v-model.trim="tagId[index]"
                placeholder="ex) java, spring boot, sql"
                label="관련 태그"
              ></v-text-field>
            </v-col>
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
        </template>
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
import { useQuestionStore } from '@/stores/questionStore';
import { useUserStore } from '@/stores/userStore';
import { useRouter, useRoute } from 'vue-router';
import { ref, onMounted } from 'vue';
import axios from 'axios';

const questionStore = useQuestionStore();
const userStore = useUserStore();
const router = useRouter();
const route = useRoute();

// 받아와서 넘겨줄 데이터
const questionTitle = ref('');
const tagId = ref('');
// 만들어서 넘겨줄 데이터
const questionContent = ref('');
const questionWriterId = ref(null); // 로그인된 사용자 id 가져와서 넣어야함
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

////////////////////////////  아래 개발중  //////////////////////////////

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
////////////////////////////  위 개발중  //////////////////////////////

onMounted(() => {
  questionTitle.value = questionStore.gptTitles.value;
  tagId.value = questionStore.gptTagIds.value;
  console.log('옮겨진타이틀:', questionTitle.value);
  console.log('옮겨진태그들:', tagId.value);
});
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
