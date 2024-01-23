import { ref } from 'vue';
import { defineStore } from 'pinia';
import router from '@/router';
import axios from 'axios';

const URL = 'http://localhost:80/decode';

export const useUserStore = defineStore('user', () => {
  const isLoggedIn = ref(false); // 로그인 여부 확인용 T/F 변수 선언
  const users = ref([]);
  const searchUsers = ref([]);
  const user = ref(null);
  const userCnt = ref(0);
  const searchUserCnt = ref(0);
  const accessToken = ref('');
  const registrationData = ref(null);
  const withCredentials = ref(false);

  // responseBody에서 토큰 값을 추출
  const parseToken = (response) => {
    // const token = response.data.token;
    // // 토큰 값에서 앞의 7자 이후를 파싱
    // return token.substring(0, 7); // Authorization 헤더에 "Bearer"를 붙여서 요청
    console.log('Response:', response);
    if (response.data && response.data.data) {
      return response.data.data.substring(8);
    } else {
      // Handle the case where token is not present or undefined
      console.error('Token is not present in the response data');
      return ''; // or handle it according to your requirements
    }
  };

  // 회원 가입 1단계
  const createUser = (user) => {
    axios
      .post(`${URL}/regist`, user, {
        withCredentials: true,
        headers: {
          Authorization: `Bearer ${accessToken.value}`,
        },
      })
      .then((res) => {
        const response = res.data;
        console.log(res.data); // 작동 안 함 => CORS 문제로..
        if (response.status === 'OK') {
          registrationData.value = response.data;
          console.log(registrationData.value); // 테스트용 => 안 뜸
          users.value.push(response.data);
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

  // 회원 가입 2단계: 선택한 기술 스택 저장 (필수 입력 정보는 아님)
  const saveTechStack = async (selectedTechStack) => {
    try {
      // 1단계 정보가 비어있을 경우 에러 처리
      if (!registrationData.value || !registrationData.value.email) {
        console.error('User registration data is missing.'); // 뜨는 것 같다가 regist 강제 통과시켜서 그런가 갑자기 안 뜸
        return;
      }
      console.log(registrationData.value); // 안 뜸
      console.log(registrationData.value.email); // 안 뜸

      const email = registrationData.value.email;
      const res = await axios.post(
        `${URL}/techstack`,
        { email, techStack: selectedTechStack },
        {
          withCredentials: true,
          headers: {
            Authorization: accessToken.value,
          },
        },
      );
      // 1단계 가입 때 혹은 소셜 로그인 때 작성한 이메일을 그대로 들고 와서
      // 그 이메일이 추가된 db에 techstack도 같이 추가하고 싶음. 어떻게 구현?
      if (res.data.status === 200) {
        console.log('Tech stack saved successfully:', res.data);
      } else {
        throw new Error('Failed to save tech stack');
      }
    } catch (error) {
      console.error('Error saving tech stack:', error.message); // 일단 테스트에서는 여기서 network error가 뜸 => CORS 문제..
    }
  };

  // 토큰 + 로그인
  const setLoginUser = async (loginuser) => {
    // loginform에서 login까지는 잘 작동하는데 store 들어와서 CORS 오류로 자꾸 콘솔 출력조차 안 됨..
    // 그래서 결국 Login failed: Network error로 끝나버림..
    try {
      const res = await axios.post(`${URL}/login`, loginuser);
      console.log(res.data);
      accessToken.value = parseToken(res);
      console.log(accessToken.value);

      isLoggedIn.value = true;

      router.push({ name: 'mainview' });
      alert('로그인되었습니다.');
      return { success: true, data: accessToken };
    } catch (error) {
      alert('로그인에 실패했습니다.');
      return { success: false, error: error.message };
    }
  };

  // 로그아웃
  const setLogout = () => {
    isLoggedIn.value = false;
    accessToken.value = '';
    router.push({ name: 'mainview' });
  };

  // 모든 회원 조회
  const setUsers = () => {
    axios
      .get(`${URL}/user`, {
        withCredentials: true,
        headers: {
          Authorization: accessToken.value,
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
          Authorization: accessToken.value,
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

  // 회원 이름 검색
  const searchName = (username) => {
    axios
      .get(`${URL}/user/search`, {
        withCredentials: true,
        params: { key: 'user_name', word: username },
        headers: {
          Authorization: accessToken.value,
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

  // 회원 정보 수정
  const updateUser = () => {
    axios
      .put(`${URL}/user`, user.value, {
        withCredentials: true,
        headers: {
          Authorization: accessToken.value,
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
          Authorization: accessToken.value,
        },
      })
      .then(() => {
        users.value = users.value.filter((u) => u.userId !== userid);
        userCnt.value = users.value.length;
        router.push({ name: 'userList' });
      });
  };

  return {
    withCredentials,
    accessToken,
    isLoggedIn,
    users,
    searchUsers,
    user,
    userCnt,
    registrationData,
    searchUserCnt, // 애는 회원 이름 검색에서 쓸 일이 있나 추후 확인
    createUser,
    deleteUser,
    setLogout,
    setUser,
    searchName,
    updateUser,
    setLoginUser,
    setUsers,
    saveTechStack,
  };
});
