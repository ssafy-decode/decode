<template>
  <v-container style="padding: 0">
    <v-card class="rank-list-container">
      <v-row no-gutters>
        <v-col cols="2" class="table-title">Top100</v-col>
        <v-col cols="6"></v-col>
        <v-col cols="4" style="align-items: center;">
          <div class="search-input">
            <v-text-field
              v-model="search"
              variant="outlined"
              rounded
              label="닉네임 검색"
              single-line
              hide-details
              density="compact"
            ></v-text-field>
          </div>
        </v-col>
      </v-row>
      <div class="ranking-list">
        <v-data-table
          class="ranking-table"
          :headers="headers"
          :items="rank"
          :items-per-page="10"
          :server-items-length="100"
          :sort-by.sync="sortBy"
          :sort-desc.sync="sortDesc"
          :page.sync="page"
          :search="search"
          :custom-key-filter="nicknameFilter"
          @update:page="pageUpdate"
        >
          <template v-slot:[`item.ranking`]="{ index }">
            <p style="font-weight: bold">{{ index + 1 + (page - 1) * 10 }}</p>
          </template>
          <template v-slot:[`item.nickname`]="{ item }">
            <div class="nickname-container" @click="userDetail(item.userId)">
              <v-avatar image="../../default.png" size="28px" style="margin-right: 4px;"/>
              {{ item.nickname }}
            </div>
          </template>
          <template v-slot:[`item.tier`]="{ item }">
            <div class="tier-container">
              <img :src="`../../${item.tier}.png`" width="40px"/>
            </div>
          </template>
          <template v-slot:[`item.answerCount`]="{ item }">
            {{ item.answerCount }}
          </template>
          <template v-slot:[`item.exp`]="{ item }">
            {{ item.exp }}
          </template>
          <template v-slot:[`item.adoptCount`]="{ item }">
            {{ item.adoptCount }}
          </template>
          <template v-slot:[`item.followerCount`]="{ item }">
            {{ item.followerCount }}
          </template>
        </v-data-table>
      </div>
    </v-card>
  </v-container>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRankStore } from '@/stores/rankStore';
import { storeToRefs } from 'pinia';
import { useRouter } from 'vue-router';

const rankStore = useRankStore();
const { handleRank: rank } = storeToRefs(rankStore);
const sortBy = ref(['exp']);
const sortDesc = ref([true]);
const page = ref(1);
const headers = ref([
  { title: '#', align: 'center', value: 'ranking', width:'100px'},
  { title: '닉네임', align: 'center', value: 'nickname', width: '150px' },
  { title: '티어', align: 'center', value: 'tier', width: '100px' },
  { title: '경험치', align: 'center', value: 'exp', sortable: true, width: '250px' },
  { title: '답변수', align: 'center', value: 'answerCount', sortable: true, width: '150px' },
  { title: '채택수', align: 'center', value: 'adoptCount', sortable: true, width: '150px' },
  { title: '팔로워수', align: 'center', value: 'followerCount', sortable: true, width: '150px' },
]);
const search = ref('');
function nicknameFilter(value, search, item) {
  return item.nickname.toLowerCase().includes(search.toLowerCase());
}
const pageUpdate = function (newPage) {
  page.value = newPage;
};
const router = useRouter();

const userDetail = function (userId) {
  router.push({ path: `/profile/${userId}` });
};


onMounted(() => {
  rankStore.getRank();
});
</script>

<style>
.table-title {
  align-self: center;
  font-size: 25px;
  font-weight: bold;
  color: #999999;
}
.search-input {
  height: 50px;
  max-width: 400px;
  margin: 0;
  padding: 0;
  display: flex;
  align-items: center;
}
.ranking-list {
  margin-top: 5px;
  border: 1px solid #ccc;
  background-color: white;
  border-radius: 15px;
}
.nickname-container {
  display: flex;
  align-items: center;
  cursor: pointer;
}
.tier-container {
  display: flex;
  align-items: center;
  justify-content: center;
}
</style>
