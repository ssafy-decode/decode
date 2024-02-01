<template>
  <div class="wholeContainer">
    <h1 style="text-align: center">질문 게시판</h1>
    <br />
    <div class="btnContainer d-flex justify-end">
      <v-btn class="createBtn" @click="goCreateQuestion()">질문등록</v-btn>
    </div>
    <br />

    <div id="app">
      <v-responsive max-width="400" class="mx-auto mb-4"> </v-responsive>
      <v-card elevation="16" max-width="60%" class="mx-auto px-5 rounded-xl">
        <v-row>
          <v-col :cols="12">
            <!-- <v-col :cols="8"> -->
            <v-list-item>
              <v-list-item-title class="text-center"> 질 문 </v-list-item-title>
            </v-list-item>
          </v-col>

          <!-- <v-col :cols="4">
            <v-list-item :key="'author'">
              <v-list-item-title class="text-center"> 작 성 </v-list-item-title>
            </v-list-item>
          </v-col> -->
        </v-row>
        <QuestionListItem v-for="question in questionStore.questions" :key="question.id" :question="question" />
      </v-card>
    </div>
  </div>
</template>

<script setup>
import { useQuestionStore } from '@/stores/questionStore';
import { ref, computed } from 'vue';
import QuestionListItem from './QuestionListItem.vue';
import { useRouter } from 'vue-router';

const router = useRouter();
// const model = ref(false);

const goCreateQuestion = function () {
  router.push({ path: `/question-title-create` });
};

// const switchLabel = computed(() => {
//   return model.value ? '코드로 검색' : '키워드로 검색';
// });

// const benched = ref(0);

const questionStore = useQuestionStore();
const keyword = ref('');
const tagIds = ref('');

const searchParams = function (keyword, tagIds) {
  questionStore.getQuestions(keyword, tagIds);
};

const select = ref([]);
const items = questionStore.items;
</script>

<style scoped>
.wholeContainer {
  margin-bottom: 40px;
}

input {
  border-left: 1px solid black;
}

.chips-switch-container {
  align-items: center;
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

.searchBox {
  border: 2px solid black;
  height: 70px;
  border-radius: 30px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding-left: 10px;
}

.searchInput {
  width: 50%;
  height: 60px;
}

.searchBtn {
  position: relative;
  background-color: #62c0a6;
  margin-right: 0px;
  border-radius: 30px;
}

.stackBox ::v-deep(.v-field) {
  border-radius: 30px;
}

.btnContainer {
  width: 80%;
}

.createBtn {
  border-radius: 30px;
  background-color: #62c0a6;
}
</style>
