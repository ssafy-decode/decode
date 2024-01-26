import { ref } from 'vue';
import { defineStore } from 'pinia';
import router from '@/router';
import axios from 'axios';

// 백엔드 서버 URL로 작성
const URL = process.env.VUE_APP_BACKEND_URL;

export const useUserStore = defineStore('user', () => {
  const isLoggedIn = ref(false); // 로그인 여부 확인용 T/F 변수 선언
  const users = ref([]);
  const searchUsers = ref([]);
  const tagIdList = ref([]);
  const user = ref(null);
  const userCnt = ref(0);
  const searchUserCnt = ref(0);
  const accessToken = ref('');
  const userId = ref(''); // 가입 시 회원 번호
  const userEmail = ref('');
  // const withCredentials = ref(false);

  const tagNum = {
    python: 1,
    java: 2,
    'C++': 3,
    javascript: 4,
    django: 5,
    spring: 6,
    'spring boot': 7,
    kotlin: 8,
    sql: 9,
    react: 10,
    vue: 11,
    'C#': 12,
  };

  // responseBody에서 토큰 값을 추출
  const parseToken = (response) => {
    console.log('Response:', response);

    if (response.data && response.data.data) {
      return response.data.data.substring(7); // 8 -> 7 로 수정!
    } else {
      console.error('Token is not present in the response data');
      return '';
    }
  };

  // 회원 가입 1단계
  const createUser = (user) => {
    axios
      .post(`${URL}/regist`, user, {
        // withCredentials: true,
        headers: {
          Authorization: `Bearer ${accessToken}`, // 추후 모든 메소드 headers에 이렇게 작성할 예정 => 일단 회원가입에만
        },
      })
      .then((res) => {
        const response = res.data;

        if (response.status === 'OK') {
          users.value.push(response.data);
          userId.value = response.data;
          userCnt.value = users.value.length;
          router.push({ name: 'techstack' });
        } else {
          throw new Error('Failed to create user');
        }
      })
      .catch((error) => {
        console.error('Error creating user:', error);
      });
  };

  // 회원 가입 2단계: 선택한 기술 스택 저장
  const saveTechStack = async (selectedTechStack) => {
    try {
      // 1단계 정보가 비어있을 경우 에러 처리
      if (!userId.value) {
        console.error('User registration data is missing.');
        return;
      }

      const tagNums = selectedTechStack.map((item) => tagNum[item]);
      console.log(tagNums);

      const res = await axios.post(
        `${URL}/addUserTag`,
        { userId: userId.value, tagIdList: tagNums },
        {
          // withCredentials: true,
          headers: {
            Authorization: `Bearer ${accessToken}`,
            'Content-Type': 'application/json',
          },
        },
      );

      if (res.data.status === 'OK') {
        tagIdList.value.push(...tagNums);
        console.log('Tech stack saved successfully:', tagIdList.value);
      } else {
        throw new Error('Failed to save tech stack');
      }
    } catch (error) {
      console.error('Error saving tech stack:', error.message);
    }
  };

  // 토큰 + 로그인
  const setLoginUser = async (loginuser) => {
    try {
      console.log('hi');
      const res = await axios.post(`${URL}/user/1`);
      console.log(res);
      // const res = await axios.post(`${URL}/login`, loginuser);
      // accessToken.value = parseToken(res);
      // console.log(accessToken.value);

      // isLoggedIn.value = true;

      // console.log(res.data);
      // console.log(accessToken.value);
      // router.push({ name: 'mainview' });
      // alert('로그인되었습니다.');
      return { success: true, data: accessToken };
    } catch (error) {
      alert('로그인에 실패했습니다.');
      return { success: false, error: error.message };
    }
  };

  // 로그아웃 (아직 버튼 구현 안 함)
  const setLogout = () => {
    isLoggedIn.value = false;
    accessToken.value = '';
    router.push({ name: 'mainview' });
    alert('로그아웃되었습니다.');
  };

  // 모든 회원 조회
  const setUsers = () => {
    axios
      .get(`${URL}/user`, {
        withCredentials: true,
        headers: {
          Authorization: `Bearer ${accessToken}`,
        },
      })
      .then((res) => {
        console.log(res.data); // 작동 안 함 cors?
        users.value = res.data;
      })
      .catch((error) => {
        console.error('Error fetching users:', error);
      });
  };

  // 특정 회원 조회
  const setUser = (userid) => {
    axios
      .get(`${URL}/user/${userid}`, {
        withCredentials: true,
        headers: {
          Authorization: `Bearer ${accessToken}`,
        },
      })
      .then((res) => {
        console.log('Fetched user data:', res.data);
        user.value = { ...res.data };
      })
      .catch((error) => {
        console.error('Error fetching user data:', error);
      });
  };

  // 이메일 찾기
  const findUserEmail = (user) => {
    // axios.post(`${URL}/email`, user,
    // {
    // 12
    //   // withCredentials: true,
    //   headers: {
    //     Authorization: `Bearer ${accessToken}`,
    //   },
    // })
    // .then((res) => {
    //   const response = res.data;
    //   if (response.status === 'OK') {
    //     userEmail.value = response.data;

    //     console.log('확인', userEmail.value);

    router.push({ name: 'foundemail' });
    //   } else {
    //     throw new Error('Failed to create user');
    //   }).catch((error) => {
    //     console.error('Error creating user:', error);
    //   });
  };

  // 비밀번호 찾기
  const findUserPwd = (user) => {
    // 아직 작성 안 됨

    router.push('/foundpwd');
  };

  // 회원 이름 검색 (아직 구현 안 함)
  const searchName = (username) => {
    axios
      .get(`${URL}/user/search`, {
        withCredentials: true,
        params: { key: 'user_name', word: username },
        headers: {
          Authorization: `Bearer ${accessToken}`,
        },
      })
      .then((res) => {
        searchUsers.value = res.data;
        searchUserCnt.value = searchUsers.value.length;
      })
      .catch(() => {
        alert('검색 대상을 찾지 못했습니다.');
      });
  };

  // 회원 정보 수정 (아직 구현 안 함)
  const updateUser = (userid) => {
    axios
      .put(`${URL}/profile/${userid}`, user.value, {
        withCredentials: true,
        headers: {
          Authorization: `Bearer ${accessToken}`,
        },
      })
      .then(() => {
        // 기존 사용자 정보 업데이트
        const index = users.value.findIndex((u) => u.userId === user.value.userId);
        if (index !== -1) {
          users.value[index] = { ...user.value };
        }
        router.push({ name: 'userList' });
      });
  };

  // 회원 삭제
  const deleteUser = (userid) => {
    axios
      .delete(`${URL}/user/${userid}`, {
        withCredentials: true,
        headers: {
          Authorization: `Bearer ${accessToken}`,
        },
      })
      .then(() => {
        users.value = users.value.filter((u) => u.userId !== userid);
        userCnt.value = users.value.length;
        router.push({ name: 'userList' });
      });
  };

  return {
    // withCredentials,
    accessToken,
    isLoggedIn,
    users,
    searchUsers,
    tagIdList,
    tagNum,
    user,
    userCnt,
    userId,
    userEmail,
    searchUserCnt, // 애는 회원 이름 검색에서 쓸 일이 있나 추후 확인
    createUser,
    deleteUser,
    setLogout,
    setUser,
    findUserEmail,
    findUserPwd,
    searchName,
    updateUser,
    setLoginUser,
    setUsers,
    saveTechStack,
  };
});
