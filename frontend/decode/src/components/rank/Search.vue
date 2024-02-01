<template>
  <v-container>
    <div class="text-center">
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
    </div>
  </v-container>
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
</style>
