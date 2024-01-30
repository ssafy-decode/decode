import { ref } from 'vue';
import { defineStore } from 'pinia';
import axios from 'axios';
import { useRouter } from 'vue-router';
import { useUserStore } from '@/stores/userStore';

export const useQuestionStore = defineStore('question', () => {
  const URL = process.env.VUE_APP_BACKEND_URL;
  const router = useRouter();
  const accessToken = ref(null);
  const questions = ref([]);
  const gptTitles = ref([]);
  const gptTagIds = ref([]);
  const userStore = useUserStore();
  const originalContent = ref('');
  const items = {
    python: 0,
    java: 1,
    'C++': 2,
    javascript: 3,
    django: 4,
    spring: 5,
    'spring boot': 6,
    kotlin: 7,
    sql: 8,
    react: 9,
    vue: 10,
    'C#': 11,
  };

  const reverseItems = {
    0: 'python',
    1: 'java',
    2: 'C++',
    3: 'javascript',
    4: 'django',
    5: 'spring',
    6: 'spring boot',
    7: 'kotlin',
    8: 'sql',
    9: 'react',
    0: 'vue',
    11: 'C#',
  };

  const getQuestions = function (keyword = '', tagIds = '') {
    axios({
      method: 'get',
      url: `${URL}/question`,
      params: { keyword, tagIds },
      headers: {
        'Access-Control-Allow-Origin': '*',
      },
    })
      .then((res) => {
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
    originalContent,
    items,
    reverseItems,
    getQuestions,
  };
});
