<template>
  <div>
    <div id="viewer"></div>
  </div>
</template>

<script setup>
import Viewer from '@toast-ui/editor/dist/toastui-editor-viewer';
import '@toast-ui/editor/dist/toastui-editor-viewer.css';
import { ref, watchEffect } from 'vue';

const props = defineProps({
  initialValue: String,
});

const viewer = ref(null);

watchEffect(() => {
  console.log('디스 이니셜 밸류의 값:', props.initialValue);
  if (props.initialValue) {
    viewer.value = new Viewer({
      el: document.querySelector('#viewer'),
      initialEditType: 'wysiwyg',
      initialValue: props.initialValue,
      previewStyle: 'vertical',
    });
  }
});
</script>

<style scoped></style>
