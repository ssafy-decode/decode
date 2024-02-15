<template>
  <!-- v-if 디렉티브를 사용하여 로딩 상태를 기반으로 오버레이를 조건부로 표시합니다. -->
  <div v-if="showLoadingOverlay" class="loading-overlay">
    <div class="loading-spinner"></div>
  </div>

  <v-sheet class="mx-auto createBox card" width="1000">
    <v-form @submit.prevent="createQuestion">
      <v-text-field class="stackBox" variant="solo" label="질문 제목" v-model.trim="questionTitle">
        <template #prepend-inner>
          <img src="/questionIcon2.png" alt="검색아이콘" style="width: 40px; height: 40px" />
        </template>
      </v-text-field>
      <v-container class="tagContainer" v-if="tagIds">
        <template v-for="(tag, index) in tagIds" :key="index">
          <v-row class="d-flex justify-end">
            <v-col cols="12" sm="6" md="4" class="tagContainer">
              <v-combobox
                variant="solo"
                class="stackBox"
                bg-color="fff"
                v-model="tagIds[index]"
                :items="Object.keys(items)"
                placeholder="ex) java, spring boot, sql"
                label="관련 기술 태그"
                clearable
                hide-details="true"
              ></v-combobox>
            </v-col>
            <v-col cols="12" sm="6" md="4" class="tagContainer">
              <v-text-field
                variant="solo"
                class="stackBox"
                bg-color="fff"
                v-model.trim="versions[index]"
                placeholder="ex) 1.0.1"
                label="태그 버전"
              ></v-text-field>
            </v-col>
            <v-col cols="1" class="xBtn">
              <v-btn size="x-small" variant="tonal" @click="removeField(index)" icon="mdi-close"></v-btn>
            </v-col>
          </v-row>
        </template>
      </v-container>
      <div class="addTagBox">
        <div>주의) 최소 1개 이상의 태그를 입력해주세요.</div>
        <div>주의) 태그를 입력할 땐, react.js, vue.js 등은 뒤에 ".js"를 지워주세요</div>
        <div class="addTagBtnBox">
          <v-btn class="submitBtn" @click="addEmptyFields">태그추가</v-btn>
        </div>
      </div>

      <br />
      <div style="background-color: white">
        <MyEditor @editor-content-updated="updateEditorContent" />
      </div>
      <div class="btnBox">
        <v-btn class="submitBtn" type="submit">질문등록</v-btn>
      </div>
    </v-form>
  </v-sheet>
</template>

<script setup>
import MyEditor from '@/components/common/MyEditor.vue';
import { useQuestionStore } from '@/stores/questionStore';
import { useUserStore } from '@/stores/userStore';
import { useAnswerStore } from '@/stores/answerStore';
import { useRouter } from 'vue-router';
import { ref, onMounted } from 'vue';
import axios from '@/utils/common-axios';

const questionStore = useQuestionStore();
const userStore = useUserStore();
const answerStore = useAnswerStore();
const router = useRouter();

const questionTitle = ref('');
const questionContent = ref('');
const tagIds = ref([]);
const versions = ref([]);
const questionId = ref(null);

const items = questionStore.items;

const showLoadingOverlay = ref(false);

const updateEditorContent = function (content) {
  questionContent.value = content;
};

