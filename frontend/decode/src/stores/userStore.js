import { ref } from 'vue';
import { defineStore } from 'pinia';
import router from '@/router';
import axios from '@/utils/common-axios';

export const useUserStore = defineStore(
  'user',
  () => {
    // 배열
    const users = ref([]); // 회원 목록
    const searchUsers = ref([]); // 아직은 쓸 일 없지만 검색된 회원 목록
    const tagIdList = ref([]); // 선택한 기술 태그 목록
    const loginUserProfile = ref([]); // 로그인 유저 프로필 data 저장 목록
    const userProfile = ref([]); // 특정 유저 프로필 data 저장 목록
    const qList = ref([]); // 질문 목록
    const qListLength = ref(0); // 질문 목록 총 개수
    const aList = ref([]); // 답변이 작성된 질문 목록
    const aListLength = ref(0); // 답변 목록 총 개수
    const followerList = ref([]); // 팔로워 목록
    const followingList = ref([]); // 팔로잉 목록
    const rankList = ref([]); // 경험치순 모든 유저 목록 조회

    // 각 회원 정보
    const userProfileImg = ref(''); // 유저 프로필 사진 url
    const updatedUserProfileImg = ref(''); // 변경한 유저 프로필 사진 url
    const user = ref(null);
    const userId = ref(''); // 가입 시 회원 번호
    const userCnt = ref(0); // 아직은 쓸 일 없지만 회원 총 수
    const searchUserCnt = ref(0); // 아직은 쓸 일 없지만 검색한 회원 총 수

    // 토큰 정보
    const accessToken = ref(''); // 파싱된 토큰 값 (활용 시 앞에 'Bearer '을 붙일 것!)

    // 로그인 유저 정보
    const isLoggedIn = ref(false); // 로그인 여부 T/F
    const loginUserId = ref(0); // 로그인 유저 회원 번호
    const loginUserName = ref(''); // 로그인 유저 이름
    const loginUserNickName = ref(''); // 로그인 유저 닉네임
    const loginUserBirthday = ref(''); // 로그인 유저 생년월일 6자리
    const loginUserEmail = ref(''); // 로그인 유저 이메일
    const loginUserPhone = ref(0); // 로그인 유저 전화번호 뒷 4자리
    const foundEmail = ref(''); // 이메일 찾기에서의 회원 이메일
    const mypwd = ref(false); // 수정 전 비번 확인에서의 T/F
    const isFollow = ref(false); // 로그인 유저가 그 대상을 팔로우했는지 여부 T/F

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

    // 태그번호 & 태그명 역매칭
    const tagName = {
      1: 'python',
      2: 'java',
      3: 'C++',
      4: 'javascript',
      5: 'django',
      6: 'spring',
      7: 'spring boot',
      8: 'kotlin',
      9: 'sql',
      10: 'react',
      11: 'vue',
      12: 'C#',
    };

    // 회원 가입 1단계
    const createUser = (user) => {
      axios
        .post(`/regist`, user, {
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
          console.error('Error:', error);
        });
    };

    // 소셜 로그인 (Github oauth)
    //(경로: /auth/github  파라미터: code(string)  응답: status가 100 CONTINUE면 2단계로 페이지 넘어가도록)
    //다시 확인해볼 것 (아직 미완성)

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
          `/addUserTag`,
          { userId: userId.value, tagIdList: tagNums },
          {
            withCredentials: true,
          },
        );
        if (res.data.status === 'OK') {
          tagIdList.value.push(...tagNums);
        } else {
          throw new Error('Failed to save tech stack');
        }
      } catch (error) {
        console.error('Error:', error.message);
      }
    };

    // 토큰 + 로그인
    // 추후 쿠키에 저장할 것 <= 새로고침해도 로그인 안 풀리게!
    const setLoginUser = async (loginuser) => {
      try {
        const res = await axios.post(`/login`, loginuser);
        accessToken.value = parseToken(res);

        isLoggedIn.value = true; // 로그인 여부
        loginUserId.value = res.data.data; // 로그인 사용자 번호
        router.push({ name: 'mainview' });
        return { success: true, data: accessToken };
      } catch (error) {
        return { success: false, error: error.message };
      }
    };

    // responseBody에서 토큰 값 추출
    const parseToken = (response) => {
      if (response.data && response.headers && response.headers.authorization) {
        const newToken = response.headers.authorization.substring(7); // 파싱한 새 accessToken 값 갱신
        console.log(newToken);
        if (newToken === null) {
          // 새 토큰 값 없으면 기존 토큰 값 유지
          return accessToken.value;
        }
        return newToken;
      } else {
        return accessToken.value;
      }
    };

    // 로그아웃
    const setLogout = () => {
      axios
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
          const response = res.data;
          if (response.status === 'OK') {
            isLoggedIn.value = false;
            accessToken.value = '';
            router.push({ name: 'mainview' });
          }
        })
        .catch((error) => {
          console.error('Error:', error);
        });
    };

    // 로그인 사용자 프로필 조회
    // (exp, point, coin, nickname, tier, profileImg)
    const myProfile = () => {
      axios
        .get(`/info`, {
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
            console.log('디버깅용', loginUserProfile.value); // 디버깅용
          } else {
            console.log('Failed to get loginuser profile');
          }
        })
        .catch((error) => {
          console.error('Error:', error);
        });
    };

    // 해당 유저 프로필에서 그 사람이 올린 질문 목록 조회
    const setQList = (userid) => {
      axios
        .get(`/question/list/${userid}`, {
          withCredentials: true,
        })
        .then((res) => {
          accessToken.value = parseToken(res);
          const response = res.data;
          if (response.status === 'OK') {
            qList.value = response.data.list;
            qListLength.value = response.data.size;
          }
        })
        .catch((error) => {
          console.error('Error:', error);
        });
    };

    // 해당 유저 프로필에서 그 사람이 작성한 답변이 들어 있는 질문 목록 조회
    const setAList = (userid) => {
      axios
        .get(`/question/list/${userid}`, {
          withCredentials: true,
        })
        .then((res) => {
          accessToken.value = parseToken(res);
          const response = res.data;
          if (response.status === 'OK') {
            aList.value = response.data.list;
            aListLength.value = response.data.size;
          }
        })
        .catch((error) => {
          console.error('Error:', error);
        });
    };

    // 프로필에서 팔로워 목록 조회
    const setFollowerList = (userid) => {
      axios
        .get(`/followerlist/${userid}`, {
          withCredentials: true,
        })
        .then((res) => {
          accessToken.value = parseToken(res);
          const response = res.data;
          followerList.value = response.data;
        })
        .catch((error) => {
          console.error('Error:', error);
        });
    };

    // 프로필에서 팔로잉 목록 조회
    const setFollowingList = (userid) => {
      axios
        .get(`/followinglist/${userid}`, {
          withCredentials: true,
        })
        .then((res) => {
          accessToken.value = parseToken(res);
          const response = res.data;
          console.log(response);
          console.log('테스트중입니다');
          followingList.value = response.data;
        })
        .catch((error) => {
          console.error('Error:', error);
        });
    };

    // 특정 회원을 팔로우하기
    // (이미 팔로우한 사용자 X, 본인 X)
    const toFollow = (userid) => {
      axios
        .post(`/follow/${userid}`, {
          withCredentials: true,
          headers: {
            Authorization: `Bearer ${accessToken.value}`,
          },
        })
        .then((res) => {
          accessToken.value = parseToken(res);
          const response = res.data;
          if (response.status !== 'BAD_REQUEST') {
            // 요청이 정상인 경우
            if (userid === loginUserId) {
              // 본인 자신을 팔로우 시 X
              console.log('본인을 팔로우할 수 없습니다.');
              return;
            }
            // 팔로우 여부 확인 (T/F)
            isFollowOrNot(userid);
            if (isFollow.value) {
              // 이미 팔로우한 사람을 팔로우 시 X
              console.log('이미 팔로우한 사용자는 팔로우할 수 없습니다.');
              return;
            } else {
              // 정상적으로 팔로우 시
              console.log('팔로우 성공');
            }
          } else {
            console.log('BAD_REQUEST');
            return;
          }
        })
        .catch((error) => {
          console.error('Error:', error);
        });
    };

    // 팔로우 여부 확인 (T/F로 반환)
    const isFollowOrNot = (userid) => {
      axios
        .get(`/isfollow/${userid}`, {
          withCredentials: true,
          headers: {
            Authorization: `Bearer ${accessToken.value}`,
          },
        })
        .then((res) => {
          accessToken.value = parseToken(res);
          isFollow.value = res.data;
          console.log('팔로우 여부 조회 성공');
        })
        .catch((error) => {
          console.error('Error:', error);
        });
    };

    // 팔로우하고 있는 특정 회원을 팔로우취소
    const unFollow = (userid) => {
      axios
        .delete(`/follow/${userid}`, {
          withCredentials: true,
          headers: {
            Authorization: `Bearer ${accessToken.value}`,
          },
        })
        .then((res) => {
          accessToken.value = parseToken(res);
          isFollowOrNot(userid);
          const response = res.data;
          if (response.status !== 'BAD_REQUEST') {
            // 정상적으로 팔로우되어 있는 상태라면
            if (isFollow.value) {
              isFollow.value = false;
              console.log('팔로우 취소 성공');
            } else {
              console.log('팔로우 취소 불가');
              return;
            }
          } else {
            alert('BAD_REQUEST');
            return;
          }
        })
        .catch((error) => {
          console.error('Error:', error);
        });
    };

    // 회원 수정 전 비밀번호 확인
    const checkPwd = (pwd) => {
      axios
        .post(`/confirm`, pwd, {
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
          console.error('Error:', error);
        });
    };

    // 로그인 유저 프로필 사진 변경 (S3에 등록) => 지금은 작동 안 함
    // (API 2개 호출, /image => 결과물을 /profile/{user_id} 에 있는 profileImg에 대입 후 수정 완료)
    // requestbody: key는 file, 형태는 File, Value는 ssafy.png 처럼 파일로
    // responsebody: status: OK, message: 이미지 업로드 성공, data.url: 그 이미지의 url
    const updateProfileImg = (img) => {
      console.log('작동 확인');
      axios
        .post(`/image`, img, {
          withCredentials: true,
        })
        .then((res) => {
          accessToken.value = parseToken(res);
          const response = res.data;
          if (response.status === 'OK') {
            updatedUserProfileImg.value = response.data.url;
            // 업로드된 이미지를 /profile/{user_id} 의 profileImg 로 저장시켜야'
            setUserProfile(loginUserId);
            // setUserProfile 함수 작동 시 data.profileImg에 갱신할 수 있나?
          }
        })
        .catch((error) => {
          console.error('Error:', error);
        });
    };

    // 로그인 유저 선택한 기술 스택 변경
    const updateTechStack = (updateduser) => {
      const updatedTagNums = updateduser.tagIdList.map((item) => tagNum[item]);
      updateduser.tagIdList = updatedTagNums;
      console.log('테스트중', updateduser.tagIdList); // 테스트중 [7,8,9]
      console.log('들어갔나', updatedTagNums); // 들어갔나 [7,8,9]
      axios
        .patch(`/updateUserTag`, updateduser, {
          withCredentials: true,
          headers: {
            Authorization: `Bearer ${accessToken.value}`,
          },
        })
        .then((res) => {
          accessToken.value = parseToken(res);
          const response = res.data;
          if (response.status === 'OK') {
            console.log('정상 작동하는지 확인'); // 출력되고 있음
            // updateduser.tagIdList = updatedTagNums;
            console.log('store에서 마지막 확인', updateduser.tagIdList); // [7,8,9]
          }
        })
        .catch((error) => {
          console.error('Error:', error);
        });
    };

    // 로그인 유저 비밀번호 변경
    const updatePwd = (user) => {
      axios
        .post(`/user`, user, {
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
          console.error('Error:', error);
        });
    };

    // 다른 사용자 프로필 조회
    // (exp, point, coin, nickname, tier, profileImg)
    const setUserProfile = (userid) => {
      axios
        .get(`/profile/${userid}`, {
          withCredentials: true,
        })
        .then((res) => {
          accessToken.value = parseToken(res);
          const response = res.data;
          if (response.status === 'OK') {
            userProfile.value = response.data;
            // 프로필 사진만 변경했을 경우 data.profileImg 에 값 갱신 가능한가??
            userProfileImg.value = updatedUserProfileImg.value;
          } else {
            console.log('Failed to get current user profile');
          }
        })
        .catch((error) => {
          console.error('Error:', error);
        });
    };

    // 특정 회원 정보 조회
    // (id, email, 암호화된password, phoneNumber, birth, name, createdTime, updatedTime)
    const setUser = (userid) => {
      axios
        .get(`/user/${userid}`, {
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
          console.error('Error:', error);
        });
    };

    // 이메일 찾기
    const findUserEmail = (user) => {
      axios
        .post(`/email`, user, {
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
          console.error('Error:', error);
        });
    };

    // 비밀번호 찾기
    const findUserPwd = (user) => {
      axios
        .post(`/password`, user, {
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
          console.error('Error:', error);
        });
    };

    // 경험치순 모든 회원 목록 조회
    const getRank = () => {
      axios
        .get(`/rank`, {
          withCredentials: true,
        })
        .then((res) => {
          accessToken.value = parseToken(res);
          const response = res.data;
          if (response.status === 'OK') {
            rankList.value = response.data;
          }
        })
        .catch((error) => {
          console.error('Error:', error);
        });
    };

    // // 회원 삭제/탈퇴
    // const deleteUser = (userid) => {
    //   axios
    //     .delete(`/user/${userid}`, {
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
    //     .get(`/user/search`, {
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

    // 모든 회원 조회 (deprecated)
    const setUsers = () => {
      axios
        .get(`/user`, {
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
          console.error('Error:', error);
        });
    };

    return {
      accessToken,
      isLoggedIn,
      users,
      searchUsers,
      tagIdList,
      loginUserProfile,
      qList,
      qListLength,
      aList,
      aListLength,
      followerList,
      followingList,
      rankList,
      tagNum,
      tagName,
      userProfileImg,
      updatedUserProfileImg,
      user,
      userCnt,
      userId,
      loginUserId,
      loginUserName,
      loginUserNickName,
      loginUserBirthday,
      loginUserEmail,
      loginUserPhone,
      foundEmail,
      mypwd,
      isFollow,
      searchUserCnt, // 회원 이름 검색에서 쓸 일이 있다면 사용할 예정
      parseToken,
      createUser,
      // deleteUser,
      setLogout,
      setUser,
      setUserProfile,
      updateProfileImg,
      findUserEmail,
      findUserPwd,
      myProfile,
      setQList,
      setAList,
      setFollowerList,
      setFollowingList,
      checkPwd,
      // searchName,
      // updateProfileImg, // 나중에 주석 풀기
      updatePwd,
      // updateUser,
      setLoginUser,
      setUsers,
      saveTechStack,
      updateTechStack,
      toFollow,
      isFollowOrNot,
      unFollow,
      getRank,
    };
  },
  {
    persist: {
      storage: sessionStorage, // 새로고침해도 로그인 풀리지 않게 (추후 쿠키로 수정할 수도 있음)
    },
  },
);
