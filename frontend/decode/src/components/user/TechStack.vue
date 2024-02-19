<template>
  <div class="pa-5" rounded>
    <v-card
      class="mx-auto px-4 py-8"
      max-width="426"
      style="background-color: #f3f3f3; border-radius: 68px; box-shadow: 0 0px 36px rgba(0, 0, 0, 0.2)"
    >
      <div>
        <span style="margin-left: 40px">
          <img src="../levelunselected.png" />&nbsp;&nbsp;&nbsp;&nbsp;<img src="../levelselected.png" />
        </span>
        <div style="text-align: center">
          <img style="width: 8%" src="../LogoDecode3.png" />
        </div>
        <h4 style="font-size: 15px; color: #999999; text-align: center">선호하는 기술 스택을 모두 선택해주세요</h4>
      </div>
      <div>
        <v-container fluid>
          <v-row>
            <v-col cols="12">
              <v-combobox
                variant="solo"
                class="combo"
                bg-color="#d9d9d9"
                v-model="select"
                :items="items"
                placeholder="ex) java, spring boot, sql"
                label="기술 스택"
                multiple
                chips
                clearable
              ></v-combobox>
            </v-col>
            <v-col v-for="item in filtered" :key="item" cols="4">
              <div class="d-flex align-center">
                <img
                  @click="toggleSelection(item)"
                  :src="checkboxIcon(item)"
                  alt="선택"
                  style="width: 20px; height: 20px; cursor: pointer"
                />
                <span
                  @click="toggleSelection(item)"
                  style="cursor: pointer; font-size: 15px; font-weight: bold"
                  :style="{ color: isSelected(item) ? '#34A080' : '#999999' }"
                  class="ml-2"
                  >{{ item }}</span
                >
              </div>
            </v-col>
          </v-row>
        </v-container>
      </div>

      <span style="display: flex; justify-content: flex-end; margin-right: 30px">
        <router-link to="/"
          ><v-btn
            @click="complete"
            color="#62C0A6"
            size="x-large"
            type="submit"
            variant="elevated"
            style="
              width: 95px;
              height: 58px;
              border-radius: 34px;
              margin-right: 10px;
              font-size: 15px;
              font-weight: bold;
              color: #000000;
            "
          >
            완료
          </v-btn></router-link
        >
      </span>
    </v-card>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { useUserStore } from '@/stores/userStore';

const userStore = useUserStore();

const select = ref([]);
const items = ref([
  'python',
  'java',
  'C++',
  'javascript',
  'django',
  'spring',
  'spring boot',
  'kotlin',
  'sql',
  'react',
  'vue',
  'C#',
]);

const filtered = ref(items.value.slice(0, 10));

const isSelected = (item) => {
  return select.value.includes(item);
};

const checkboxIcon = (item) => {
  return isSelected(item)
    ? require('../../../public/checkboxchecked.png')
    : require('../../../public/checkboxunchecked.png');
};

const complete = async () => {
  try {
    await userStore.saveTechStack(select.value);
  } catch (error) {}
};

const toggleSelection = (item) => {
  const index = select.value.indexOf(item);
  if (index !== -1) {
    select.value.splice(index, 1);
  } else {
    select.value.push(item);
  }
};
</script>

<style scoped>
.combo {
  width: 349px;
  height: 63px;
  margin-left: auto;
  margin-right: auto;
}
.combo :deep(.v-field) {
  border-radius: 55px;
}
</style>
