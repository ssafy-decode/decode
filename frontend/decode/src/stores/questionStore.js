import { ref } from 'vue';
import { defineStore } from 'pinia';
import axios from 'axios';
import { useRouter } from 'vue-router';

export const useQuestionStore = defineStore('question', () => {
  const API_URL = process.env.VUE_APP_BACKEND_URL;
  // const API_URL = 'http://localhost:80/decode'; // 테스트할 때는 로컬로
  const router = useRouter();
  const accessToken = ref(null);
  const questions = ref([]);

  const getQuestions = function (keyword = '', tagIds = '') {
    axios({
      method: 'get',
      url: `${API_URL}/question`,
      params: { keyword, tagIds },
      headers: {
        'Access-Control-Allow-Origin': '*', // 이 부분을 추가하여 CORS 정책 우회
        // 'Authorization': `${accessToken.value}`, // 만약 인증이 필요하다면 주석 해제
      },
    })
      .then((res) => {
        // console.log(res);
        console.log(res.data.data);
        questions.value = res.data.data;
      })
      .catch((err) => {
        console.log(err);
        console.log('게시글 목록 조회 오류');
      });
  };

  return {
    API_URL,
    accessToken,
    questions,
    getQuestions,
  };
});
