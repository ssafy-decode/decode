import { ref } from 'vue';
import { defineStore } from 'pinia';
import axios from 'axios';
import { useRouter } from 'vue-router';

export const useAnswerStore = defineStore('answer', () => {
  const URL = process.env.VUE_APP_BACKEND_URL;
  const questionId = ref(null);

  return {
    URL,
    questionId,
  };
});
