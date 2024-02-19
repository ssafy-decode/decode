<template>
  <div class="searchContainer">
    <v-form class="searchBox" @submit.prevent="searchParams(keyword, tagIds)">
      <div style="display: flex; justify-content: center; align-items: center; width: 70%">
        <v-row>
          <v-col cols="9" class="leftBox">
            <span class="text-field-container">
              <div class="search-text-container">
                <v-text-field
                  variant="plain"
                  label="검색 키워드를 입력하세요"
                  class="searchInput"
                  v-model="keyword"
                  bg-color="fff"
                  rounded
                  hide-details="true"
                >
                </v-text-field>
              </div>
              <div class="searchBtn-container">
                <input class="searchBtn" type="submit" value=" " />
              </div>
            </span>
          </v-col>
          <v-col cols="3" class="rightBox">
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
              hide-details="true"
              rounded=""
            ></v-combobox>
          </v-col>
        </v-row>
      </div>
    </v-form>
  </div>
  <div>
    <v-card color="#f3f3f3" elevation="16" width="70%" class="card mx-auto px-5">
      <v-row>
        <v-col :cols="9"> </v-col>

        <v-col :cols="3">
          <div class="btnContainer d-flex justify-end">
            <v-btn class="createBtn" @click="goCreateQuestion()">질문생성</v-btn>
          </div>
        </v-col>
      </v-row>
      <div>
        <QuestionListItem v-for="question in questionStore.questions" :key="question.id" :question="question" />
      </div>
    </v-card>
  </div>
</template>

<script setup>
import { useQuestionStore } from '@/stores/questionStore';
import { ref, onMounted } from 'vue';
import QuestionListItem from './QuestionListItem.vue';
import { useRouter } from 'vue-router';

const filters = ref({
  bookmarked: false,
  curious: false,
});
const router = useRouter();

const goCreateQuestion = function () {
  router.push({ path: `/question-title-create` });
};

const questionStore = useQuestionStore();
const keyword = ref('');
const tagIds = ref([]);

const searchParams = function (keyword, tagIds) {
  const temp = tagIds.map((tag) => items[tag]);
  questionStore.getQuestions(keyword, ...temp);
};

const items = questionStore.items;
</script>

<style scoped>
.card {
  margin-top: 1%;
  border-top-left-radius: 50px;
  border-bottom-left-radius: 50px;
  border-bottom-right-radius: 50px;
  padding-bottom: 20px;
}

.text-field-container {
  display: flex;
  width: 100%;
  height: 85%;
  max-height: 60px;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  background-color: white;
  border-radius: 30px;
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
}

.leftBox {
  padding-left: 2%;
  margin: 0;
  display: flex;
  justify-content: center;
  align-items: center;
}

.rightBox {
  padding-right: 2%;
  margin: 0;
  display: flex;
  justify-content: center;
  align-items: center;
}

.searchInput {
  border-radius: 30px;
  padding-left: 5%;
  width: 100%;
  height: 60px;
  /* background-color: white; */
}

.searchInput input {
  text-align: left;
  /* 커서를 왼쪽으로 이동 */
}

.search-text-container {
  /* width: calc(100%-70px); */
  width: 100%;
}

.searchBtn-container {
  width: 70px;
  display: flex;
  justify-content: center;
}

.searchBtn {
  background-image: url('/public/searchicon.png');
  background-size: contain;
  background-repeat: no-repeat;
  width: 50px;
  height: 50px;
  border: none;
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
  color: #565656;
  background-color: #62c0a6;
  font-weight: 600;
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
