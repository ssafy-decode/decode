<template>
  <div class="text-center">
    <div class="search-container">
      <span style="position: relative; margin: auto">
        <input
          class="view"
          type="text"
          placeholder="키워드를 입력하세요."
          style="width: 60%; border: 1px solid #bbb; border-radius: 8px; font-size: 20px"
        />
        <input
          type="image"
          src="https://s3.ap-northeast-2.amazonaws.com/cdn.wecode.co.kr/icon/search.png"
          alt="검색"
          style="position: absolute; width: 15px; top: 5px; right: 10px; margin: 0"
        />
      </span>
    </div>
    <div class="chips-switch-container">
      <span>
        <!-- <v-if ="search"> -->
        <v-chip closable variant="elevated" style="background-color: cornflowerblue"> python </v-chip>
        <v-chip closable variant="elevated" style="background-color: orangered"> java </v-chip>
        <v-chip closable variant="elevated" style="background-color: gold"> C++ </v-chip>
        <v-chip closable variant="elevated" style="background-color: mediumorchid"> javascript </v-chip>
        <!-- </v-if> -->
      </span>
      <v-switch inset :label="switchLabel"></v-switch>
    </div>
  </div>

  <div id="app">
    <v-app id="inspire">
      <div>
        <v-responsive max-width="400" class="mx-auto mb-4"> </v-responsive>

        <v-card elevation="16" max-width="60%" class="mx-auto">
          <v-row>
            <v-col :cols="8">
              <v-list-item :key="'question'">
                <v-list-item-content>
                  <v-list-item-title class="text-center"> 질 문 </v-list-item-title>
                </v-list-item-content>
              </v-list-item>
            </v-col>

            <v-col :cols="4">
              <v-list-item :key="'author'">
                <v-list-item-content>
                  <v-list-item-title class="text-center"> 작 성 </v-list-item-title>
                </v-list-item-content>
              </v-list-item>
            </v-col>
          </v-row>

          <v-virtual-scroll :bench="benched" :items="items" height="300" item-height="70">
            <template v-slot:default="{ item }">
              <v-list-item :key="item">
                <v-list-item-action class="d-inline">
                  {{ item }}
                </v-list-item-action>
                &nbsp;
                <v-list-item-content>
                  <v-list-item-title class="d-inline">
                    예시 질문이예요 뾰로롱 <strong>뭐라고 적지 할 말이 없네 {{ item }}</strong>
                  </v-list-item-title>
                </v-list-item-content>
              </v-list-item>

              <v-divider></v-divider>
            </template>
          </v-virtual-scroll>
        </v-card>
      </div>
    </v-app>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue';

const model = ref(false);

const switchLabel = computed(() => {
  return model.value ? '코드로 검색' : '키워드로 검색';
});

const benched = ref(0);

const items = computed(() => {
  return Array.from({ length: 7000 }, (k, v) => v + 1);
});
</script>

<style scoped>
.chips-switch-container {
  align-items: center;
}

span {
  margin: 5px;
}
</style>
