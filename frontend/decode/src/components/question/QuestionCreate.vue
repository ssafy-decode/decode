<template>
  <v-sheet class="mx-auto createBox" width="1000">
    <v-form @submit.prevent="createQuestion">
      <v-text-field variant="solo" label="질문 제목" v-model.trim="questionTitle"></v-text-field>
      <v-container>
        <v-row class="d-flex justify-end">
          <v-col cols="12" sm="6" md="4"> </v-col>
        </v-row>
        <template v-for="(tag, index) in tagIds" :key="index">
          <v-row class="d-flex justify-end">
            <v-col cols="12" sm="6" md="4">
              <v-text-field
                variant="solo"
                class="stackBox"
                bg-color="fff"
                v-model.trim="tagIds[index]"
                placeholder="ex) java, spring boot, sql"
                label="관련 태그"
              ></v-text-field>
            </v-col>
            <v-col cols="12" sm="6" md="4">
              <v-text-field
                variant="solo"
                class="stackBox"
                bg-color="fff"
                v-model.trim="versions[index]"
                placeholder="ex) 1.0.1"
                label="태그 버전"
              ></v-text-field>
            </v-col>
          </v-row>
        </template>
      </v-container>

      <br />
      <MyEditor @editor-content-updated="updateEditorContent" />
      <div class="btnBox">
        <v-btn class="submitBtn" type="submit">질문등록</v-btn>
      </div>
    </v-form>
  </v-sheet>
</template>

<script setup>
import MyEditor from '@/components/common/MyEditor.vue';
import { useQuestionStore } from '@/stores/questionStore';
import { useUserStore } from '@/stores/userStore';
import { useRouter } from 'vue-router';
import { ref, onMounted } from 'vue';
import axios from 'axios';

const questionStore = useQuestionStore();
const userStore = useUserStore();
const router = useRouter();

const questionTitle = ref('');
const tagIds = ref([]);
const questionContent = ref('');
const versions = ref([]);

const items = {
  python: 0,
  java: 1,
  'C++': 2,
  javascript: 3,
  django: 4,
  spring: 5,
  'spring boot': 6,
  kotlin: 7,
  sql: 8,
  react: 9,
  vue: 10,
  'C#': 11,
};

const updateEditorContent = function (content) {
  questionContent.value = content;
};

const createQuestion = function () {
  const tags = tagIds.value.map((tagId, index) => {
    return {
      tagId: items[tagId],
      version: versions.value[index],
    };
  });

  let data = {
    title: questionTitle.value,
    content: questionContent.value,
    questionWriterId: userStore.loginUserId,
    tags: tags,
  };

  axios({
    method: 'post',
    url: `${questionStore.URL}/question`,
    data: data,
    headers: {
      'Content-Type': 'application/json',
      'Access-Control-Allow-Origin': '*',
      Authorization: `Bearer ${userStore.accessToken}`,
    },
  })
    .then((res) => {
      console.log('질문 생성 완료');
      router.push({ name: 'questionview' });
    })
    .catch((err) => {
      console.log(err);
      console.log('질문 생성 오류');
    });
};

onMounted(() => {
  questionTitle.value = questionStore.gptTitles.value;
  tagIds.value = questionStore.gptTagIds.value;
  versions.value = Array.from({ length: tagIds.value.length }, () => '');
});
</script>

<style scoped>
.btnBox {
  position: relative;
}

.submitBtn {
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
