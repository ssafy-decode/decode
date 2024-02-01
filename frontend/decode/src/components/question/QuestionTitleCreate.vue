<template>
  <div>
    <v-sheet class="mx-auto createBox" width="1000">
      <h1>제목 생성을 위한 Error 입력 창</h1>
      <br />
      <v-form @submit.prevent="createQuestionTitle">
        <v-text-field disabled="true" variant="solo" label="제목 자동 생성 전.."></v-text-field>
        <ErrorEditor @editor-content-updated="updateEditorContent" />
        <br />
        <div id="btnBox">
          <v-btn id="submitBtn" type="submit">질문 제목 생성</v-btn>
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

const questionStore = useQuestionStore();
const userStore = useUserStore();
const router = useRouter();

const inputContent = ref('');

const updateEditorContent = function (content) {
  inputContent.value = content;
};

const createQuestionTitle = function () {
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
      console.log('질문 제목 생성 완료');
      questionStore.gptTitles.value = res.data.data.titles;
      questionStore.gptTagIds.value = res.data.data.tagIds;
      router.push({ name: 'question-create' });
    })
    .catch((err) => {
      console.log('질문 생성 오류');
      console.log(err);
    });
};
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
