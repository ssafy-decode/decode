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
              {{ comment.createdTime }}
            </span>
          </p>
        </div>
      </div>
      <div class="commentBox listItem">
        {{ comment.content }}
      </div>
      <div class="editDeleteBox">
        <span class="deleteText" @click="commentStore.deleteComment(comment.commentId)">답변삭제</span>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useCommentStore } from '@/stores/commentStore';

const commentStore = useCommentStore();

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

.btn {
  margin-left: 10px;
  margin-right: 10px;
}

.answerBox {
  display: flex;
  justify-content: space-between;
}

.commentBox {
  margin-left: 40px;
}

.commentInputArea {
  margin: 0px;
  display: flex;
  justify-content: center;
  align-items: center;
}

.img {
  width: 75px;
  height: 75px;
}
.metooImg {
  width: 60px;
  height: 70px;
  margin-right: 5px;
}
.answerCountImg {
  margin-right: 10px;
  height: 45px;
}

.myListItem {
  position: relative;
  background-color: white;
  border-radius: 35px;
  border: #d9d9d9 2px solid;
}
.myListItem2 {
  background-color: white;
  border-radius: 35px;
  display: flex;
  justify-content: space-between;
  align-items: center;
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

.answerContent {
  border: #62c0a6 solid 2px;
  border-radius: 24px;
  padding: 40px;
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
