<template>
  <div>
    <div class="pa-5" rounded style="color: #575757; font-weight: bold">
      <v-card
        class="mx-auto px-4 py-8"
        max-width="481"
        style="
          background-color: #f3f3f3;
          border-radius: 68px;
          box-shadow: 0 0px 36px rgba(0, 0, 0, 0.2);
          text-align: center;
        "
      >
        <div style="display: flex; justify-content: flex-start">
          <!-- 뒤로가기 버튼--><router-link :to="`/profile/${userStore.loginUserId}`"
            ><img style="margin-left: 5px" width="23px" src="./leftarrowicon.png"
          /></router-link>
        </div>
        <v-row style="margin-left: 5px">
          <v-col cols="5" style="margin-left: 20px">
            <div class="upperphoto">
              <img style="width: 70%; height: auto; border-radius: 50%" src="./default.png" />
              <v-btn class="photobtn" color="#62C0A6" type="submit" variant="elevated">사진 변경</v-btn>
            </div>
          </v-col>
          <v-col cols="5">
            <div style="font-size: 16px">
              <table class="profile-table">
                <tr>
                  <div class="uppertext active-div" @mouseover="showTooltip('이름')" @mouseleave="hideTooltip">
                    <span>
                      <img class="uppericon" src="./person.png" />
                    </span>
                    <span style="margin-left: 20px">
                      <td>{{ user.name }}</td>
                    </span>
                  </div>
                </tr>
                <tr>
                  <div class="uppertext active-div">
                    <span>
                      <img style="margin-top: 5px; margin-left: 10px" src="./pencil2.png" />
                    </span>
                    <span style="margin-left: 20px">
                      <td>{{ profile.nickname }}</td>
                    </span>
                  </div>
                </tr>
                <tr>
                  <div class="uppertext active-div">
                    <span>
                      <img class="uppericon" src="./calendar2.png" />
                    </span>
                    <span style="margin-left: 20px">
                      <td>{{ user.birth }}</td>
                    </span>
                  </div>
                </tr>
              </table>
            </div>
          </v-col>
        </v-row>
        <div style="text-align: left; margin-top: 15px; margin-left: 20px">
          <table class="profile-table">
            <tr>
              <div class="lowertext active-div">
                <span>
                  <img class="lowericon" src="./email.png" />
                </span>
                <span style="margin-left: 20px">
                  <td>{{ user.email }}</td>
                </span>
              </div>
            </tr>
            <tr>
              <div class="lowertext active-div">
                <span>
                  <img class="lowericon" src="./phone2.png" />
                </span>
                <span style="margin-left: 20px">
                  <td>{{ user.phoneNumber }}</td>
                </span>
              </div>
            </tr>
          </table>
        </div>
        <div style="margin-top: 40px; margin-bottom: 40px">
          <div>
            <div style="text-align: center">
              <div style="color: #34a080; font-weight: bold; margin-bottom: 20px">기술 스택 변경</div>
            </div>
            <template v-if="editing">
              <v-combobox
                v-if="editing"
                variant="solo"
                class="combo"
                bg-color="#f3f3f3"
                v-model="selectedTags"
                :items="items"
                placeholder="ex) java, spring boot, sql"
                label="기술 스택"
                multiple
                chips
                clearable
              ></v-combobox>
            </template>
            <template v-else-if="tagIdList.length > 0">
              <div class="chip-container">
                <v-chip
                  v-for="tag in tagIdList"
                  :clearable="editing"
                  :key="tag"
                  label
                  :style="{ backgroundColor: tagBackGroundColor(tag), color: tagTextColor(tag), margin: '5px' }"
                  class="mr-2 mb-2 chips"
                >
                  {{ tagName[tag] }}</v-chip
                >
              </div>
            </template>
            <template v-else>
              <div>선택한 기술 스택이 없습니다.</div>
            </template>
          </div>
          <v-btn @click="toggleEdit" class="tagbtn" color="#62C0A6" type="submit" variant="elevated">{{
            editing ? '기술 스택 변경 저장' : '기술 스택 변경'
          }}</v-btn>
        </div>
        <div style="margin-top: 50px">
          <div style="margin-bottom: 20px">
            <span style="color: #34a080; font-weight: bold">비밀번호 변경</span>
          </div>
          <v-text-field
            bg-color="#d9d9d9"
            :rules="[writePassword]"
            v-model="password"
            variant="solo"
            class="textfield"
            clearable
            label="비밀번호"
            hint="영문, 숫자, 특수문자 조합 8자리 이상"
            prepend-inner
            append-inner
            :type="showPassword ? 'text' : 'password'"
            style="margin-left: 20px; margin-right: 20px; border-radius: 27px"
            ><template #prepend-inner>
              <img
                src="./pwd.png"
                alt="비밀번호 아이콘"
                style="width: auto; height: auto; margin-left: 1px; margin-right: 10px"
              />
            </template>
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
            prepend-inner
            append-inner
            :type="showPassword2 ? 'text' : 'password'"
            style="margin-left: 20px; margin-right: 20px; border-radius: 27px"
            ><template #prepend-inner>
              <img
                src="../pwd.png"
                alt="비밀번호 아이콘"
                style="width: auto; height: auto; margin-left: 1px; margin-right: 10px"
              />
            </template>
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
const { handleUser: user } = storeToRefs(userStore);
const { handleUserProfile: profile } = storeToRefs(profileStore);
const { handleTags: tagIdList } = storeToRefs(tagStore);

