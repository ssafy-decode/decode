<template>
  <div class="answer-list-box">
    <AnswerListItem
      v-for="answer in answerList"
      :key="answer.answerId"
      :answer="answer"
      :recommended-answer-list="recommendedAnswerList"
      :adopted-answer-list="adoptedAnswerList"
    />
  </div>
</template>

<script setup>
import AnswerListItem from '@/components/answer/AnswerListItem.vue';
import { ref, onMounted } from 'vue';

import { storeToRefs } from 'pinia';
import { useAdoptStore } from '@/stores/adoptStore';
const adoptStore = useAdoptStore();
// const { handleAdoptList: adoptList } = storeToRefs(adoptStore);
const props = defineProps({
  answerList: Array,
});

const recommendedAnswerList = ref({});
const adoptedAnswerList = ref({});

onMounted(() => {
  // answerList 배열의 각 객체를 순회하면서 recommendedAnswerList를 초기화합니다.
  props.answerList.forEach((answer) => {
    // answer 객체에서 answerId 값을 추출합니다.
    const answerId = answer.answerId;
    // answerId를 키로 하고, false를 값으로 갖는 새로운 속성을 recommendedAnswerList 객체에 추가합니다.
    recommendedAnswerList.value[answerId] = false;
  });

  // answerList 배열의 각 객체를 순회하면서 adoptedAnswerList 초기화합니다.
  props.answerList.forEach((answer) => {
    // answer 객체에서 answerId 값을 추출합니다.
    const answerId = answer.answerId;
    // answerId를 키로 하고, false를 값으로 갖는 새로운 속성을 adoptedAnswerList 객체에 추가합니다.
    adoptedAnswerList.value[answerId] = false;
  });
});
</script>

<style scoped>
.answer-list-box {
  border-radius: 35px;
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: flex-end;
}
</style>
