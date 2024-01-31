import { ref } from 'vue';
import { defineStore } from 'pinia';
import axios from 'axios';
import { useRouter } from 'vue-router';
import { useUserStore } from '@/stores/userStore';

export const useAnswerStore = defineStore('answer', () => {
  const userStore = useUserStore();
  const URL = process.env.VUE_APP_BACKEND_URL;
  const questionId = ref(null);
  const router = useRouter();

  const deleteAnswer = function (answerId) {
    if (confirm('답변을 삭제하시겠습니까?')) {
      axios({
        method: 'delete',
        url: `${URL}/answer/${answerId}`,
        headers: {
          'Access-Control-Allow-Origin': '*',
          Authorization: `Bearer ${userStore.accessToken}`,
        },
      })
        .then((res) => {
          console.log('답변 삭제됨');
          router.go(0);
        })
        .catch((err) => {
          console.log(err);
          console.log('답변 삭제 오류');
        });
    } else {
    }
  };
  const updateAnswer = function (answerId) {
    console.log(answerId);
  };

  return {
    URL,
    questionId,
    deleteAnswer,
    updateAnswer,
  };
});
