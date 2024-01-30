<template>
  <v-card class="mx-auto d-flex justify-space-between py-5 px-3" width="1550" height="750">
    <v-sheet width="600" style="border: 2px solid black">왼쪽 질문 상세 조회 부분</v-sheet>
    <v-sheet class="mx-auto" width="900" style="border: 2px solid black">
      <h5>답변 작성 중...</h5>
      <br />
      <form @submit.prevent="createAnswer">
        <MyEditor @editor-content-updated="updateEditorContent" />
        <div id="btnBox">
          <v-btn id="submitBtn" type="submit">답변등록</v-btn>
        </div>
      </form>
    </v-sheet>
  </v-card>
</template>

<script setup>
import MyEditor from '@/components/common/MyEditor.vue';
import { ref } from 'vue';
const answerContent = ref('');

const updateEditorContent = function (content) {
  answerContent.value = content;
};

const createAnswer = function () {
  console.log(answerContent.value);
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
</style>
