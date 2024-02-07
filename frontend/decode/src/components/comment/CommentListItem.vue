<template>
  <div class="bigBox">
    <br />
    <div class="myListItem" v-if="comment">
      <div class="listItem">
        <div class="writerInfo">
          <p>
            <span class="nickname title">
              {{ comment.commentWriter.nickname }}
            </span>
            &nbsp;
            <span class="time info">
              {{ comment.createdTime[0] }}년 {{ comment.createdTime[1] }}월 {{ comment.createdTime[2] }}일
            </span>
          </p>
        </div>
      </div>
      <div class="commentBox listItem">
        {{ comment.content }}
      </div>
      <div class="editDeleteBox" @click="commentStore.deleteComment(comment.commentId)">
        <span v-if="comment.commentWriter.id === userStore.loginUserId" class="deleteText">댓글삭제</span>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useCommentStore } from '@/stores/commentStore';
import { useUserStore } from '@/stores/userStore';

const commentStore = useCommentStore();
const userStore = useUserStore();

defineProps({
  comment: Object,
});
</script>

<style scoped>
div {
  margin: 20px;
}

.bigBox {
  position: relative;
  right: -80px;
}

.commentBox {
  margin-left: 40px;
}

.myListItem {
  position: relative;
  background-color: white;
  border-radius: 35px;
  border: #d9d9d9 2px solid;
}

.listItem {
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

.nickname {
  color: #62c0a6;
}

.title {
  font-size: large;
  font-weight: 800;
}

.time {
  color: #d9d9d9;
}

.editDeleteBox {
  display: flex;
  justify-content: flex-end;
  margin-top: 0;
  margin-right: 30px;
}

.deleteText {
  color: #12a980;
  font-size: small;
  font-weight: 800;
}

.writerInfo {
  position: absolute;
  top: -50px;
  background-color: white;
  border: 15px solid white;
}
</style>