const createQuestion = function () {
  if (typeof tagIds.value[0] !== 'undefined') {
    const tags = tagIds.value.map((tagId, index) => {
      return {
        tagId: items[tagId],
        version: versions.value[index],
      };
    });
    // 유효성 검사
    if (tags[0].tagId && tags[0].version && questionContent.value.trim() && questionTitle.value.trim()) {
      showLoadingOverlay.value = true;
      let data = {
        title: questionTitle.value,
        content: questionContent.value,
        questionWriterId: userStore.loginUserId,
        tags: tags,
      };
      axios({
        method: 'post',
        url: `/question`,
        data: data,
        headers: {
          Authorization: `Bearer ${userStore.accessToken}`,
        },
      })
        .then((res) => {
          questionId.value = res.data.data.id;
          questionStore.gptTitles = [''];
          questionStore.gptTagIds = [''];
        })
        .then((res) => {
          // GPT 답변 자동 생성
          answerStore.getGptAnswer(questionId.value, questionContent.value);
          answerStore.getSofAnswer(questionId.value, questionContent.value);
          alert('잠시 후 GPT의 답변과 Stackoverflow의 답변이 생성됩니다.');

          setTimeout(() => {
            showLoadingOverlay.value = false;
            if (confirm('GPT와 Stackoverflow의 답변이 생성되면 알려드릴게요!')) {
              router.push({ name: 'questionview' });
            } else {
              router.push({ name: 'questionview' });
            }
          }, 1000);
        })
        .catch((err) => {
          alert('제목과 내용, 태그를 입력해주세요');

          showLoadingOverlay.value = false;
        });
    } else {
      alert('제목, 내용, 태그, 버전을 빠짐 없이 입력해주세요');
    }
  } else {
    alert('관련 태그를 한 개 이상 입력해주세요!');
  }
};

onMounted(() => {
  questionTitle.value = questionStore.gptTitles;
  tagIds.value = questionStore.gptTagIds;
  if (tagIds.value == [] || tagIds.value == ['']) {
    versions.value = [''];
  } else {
    versions.value = Array.from({ length: tagIds.value.length }, () => '');
  }
});

const addEmptyFields = function () {
  tagIds.value.push('');
  versions.value.push('');
};

const removeField = function (index) {
  if (tagIds.value.length >= 2) {
    tagIds.value.splice(index, 1);
    versions.value.splice(index, 1);
  } else {
    alert('관련 태그를 최소 1개 이상 입력해주세요');
  }
};
</script>

<style scoped>
.addTagBox {
  margin-top: 15px;
}

.xBtn {
  margin-bottom: 20px;
}

.tagContainer {
  padding: 5px 10px 5px;
}

.btnBox {
  position: relative;
  margin-top: 20px;
}
.addTagBtnBox {
  position: relative;
  top: -40px;
}

.submitBtn {
  position: absolute;
  right: 10px;
  top: 5px;
  border-radius: 40px;
  background-color: #62c0a6;
  font-weight: 800;
  box-shadow: 0px 0px 5px rgba(0, 0, 0, 0.4);
}

.createBox {
  margin-bottom: 150px;
}

#btnBox {
  position: relative;
}

.createBox {
  margin-bottom: 150px;
}

.card {
  width: 1000px;
  border-top-left-radius: 50px;
  border-bottom-left-radius: 50px;
  border-bottom-right-radius: 50px;
  padding: 30px 30px 70px;
  background-color: #f4f4f4;
  box-shadow: 0px 0px 20px rgba(0, 0, 0, 0.4);
}

input {
  border-left: 1px solid black;
}

span {
  margin: 5px;
}

::-webkit-scrollbar {
  width: 15px;
}

::-webkit-scrollbar-thumb {
  background: #b0b0b0;
  border: solid 2px #e6e6e6;
  border-radius: 5px;
}

::-webkit-scrollbar-track {
  background-color: #e6e6e6;
}

.stackBox ::v-deep(.v-field) {
  border-radius: 45px;
  padding: 5px 10px;
}

/* 로딩 오버레이 화면 관련 CSS */
.loading-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(255, 255, 255, 0.5); /* 반투명한 흰 배경 */
  z-index: 9999; /* 다른 요소들 위에 표시되도록 설정 */
  display: flex;
  justify-content: center;
  align-items: center;
}

.loading-spinner {
  width: 50px;
  height: 50px;
  border-radius: 50%;
  border: 3px solid #62c0a6; /* 로딩 스피너 색상 */
  border-top-color: transparent; /* 투명한 상단 가장자리 */
  animation: spin 1s linear infinite; /* 회전 애니메이션 적용 */
}

@keyframes spin {
  100% {
    transform: rotate(360deg); /* 360도 회전 */
  }
}
</style>
