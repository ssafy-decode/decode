import { ref } from 'vue';
import { defineStore } from 'pinia';
import axios from 'axios';
import { useRouter } from 'vue-router';

export const useQuestionStore = defineStore('question', () => {
  const URL = process.env.VUE_APP_BACKEND_URL;
  const router = useRouter();
  const accessToken = ref(null);
  const questions = ref([]);
  const gptTitles = ref([]);
  const gptTagIds = ref([]);

  const getQuestions = function (keyword = '', tagIds = '') {
    axios({
      method: 'get',
      url: `${URL}/question`,
      params: { keyword, tagIds },
      headers: {
        'Access-Control-Allow-Origin': '*', // 이 부분을 추가하여 CORS 정책 우회
        Authorization: `${accessToken}`, // 만약 인증이 필요하다면 주석 해제
      },
    })
      .then((res) => {
        console.log(res.data.data);
        questions.value = res.data.data;
      })
      .catch((err) => {
        console.log(err);
        console.log('게시글 목록 조회 오류');
      });
  };

  return {
    URL,
    accessToken,
    questions,
    gptTitles,
    gptTagIds,
    getQuestions,
  };
});
