<template>
  <div class="searchContainer">
    <v-form class="searchBox" @submit.prevent="searchParams(keyword, tagIds)">
      <v-row>
        <v-col cols="4" sm="2" md="2"></v-col>
        <v-col cols="6" sm="5" md="6" class="leftBox">
          <v-text-field
            variant="plain"
            label="검색 키워드를 입력하세요"
            class="searchInput"
            v-model="keyword"
            bg-color="fff"
            clearable
          >
          </v-text-field>
          <input class="searchBtn" type="submit" value=" " />
        </v-col>

        <v-col cols="1" sm="5" md="2" class="rightBox">
          <v-combobox
            variant="solo"
            class="stackBox"
            bg-color="fff"
            v-model="tagIds"
            :items="Object.keys(items)"
            placeholder="ex) java, spring boot, sql"
            label="기술 스택"
            multiple
            chips
            clearable
          ></v-combobox>
        </v-col>
        <v-col cols="1" sm="0" md="2"></v-col>
      </v-row>
    </v-form>
  </div>
  <div class="btnContainer d-flex justify-end">
    <v-btn class="createBtn" @click="goCreateQuestion()">질문등록</v-btn>
  </div>

  <div>
    <v-card color="#f3f3f3" elevation="16" max-width="70%" class="card mx-auto px-5">
      <v-row>
        <v-col :cols="9">
          <v-list-item class="titleBox">
            <v-list-item-title class="text-center title"> 질 문 </v-list-item-title>
          </v-list-item>
        </v-col>

        <v-col :cols="3">
          <v-list-item class="titleBox">
            <v-list-item-title class="text-center title"> 작 성 </v-list-item-title>
          </v-list-item>
        </v-col>
      </v-row>
      <QuestionListItem v-for="question in questionStore.questions" :key="question.id" :question="question" />
    </v-card>
  </div>
</template>

<script setup>
import { useQuestionStore } from '@/stores/questionStore';
import { ref } from 'vue';
import QuestionListItem from './QuestionListItem.vue';
import { useRouter } from 'vue-router';

const router = useRouter();

const goCreateQuestion = function () {
  router.push({ path: `/question-title-create` });
};

const questionStore = useQuestionStore();
const keyword = ref('');
const tagIds = ref([]);

const searchParams = function (keyword, tagIds) {
  questionStore.getQuestions(keyword, ...tagIds);
};

const items = questionStore.items;
</script>

<style scoped>
.card {
  border-top-left-radius: 50px;
  border-bottom-left-radius: 50px;
  border-bottom-right-radius: 50px;
  padding-bottom: 20px;
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

.searchContainer {
  position: sticky;
  top: 0px;
  z-index: 9;
}

.searchBox {
  background-color: #62c0a6;
  border-bottom-right-radius: 40px;
  height: 70px;
  width: 100%;
  display: flex;
  justify-content: center;
  /* position: relative; */
}

.leftBox {
  padding-top: 17px;
  margin: 0px;
  position: relative;
}

.rightBox {
  padding-top: 19px;
  margin: 0px;
}

.searchInput {
  width: 100%;
  height: 60px;
  background-color: white;
}

.searchBtn {
  position: absolute;
  top: 23px;
  right: 1.5%;
  background-image: url('/public/searchicon.png');
  background-size: contain;
  background-repeat: no-repeat;
  width: 50px;
  height: 50px;
  border: none;
}

.stackBox ::v-deep(.v-field) {
  border-radius: 30px;
}

.btnContainer {
  width: 100%;
  margin: 10px 0px 20px;
  padding: 15px;
  position: relative;
}

.createBtn {
  position: absolute;
  top: 0px;
  right: 16%;
  border-radius: 30px;
  background-color: #62c0a6;
  font-weight: 800;
}

.titleBox {
  border-radius: 30px;
  background-color: #d9d9d9;
  margin: 40px 40px;
  height: 55px;
  color: #2f423d;
}

.title {
  font-size: x-large;
  font-weight: 900;
}
</style>
