<template>
  <div>
    <div id="viewer"></div>
  </div>
</template>

<script>
import Viewer from '@toast-ui/editor/dist/toastui-editor-viewer';
import '@toast-ui/editor/dist/toastui-editor-viewer.css';
import { useQuestionStore } from '@/stores/questionStore';

export default {
  data() {
    return {
      viewer: null,
    };
  },
  mounted() {
    const questionStore = useQuestionStore();
    this.viewer = new Viewer({
      el: document.querySelector('#viewer'),
      initialEditType: 'wysiwyg',
      initialValue: questionStore.originalContent,
      previewStyle: 'vertical',
    });
  },
};
</script>

<style scoped></style>
