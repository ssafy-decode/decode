<template>
  <div>
    <div id="editor"></div>
  </div>
</template>

<script>
import Editor from '@toast-ui/editor';
import '@toast-ui/editor/dist/toastui-editor.css';
import $ from 'jquery';
import { useQuestionStore } from '@/stores/questionStore';

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
      height: '600px',
      initialEditType: 'markdown',
      initialValue: questionStore.originalContent,
      previewStyle: 'vertical',
      // 추가된 내용
      hooks: {
        addImageBlobHook: (blob, callback) => {
          // blob : Java Script 파일 객체

          const formData = new FormData();
          formData.append('image', blob);
          let url = `${process.env.VUE_APP_BACKEND_URL}/image`;
          $.ajax({
            type: 'POST',
            enctype: 'multipart/form-data',
            url: `${process.env.VUE_APP_BACKEND_URL}/image`,
            data: formData,
            dataType: 'json',
            processData: false,
            contentType: false,
            cache: false,
            timeout: 600000,
            success: function (data) {
              url += data.filename;

              // callback : 에디터(마크다운 편집기)에 표시할 텍스트, 뷰어에는 imageUrl 주소에 저장된 사진으로 나옴
              // 형식 : ![대체 텍스트](주소)
              callback(url, '사진 대체 텍스트 입력');
            },
            error: function (e) {
              callback('image_load_fail', '사진 대체 텍스트 입력');
            },
          });
        },
      },
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
