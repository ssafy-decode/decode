<template>
  <v-container class="main">
    <div style="border: 5px solid #cfcfcf; text-align: center; width: 1250px">
      <UserInfo></UserInfo>
    </div>
    <br />
    <Search></Search>
    <RankingTable></RankingTable>
  </v-container>
</template>

<script>
import Search from '@/components/rank/Search.vue';
import UserInfo from '@/components/rank/UserInfo.vue';
import RankingTable from '@/components/rank/RankingTable.vue';

export default {
  data() {
    return {};
  },
  methods: {},
  components: {
    Search,
    RankingTable,
    UserInfo,
  },
};
</script>

<style>
.main {
  text-align: center;
  /* margin: 0 auto; */
}

.user-info {
  display: flex;
  margin-top: 20px;
}

.ranking-list {
  margin-top: 20px;
  border: 1px solid #ccc;
  background-color: white;
  border-radius: 15px;
}

.rounded-border {
  border-radius: 15px;
  border: 1px solid #ccc;
  overflow: hidden;
}
</style>
