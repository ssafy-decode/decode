<template>
  <v-form>
    <v-container>
      <v-row no-gutters>
        <v-col cols="8"></v-col>
        <v-col cols="4" class="text-end"> 
          <v-text-field 
            label="닉네임검색"
            variant="solo" 
            class="search-input" 
            height="50px"
            rounded 
            filled >
            <template #append-inner>
              <img style="width: 35px;" src="../../searchicon.png" />
            </template>
          </v-text-field>
        </v-col>
      </v-row>
    </v-container>
  </v-form>
</template>

<script>
// 추후 아이콘에 검색 기능 넣기
</script>

<style scoped>
/* .searchInput {
  width: 1250px;
  height: 70px;
  border-radius: 31px;
  padding-left: 30px;
  padding-right: 10px;
} */

.search-input {
  height: 46px;
  max-width: 375px;
}
/* .search-input{
  height: 46px;
  max-width: 375px;
} */
</style>
