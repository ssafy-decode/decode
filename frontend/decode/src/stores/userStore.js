import { ref } from 'vue';
import { defineStore } from 'pinia';
import router from '@/router';
import axios from 'axios';

const URL = process.env.VUE_APP_BACKEND_URL;

export const useUserStore = defineStore(
  'user',
  () => {
    const users = ref([]);
    const searchUsers = ref([]);
    const tagIdList = ref([]);
    const loginUserProfile = ref([]);
    const userProfile = ref([]);
    const followerList = ref([]);
    const followingList = ref([]);

    const user = ref(null);
    const userId = ref(''); // 가입 시 회원 번호
    const userCnt = ref(0); // 아직은 쓸 일 없지만 회원 총 수
    const searchUserCnt = ref(0); // 아직은 쓸 일 없지만 검색한 회원 총 수

    const accessToken = ref(''); // 파싱된 토큰 값 (활용 시 앞에 'Bearer '을 붙일 것!)

    // 로그인 유저 정보
    const isLoggedIn = ref(false); // 로그인 여부 T/F
    const loginUserId = ref(0); // 로그인 유저 회원 번호
    // const loginUserPwd = ref(null); // 디버깅된 로그인 유저 비밀번호 (비밀번호 확인용)
    const loginUserName = ref('');
    const loginUserNickName = ref('');
    const loginUserBirthday = ref('');
    const loginUserEmail = ref('');
    const loginUserPhone = ref(0);
    const foundEmail = ref(''); // 이메일 찾기에서의 회원 이메일
    const mypwd = ref(false); // 수정 전 비번 확인에서의 T/F

    // 태그명 & 태그번호 매칭
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

    // 회원 가입 1단계
    const createUser = (user) => {
      axios
        .post(`${URL}/regist`, user, {
          withCredentials: true,
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
        const res = await axios.post(
          `${URL}/addUserTag`,
          { userId: userId.value, tagIdList: tagNums },
          {
            withCredentials: true,
            headers: {
              'Content-Type': 'application/json',
            },
          },
        );
        if (res.data.status === 'OK') {
          tagIdList.value.push(...tagNums);
        } else {
          throw new Error('Failed to save tech stack');
        }
      } catch (error) {
        console.error('Error saving tech stack:', error.message);
      }
    };

    // 토큰 + 로그인
    // 추후 쿠키에 저장할 것 <= 새로고침해도 로그인 안 풀리게!
    const setLoginUser = async (loginuser) => {
      try {
        const res = await axios.post(`${URL}/login`, loginuser);
        accessToken.value = parseToken(res);
        console.log(accessToken.value); // 테스트를 위해 꼭 필요한 토큰값
        isLoggedIn.value = true; // 로그인 여부
        loginUserId.value = res.data.data; // 로그인 사용자 번호
        // loginUserPwd.value = loginuser.password; // 로그인 사용자 비번 (디버깅된 ver.)
        router.push({ name: 'mainview' });
        return { success: true, data: accessToken };
      } catch (error) {
        return { success: false, error: error.message };
      }
    };

    // responseBody에서 토큰 값 추출
    const parseToken = (response) => {
      if (response.headers.authorization) return response.headers.authorization.split(' ')[1];
      else if (accessToken.value) return accessToken.value;
      else {
        console.error('Token is not present in the response data');
        return accessToken.value;
      }
    };

    // 로그아웃
    const setLogout = () => {
      isLoggedIn.value = false;
      accessToken.value = '';
      router.push({ name: 'mainview' });
    };

    // 로그인 사용자 프로필 조회
    // (exp, point, coin, nickname, tier, profileImg)
    const myProfile = () => {
      axios
        .get(`${URL}/info`, {
          withCredentials: true,
          headers: {
            Authorization: `Bearer ${accessToken.value}`,
          },
        })
        .then((res) => {
          accessToken.value = parseToken(res);
          const response = res.data;
          if (response.status === 'OK') {
            loginUserProfile.value = response.data;
          } else {
            console.log('Failed to get loginuser profile');
          }
        })
        .catch((error) => {
          console.error('Error fetching loginuser profile:', error);
        });
    };

    // 프로필에서 올린 질문 목록 조회 (보류)

    // 프로필에서 작성한 답변 목록 조회 (보류)

    // 프로필에서 팔로워 목록 조회
    const setFollowerList = (userid) => {
      axios
        .get(`${URL}/followerlist/${userid}`, {
          withCredentials: true,
        })
        .then((res) => {
          accessToken.value = parseToken(res);
          const response = res.data;
          followerList.value = response.data;
        })
        .catch((error) => {
          console.error('Error fetching followerlist:', error);
        });
    };

    // 프로필에서 팔로잉 목록 조회
    const setFollowingList = (userid) => {
      axios
        .get(`${URL}/followinglist/${userid}`, {
          withCredentials: true,
        })
        .then((res) => {
          accessToken.value = parseToken(res);
          const response = res.data;
          followingList.value = response.data;
        })
        .catch((error) => {
          console.error('Error fetching followinglist:', error);
        });
    };

    // 특정 회원을 팔로우하기
    // const checkPwd = (towhom) => {
    //   axios
    //     .post(`${URL}/confirm`, pwd, {
    //       withCredentials: true,
    //       headers: {
    //         Authorization: `Bearer ${accessToken.value}`,
    //       },
    //     })
    //     .then((res) => {
    //       accessToken.value = parseToken(res);
    //       const response = res.data;
    //       if (response.status === 'OK') {
    //         mypwd.value = response.data;
    //         if (mypwd.value) {
    //           router.push({ name: 'myprofileupdate' });
    //         } else {
    //           alert('비밀번호가 일치하지 않습니다.');
    //           return;
    //         }
    //       }
    //     })
    //     .catch((error) => {
    //       console.error('Error confirming user:', error);
    //     });
    // };

    // 팔로우 여부 확인

    // 팔로우하고 있는 특정 회원을 팔로우취소

    // 회원 수정 전 비밀번호 확인
    const checkPwd = (pwd) => {
      axios
        .post(`${URL}/confirm`, pwd, {
          withCredentials: true,
          headers: {
            Authorization: `Bearer ${accessToken.value}`,
          },
        })
        .then((res) => {
          accessToken.value = parseToken(res);
          const response = res.data;
          if (response.status === 'OK') {
            mypwd.value = response.data;
            if (mypwd.value) {
              router.push({ name: 'myprofileupdate' });
            } else {
              alert('비밀번호가 일치하지 않습니다.');
              return;
            }
          }
        })
        .catch((error) => {
          console.error('Error confirming user:', error);
        });
    };

    // 선택한 기술 스택 변경
    const updateTechStack = (updateduser) => {
      const updatedTagNums = updateduser.tagIdList.map((item) => tagNum[item]);
      updateduser.tagIdList = updatedTagNums;
      axios
        .patch(`${URL}/updateUserTag`, updateduser, {
          withCredentials: true,
          headers: {
            Authorization: `Bearer ${accessToken.value}`,
          },
        })
        .then((res) => {
          accessToken.value = parseToken(res);
          const response = res.data;
          if (response.status === 'OK') {
            tagIdList.value = updateduser.tagIdList.value;
            router.push({ name: 'myprofile' });
          }
        })
        .catch((error) => {
          console.error('Error updating user:', error);
        });
    };

    // 로그인 유저 비밀번호 변경
    const updatePwd = (user) => {
      axios
        .post(`${URL}/user`, user, {
          withCredentials: true,
          headers: {
            Authorization: `Bearer ${accessToken.value}`,
          },
        })
        .then((res) => {
          accessToken.value = parseToken(res);
          const response = res.data;
          if (response.status === 'OK') {
            router.push({ name: 'myprofile' });
          }
        })
        .catch((error) => {
          console.error('Error updating user:', error);
        });
    };

    // 모든 회원 조회 (deprecated)
    const setUsers = () => {
      axios
        .get(`${URL}/user`, {
          withCredentials: true,
          headers: {
            Authorization: `Bearer ${accessToken.value}`,
          },
        })
        .then((res) => {
          accessToken.value = parseToken(res);
          users.value = res.data;
        })
        .catch((error) => {
          console.error('Error fetching users:', error);
        });
    };

    // 다른 사용자 프로필 조회
    // (exp, point, coin, nickname, tier, profileImg)
    const setUserProfile = (userid) => {
      axios
        .get(`${URL}/profile/${userid}`, {
          withCredentials: true,
        })
        .then((res) => {
          accessToken.value = parseToken(res);
          const response = res.data;
          if (response.status === 'OK') {
            userProfile.value = response.data;
          } else {
            console.log('Failed to get current user profile');
          }
        })
        .catch((error) => {
          console.error('Error fetching user profile:', error);
        });
    };

    // 특정 회원 정보 조회
    // (id, email, 암호화된password, phoneNumber, birth, name, createdTime, updatedTime)
    const setUser = (userid) => {
      axios
        .get(`${URL}/user/${userid}`, {
          withCredentials: true,
        })
        .then((res) => {
          accessToken.value = parseToken(res);
          const response = res.data;
          if (response.status === 'OK') {
            loginUserName.value = response.data.name;
            loginUserBirthday.value = response.data.birth;
            loginUserEmail.value = response.data.email;
            loginUserPhone.value = response.data.phoneNumber;
            user.value = { ...res.data };
          }
        })
        .catch((error) => {
          console.error('Error fetching user data:', error);
        });
    };

    // 이메일 찾기
    const findUserEmail = (user) => {
      axios
        .post(`${URL}/email`, user, {
          withCredentials: true,
        })
        .then((res) => {
          accessToken.value = parseToken(res);
          const response = res.data;
          if (response.status === 'OK') {
            foundEmail.value = response.data;
            router.push({ name: 'foundemail' });
          } else {
            throw new Error('Failed to find user email');
          }
        })
        .catch((error) => {
          console.error('Error finding user email:', error);
        });
    };

    // 비밀번호 찾기
    const findUserPwd = (user) => {
      axios
        .post(`${URL}/password`, user, {
          withCredentials: true,
        })
        .then((res) => {
          accessToken.value = parseToken(res);
          const response = res.data;
          if (response.status === 'OK') {
            router.push({ name: 'foundpwd' });
          } else {
            throw new Error('Failed to find user password');
          }
        })
        .catch((error) => {
          console.error('Error finding user password:', error);
        });
    };

    // // 회원 프로필 수정 (보류)
    // const updateUser = (userid, userprofile) => {
    //   axios
    //     .post(`${URL}/profile/${userid}`, userprofile, {
    //       withCredentials: true,
    //       headers: {
    //         Authorization: `Bearer ${accessToken.value}`,
    //       },
    //     })
    //     .then((res) => {
    //       accessToken.value = parseToken(res);
    //       // userprofile 안의 값들을 requestbody로 전송
    //       // responsebody에 status가 OK면 통과, 아니면 실패
    //       // 아래부터는 수정해야 함
    //       // 기존 사용자 정보 업데이트
    //       const index = users.value.findIndex((u) => u.userId === user.value.userId);
    //       if (index !== -1) {
    //         users.value[index] = { ...user.value };
    //       }
    //       router.push({ name: 'myprofile' });
    //     })
    //     .catch((error) => {
    //       console.error('Error updating user:', error);
    //     });
    // };

    // // 회원 삭제/탈퇴
    // const deleteUser = (userid) => {
    //   axios
    //     .delete(`${URL}/user/${userid}`, {
    //       withCredentials: true,
    //       headers: {
    //         Authorization: `Bearer ${accessToken.value}`,
    //       },
    //     })
    //     .then(() => {
    //         accessToken.value = parseToken(res);
    //       users.value = users.value.filter((u) => u.userId !== userid);
    //       userCnt.value = users.value.length;
    //       router.push({ name: 'userList' });
    //     });
    // };

    // // 회원 이름 검색
    // const searchName = (username) => {
    //   axios
    //     .get(`${URL}/user/search`, {
    //       withCredentials: true,
    //       params: { key: 'user_name', word: username },
    //       headers: {
    //         Authorization: `Bearer ${accessToken.value}`,
    //       },
    //     })
    //     .then((res) => {
    // accessToken.value = parseToken(res);
    //       searchUsers.value = res.data;
    //       searchUserCnt.value = searchUsers.value.length;
    //     })
    //     .catch(() => {
    //       alert('검색 대상을 찾지 못했습니다.');
    //     });
    // };

    return {
      accessToken,
      isLoggedIn,
      users,
      searchUsers,
      tagIdList,
      loginUserProfile,
      followerList,
      followingList,
      tagNum,
      user,
      userCnt,
      userId,
      loginUserId,
      // loginUserPwd,
      loginUserName,
      loginUserNickName,
      loginUserBirthday,
      loginUserEmail,
      loginUserPhone,
      foundEmail,
      mypwd,
      searchUserCnt, // 회원 이름 검색에서 쓸 일이 있다면 사용할 예정
      createUser,
      // deleteUser,
      setLogout,
      setUser,
      setUserProfile,
      findUserEmail,
      findUserPwd,
      myProfile,
      setFollowerList,
      setFollowingList,
      checkPwd,
      // searchName,
      updatePwd,
      // updateUser,
      setLoginUser,
      setUsers,
      saveTechStack,
      updateTechStack,
    };
  },
  {
    persist: {
      storage: sessionStorage, // 새로고침해도 로그인 풀리지 않게 (추후 쿠키로 수정할 수도 있음)
    },
  },
);
