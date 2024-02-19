import { computed, ref } from 'vue';
import { defineStore } from 'pinia';
import axios from '@/utils/common-axios';

export const useQuestionStore = defineStore(
  'useQuestionStore',
  () => {
    const accessToken = ref(null);
    const detailQuestion = ref({});
    const questions = ref([]);
    const gptTitles = ref('');
    const gptTagIds = ref([]);
    const inputQuestionContent = ref();
    const originalContent = ref('');

    const items = {
      python: 1,
      java: 2,
      'c++': 3,
      javascript: 4,
      django: 5,
      spring: 6,
      'spring boot': 7,
      kotlin: 8,
      sql: 9,
      react: 10,
      vue: 11,
      'c#': 12,
    };

    const reverseItems = {
      1: 'python',
      2: 'java',
      3: 'c++',
      4: 'javascript',
      5: 'django',
      6: 'spring',
      7: 'spring boot',
      8: 'kotlin',
      9: 'sql',
      10: 'react',
      11: 'vue',
      12: 'c#',
    };

    const getQuestions = function (keyword = '', tagIds = '') {
      axios({
        method: 'get',
        url: `/question`,
        params: { keyword, tagIds },
        headers: {
          'Access-Control-Allow-Origin': '*',
        },
      })
        .then((res) => {
          questions.value = res.data.data;
        })
        .catch((err) => {});
    };

    return {
      accessToken,
      detailQuestion,
      questions,
      gptTitles,
      gptTagIds,
      inputQuestionContent,
      originalContent,
      items,
      reverseItems,
      getQuestions,
    };
  },
  {
    persist: {
      storage: sessionStorage,
    },
  },
);
