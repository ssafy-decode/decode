import { ref } from 'vue';
import { defineStore } from 'pinia';
import axios from '@/utils/common-axios';
import { useRouter } from 'vue-router';
import { useUserStore } from '@/stores/userStore';

export const useAnswerStore = defineStore('answer', () => {
  const userStore = useUserStore();
  const questionId = ref(null);
  const router = useRouter();
  const gptAnswer = ref('');
  const sofAnswer = ref('');

  const deleteAnswer = function (answerId) {
    if (confirm('답변을 삭제하시겠습니까?')) {
      axios({
        method: 'delete',
        url: `/answer/${answerId}`,
        headers: {
          Authorization: `Bearer ${userStore.accessToken}`,
        },
      })
        .then((res) => {
          router.go(0);
        })
        .catch((err) => {});
    } else {
    }
  };

  const createGptAnswer = function (questionId) {
    let data = {
      questionId: parseInt(questionId),
      userId: parseInt(userStore.loginUserId),
      content: gptAnswer.value,
    };
    axios({
      method: 'post',
      url: `/answer`,
      data: data,
      headers: {
        Authorization: `Bearer ${userStore.accessToken}`,
      },
    })
      .then((res) => {})
      .catch((err) => {});
  };

  const getGptAnswer = function (questionId, questionContent) {
    let data = {
      content: questionContent,
    };
    axios({
      method: 'post',
      url: `/gpt/answer`,
      data: data,
      headers: {
        Authorization: `Bearer ${userStore.accessToken}`,
      },
    })
      .then((res) => {
        gptAnswer.value = '이 답변은 gpt 4.0에 의해 생성된 답변입니다.\n\n' + res.data.data.answer;
        createGptAnswer(questionId);
      })
      .catch((err) => {});
  };

  // Stackoverflow 답변 받으면 생성하는 함수
  const createSofAnswer = function (questionId) {
    let data = {
      questionId: parseInt(questionId),
      userId: parseInt(userStore.loginUserId),
      content: sofAnswer.value,
    };
    axios({
      method: 'post',
      url: `/answer`,
      data: data,
      headers: {
        Authorization: `Bearer ${userStore.accessToken}`,
      },
    })
      .then((res) => {})
      .catch((err) => {});
  };

  // Stackoverflow 답변 받고 생성하는 함수
  const getSofAnswer = function (questionId, questionContent) {
    let data = {
      content: questionContent,
    };
    axios({
      method: 'post',
      url: `/gpt/answer/sof`,
      data: data,
      headers: {
        Authorization: `Bearer ${userStore.accessToken}`,
      },
    })
      .then((res) => {
        sofAnswer.value =
          '올려주신 질문을 기반으로, stackoverflow에서 받아온 유사 질문의 답변입니다.\n\n' + res.data.data.answer;
        createSofAnswer(questionId);
        if (confirm('생성하신 질문에 GPT와 Stackoverflow 답변이 추가되었습니다.\n지금 바로 답변을 확인하실래요?')) {
          router.push({ name: 'question-detail', params: { id: questionId } });
        }
      })
      .catch((err) => {});
  };

  return {
    questionId,
    gptAnswer,
    sofAnswer,
    deleteAnswer,
    createGptAnswer,
    getGptAnswer,
    createSofAnswer,
    getSofAnswer,
  };
});
