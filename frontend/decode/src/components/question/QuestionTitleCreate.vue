<template v-slot:actions>
  <div>
    <v-sheet class="mx-auto createBox card">
      <v-form @submit.prevent="createQuestionTitle">
        <v-text-field class="stackBox" disabled="true" variant="solo">
          <div>제목 자동 생성 예정</div>
          <template #prepend-inner>
            <img src="/questionIcon2.png" alt="검색아이콘" style="width: 40px; height: 40px" />
          </template>
        </v-text-field>
        <ErrorEditor @editor-content-updated="updateEditorContent" />
        <br />
        <div id="btnBox">
          <v-btn class="submitBtn" @click="goCreate()">제목 직접 생성</v-btn>
          <v-btn class="submitBtn" type="submit" :loading="loading" @click="load">제목 자동 생성</v-btn>
        </div>
      </v-form>
    </v-sheet>
  </div>
</template>

<script setup>
import ErrorEditor from '@/components/common/ErrorEditor.vue';
import { useQuestionStore } from '@/stores/questionStore';
import { useUserStore } from '@/stores/userStore';
import { useRouter } from 'vue-router';
import { ref } from 'vue';
import axios from '@/utils/common-axios';

const loading = ref(false);

const load = () => {
  loading.value = true;
  setTimeout(() => (loading.value = false), 5000);
};

const questionStore = useQuestionStore();
const userStore = useUserStore();
const router = useRouter();

const inputContent = ref('');

const updateEditorContent = function (content) {
  inputContent.value = content;
};

const goCreate = function () {
  router.push({ path: `/question-create` });
};

const createQuestionTitle = function () {
  if (inputContent.value) {
    let data = {
      content: inputContent.value,
    };
    axios({
      method: 'post',
      url: `/gpt`,
      data: data,
      headers: {
        Authorization: `Bearer ${userStore.accessToken}`,
      },
    })
      .then((res) => {
        questionStore.inputQuestionContent = inputContent.value;
        questionStore.gptTitles = res.data.data.titles;
        questionStore.gptTagIds = res.data.data.tagIds;
        router.push({ name: 'question-create' });
      })
      .catch((err) => {});
  } else {
    alert('에러 내용을 입력하세요!');
  }
};
</script>

<style scoped>
#btnBox {
  display: flex;
  justify-content: flex-end;
}

.submitBtn {
  margin-left: 20px;
  margin-top: 20px;
  border-radius: 40px;
  background-color: #62c0a6;
  box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.4);
  font-weight: 600;
}

.createBox {
  margin-bottom: 150px;
}

.card {
  width: 1000px;
  border-top-left-radius: 50px;
  border-bottom-left-radius: 50px;
  border-bottom-right-radius: 50px;
  padding: 30px 30px 70px;
  background-color: #f4f4f4;
  box-shadow: 0px 0px 20px rgba(0, 0, 0, 0.4);
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

.stackBox ::v-deep(.v-field) {
  border-radius: 30px;
}
</style>
