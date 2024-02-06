<template>
  <div>
    <div :id="'viewer_' + answerId"></div>
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
    answerId: {
      type: Number,
      required: true,
    },
  },
  data() {
    return {
      viewer: null,
    };
  },
  mounted() {
    this.viewer = new Viewer({
      el: document.querySelector(`#viewer_${this.answerId}`),
      initialEditType: 'wysiwyg',
      initialValue: this.initialValue,
      previewStyle: 'vertical',
    });
  },
};
</script>

<style scoped></style>
