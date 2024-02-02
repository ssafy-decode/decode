<template>
  <div class="myListItem" @click="goDetail(question.id)">
    <div class="listItem">
      <!-- <p>질문번호: {{ question.id }}</p> -->
      <img src="./questionIcon.png" alt="질문아이콘" />
      <div>
        <p class="info">
          <span class="nickname">
            {{ question.writer.nickname }}
          </span>
          &nbsp;
          <span>
            {{ question.createdTime }}
          </span>
        </p>
        <p class="title">{{ question.title }}</p>
      </div>
      <!-- <p>질문태그: {{ question.tagList }}</p> -->
      <!-- <p>답변개수: {{ question.answerCnt }}</p> -->
      <!-- <p>나도궁금수: {{ question.meTooCnt }}</p> -->
      <!-- <br />
    <hr />
    <br /> -->
    </div>
  </div>
  <br />
</template>

<script setup>
import { useQuestionStore } from '@/stores/questionStore';
import { useRouter } from 'vue-router';

const router = useRouter();
const questionStore = useQuestionStore();

const goDetail = function (questionId) {
  questionStore.questionId = questionId;
  router.push({ path: `/board/${questionId}` });
};

defineProps({
  question: Object,
});
</script>

<style scoped>
img {
  margin-right: 10px;
  width: 70px;
  height: auto;
}

.myListItem {
  background-color: white;
  border-radius: 45px;
}

.myListItem:hover {
  box-shadow: 0px 0px 20px rgba(0, 0, 0, 0.4);
  transition: 0.3s;
}
.listItem {
  width: 100%;
  height: auto;
  font-size: large;
  color: #575757;
  display: flex;
  align-items: center;
}

.info {
  font-size: small;
}

.nickname {
  color: #62c0a6;
}

.title {
  font-size: large;
  font-weight: 800;
}
</style>
