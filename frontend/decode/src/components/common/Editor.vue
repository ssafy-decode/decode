<template>
  <div>
    <div id="editor"></div>
  </div>
</template>

<script>
import Editor from '@toast-ui/editor';
import '@toast-ui/editor/dist/toastui-editor.css';

export default {
  data() {
    return {
      editor: null,
    };
  },
  mounted() {
    this.editor = new Editor({
      el: document.querySelector('#editor'),
      // minHeight: '600px',
      height: '600px',
      initialEditType: 'markdown',
      initialValue: '질문 내용을 마크다운 형식으로 작성해주세요!',
      previewStyle: 'vertical',
    });
  },
};
</script>

<style scoped></style>
