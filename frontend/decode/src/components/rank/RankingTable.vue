<template>
<v-container class="ranking-list">
  <v-container class="rounded-border">
    <v-data-table
      :headers="headers"
      :items="desserts"
      :page.sync="page"
      :items-per-page.sync="itemsPerPage"
    >
      <template v-slot:header="{ props }">
        <thead>
          <tr>
            <th v-for="header in props.headers" :key="header.text" :class="`text-${header.align}`">
              {{ header.text }}
            </th>
          </tr>
        </thead>
      </template>

      <template v-for="column in headers" v-slot:[`item.${column.value}`]="{ item }">
        <td :class="`text-${column.align}`">{{ item[column.value] }}</td>
      </template>
    </v-data-table>
  </v-container>
</v-container>
  </template>
  
  <script>
  export default {
    data() {
      return {
  // 유저 정보에 대한 가상의 데이터
  userRankingPercentile: 25,
        userTier: 'Gold',
        userName: 'JohnDoe',
        userPoints: 500,
        userFollowers: 1000,
        userFollowing: 500,
        userAnswerCount: 200,
        userSelectionCount: 50,
        userExperience: 1200,
        userExperienceNeeded: 2000,
  
  
  
        page: 1,
        itemsPerPage: 10,
        headers: [
          { title: '랭킹', key: 'ranking', align: 'start' },
          { title: '닉네임', key: 'nickname', align: 'start' },
          { title: '티어', key: 'tier', align: 'start' },
          { title: '답변 수', key: 'answerCount', align: 'end' },
          { title: '채택 수', key: 'selectionCount', align: 'end' },
          { title: '팔로워 수', key: 'followerCount', align: 'end' },
        ],
        desserts: Array.from({ length: 100 }, (_, index) => ({
          ranking: index + 1,
          nickname: `User${index + 1}`,
          tier: `Tier ${Math.floor(Math.random() * 5) + 1}`,
          answerCount: Math.floor(Math.random() * 100),
          selectionCount: Math.floor(Math.random() * 50),
          followerCount: Math.floor(Math.random() * 1000),
        })),
      };
    },
    methods: {

    },

  
  };
  </script>
  
  <style>

  
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
  