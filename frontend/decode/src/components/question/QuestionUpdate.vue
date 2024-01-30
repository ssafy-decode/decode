<template>
  <v-sheet class="mx-auto createBox" width="1000">
    <h1>질문 수정</h1>
    <br />
    <v-form @submit.prevent="updateQuestion">
      <v-text-field variant="solo" label="질문 제목" v-model.trim="questionTitle"></v-text-field>
      <v-container>
        <template v-for="(tag, index) in tagIds" :key="index">
          <v-row class="d-flex justify-end">
            <v-col cols="12" sm="6" md="4">
              <v-text-field
                variant="solo"
                class="stackBox"
                bg-color="fff"
                v-model.trim="reverseItems[tag]"
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
      <UpdateEditor @editor-content-updated="updateEditorContent" />
      <div class="btnBox">
        <v-btn class="submitBtn" type="submit">질문수정</v-btn>
      </div>
    </v-form>
  </v-sheet>
</template>

<script setup>
import UpdateEditor from '@/components/common/UpdateEditor.vue';
import { useQuestionStore } from '@/stores/questionStore';
import { useUserStore } from '@/stores/userStore';
import { useRouter, useRoute } from 'vue-router';
import { ref, onMounted } from 'vue';
import axios from 'axios';

const questionStore = useQuestionStore();
const userStore = useUserStore();
const router = useRouter();
const route = useRoute();

const question = ref({});
const questionId = ref(null);
const questionWriterId = ref(null);
const questionTitle = ref('');
const questionContent = ref('');
const tagList = ref([]);

const tagIds = ref([]);
const versions = ref([]);

const items = questionStore.items;
const reverseItems = questionStore.reverseItems;
const updateEditorContent = function (content) {
  questionContent.value = content;
};

const getOriginalQuestion = function () {
  axios({
    method: 'get',
    url: `${questionStore.URL}/question/${route.params.id}`,
    headers: {
      'Access-Control-Allow-Origin': '*',
      Authorization: `${userStore.accessToken}`,
    },
  })
    .then((res) => {
      question.value = res.data.data;
      questionWriterId.value = question.value.questionWriter.id;
      questionTitle.value = question.value.title;
      tagList.value = question.value.tagList;
      tagList.value.forEach((item) => {
        tagIds.value.push(item.tagId);
        versions.value.push(item.version);
      });
    })
    .catch((err) => {
      console.log(err);
      console.log('기존 질문 내용 조회 오류');
    });
};

onMounted(() => {
  questionId.value = route.params.id;
  getOriginalQuestion(); // 기존 변수들이 담김
});

const updateQuestion = function () {
  const tags = tagIds.value.map((tagId, index) => {
    return {
      tagId: tagId,
      tagName: questionStore.reverseItems[tagId],
      version: versions.value[index],
    };
  });

  let data = {
    userId: userStore.loginUserId,
    questionId: parseInt(route.params.id),
    title: questionTitle.value,
    content: questionContent.value,
    tagList: tags,
  };
  axios({
    method: 'patch',
    url: `${questionStore.URL}/question`,
    data: data,
    headers: {
      'Content-Type': 'application/json',
      'Access-Control-Allow-Origin': '*',
      Authorization: `Bearer ${userStore.accessToken}`,
    },
  })
    .then((res) => {
      console.log('질문 수정 완료');
      router.push({ path: `/board/${questionId.value}` });
    })
    .catch((err) => {
      console.log(err);
      console.log('질문 수정 오류');
    });
};
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
