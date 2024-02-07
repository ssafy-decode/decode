<template>
  <div>
    <div class="pa-5" rounded style="color: #575757; font-weight: bold">
      <v-card
        class="mx-auto px-4 py-8"
        max-width="481"
        style="
          box-shadow: none;
          text-align: center;
          background-color: #f3f3f3;
          border-radius: 68px;
          border: 15px solid #d9d9d9;
        "
      >
        <div style="display: flex; justify-content: flex-start">
          <!-- 뒤로가기 버튼--><router-link :to="`/profile/${userStore.loginUserId}`"
            ><img style="margin-left: 5px" width="15px" src="../../leftarrowicon.png"
          /></router-link>
        </div>
        <v-row style="margin-left: 5px">
          <v-col cols="4">
            <img style="width: 70%" src="../default.png" />
            <br />
            <v-btn class="photobtn" color="#62C0A6" type="submit" variant="elevated">사진 변경</v-btn>
            <!-- 추후 API 연결 -->
          </v-col>
          <v-col cols="8">
            <div style="font-size: 16px">
              <span> 이 &nbsp;&nbsp; 름: {{ user.name }}</span>
              <br />
              <br />
              <span>닉 네 임: {{ profile.nickname }}</span>
              <br />
              <br />
              <span>생년월일: {{ user.birth }}</span>
              <br />
            </div>
          </v-col>
        </v-row>
        <br />
        <div style="text-align: left; margin-left: 20px">
          <span>이&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 메&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 일: {{ user.email }}</span>
          <br />
          <br />
          <span>휴대폰 뒷자리: {{ user.phoneNumber }}</span>
          <br />
          <br />
        </div>
        <br />
        <div>
          기술 스택 변경
          <br />
          <br />
          <div v-if="tagIdList.length > 0">
            <v-combobox
              v-if="editing"
              variant="solo"
              class="combo"
              bg-color="#d9d9d9"
              v-model="selectedTags"
              :items="items"
              placeholder="ex) java, spring boot, sql"
              label="기술 스택"
              multiple
              chips
              clearable
            ></v-combobox>
            <v-chip v-for="tag in selectedTags" :clearable="editing" :key="tag" label color="primary" class="mr-2 mb-2">
              {{ tag }}</v-chip
            >
          </div>
          <div v-else>선택한 기술 스택이 없습니다.</div>
          <br />
          <v-btn @click="toggleEdit" class="tagbtn" color="#62C0A6" type="submit" variant="elevated">{{
            editing ? '기술 스택 변경 저장' : '기술 스택 변경'
          }}</v-btn>
        </div>
        <br />
        <br />
        <div>
          비밀번호 변경
          <br />
          <br />
          <v-text-field
            bg-color="#d9d9d9"
            :rules="[writePassword]"
            v-model="password"
            variant="solo"
            class="textfield"
            clearable
            label="비밀번호 변경"
            hint="영문, 숫자, 특수문자 조합 8자리 이상"
            append-inner
            :type="showPassword ? 'text' : 'password'"
            style="margin-left: 20px; margin-right: 20px"
          >
            <template #append-inner>
              <v-icon @click="toggleEye" style="margin-right: 10px">{{
                showPassword ? 'mdi-eye' : 'mdi-eye-off'
              }}</v-icon>
            </template></v-text-field
          >
          <v-text-field
            bg-color="#d9d9d9"
            :rules="[writePassword2]"
            v-model="password2"
            variant="solo"
            class="textfield"
            clearable
            label="비밀번호 확인"
            append-inner
            :type="showPassword2 ? 'text' : 'password'"
            style="margin-left: 20px; margin-right: 20px"
          >
            <template #append-inner>
              <v-icon @click="toggleEye2" style="margin-right: 10px">{{
                showPassword2 ? 'mdi-eye' : 'mdi-eye-off'
              }}</v-icon>
            </template></v-text-field
          >
        </div>
        <v-btn class="updatebtn" @click="updatepwd" color="#62C0A6" type="submit" variant="elevated"
          >비밀번호 변경 저장</v-btn
        >
      </v-card>
    </div>
  </div>
</template>

