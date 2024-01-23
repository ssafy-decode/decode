<template>
  <div>
    <!-- <div>
      <div class="text-center" style="display: flex; flex-direction: column; align-items: center">
        <div class="search-container" style="width: 60%">
          <span style="position: relative; margin: auto; display: flex; align-items: center">
            <textarea
              class="view"
              :placeholder="switchLabel"
              :style="{
                height: model ? '300px' : '25px',
                width: '100%',
                border: '1px solid #bbb',
                borderRadius: '8px',
                fontSize: '15px',
              }"
            ></textarea>
            <input
              type="image"
              src="https://s3.ap-northeast-2.amazonaws.com/cdn.wecode.co.kr/icon/search.png"
              alt="검색"
              style="
                position: absolute;
                width: 15px;
                top: 50%;
                transform: translateY(-50%);
                right: 10px;
                margin: 0;
                cursor: pointer;
              "
            />
          </span>
        </div>
        <div class="chips-switch-container" style="display: flex; align-items: center; width: 60%">
          <span style="display: flex; align-items: center">
            <v-if ="search">
              <v-chip closable variant="elevated" style="background-color: cornflowerblue"> python </v-chip>
              <v-chip closable variant="elevated" style="background-color: orangered"> java </v-chip>
              <v-chip closable variant="elevated" style="background-color: gold"> C++ </v-chip>
              <v-chip closable variant="elevated" style="background-color: mediumorchid"> javascript </v-chip>
            </v-if>
          </span>
          <v-switch inset :label="switchLabel" v-model="model" style="margin-top: 3%"></v-switch>
        </div>
      </div>

      <div id="app">
        <div id="inspire">
          <div>
            <v-responsive max-width="400" class="mx-auto mb-4"> </v-responsive>

            <v-card elevation="16" max-width="60%" class="mx-auto">
              <v-row>
                <v-col :cols="8">
                  <v-list-item :key="'question'">
                    <v-list-item-content>
                      <v-list-item-title class="text-center"> 질 문 </v-list-item-title>
                    </v-list-item-content>
                  </v-list-item>
                </v-col>

                <v-col :cols="4">
                  <v-list-item :key="'author'">
                    <v-list-item-content>
                      <v-list-item-title class="text-center"> 작 성 </v-list-item-title>
                    </v-list-item-content>
                  </v-list-item>
                </v-col>
              </v-row>

              <v-virtual-scroll id="virtualScroll" :bench="benched" :items="items" height="500" item-height="50">
                <template v-slot:default="{ item }"> -->
    <!-- Q List Item 들어갈 부분 -->
    <input type="text" v-model="keyword" />
    <input type="text" v-model="tagIds" />
    <v-btn @click="searchParams(keyword, tagIds)">검색</v-btn>
    <!-- <v-btn @click="myFunc(keyword, tagIds)">검색</v-btn> -->
    <QuestionListItem v-for="question in store.questions" :key="question.id" :question="question" />
    <!-- for문으로 -->
    <!-- <v-divider></v-divider>
                </template>
              </v-virtual-scroll>
            </v-card>
          </div>
        </div>
      </div>
    </div> -->
  </div>
</template>

<script setup>
import { useQuestionStore } from '@/stores/questionStore';
import { ref, computed } from 'vue';
import QuestionListItem from './QuestionListItem.vue';

const model = ref(false);

const switchLabel = computed(() => {
  return model.value ? '코드로 검색' : '키워드로 검색';
});

const benched = ref(0);

// const items = computed(() => {
//   return Array.from({ length: 7000 }, (k, v) => v + 1);
// });

// 진영 작성
const store = useQuestionStore();
const keyword = ref('');
const tagIds = ref('');

const myFunc = function (keyword, tagIds) {
  console.log('keyword is: ', keyword);
  console.log('tag id is: ', tagIds);
};

const searchParams = function (keyword, tagIds) {
  store.getQuestions(keyword, tagIds);
};
</script>

<style scoped>
.chips-switch-container {
  align-items: center;
}

span {
  margin: 5px;
}

/* 스크롤바 안 보이게 할 거면 주석 제거 */
/* #virtualScroll::-webkit-scrollbar {
  display: none;
} */

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
</style>
