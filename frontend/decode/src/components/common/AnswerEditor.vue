<template>
  <div>
    <div id="editor"></div>
  </div>
</template>

<script>
import Editor from '@toast-ui/editor';
import '@toast-ui/editor/dist/toastui-editor.css';
import axios from '@/utils/common-axios';
import { useQuestionStore } from '@/stores/questionStore';
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
    const questionStore = useQuestionStore();
    this.editor = new Editor({
      el: document.querySelector('#editor'),
      height: '570px',
      initialEditType: 'markdown',
      initialValue: '내용을 마크다운 형식으로 입력해주세요!',
      previewStyle: 'vertical',
      hooks: {
        addImageBlobHook: addImageBlobHook,
      },
      plugins: [colorSyntax, fontSize],
    });

    this.editor.on('change', () => {
      // 변경된 내용을 부모 컴포넌트로 전달
      this.$emit('editor-content-updated', this.editor.getMarkdown());
    });
  },
};

const addImageBlobHook = async (blob, callback) => {
  const formData = new FormData();
  formData.append('file', blob);

  await axios
    .post('/image', formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    })
    .then((res) => {
      const uploadURL = res.data.data.url;
      callback(uploadURL, '사진');
    })
    .catch((e) => {
      callback('', '이미지 업로드 실패');
    });
};
</script>

<style scoped></style>
