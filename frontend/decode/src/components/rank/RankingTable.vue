<template>
  <v-container class="ranking-list">
    <v-data-table
      :headers="headers"
      :items="rank"
      :items-per-page="10"
      :server-items-length="100"
      :sort-by.sync="sortBy"
      :sort-desc.sync="sortDesc"
      class="ranking-table"
      v-model:page="page"
    >
    <template v-slot:item.ranking="{ index }">
      {{ index + 1 + (page - 1) * 10 }}
    </template>
    <template v-slot:item.nickname="{ item }">
      <div class="nickname-container">
        <img src="../../default.png" alt="Profile" width="28"/>
        {{ item.nickname }}
      </div>
    </template>
    <template v-slot:item.tier="{ item }">
      <div class="tier-container">
        <img :src="`../../${item.tier}.png`" :width="35"/>
      </div>
    </template>
    <template v-slot:item.answerCount="{ item }">
      {{ item.answerCount }}
    </template>
    <template v-slot:item.exp="{ item }">
      {{ item.exp }}
    </template>
    <template v-slot:item.adoptCount="{ item }">
      {{ item.adoptCount }}
    </template>
    <template v-slot:item.followerCount="{ item }">
      {{ item.followerCount }}
    </template>
    </v-data-table>
  </v-container>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRankStore } from '@/stores/rankStore';
import { storeToRefs } from 'pinia';

const rankStore = useRankStore();
const { handleRank: rank } = storeToRefs(rankStore);
const sortBy = ref(['exp']);
const sortDesc = ref([true]);
const page = ref(1);
const headers = ref([
  { title: '랭킹', align: 'center', value: 'ranking' },
  { title: '닉네임', align: 'center' , value: 'nickname', width: '200px' },
  { title: '티어', align: 'start', value: 'tier', width: '100px'},
  { title: '경험치', align: 'center', value: 'exp', sortable: true, width: '250px'},
  { title: '답변수', align: 'center', value: 'answerCount', sortable: true , width: '150px' },
  { title: '채택수', align: 'center', value: 'adoptCount', sortable: true , width: '150px' },
  { title: '팔로워수', align: 'center', value: 'followerCount', sortable: true , width: '150px' },
]);

onMounted(() => {
  rankStore.getRank();
});

</script>

<style>
.ranking-list {
  max-width: 1150px;
  margin-left: 10;
  margin-right: 10;
  margin-bottom: 10px;
  border: 1px solid #ccc;
  background-color: white;
  border-radius: 15px;
}
.nickname-container {
  display: flex;
  align-items: center;
}
.tier-container {
  display: flex;
  align-items: center;
}
</style>
