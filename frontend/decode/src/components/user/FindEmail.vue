<template>
  <div class="pa-5" rounded>
    <v-card
      class="mx-auto px-4 py-8"
      max-width="418"
      style="background-color: #f3f3f3; border-radius: 68px; box-shadow: 0 0px 36px rgba(0, 0, 0, 0.2)"
    >
      <v-form v-model="form">
        <br />
        <div style="text-align: center">
          <img style="width: 10%" src="./LogoDecode3.png" />
          <h2 style="color: #999999">계정 찾기</h2>
          <h4 style="color: #34a080">정보를 입력해주세요</h4>
        </div>
        <br />
        <v-text-field
          variant="solo"
          class="textfield"
          bg-color="#d9d9d9"
          v-model="name"
          :rules="[writeName]"
          label="이름"
          prepend-inner
        >
          <template #prepend-inner>
            <img
              src="./pencil.png"
              alt="이름 아이콘"
              style="width: auto; height: auto; margin-left: 4px; margin-right: 10px"
            />
          </template>
        </v-text-field>

        <v-text-field
          variant="solo"
          class="textfield"
          bg-color="#d9d9d9"
          v-model="birthday"
          :rules="[writeBirthday]"
          label="생년월일 6자리 ex) 990101"
          prepend-inner
        >
          <template #prepend-inner>
            <img
              src="./calendar.png"
              alt="달력 아이콘"
              style="width: auto; height: auto; margin-left: 6px; margin-right: 10px"
            />
          </template>
        </v-text-field>

        <v-text-field
          variant="solo"
          class="textfield"
          bg-color="#d9d9d9"
          v-model="phone"
          :rules="[writePhone]"
          label="휴대폰 뒷 네 자리 ex) 1234"
          prepend-inner
        >
          <template #prepend-inner>
            <img
              src="./phone.png"
              alt="전화 아이콘"
              style="width: auto; height: auto; margin-left: 6px; margin-right: 10px"
            />
          </template>
        </v-text-field>

        <div style="text-align: center">
          <span style="display: flex; justify-content: flex-end; margin-right: 20px">
            <router-link to="/foundemail"
              ><v-btn
                @click.prevent="findemail"
                color="#62C0A6"
                size="x-large"
                type="submit"
                variant="elevated"
                style="
                  width: 95px;
                  font-size: 15px;
                  font-weight: bold;
                  margin-top: 10px;
                  border-radius: 34px;
                  color: #000000;
                "
              >
                확인
              </v-btn></router-link
            >
          </span>
        </div>
      </v-form>
    </v-card>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { useUserStore } from '@/stores/userStore';

const userStore = useUserStore();

const form = ref(null);

const name = ref('');
const birthday = ref('');
const phone = ref('');

// 필수 입력란 비어있을 때 빨간 경고
const writeName = (value) => {
  return !!value;
};

const writeBirthday = (value) => {
  return !!value;
};

const writePhone = (value) => {
  return !!value;
};

// 생년월일 형식 올바른지
const isBirthdayValid = (birthday) => {
  return birthday.length == 6;
};

// 전화번호 형식 올바른지
const isPhoneValid = (phone) => {
  return phone.length == 4;
};

// 확인 버튼 누르면 전체 정보 올바른지 확인
const findemail = () => {
  try {
    if (name.value === '' || birthday.value === '' || phone.value === '') {
      alert('필수 정보를 모두 입력해주세요.');
      return;
    }

    if (!isBirthdayValid(birthday.value)) {
      alert('생년월일을 6자리로 입력해주세요.');
    }

    if (!isPhoneValid(phone.value)) {
      alert('전화번호를 4자리로 입력해주세요.');
    }

    const user = {
      name: name.value,
      phoneNumber: phone.value,
      birth: birthday.value,
    };

    userStore.findUserEmail(user);
  } catch (error) {}
};
</script>

<style scoped>
.textfield {
  margin: 0 20px;
  margin-bottom: 5px;
  height: 63px;
}
.textfield :deep(label) {
  color: #ffffff;
}

.textfield :deep(.v-field) {
  border-radius: 55px;
}
</style>
