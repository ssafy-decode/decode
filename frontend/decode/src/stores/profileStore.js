import { ref, computed } from 'vue';
import { defineStore } from 'pinia';
import router from '@/router';
import axios from '@/utils/common-axios';
import { useUserStore } from './userStore';
import { useTagStore } from './tagStore';

const useProfileStore = defineStore(
  'useProfileStore',
  () => {
    // 스토어
    const userStore = useUserStore();
    const tagStore = useTagStore();

    // 배열
    const profile = ref([]); // 로그인 유저가 아닌 다른 유저 프로필 data 저장 목록
    const selectedTags = ref([]); // 수정한 기술 태그 목록
    const qList = ref([]); // 질문 목록
    const aList = ref([]); // 답변이 작성된 질문 목록
    const rankList = ref([]); // 경험치순 모든 유저 목록 조회

    // 개수
    const qListLength = ref(0); // 질문 목록 총 개수
    const aListLength = ref(0); // 답변 목록 총 개수
    const selectedCount = ref(0); // 답변 채택 총 개수

    // T/F
    const mypwd = ref(false); // 회원 정보 수정 전 비번 확인 일치 여부 T/F

    // 개별 요소
    const userProfileImgURL = ref(''); // 유저 프로필 사진 url

    // 해당 유저 프로필 조회 (id, exp, point, coin, nickname, tier, profileImg)
    const setUserProfile = async (userid) => {
      await axios
        .get(`/profile/${userid}`)
        .then((res) => {
          if (res.data.data) {
            userStore.accessToken = userStore.parseToken(res);
            if (res.data.status === 'OK') {
              profile.value = res.data.data;
            }
          } else {
            router.push({ name: 'mainview' });
            alert('접근 권한이 없습니다.');
          }
        })
        .catch((error) => {
          if (error.response && error.response.status === 401) {
            router.push({ name: 'mainview' });
            alert('접근 권한이 없습니다.');
          }
        });
    };

    // 해당 유저 프로필에서 그 사람이 올린 질문 목록 조회
    const setQList = async (userid) => {
      await axios
        .get(`/question/list/${userid}`)
        .then((res) => {
          userStore.accessToken = userStore.parseToken(res);
          if (res.data.status === 'OK') {
            qList.value = res.data.data.list;
            qListLength.value = res.data.data.size;
          }
        })
        .catch((error) => {
          if (error.response && error.response.status === 401) {
            router.push({ name: 'mainview' });
            alert('접근 권한이 없습니다.');
          }
        });
    };

    // 해당 유저 프로필에서 그 사람이 작성한 답변이 들어 있는 질문 목록 조회
    const setAList = async (userid) => {
      await axios
        .get(`/answer/list/${userid}`)
        .then((res) => {
          userStore.accessToken = userStore.parseToken(res);
          if (res.data.status === 'OK') {
            aList.value = res.data.data.list;
            aListLength.value = res.data.data.size;
          }
        })
        .catch((error) => {
          if (error.response && error.response.status === 401) {
            router.push({ name: 'mainview' });
            alert('접근 권한이 없습니다.');
          }
        });
    };

    // 회원 수정 전 비밀번호 확인
    const checkPwd = async (password, token) => {
      await axios
        .post(`/confirm`, password, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        })
        .then((res) => {
          userStore.accessToken = userStore.parseToken(res);
          if (res.data.status === 'OK') {
            mypwd.value = res.data.data;
            if (mypwd.value) {
              userStore.setUser(userStore.loginUserId);
              router.push({ name: 'myprofileupdate' });
            } else {
              alert('비밀번호가 일치하지 않습니다.');
              return;
            }
          }
        })
        .catch((error) => {
          alert('비밀번호가 일치하지 않습니다.');
          return;
        });
    };

    // 로그인 유저 프로필 사진 변경 (S3에 등록)
    // (API 2개 호출, /image => 결과물을 /profile/{user_id} 에 있는 profileImg에 대입 후 수정 완료)
    // requestbody: key는 file, 형태는 File, Value는 ssafy.png 처럼 파일로
    // responsebody: status: OK, message: 이미지 업로드 성공, data.url: 그 이미지의 url
    const updateProfileImg = async (img) => {
      await axios.post(`/image`, img).then((res) => {
        userStore.accessToken = userStore.parseToken(res);
        if (res.data.status === 'OK') {
          userProfileImgURL.value = res.data.data.url;
          // 이미지를 /profile/{user_id} 의 profileImg 로 저장시켜야 (아직 작성 중)
        }
      });
    };

    // 로그인 유저 선택한 기술 스택 변경
    const updateTechStack = async (user, token) => {
      await axios
        .patch(
          `/updateUserTag`,
          { userId: user.userId, tagIdList: user.tagIdList },
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          },
        )
        .then((res) => {
          tagStore.setTagNumList(user.userId);
          userStore.accessToken = userStore.parseToken(res);
        });
    };

    // 로그인 유저 비밀번호 변경
    const updatePwd = async (user, token) => {
      await axios
        .post(`/user`, user, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        })
        .then((res) => {
          userStore.accessToken = userStore.parseToken(res);
          if (res.data.status === 'OK') {
            alert('비밀번호가 변경되었습니다.');
            return;
          }
        });
    };

    // 해당 유저 답변 채택 수 가져오기
    const getSelectCnt = async (userid) => {
      await axios.get(`/answer/count/${userid}`).then((res) => {
        userStore.accessToken = userStore.parseToken(res);
        if (res.data.status === 'OK') {
          selectedCount.value = res.data.data.selectedCnt;
        }
      });
    };

    // computed
    const handleUserProfile = computed(() => profile.value);
    const handleQuestions = computed(() => qList.value);
    const handleAnswers = computed(() => aList.value);
    const handleSelectCnt = computed(() => selectedCount.value);
    const handleQuestionsNumber = computed(() => qListLength.value);
    const handleAnswersNumber = computed(() => aListLength.value);
    const handleProfileImg = computed(() => userProfileImgURL.value);
    const handleSelectedTags = computed(() => selectedTags.value);

    // 반환
    return {
      qList,
      aList,
      rankList,
      qListLength,
      aListLength,
      mypwd,
      userProfileImgURL,
      selectedCount,
      selectedTags,
      setUserProfile,
      setQList,
      setAList,
      checkPwd,
      updateProfileImg,
      updateTechStack,
      updatePwd,
      getSelectCnt,
      handleUserProfile,
      handleQuestions,
      handleAnswers,
      handleQuestionsNumber,
      handleAnswersNumber,
      handleProfileImg,
      handleSelectCnt,
      handleSelectedTags,
    };
  },
  {
    persist: true,
  },
);

export { useProfileStore };
