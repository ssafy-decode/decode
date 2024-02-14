import { ref, computed } from 'vue';
import { defineStore } from 'pinia';
import axios from '@/utils/common-axios';
import { useUserStore } from './userStore';

const useBookmarkStore = defineStore(
  'useBookmarkStore',
  () => {
    const userStore = useUserStore();

    const bookmarkList = ref([]);
    const isBookmarked = ref(false);

    // 로그인 유저의 북마크 중인 질문 번호의 목록을 불러옴
    const setBookmarkList = async (questionId, userId) => {
      await axios.get(`/bookmark/${userId}`).then((res) => {
        bookmarkList.value = [];
        isBookmarked.value = false;
        res.data.data.forEach((question) => {
          bookmarkList.value.push(question.id);
          if (parseInt(questionId) === parseInt(question.id)) {
            isBookmarked.value = true;
          }
        });
      });
    };

    const addBookmark = async (userId, questionId) => {
      let data = {
        userId,
        questionId,
      };
      await axios
        .post(`/bookmark`, data, {
          headers: {
            Authorization: `Bearer ${userStore.accessToken}`,
          },
        })
        .then((res) => {
          isBookmarked.value = true;
          alert('북마크에 추가했습니다!');
        })
        .catch((error) => {
          alert('북마크 추가에 실패했습니다.');
        });
    };

    const deleteBookmark = async (questionId) => {
      await axios
        .delete(`/bookmark/${questionId}`, {
          headers: {
            Authorization: `Bearer ${userStore.accessToken}`,
          },
        })
        .then((res) => {
          isBookmarked.value = false;
          alert('북마크에서 제외되었습니다.');
        })
        .catch((error) => {
          alert('북마크 취소 실패');
        });
    };

    // // computed
    const handleBookmarkList = computed(() => bookmarkList.value);
    const handleBookmarkState = computed(() => isBookmarked.value);

    // 반환
    return {
      bookmarkList,
      isBookmarked,
      setBookmarkList,
      addBookmark,
      deleteBookmark,
      handleBookmarkList,
      handleBookmarkState,
    };
  },
  {
    persist: true,
  },
);

export { useBookmarkStore };
