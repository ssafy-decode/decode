import { ref, computed } from 'vue';
import { defineStore } from 'pinia';
import router from '@/router';
import axios from '@/utils/common-axios';
import { useTagStore } from './tagStore';
import { useProfileStore } from './profileStore';

const useUserStore = defineStore(
  'useUserStore',
  () => {
    // 스토어
    const profileStore = useProfileStore();
    const tagStore = useTagStore();

    // 토큰 정보
    const accessToken = ref(''); // 파싱된 토큰 값 (활용 시 앞에 'Bearer '을 붙일 것!)

    // 배열
    const users = ref([]); // 전체 회원 목록
    const user = ref([]); // 해당 유저의 id, email, password, phoneNumber, birth, name, createdTime, updatedTime 저장한 목록
    const loginUser = ref([]); // 로그인 유저의 id, email, password, phoneNumber, birth, name, createdTime, updatedTime 저장한 목록

    // 값
    const registId = ref(0); // 회원 가입 2단계에 필요한 회원 번호
    const isLoggedIn = ref(false); // 로그인 여부 T/F
    const loginUserId = ref(0); // 로그인 유저 회원 번호
    const foundEmail = ref(''); // 이메일 찾기에서의 이메일

    // 함수
    // 회원 가입 1단계: 일반 가입
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
        accessToken.value = parseToken(res);
        if (res.data.status === 'OK') {
          isLoggedIn.value = true;
          loginUserId.value = res.data.data;
          router.push({ name: 'mainview' });
          return accessToken.value;
        }
      } catch (error) {
        alert('로그인에 실패했습니다.');
        console.error('Login error:', error);
        return;
      }
    };

    // responseBody에서 토큰 값 추출
    const parseToken = (response) => {
      if (response.data && response.headers && response.headers.authorization) {
        const newToken = response.headers.authorization.substring(7); // 파싱한 새 accessToken 값 갱신
        console.log('기존 토큰:', accessToken.value);
        console.log('새 토큰:', newToken); // 헤더에 넣어 테스트할 토큰 값
        if (newToken === null) {
          // 새 토큰 값 없으면 기존 토큰 값 유지
          return accessToken.value;
        }
        accessToken.value = newToken;
        return accessToken.value;
      }
    };

    // 로그아웃
    const setLogout = async () => {
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
          if (res.data.status === 'OK') {
            // session storage에 저장된 로그인 관련 data 초기화
            isLoggedIn.value = false;
            accessToken.value = '';
            loginUserId.value = '';
            user.value = [];
            profileStore.userProfile.value = [];
            // session storage에서 모든 store 데이터 제거 (작동하는지 불확실 => 401에러)
            sessionStorage.removeItem('pinia:useProfileStore');
            sessionStorage.removeItem('pinia:useQuestionStore');
            sessionStorage.removeItem('pinia:useUserStore');
            router.push({ name: 'mainview' });
          }
        });
    };

    // 특정 회원 정보 조회
    // (id, email, 암호화된password, phoneNumber, birth, name, createdTime, updatedTime)
    const setUser = async (userid) => {
      await axios.get(`/user/${userid}`).then((res) => {
        accessToken.value = parseToken(res);
        if (res.data.status === 'OK') {
          if (userid === loginUserId) {
            // 로그인 유저와 일치할 경우
            // loginUserName.value = res.data.data.name;
            // loginUserBirthday.value = res.data.data.birth;
            // loginUserEmail.value = res.data.data.email;
            // loginUserPhone.value = res.data.data.phoneNumber;
            // loginUser.value = { ...res.data };
            loginUser.value = res.data.data;
          } else {
            // 그 외일 경우
            // userName.value = res.data.data.name;
            // userBirthday.value = res.data.data.birth;
            // userEmail.value = res.data.data.email;
            // userPhone.value = res.data.data.phoneNumber;
            // user.value = { ...res.data };
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

    // computed
    const handleUsers = computed(() => users.value);
    const handleUser = computed(() => user.value);
    const handleLoginUser = computed(() => loginUser.value);

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
      saveTechStack,
      setLoginUser,
      parseToken,
      setLogout,
      setUser,
      findUserEmail,
      findUserPwd,
      // setUsers,
      handleUsers,
      handleUser,
      handleLoginUser,
    };
  },
  {
    // 새로고침해도 로그인 풀리지 않게 설정
    persist: {
      storage: sessionStorage,
    },
  },
);

export { useUserStore };
