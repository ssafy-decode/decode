<template>
  <div>
    <div id="viewer"></div>
  </div>
</template>

<script>
import Viewer from '@toast-ui/editor/dist/toastui-editor-viewer';
import '@toast-ui/editor/dist/toastui-editor-viewer.css';

export default {
  props: {
    initialValue: {
      type: String,
      default: '값을 불러오지 못했습니다.',
    },
  },
  data() {
    return {
      viewer: null,
    };
  },
  mounted() {
    console.log('this.initialValue의 값: ', this.initialValue);
    this.viewer = new Viewer({
      el: document.querySelector('#viewer'),
      initialEditType: 'wysiwyg',
      initialValue: this.initialValue,
      previewStyle: 'vertical',
    });
  },
};
</script>

<style scoped></style>
