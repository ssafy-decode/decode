import { ref, computed } from 'vue';
import { defineStore } from 'pinia';
import router from '@/router';
import axios from '@/utils/common-axios';
import { useTagStore } from './tagStore';

const useUserStore = defineStore(
  'useUserStore',
  () => {
    // 스토어
    const tagStore = useTagStore();
    // 토큰 정보
    const accessToken = ref(''); // 파싱된 토큰 값 (활용 시 앞에 'Bearer '을 붙일 것!)
    // 배열
    const users = ref([]); // 전체 회원 목록
    const user = ref([]); // 해당 유저의 id, email, password, phoneNumber, birth, name, createdTime, updatedTime 저장한 목록
    const loginUser = ref([]); // 로그인 유저의 id, email, password, phoneNumber, birth, name, createdTime, updatedTime 저장한 목록
    const myProfile = ref({});

    // 값
    const registId = ref(''); // 회원 가입 2단계에 필요한 회원 번호
    const isLoggedIn = ref(false); // 로그인 여부 T/F
    const loginUserId = ref(0); // 로그인 유저 회원 번호
    const foundEmail = ref(''); // 이메일 찾기에서의 이메일

    // 함수
    // 회원 가입 1단계 (1): 일반 가입
    const createUser = async (user) => {
      await axios.post(`/regist`, user).then((res) => {
        if (res.data.status === 'OK') {
          users.value.push(res.data.data);
          registId.value = res.data.data;
          router.push({ name: 'techstack' });
        }
      });
    };

    // 회원 가입 2단계: 선택한 기술 스택 저장
    const saveTechStack = async (selectedTechStack) => {
      // 1단계 정보(registId)가 비어있을 경우 에러 처리
      if (!registId.value) {
        console.error('userId가 없습니다.');
        return;
      }
      const tagNums = selectedTechStack.map((item) => tagStore.tagNum[item]);
      const res = await axios.post(`/addUserTag`, { userId: registId.value, tagIdList: tagNums });
      if (res.data.status === 'OK') {
        tagStore.tagIdList.value.push(...tagNums);
      }
    };

    // 토큰 + 로그인
    const setLoginUser = async (loginuser) => {
      try {
        const res = await axios.post(`/login`, loginuser);

        if (res.data.status === 'OK') {
          isLoggedIn.value = true;
          setToken(parseToken(res));
          setLoginUserId(res.data.data);
          setMyProfile();

          router.push({ name: 'mainview' });
        } else {
          alert('로그인에 실패했습니다.');
        }
      } catch (error) {
        console.error('Login error:', error);
        return;
      }
    };
    const setMyProfile = async () => {
      axios.get(`/profile/${loginUserId.value}`).then((res) => {
        myProfile.value = res.data.data;
      });
    };

    // responseBody에서 토큰 값 추출
    const parseToken = (response) => {
      if (response.data && response.headers && response.headers.authorization) {
        const newToken = response.headers.authorization.substring(7); // 파싱한 새 accessToken 값 갱신
        return newToken;
      }
      return accessToken.value;
    };

    // 로그아웃
    const setLogout = async () => {
      isLoggedIn.value = false;
      router.push({ name: 'mainview' });
      if (!accessToken.value) return;
      await axios
        .post(
          `/logout`,
          {},
          {
            headers: {
              Authorization: `Bearer ${accessToken.value}`,
            },
          },
        )
        .then((res) => {
          accessToken.value = '';
        });
    };

    // 특정 회원 정보 조회
    // (id, email, 암호화된password, phoneNumber, birth, name, createdTime, updatedTime)
    const setUser = async (userid) => {
      await axios.get(`/user/${userid}`).then((res) => {
        accessToken.value = parseToken(res);
        if (res.data.status === 'OK') {
          if (userid === loginUserId.value) {
            loginUser.value = res.data.data;
          } else {
            user.value = res.data.data;
          }
        }
      });
    };

    // 이메일 찾기
    const findUserEmail = async (user) => {
      await axios.post(`/email`, user).then((res) => {
        accessToken.value = parseToken(res);
        if (res.data.status === 'OK') {
          foundEmail.value = res.data.data;
          router.push({ name: 'foundemail' });
        }
      });
    };

    // 비밀번호 찾기
    const findUserPwd = async (user) => {
      await axios.post(`/password`, user).then((res) => {
        accessToken.value = parseToken(res);
        if (res.data.status === 'OK') {
          router.push({ name: 'foundpwd' });
        }
      });
    };

    // // 모든 회원 조회 (deprecated)
    // const setUsers = async () => {
    //   await axios
    //     .get(`/user`, {
    //       headers: {
    //         Authorization: `Bearer ${accessToken.value}`,
    //       },
    //     })
    //     .then((res) => {
    //       accessToken.value = parseToken(res);
    //       users.value = res.data;
    //     });
    // };
    const setLoginUserId = (id) => {
      loginUserId.value = id;
    };
    const setToken = (token) => {
      accessToken.value = token;
    };

    // computed
    const handleUsers = computed(() => users.value);
    const handleUser = computed(() => user.value);
    const handleLoginUser = computed(() => loginUser.value);
    const handleLoginUserId = computed(() => loginUserId.value);
    const handleAccessToken = computed(() => accessToken.value);
    const handleMyprofile = computed(() => myProfile.value);

    // 반환
    return {
      accessToken,
      users,
      user,
      loginUser,
      registId,
      isLoggedIn,
      loginUserId,
      foundEmail,
      createUser,
      setLoginUserId,
      saveTechStack,
      setLoginUser,
      parseToken,
      setLogout,
      setMyProfile,
      setUser,
      setToken,
      findUserEmail,
      findUserPwd,
      // setUsers,
      handleUsers,
      handleUser,
      handleLoginUser,
      handleLoginUserId,
      handleAccessToken,
      handleMyprofile,
    };
  },
  {
    // 새로고침해도 로그인 풀리지 않게 설정
    persist: {},
  },
);

export { useUserStore };
