<template>
  <v-form>
    <v-container>
      <v-row no-gutters>
        <v-col cols="8"></v-col>
        <v-col cols="4" class="text-end"> 
          <v-text-field
              class="search-input"
            >
            <template #append-inner>
              <img style="width: 28px;" src="../../searchicon.png" />
            </template>
          </v-text-field>
        </v-col>
      </v-row>
  
  
      <!-- <div class="text-center">
        <div class="search-container">
          <span class="searchBox">
            <v-text-field
              variant="plain"
              label="닉네임을 입력해주세요"
              class="searchInput"
              v-model="keyword"
              bg-color="fff"
              style="border: 1px solid #898989"
              append-inner
            >
              <template #append-inner><img style="width: 58px; height: 58px" src="../../searchicon.png" /></template
            ></v-text-field>
          </span>
        </div>
      </div> -->
    </v-container>
  </v-form>
</template>

<script>
// 추후 아이콘에 검색 기능 넣기
</script>

<style>
.searchInput {
  width: 1250px;
  height: 70px;
  border-radius: 31px;
  padding-left: 30px;
  padding-right: 10px;
}
.search-input{
  height: 46px;
  max-width: 375px;
}
</style>
