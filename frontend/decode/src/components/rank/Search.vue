<template>
    <v-container>
        <v-row>
            <v-text-field
                v-model="nickname"
                label="닉네임을 입력해주세요."
                append-icon="mdi-magnify"
                @click:append="nickNameSearch"
            ></v-text-field>
        </v-row>
    </v-container>
</template>
  
<script>
</script>
  
<style>

  
</style>
  