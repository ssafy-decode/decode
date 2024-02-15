<template>
  <div class="comment-container">
    <div class="content-header" v-if="comment">
      <div class="comment-info-container">
          <span class="comment-nickname">
            <profileRouter :uid="comment.commentWriter.id" :nickName="comment.commentWriter.nickname" />
          </span>
          &nbsp;
          <span class="comment-time">
            {{ comment.createdTime[0] }}년 {{ comment.createdTime[1] }}월 {{ comment.createdTime[2] }}일
          </span>
      </div>
      <div class="comment-delete-container">
        <div v-if="comment.commentWriter.id === userStore.loginUserId" class="editDeleteBox" @click="commentStore.deleteComment(comment.commentId)">
          <span  class="deleteText">댓글삭제</span>
        </div>
      </div>
    </div>
    <div class="comment-content">
      {{ comment.content }}
    </div>
  </div>
</template>

<script setup>
import { useCommentStore } from '@/stores/commentStore';
import { useUserStore } from '@/stores/userStore';
import profileRouter from '@/components/common/profileRouter.vue';

const commentStore = useCommentStore();
const userStore = useUserStore();

const props = defineProps({
  comment: Object,
});
</script>

<style scoped>

.comment-container {
  width: 95%;
  margin: 0;
  padding: 0;
  display: flex;
  flex-direction: column;
}
.content-header {
  width: 100%;
  height: auto;
  display: flex;
  align-items: center;
  margin: 0;
  padding: 0;
}
.comment-info-container {
  width: fit-content;
  margin-left: 15px;
}
.comment-delete-container{
  flex-grow: 1;
  display: flex;
  align-items: flex-end;
  justify-content: flex-end;
}
.comment-content {
  align-items: center;
  background-color: white;
  border: #62c0a6 solid 0.5px;
  border-radius: 35px;
  padding-top: 5px;
  padding-bottom: 5px;
  padding-left: 15px;
  padding-right: 15px;
  font-size: 13px;
}

.listItem {
  border: solid red;
  width: 100%;
  height: auto;
  font-size: large;
  color: #575757;
  display: flex;
  align-items: center;
}

.info {
  font-size: small;
}

.comment-nickname {
  color: #62c0a6;
  font-size: 12px;
}

.comment-time {
  color: #d9d9d9;
  font-size: 10px;
}

.editDeleteBox {
  display: flex;
  justify-content: flex-end;
  margin-top: 0;
  margin-right: 30px;
}

.deleteText {
  color: #12a980;
  text-align: flex-end;
  font-size: 10px;
  font-weight: 800;
  cursor: pointer;
}


</style>
