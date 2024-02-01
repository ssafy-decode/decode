<template>
    <v-container class="user-info">
        <v-row>
        <!-- 유저 랭킹 등수 -->
        <v-row>
          <v-card>
            <v-card-title class="text-center">랭킹</v-card-title>
            <v-card-text class="text-center">{{ userRankingPercentile }}%</v-card-text>
          </v-card>
        </v-row>
  
        <!-- 유저 티어 -->
        <v-row>
          <v-card>
            <v-card-title class="text-center">티어</v-card-title>
            <v-card-text class="text-center">{{ userTier }}</v-card-text>
          </v-card>
        </v-row>
  
        <!-- 유저 닉네임 및 포인트 -->
        <v-row>
          <v-card>
            <v-card-title class="text-center">닉네임 & 포인트</v-card-title>
            <v-card-text class="text-center">{{ userName }} - {{ userPoints }} 포인트</v-card-text>
          </v-card>
        </v-row>
      </v-row>
  
      <v-col>
        <!-- 팔로워, 팔로잉, 답변 수, 채택 수 -->
        <v-row>
          <v-card>
            <v-card-title class="text-center">팔로워 & 팔로잉</v-card-title>
            <v-card-text class="text-center">
              {{ userFollowers }} 팔로워, {{ userFollowing }} 팔로잉
            </v-card-text>
          </v-card>
        </v-row>
  
        <v-row >
          <v-card>
            <v-card-title class="text-center">답변 수 & 채택 수</v-card-title>
            <v-card-text class="text-center">
              {{ userAnswerCount }} 답변, {{ userSelectionCount }} 채택
            </v-card-text>
          </v-card>
        </v-row>
      </v-col>
  
      <v-col>
        <!-- 내랭크 경험치 현황 -->
        <v-row>
          <v-card>
            <v-card-title class="text-center">내랭크 경험치 현황</v-card-title>
            <v-card-text class="text-center">
              경험치: {{ userExperience }} / {{ userExperienceNeeded }}
            </v-card-text>
          </v-card>
        </v-row>
      </v-col>
    </v-container>
</template>
  
<script>

</script>

<style>

  
  .user-info {
    display: flex;
    margin-top: 20px;
  }
  
  
  </style>
  