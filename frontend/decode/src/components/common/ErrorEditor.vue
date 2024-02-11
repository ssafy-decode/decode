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
      height: '500px',
      initialEditType: 'wysiwyg',
      initialValue: '이 문장을 지우고 에러를 붙여넣어주세요!',
      previewStyle: 'vertical',
    });
    // 에디터의 내용이 변경될 때마다 change 이벤트 발생
    this.editor.on('change', () => {
      // 변경된 내용을 부모 컴포넌트로 전달
      this.$emit('editor-content-updated', this.editor.getMarkdown());
    });
  },
};
</script>

<style scoped></style>
