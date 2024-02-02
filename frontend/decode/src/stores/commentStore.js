import { ref } from 'vue';
import { defineStore } from 'pinia';
import axios from '@/utils/common-axios';
import { useRouter } from 'vue-router';
import { useUserStore } from '@/stores/userStore';

export const useCommentStore = defineStore('comment', () => {
  const userStore = useUserStore();
  const answerId = ref(null);
  const router = useRouter();

  const deleteComment = function (commentId) {
    if (confirm('댓글을 삭제하시겠습니까?')) {
      axios({
        method: 'delete',
        url: `comment/${commentId}`,
        headers: {
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

  return {
    answerId,
    deleteComment,
    updateComment,
  };
});