<script setup>
import { onBeforeMount, ref } from 'vue';
import { storeToRefs } from 'pinia';
import { useUserStore } from '@/stores/userStore';
import { useTagStore } from '@/stores/tagStore';
import { useProfileStore } from '@/stores/profileStore';

const userStore = useUserStore();
const tagStore = useTagStore();
const profileStore = useProfileStore();

const { setUser } = userStore;
const { setTagNumList } = tagStore;
const { setUserProfile, updateTechStack, updatePwd } = profileStore;
const { handleAccessToken: accessToken } = storeToRefs(userStore);
// const { handleLoginUserId: uid } = storeToRefs(userStore);
// const { loginUserId: uid } = storeToRefs(userStore);
// const { loginUser: user } = storeToRefs(userStore);
const { handleUser: user } = storeToRefs(userStore);
const { handleUserProfile: profile } = storeToRefs(profileStore);
const { tagIdList: tagIdList } = storeToRefs(tagStore);

onBeforeMount(() => {
  setUser(userStore.loginUserId);
  setTagNumList(userStore.loginUserId);
  setUserProfile(userStore.loginUserId);
});

const selectedTags = ref([]);
const items = ref([
  'python',
  'java',
  'C++',
  'javascript',
  'django',
  'spring',
  'spring boot',
  'kotlin',
  'sql',
  'react',
  'vue',
  'C#',
]);

// const isSelected = (item) => {
//   return select.value.includes(item);
// };

// const toggleSelection = (item) => {
//   const index = select.value.indexOf(item);
//   if (index !== -1) {
//     select.value.splice(index, 1);
//   } else {
//     select.value.push(item);
//   }
// };

// // DB에 수정된 번호를 다시 태그명으로 전환
// const tagName = {
//   1: 'python',
//   2: 'java',
//   3: 'C++',
//   4: 'javascript',
//   5: 'django',
//   6: 'spring',
//   7: 'spring boot',
//   8: 'kotlin',
//   9: 'sql',
//   10: 'react',
//   11: 'vue',
//   12: 'C#',
// };

// 기술 스택 목록 변경
const toggleEdit = () => {
  if (editing.value) {
    // const selectedTagNames = selectedTags.value.map((tag) => tag.tagName);
    const user = {
      userId: userStore.loginUserId,
      tagIdList: selectedTags.value,
    };
    updateTechStack(user, accessToken.value);
  }
  editing.value = !editing.value;
};

const password = ref('');
const password2 = ref('');
const showPassword = ref(false);
const showPassword2 = ref(false);
const editing = ref(false);

// 눈 버튼 누르면 비밀번호 가렸다 보였다
const toggleEye = () => {
  showPassword.value = !showPassword.value;
};
const toggleEye2 = () => {
  showPassword2.value = !showPassword2.value;
};

const writePassword = (value) => {
  return !!value || '비밀번호를 입력하세요.';
};

const writePassword2 = (value) => {
  return !!value || '비밀번호 확인을 입력하세요.';
};

// 비밀번호 형식 올바른지
const isPasswordValid = (pwd) => {
  return pwd.length >= 8 && /[!@#$%^&*(),.?":{}|<>]/g.test(pwd);
};

// 비밀번호 변경
const updatepwd = () => {
  if (!isPasswordValid(password.value)) {
    alert('비밀번호는 8자리 이상이며, 특수문자를 포함해야 합니다.');
    return;
  }

  if (password.value !== password2.value) {
    alert('비밀번호가 일치하지 않습니다.');
    return;
  }

  const user = {
    id: userStore.loginUserId,
    password: password.value,
  };

  updatePwd(user, accessToken.value);
};
</script>

<style scoped>
.photobtn {
  height: 34px;
  width: 95px;
  font-size: 15px;
  font-weight: bold;
  border-radius: 34px;
  margin-top: 10px;
  color: #000000;
}

.tagbtn {
  height: 34px;
  width: 150px;
  font-size: 15px;
  font-weight: bold;
  border-radius: 34px;
  color: #000000;
}

.updatebtn {
  height: 34px;
  width: 376px;
  font-size: 15px;
  font-weight: bold;
  border-radius: 34px;
  margin-top: 10px;
  color: #000000;
}

.textfield :deep(.v-field) {
  border-radius: 10px;
}
</style>
