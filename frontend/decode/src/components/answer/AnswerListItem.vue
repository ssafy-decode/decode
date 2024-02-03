<template>
  <div v-if="answer">
    <div class="myListItem">
      <div class="listItem">
        <div>
          <p>
            <span class="nickname title">
              {{ answer.answerWriter.nickname }}
            </span>
            &nbsp;
            <span class="time info">
              {{ answer.createdTime }}
            </span>
          </p>
        </div>
      </div>
      <div class="answerBox listItem answerContent">
        {{ answer.content }}
      </div>
      <div class="editDeleteBox">
        <span class="deleteText" @click="answerStore.deleteAnswer(answer.answerId)">답변삭제</span>
      </div>
    </div>
    <!-- <br /> -->
    <div class="commentBox">
      <CommentList :comment-list="answer.commentList" />
      <div class="searchBox">
        <div class="midBox">
          <v-form class="commentInputArea stackBox">
            <v-textarea
              variant="solo-filled"
              auto-grow
              rows="1"
              class="searchInput"
              v-model="commentContent"
              clearable
              label="댓글을 작성해주세요"
            >
              <template #append-inner>
                <v-btn type="submit" class="searchBtn" size="medium" @click="createComment">
                  <img src="/searchicon.png" alt="검색아이콘" style="width: 40px; height: auto" />
                </v-btn>
              </template>
            </v-textarea>
          </v-form>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import CommentList from '@/components/comment/CommentList.vue';
import { useAnswerStore } from '@/stores/answerStore';
import { useUserStore } from '@/stores/userStore';
import { useRouter } from 'vue-router';
import { ref } from 'vue';
import axios from '@/utils/common-axios';

const router = useRouter();

const answerStore = useAnswerStore();
const userStore = useUserStore();

const props = defineProps({
  answer: Object,
});

const commentContent = ref('');

const createComment = function () {
  let data = {
    content: commentContent.value,
    userId: userStore.loginUserId,
    answerId: props.answer.answerId,
  };

  console.log('data내용!!!!', data);

  axios({
    method: 'post',
    url: `/comment`,
    data: data,
    headers: {
      Authorization: `Bearer ${userStore.accessToken}`,
    },
  })
    .then((res) => {
      console.log('댓글 생성됨');
      router.go(0);
    })
    .catch((err) => {
      console.log(err);
      console.log('댓글 생성 오류');
    });
};
</script>

<style scoped>
div {
  margin: 20px;
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
  margin-left: 10px;
  padding: 0px;
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
  background-color: white;
  border-radius: 35px;
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
}

.deleteText {
  color: #12a980;
  font-size: small;
  font-weight: 800;
}

.searchBox {
  position: relative;
  right: -18px;
  padding-left: 127px;
  margin: 0px;
}

.midBox {
  padding: 0px;
  margin: 0px;
}

.searchInput {
  padding: 0px;
  margin: 0px;
}

.searchBtn {
  position: relative;
  border-radius: 30px;
}

.stackBox ::v-deep(.v-field) {
  border-radius: 30px;
}
</style>
