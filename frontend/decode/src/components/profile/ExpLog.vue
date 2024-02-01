<script setup>
import myaxios from '@/utils/common-axios.js';
import { defineProps, ref, reactive } from 'vue';

const props = defineProps({
  uid: Number,
});

const expLog = reactive([]);

myaxios.get(`/exp/${props.uid}`).then((res) => {
  console.log(res.data.data);
  //날짜 : 상승량 형식으로 온다.
  Object.entries(res.data.data).forEach(([key, value]) => {
    expLog.push({ date: key, exp: value });
  });
  console.log(expLog);
});
</script>

<template>
  <div>경험치로그</div>
</template>

<style scoped></style>
