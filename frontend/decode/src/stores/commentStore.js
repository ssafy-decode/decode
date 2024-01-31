import { ref } from 'vue';
import { defineStore } from 'pinia';
import axios from 'axios';
import { useRouter } from 'vue-router';
import { useUserStore } from '@/stores/userStore';

export const useCommentStore = defineStore('comment', () => {
  const userStore = useUserStore();
  const URL = process.env.VUE_APP_BACKEND_URL;
  const answerId = ref(null);
  const router = useRouter();

  const deleteComment = function (commentId) {
    if (confirm('댓글을 삭제하시겠습니까?')) {
      axios({
        method: 'delete',
        url: `${URL}/comment/${commentId}`,
        headers: {
          'Access-Control-Allow-Origin': '*',
          Authorization: `Bearer ${userStore.accessToken}`,
        },
      })
        .then((res) => {
          console.log('댓글 삭제됨');
          router.go(0);
        })
        .catch((err) => {
          console.log(err);
          console.log('댓글 삭제 오류');
        });
    } else {
    }
  };

  const updateComment = function (commentId) {
    console.log(commentId);
  };

  const createComment = function (data) {
    // userId, answerId, content 필요
    console.log(data);
    // 작성 중
    // axios({
    //   method: 'post',
    //   url: `${URL}/comment/`,
    //   data: data,
    //   headers: {
    //     'Access-Control-Allow-Origin': '*',
    //     Authorization: `Bearer ${userStore.accessToken}`,
    //   },
    // })
    //   .then((res) => {
    //     console.log('댓글 삭제됨');
    //     router.go(0);
    //   })
    //   .catch((err) => {
    //     console.log(err);
    //     console.log('댓글 삭제 오류');
    //   });
  };

  return {
    URL,
    answerId,
    deleteComment,
    updateComment,
    createComment,
  };
});
