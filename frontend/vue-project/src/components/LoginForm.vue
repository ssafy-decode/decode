<script setup>
// import { ref } from 'vue';
// import { useUserStore } from '@/stores/userStore';

// const userStore = useUserStore();

// const id = ref('');
// const password = ref('');

// const login = async () => {
//   const user = {
//     userId: id.value,
//     userPwd: password.value,
//   };

//   try {
//     const result = await userStore.setLoginUser(user);

//     if (result.success) {
//       console.log('Login result:', result.data);
//     } else {
//       console.log('Login failed:', result.error);
//     }
//   } catch (error) {
//     console.error('Login error:', error);
//   }
// };
</script>

<template>
  <div class="container">
    <fieldset class="text-center" style="width: 30%; margin-left: auto; margin-right: auto">
      <img src="@/assets/LogoDecode3.png" />
      <h2>로그인</h2>
      <br />
      <label for="id">
        <img src="../assets/Exclude.png" />
        <input placeholder="이메일을 입력하세요." type="text" id="id" v-model="id" class="view" /><br />
      </label>
      <br />
      <label for="password">
        <img src="src/assets/Secure.png" />
        <input
          placeholder="비밀번호를 입력하세요."
          type="password"
          id="password"
          v-model="password"
          class="view"
        /><br />
      </label>
      <p>이메일 찾기 비밀번호 찾기</p>
      <br />
      <span>
        <button class="btn">로그인</button>
        <button class="btn">회원가입</button>
        <!-- <button class="btn" @click="login">로그인</button> -->
      </span>
      <br />
      <br />
      <br />
      <br />
    </fieldset>
    <br />
  </div>
</template>

<style scoped>
.btn {
  display: inline-block;
  padding: 8px 12px;
  font-size: 15px;
  cursor: pointer;
  text-align: center;
  text-decoration: none;
  outline: none;
  color: #fff;
  background-color: #04aa6d;
  border: none;
  border-radius: 15px;
  box-shadow: 0 5px #999;
}

.btn:hover {
  background-color: #3e8e41;
}

.btn:active {
  background-color: #3e8e41;
  box-shadow: 0 3px #666;
  transform: translateY(4px);
}

.text-center {
  /* Rectangle 26 */

  /* position: absolute; */
  width: 40%;
  height: 70%;
  left: calc(50% - 536px / 2);
  top: calc(50% - 723px / 2 + 33.5px);

  background: #f3f3f3;
  box-shadow: 0px 0px 30px rgba(0, 0, 0, 0.25);
  border-radius: 68px;
}

#id {
  /* Component 7 */

  /* position: absolute; */
  width: 41px;
  height: 41px;
  left: 530px;
  top: 414px;

  /* Rectangle 27 */

  /* position: absolute; */
  left: -73.17%;
  right: -917.07%;
  top: -50%;
  bottom: -47.56%;

  background: #d9d9d9;
  border-radius: 55px;

  /* Exclude */

  /* position: absolute; */
  width: 90%;
  height: 51px;
  left: 1px;
  top: calc(50% - 41px / 2);

  background: #999999;

  /* Ellipse 3 */

  /* position: absolute; */
  left: 2.44%;
  right: -2.44%;
  top: 0%;
  bottom: 0%;

  background: #999999;

  /* Vector */

  /* position: absolute; */
  left: 27.44%;
  right: 22.56%;
  top: 22.22%;
  bottom: 27.78%;

  background: #d9d9d9;
}

#password {
  /* Component 6 */

  /* position: absolute; */
  width: 447px;
  height: 81px;
  left: 500px;
  top: 489.5px;

  /* Rectangle 31 */

  /* position: absolute; */
  left: 0%;
  right: 0%;
  top: 0%;
  bottom: 0%;

  background: #d9d9d9;
  border-radius: 55px;

  /* Secure */

  /* position: absolute; */
  width: 90%;
  height: 51px;
  left: 25px;
  top: calc(50% - 50px / 2);
}
</style>
