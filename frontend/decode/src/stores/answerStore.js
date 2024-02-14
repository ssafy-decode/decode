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
  // const loading = ref(false);

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
    // if (confirm('포인트를 사용하여 GPT 4.0의 답변을 받을까요?')) {
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
        // alert('답변 목록에 GPT 답변이 추가되었습니다.\n새로고침으로 GPT가 생성한 답변을 확인해보세요!');
        if (confirm('생성하신 질문에 GPT 답변이 추가되었습니다.\n지금 바로 답변을 확인하실래요?')) {
          router.push({ name: 'question-detail', params: { id: questionId } });
        }
      })
      .catch((err) => {});
    // } else {
    // }
  };

  return {
    questionId,
    gptAnswer,
    // loading,
    deleteAnswer,
    createGptAnswer,
    getGptAnswer,
  };
});
