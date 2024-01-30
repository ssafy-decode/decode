<template>
  <div class="pa-5" rounded style="color: #575757; font-weight: bold">
    <v-card
      class="mx-auto px-4 py-8"
      max-width="1036"
      style="text-align: center; background-color: #f3f3f3; border-radius: 31px; border: 15px solid #d9d9d9"
    >
      <v-row>
        <v-col :cols="2">
          냥냥
          <br />
          <br />
          <img style="width: 70%" src="./프사샘플.png" />
          <br />
          골드 IV
          <br />
          <img style="width: 20%" src="./커마아이콘샘플.png" />
        </v-col>

        <v-col :cols="7">
          출석 캘린더
          <br />
          <div class="pa-5" rounded>
            <v-card
              class="mx-auto px-4 py-8"
              max-width="569"
              style="background-color: #ffffff; border-radius: 31px; border: 5px solid #d9d9d9"
            >
              <img width="490" src="./잔디샘플.png" />
            </v-card>
          </div>
        </v-col>

        <v-col :cols="3">
          내 경험치
          <div class="pa-5" rounded>
            <v-card
              class="mx-auto px-4 py-8"
              max-width="199"
              style="background-color: #ffffff; border-radius: 31px; border: 5px solid #d9d9d9"
            >
              <img width="150" src="./그래프샘플.png" />
            </v-card>
          </div>
        </v-col>
      </v-row>

      <div class="buttons">
        <span>
          <router-link to="/info">
            <v-btn
              class="btn"
              @click="confirm"
              color="#62C0A6"
              size="x-large"
              type="submit"
              variant="elevated"
              style="color: #000000"
            >
              내 아이템
            </v-btn>
          </router-link>
          &nbsp;
          <router-link to="/confirm">
            <v-btn
              class="btn"
              @click="confirm"
              color="#62C0A6"
              size="x-large"
              type="submit"
              variant="elevated"
              style="color: #000000"
            >
              회원정보 수정
            </v-btn>
          </router-link>
        </span>
      </div>
    </v-card>
  </div>

  <v-row justify="center">
    <v-card width="1036" style="box-shadow: none; margin-top: 10px; background-color: transparent">
      <v-tabs v-model="tab" background-color="transparent" grow>
        <v-tab v-for="(item, index) in items" :key="index" :value="index" style="font-size: 16px; font-weight: bold">
          {{ item }}
        </v-tab>
      </v-tabs>

      <v-window v-model="tab">
        <v-window-item v-for="(item, index) in items" :key="index" :value="index">
          <v-card flat style="background-color: transparent">
            <v-card-text>
              <div style="text-align: center; font-size: 15px">{{ item }} 목록</div>
              <!-- 추후 각 내용을 카드로 수정 -->
            </v-card-text>
          </v-card>
        </v-window-item>
      </v-window>
    </v-card>
  </v-row>
</template>

<script setup>
import { ref } from 'vue';

const tab = ref(0);
const items = ['nickname의 질문/답변', '팔로워 100', '팔로잉 140']; // 추후 수정

// 프로필 data를 get해오기 = onmounted는 웬만하면 쓰지 말랬는데..
</script>

<style scoped>
.buttons {
  text-align: end;
}
.btn {
  width: 110px;
  height: 28px;
  border-radius: 34px;
  font-size: smaller;
  font-weight: bold;
}
</style>
