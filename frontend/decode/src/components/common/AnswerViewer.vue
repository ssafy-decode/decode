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
      default: '값을 불러오지 못했습니다.', // 초기값으로 'temp' 설정
    },
    answerId: {
      type: Number,
      required: true, // answerId는 필수 prop으로 설정
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
