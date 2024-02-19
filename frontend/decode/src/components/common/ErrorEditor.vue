<template>
  <div>
    <div id="editor"></div>
  </div>
</template>

<script>
import Editor from '@toast-ui/editor';
import '@toast-ui/editor/dist/toastui-editor.css';
import 'tui-color-picker/dist/tui-color-picker.css';
import '@toast-ui/editor-plugin-color-syntax/dist/toastui-editor-plugin-color-syntax.css';
import colorSyntax from '@toast-ui/editor-plugin-color-syntax';
import fontSize from 'tui-editor-plugin-font-size';
import 'tui-editor-plugin-font-size/dist/tui-editor-plugin-font-size.css';
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
      initialValue:
        '코딩하다 마주친 에러를 입력하고 질문 제목을 자동 생성해보세요!\n질문 제목을 직접 생성할 수도 있습니다. :)',
      previewStyle: 'vertical',
      plugins: [colorSyntax, fontSize],
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
