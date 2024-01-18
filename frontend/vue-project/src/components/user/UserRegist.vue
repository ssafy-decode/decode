<template>
  <v-sheet class="pa-5" rounded>
    <v-card class="mx-auto px-4 py-8" max-width="400">
      <v-form v-model="form">
        <h3>필수 입력 정보</h3>
        <br />
        <v-text-field class="mx-5" clearable density="compact" label="이름"></v-text-field>
        <v-text-field class="mx-5" clearable density="compact" label="닉네임"></v-text-field>
        <v-text-field class="mx-5" clearable density="compact" label="이메일 계정"></v-text-field>
        <!-- 비번 보이고 안 보이게 하기 -->
        <v-text-field
          class="mx-5"
          clearable
          density="compact"
          label="비밀번호"
          hint="8자리 이상 입력해주세요"
          :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
          :type="show1 ? 'text' : 'password'"
        ></v-text-field>
        <v-text-field class="mx-5" clearable density="compact" label="비밀번호 확인"></v-text-field>
        <v-text-field class="mx-5" clearable density="compact" label="생년월일 6자리 ex) 990101"></v-text-field>
        <v-text-field class="mx-5" clearable density="compact" label="핸드폰 번호 뒷자리 4자리"></v-text-field>
        <span>
          <v-btn :disabled="!form" color="success" size="large" type="submit" variant="elevated"> 회원가입 </v-btn>
        </span>

        <br />
      </v-form>
    </v-card>
  </v-sheet>
</template>

<script setup></script>

<style scoped></style>
