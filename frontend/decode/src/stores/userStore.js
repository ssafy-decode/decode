import { ref } from 'vue';
import { defineStore } from 'pinia';
import router from '@/router';
import axios from 'axios';

// 백엔드 서버 URL로 작성
const URL = 'http://localhost:80/decode';
// const URL = 'http://i10a507.p.ssafy.io/decode';

export const useUserStore = defineStore('user', () => {
  const isLoggedIn = ref(false); // 로그인 여부 확인용 T/F 변수 선언
  const users = ref([]);
  const searchUsers = ref([]);
  const user = ref(null);
  const userCnt = ref(0);
  const searchUserCnt = ref(0);
  const accessToken = ref('');
  const userId = ref('');
  // const withCredentials = ref(false);

  // responseBody에서 토큰 값을 추출
  const parseToken = (response) => {
    console.log('Response:', response);

    if (response.data && response.data.data) {
      return response.data.data.substring(8);
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

        console.log('출력되나', res.data);
        console.log('이게맞나', res.data.data);
        console.log('아니면이건가', res.data.value);

        if (response.status === 'OK') {
          userId.value = response.data;

          console.log('확인', userId.value);

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
      if (!userId.value) {
        console.error('User registration data is missing.');
        return;
      }

      // 여기서부터 추후 수정할 예정 (변수명도)
      console.log('RegistrationData:', registrationData.value);
      console.log('RegistrationData.email:', registrationData.value.email);

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
      console.error('Error saving tech stack:', error.message);
    }
  };

  // 토큰 + 로그인
  const setLoginUser = async (loginuser) => {
    try {
      const res = await axios.post(`${URL}/login`, loginuser); // loginuser는 아직 안 쓰이지만, 로그인 상태로 data 불러와야할 때를 대비해서 일단은 집어넣음
      accessToken.value = parseToken(res);
      isLoggedIn.value = true;

      console.log(res.data);
      console.log(accessToken.value);

      router.push({ name: 'mainview' });
      alert('로그인되었습니다.');
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

  // 이메일 찾기
  const findUserEmail = (user) => {
    console.log('잘 작동한다잉'); // 함수 추가하기
    router.push('/foundemail');
  };

  // 비밀번호 찾기
  const findUserPwd = (user) => {
    console.log('잘 작동한다잉'); // 함수 추가하기
    router.push('/foundpwd');
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
    // withCredentials,
    accessToken,
    isLoggedIn,
    users,
    searchUsers,
    user,
    userCnt,
    userId,
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
