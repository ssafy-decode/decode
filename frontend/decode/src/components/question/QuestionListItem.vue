<template>
  <div style="height: 70px; display: flex; align-items: center">
    <div class="item-container" @click="goDetail(question.id)">
      <v-row style="margin: 0; padding: 0; height: 100%">
        <v-col :cols="10" style="margin: 0; padding: 0; display: flex; align-items: center; width: 100%; height: 100%">
          <div class="myListItem">
            <div class="listItem">
              <div class="img-container">
                <img class="q-img" src="./questionIcon.png" alt="질문아이콘" />
              </div>
              <div style="overflow: hidden">
                <p class="info">
                  <span class="nickname">
                    {{ question.writer.nickname }}
                  </span>
                  &nbsp;
                  <span class="time">
                    {{ question.createdTime[0] }}년 {{ question.createdTime[1] }}월 {{ question.createdTime[2] }}일
                  </span>
                </p>
                <p class="title" style="white-space: nowrap; overflow: hidden; text-overflow: ellipsis; width: 100%">
                  {{ question.title }}
                </p>
              </div>
            </div>
          </div>
        </v-col>
        <v-col :cols="2" style="margin: 0; padding: 0; display: flex; align-items: center; width: 100%; height: 100%">
          <div class="myListItem2">
            <!-- 답변 수 -->
            <div
              class="cntBox"
              style="position: relative; width: 50%%; display: flex; justify-content: center; align-items: center"
            >
              <div style="width: 100%; display: flex; justify-content: center; align-items: center">
                <img
                  style="width: 50%; height: auto transform: translateY(-30%);"
                  src="./answerCountIcon.png"
                  alt="답변 아이콘"
                />
              </div>
              <div style="position: absolute; transform: translateY(-15%); color: #649591">
                + {{ question.answerCnt }}<br />
              </div>
            </div>
            <!-- 답변 수 -->

            <!-- 나궁 수 -->
            <div
              class="cntBox"
              style="position: relative; width: 50%; display: flex; align-items: center; justify-content: center"
            >
              <div style="width: 100%; display: flex; align-items: center; justify-content: center">
                <img style="width: 80%; height: auto" src="./metoopeople.png" alt="답변 아이콘" />
              </div>
              <div
                style="
                  display: flex;
                  align-items: center;
                  justify-content: start;
                  position: absolute;
                  height: 50%;
                  width: 50%;
                  color: #649591;
                  padding-bottom: 9%;
                  margin-left: 18%;
                "
              >
                {{ question.meTooCnt }}<br />
              </div>
            </div>
            <!-- 나궁 수 -->

            <!-- 북마크수 -->
            <div
              class="cntBox"
              style="
                position: relative;
                left: -4%;
                width: 50%%;
                display: flex;
                justify-content: center;
                align-items: center;
              "
            >
              <div style="width: 100%; display: flex; justify-content: center; align-items: center">
                <img
                  style="width: 50%; height: auto transform: translateY(-30%);"
                  src="./bookmarkIcon.png"
                  alt="북마크 아이콘"
                />
              </div>
              <div style="position: absolute; transform: translateY(-15%); color: #649591">
                {{ question.bookmarkCnt }}<br />
              </div>
            </div>
            <!-- 북마크수 -->
          </div>
        </v-col>
      </v-row>
    </div>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router';

const router = useRouter();

const goDetail = function (questionId) {
  router.push({ path: `/board/${questionId}` });
};

defineProps({
  question: Object,
});
</script>

<style scoped>
.item-container {
  width: 100%;
  height: 90%;
  background-color: white;
  border-radius: 35px;
}
.img-container {
  width: calc(height);
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 0;
}
.q-img {
  height: 65px;
  width: 65px;
}

.myListItem {
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
}
.myListItem2 {
  display: flex;
  justify-content: space-around;
  align-items: center;
  width: 100%;
  height: 100%;
}

.item-container:hover {
  box-shadow: 0px 0px 20px rgba(0, 0, 0, 0.4);
  transition: 0.3s;
}
.listItem {
  width: 100%;
  height: 80%;
  font-size: 13px;
  color: #575757;
  display: flex;
  align-items: center;
}

.info {
  font-size: 13px;
}

.nickname {
  color: #62c0a6;
}

.title {
  font-size: 15px;
  font-weight: 800;
}

.time {
  color: #d9d9d9;
}

.cntBox {
  display: flex;
  flex-direction: column;
  align-items: center;
  font-weight: 600;
  font-size: 13px;
  width: 50%;
  margin: 0;
  padding: 0;
}
</style>