onBeforeMount(() => {
  setUser(userStore.loginUserId);
  setTagNumList(userStore.loginUserId);
  setUserProfile(userStore.loginUserId);
  selectedTags.value = tagIdList.value.map((tag) => tagName[tag]);
});

const editing = ref(false);
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

// DB에 수정된 번호를 다시 태그명으로 전환
const tagName = {
  1: 'python',
  2: 'java',
  3: 'C++',
  4: 'javascript',
  5: 'django',
  6: 'spring',
  7: 'spring boot',
  8: 'kotlin',
  9: 'sql',
  10: 'react',
  11: 'vue',
  12: 'C#',
};

// 기술 태그 배경색
const tagBackGroundColor = (tag) => {
  switch (tag) {
    case 1:
      return '#9DD0FF';
    case 2:
      return '#FF9D9D';
    case 3:
      return '#FFDE9D';
    case 4:
      return '#D49DFF';
    case 5:
      return '#FFCC9F';
    case 6:
      return '#CEFAD0';
    case 7:
      return '#1FD655';
    case 8:
      return '#FF6865';
    case 9:
      return '#73A5C6';
    case 10:
      return '#FFB7CE';
    case 11:
      return '#E8D3C9';
    case 12:
      return '#B65FCF';

    default:
      return 'primary'; // Default color
  }
};

// 기술 태그 글자색
const tagTextColor = (tag) => {
  switch (tag) {
    case 1:
      return '#447CB0';
    case 2:
      return '#B54F4F';
    case 3:
      return '#BC8533';
    case 4:
      return '#9330B5';
    case 5:
      return '#FF6600';
    case 6:
      return '#008631';
    case 7:
      return '#073B3A';
    case 8:
      return '#450003';
    case 9:
      return '#051650';
    case 10:
      return '#9E4244';
    case 11:
      return '#CC5404';
    case 12:
      return '#66023C';

    default:
      return 'primary'; // Default color
  }
};

// 기술 스택 목록 변경
const toggleEdit = () => {
  if (editing.value) {
    const user = {
      userId: userStore.loginUserId,
      tagIdList: selectedTags.value,
    };
    updateTechStack(user, accessToken.value);
  }
  editing.value = !editing.value;
  setTagNumList(userStore.loginUserId);
};

const password = ref('');
const password2 = ref('');
const showPassword = ref(false);
const showPassword2 = ref(false);

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
.profile-table {
  margin-right: 10px;
  width: 100%;
}

.profile-table td {
  padding: 5px;
  text-align: left;
}

.chip-container {
  border-radius: 34px;
  background-color: #f1f1f1;
  padding: 10px;
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  align-items: center;
  border: 2px solid #62c0a6;
  margin-left: 20px;
  margin-right: 20px;
}

.chips {
  border-radius: 31px;
  background-color: #9dd0ff;
  color: #447cb0;
  font-weight: bold;
}

.photobtn {
  height: 34px;
  width: 85px;
  font-size: 12px;
  font-weight: bold;
  border-radius: 34px;
  margin-top: 15px;
  color: #000000;
}

.tagbtn {
  height: 34px;
  width: 376px;
  font-size: 15px;
  font-weight: bold;
  border-radius: 34px;
  margin-top: 30px;
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
  border-radius: 27px;
}

.textfield :deep(label) {
  color: #ffffff;
}

.upperphoto {
  background-color: #d9d9d9;
  padding-top: 20px;
  padding-bottom: 10px;
  border-radius: 30px;
  width: 143px;
  height: 192px;
}

.uppertext {
  background-color: #f1f1f1;
  border: 2px solid #d9d9d9;
  border-radius: 27px;
  width: 187px;
  height: 54px;
  display: flex;
  align-items: center;
  margin-bottom: 10px;
}

.uppertext:hover,
.lowertext:hover,
.chip-container:hover {
  background-color: #dcdcdc;
}

.lowertext {
  background-color: #f1f1f1;
  border: 2px solid #d9d9d9;
  border-radius: 27px;
  width: 400px;
  height: 54px;
  display: flex;
  align-items: center;
  margin-bottom: 10px;
}

.active-div:hover {
  border: 2px solid #62c0a6;
}

.uppericon,
.lowericon {
  margin-top: 5px;
  margin-left: 15px;
}
</style>
