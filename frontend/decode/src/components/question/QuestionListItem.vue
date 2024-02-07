<template>
  <v-row>
    <v-col :cols="9">
      <div class="myListItem" @click="goDetail(question.id)">
        <div class="listItem">
          <img class="img" src="./questionIcon.png" alt="질문아이콘" />
          <div>
            <p class="info">
              <span class="nickname">
                {{ question.writer.nickname }}
              </span>
              &nbsp;
              <span class="time">
                {{ question.createdTime }}
              </span>
            </p>
            <p class="title">
              {{ question.title && question.title.length > 80 ? question.title.slice(0, 80) + '...' : question.title }}
            </p>
          </div>
        </div>
      </div>
    </v-col>
    <v-col :cols="3">
      <div class="myListItem2">
        <img class="img" src="./answerIcon.png" aslt="답변 아이콘" />
        <p>답변: {{ question.answerCnt }}</p>
        <p>나도궁금: {{ question.meTooCnt }}</p>
      </div>
    </v-col>
  </v-row>
</template>

<script setup>
import { useRouter } from 'vue-router';

const router = useRouter();

const goDetail = function (questionId) {
  router.push({ path: `/board/${questionId}` });
};

defineProps({
  question: Object,
});
</script>

<style scoped>
.img {
  width: 75px;
  height: 75px;
}

.myListItem {
  background-color: white;
  border-radius: 35px;
}
.myListItem2 {
  background-color: white;
  border-radius: 35px;
  display: flex;
  justify-content: space-between;
  align-items: center;
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

.time {
  color: #d9d9d9;
}
</style>
