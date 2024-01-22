<template>
  <div class="pa-5" rounded>
    <v-card class="mx-auto px-4 py-8" max-width="500">
      <v-form v-model="form" onsubmit.prevent="onSubmit">
        <span style="margin-left: 20px"
          ><img src="../levelselected.png" />&nbsp;&nbsp;&nbsp;<img src="../levelunselected.png"
        /></span>
        <div style="text-align: center">
          <img style="width: 8%" src="./LogoDecode3.png" />
        </div>
        <br />
        <flex-container>
          <v-text-field class="mx-5" clearable density="compact" label="이름" prepend-inner
            ><template #prepend-inner>
              <img
                src="./person.png"
                alt="이름 아이콘"
                style="width: auto; height: auto; margin-left: 1px; margin-right: 10px"
              /> </template
          ></v-text-field>
          <v-text-field class="mx-5" clearable density="compact" label="닉네임" prepend-inner
            ><template #prepend-inner>
              <img
                src="./pencil.png"
                alt="닉네임 아이콘"
                style="width: auto; height: auto; margin-left: 1px; margin-right: 10px"
              /> </template
          ></v-text-field>
        </flex-container>
        <v-text-field v-model="email" class="mx-5" clearable density="compact" label="이메일 계정" prepend-inner
          ><template #prepend-inner>
            <img
              src="./email.png"
              alt="메일 아이콘"
              style="width: auto; height: auto; margin-left: 1px; margin-right: 10px"
            /> </template
        ></v-text-field>
        <!-- 비번 보이고 안 보이게 하기 -->
        <v-text-field
          v-model="password"
          class="mx-5"
          clearable
          density="compact"
          label="비밀번호"
          hint="영문, 숫자, 특수문자 조합 8자리 이상"
          prepend-inner
          append-inner
          :type="showPassword ? 'text' : 'password'"
          ><template #prepend-inner>
            <img
              src="./pwd.png"
              alt="비밀번호 아이콘"
              style="width: auto; height: auto; margin-left: 1px; margin-right: 10px"
            />
          </template>
          <template #append-inner>
            <v-icon @click="toggleEye">{{ showPassword ? 'mdi-eye' : 'mdi-eye-off' }}</v-icon>
          </template></v-text-field
        >
        <v-text-field
          v-model="password2"
          class="mx-5"
          clearable
          density="compact"
          label="비밀번호 확인"
          prepend-inner
          append-inner
          :type="showPassword2 ? 'text' : 'password'"
          ><template #prepend-inner>
            <img
              src="./pwd.png"
              alt="비밀번호 아이콘"
              style="width: auto; height: auto; margin-left: 1px; margin-right: 10px"
            />
          </template>
          <template #append-inner>
            <v-icon @click="toggleEye2">{{ showPassword2 ? 'mdi-eye' : 'mdi-eye-off' }}</v-icon>
          </template></v-text-field
        >
        <v-text-field class="mx-5" clearable density="compact" label="생년월일 6자리 ex) 990101" prepend-inner
          ><template #prepend-inner>
            <img
              src="./calendar.png"
              alt="달력 아이콘"
              style="width: auto; height: auto; margin-left: 1px; margin-right: 10px"
            /> </template
        ></v-text-field>
        <v-text-field class="mx-5" clearable density="compact" label="휴대폰 뒷 네 자리 ex) 1234" prepend-inner
          ><template #prepend-inner>
            <img
              src="./phone.png"
              alt="전화 아이콘"
              style="width: auto; height: auto; margin-left: 1px; margin-right: 10px"
            /> </template
        ></v-text-field>
        <span style="display: flex; justify-content: end; margin-right: 30px">
          <router-link to="/techstack"
            ><v-btn color="success" size="large" type="submit" variant="elevated"> 확인 </v-btn></router-link
          >
        </span>
      </v-form>
    </v-card>
  </div>
</template>

<script setup>
import { ref } from 'vue';

const email = ref('');
const password = ref('');
const password2 = ref('');
const showPassword = ref(false);
const showPassword2 = ref(false);

const toggleEye = () => {
  showPassword.value = !showPassword.value;
};
const toggleEye2 = () => {
  showPassword2.value = !showPassword2.value;
};
</script>

<style scoped>
flex-container {
  display: flex;
  justify-content: space-between;
}
</style>
