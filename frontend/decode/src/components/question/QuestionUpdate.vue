<template>
  <v-sheet class="mx-auto createBox" width="1000">
    <h1>질문 수정</h1>
    <br />
    <v-form @submit.prevent="updateQuestion">
      <v-text-field variant="solo" label="질문 제목" v-model.trim="questionTitle"></v-text-field>
      <v-container v-if="numToStr.length > 0">
        <template v-for="(tag, index) in numToStr" :key="index">
          <v-row class="d-flex justify-end">
            <v-col cols="12" sm="6" md="4">
              <v-text-field
                variant="solo"
                class="stackBox"
                bg-color="fff"
                v-model.trim="numToStr[index]"
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
            <v-col cols="12" sm="6" md="4">
              <v-btn @click="removeField(index)">삭제</v-btn>
            </v-col>
          </v-row>
        </template>
      </v-container>
      <v-btn @click="addEmptyFields">추가</v-btn>
      <p class="tagAlert">주의) 태그를 입력할 땐, react.js, vue.js 등은 뒤에 ".js"를 지워주세요</p>

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
import axios from '@/utils/common-axios';

const questionStore = useQuestionStore();
const userStore = useUserStore();
const router = useRouter();
const route = useRoute();

const question = ref({});
const questionId = ref(null);
const questionWriterId = ref(null);
const questionTitle = ref('');
const questionContent = ref('');
const tagList = ref([]); // DB에 있는 태그리스트를 불러와서
// 아래 두 배열에 각각 저장
const tagIds = ref([]);
const versions = ref([]);

// tagIds의 id를 문자열로 변경
const numToStr = ref([]);

const items = questionStore.items;
const reverseItems = questionStore.reverseItems;
const updateEditorContent = function (content) {
  questionContent.value = content;
};

const getOriginalQuestion = function () {
  axios({
    method: 'get',
    url: `question/${route.params.id}`,
  })
    .then((res) => {
      question.value = res.data.data;
      questionWriterId.value = question.value.questionWriter.id;
      questionTitle.value = question.value.title;
      tagList.value = question.value.tagList;
      tagList.value.forEach((item) => {
        tagIds.value.push(item.tagId);
        numToStr.value.push(reverseItems[item.tagId]);
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

// 태그 입력 칸 추가 코드
const addEmptyFields = function () {
  numToStr.value.push('');
  versions.value.push('');
};

// 태그 입력 칸 삭제 코드
const removeField = function (index) {
  numToStr.value.splice(index, 1);
  versions.value.splice(index, 1);
};

const updateQuestion = function () {
  const tags = numToStr.value.map((tag, index) => {
    return {
      tagId: items[tag],
      tagName: tag,
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
    url: `/question`,
    data: data,
    headers: {
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
