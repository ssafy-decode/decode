import { createWebHistory, createRouter } from 'vue-router';

// Views
import MainView from '@/views/MainView.vue';
import UserView from '@/views/UserView.vue';
import QuestionBoardView from '@/views/QuestionBoardView.vue';
import QuestionDetailView from '@/views/QuestionDetailView.vue';
import QuestionTitleCreateView from '@/views/QuestionTitleCreateView.vue';
import QuestionCreateView from '@/views/QuestionCreateView.vue';
import AnswerCreateView from '@/views/AnswerCreateView.vue';
import RankView from '@/views/RankView.vue';
import ShopView from '@/views/ShopView.vue';
import AuthenticationRedirectView from '@/views/AuthenticationRedirect.vue';
import ProfileView from '@/views/ProfileView.vue';
import InventoryView from '@/views/InventoryView.vue';
import ErrorView from '@/ErrorView.vue';

// Components
import LoginForm from '@/components/LoginForm.vue';
import UserRegist from '@/components/user/UserRegist.vue';
import AuthenticationLoading from '@/components/user/AuthenticationLoading.vue';
import TechStack from '@/components/user/TechStack.vue';
import FindEmail from '@/components/user/FindEmail.vue';
import FindPwd from '@/components/user/FindPwd.vue';
import FoundEmail from '@/components/user/FoundEmail.vue';
import FoundPwd from '@/components/user/FoundPwd.vue';
import MyProfileUpdateCheckPwd from '@/components/profile/MyProfileUpdateCheckPwd.vue';
import MyProfileUpdate from '@/components/profile/MyProfileUpdate.vue';
import QuestionUpdate from '@/components/question/QuestionUpdate.vue';

// stores
import { useUserStore } from '@/stores/userStore';

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/',
      name: 'mainview',
      component: MainView,
    },
    {
      path: '/user',
      name: 'userview',
      component: UserView,
    },
    {
      path: '/board',
      name: 'questionview',
      component: QuestionBoardView,
    },
    {
      path: '/board/:id',
      name: 'question-detail',
      component: QuestionDetailView,
    },
    {
      path: '/question-title-create',
      name: 'question-title-create',
      component: QuestionTitleCreateView,
    },
    {
      path: '/question-create',
      name: 'question-create',
      component: QuestionCreateView,
    },
    {
      path: '/answer-create/:id',
      name: 'answer-create',
      component: AnswerCreateView,
    },
    {
      path: '/rank',
      name: 'rankview',
      component: RankView,
    },
    {
      path: '/shop',
      name: 'shop',
      component: ShopView,
    },
    {
      path: '/login',
      name: 'login',
      component: LoginForm,
    },
    {
      path: '/regist/1',
      name: 'userregist',
      component: UserRegist,
    },
    {
      path: '/loading',
      name: 'authenticationloading',
      component: AuthenticationLoading,
    },
    {
      path: '/oauth2/callback/github',
      name: 'oauth2redirect',
      component: AuthenticationRedirectView,
    },
    {
      path: '/regist/2',
      name: 'techstack',
      component: TechStack,
    },
    {
      path: '/findemail',
      name: 'findemail',
      component: FindEmail,
    },
    {
      path: '/findpwd',
      name: 'findpwd',
      component: FindPwd,
    },
    {
      path: '/foundemail',
      name: 'foundemail',
      component: FoundEmail,
    },
    {
      path: '/foundpwd',
      name: 'foundpwd',
      component: FoundPwd,
    },
    {
      path: '/question-update/:id',
      name: 'question-update',
      component: QuestionUpdate,
    },
    {
      path: '/profile/:id',
      name: 'userProfile',
      component: ProfileView,
    },
    {
      path: '/checkpwd',
      name: 'myprofileupdatecheckpwd',
      component: MyProfileUpdateCheckPwd,
    },
    {
      path: '/updateprofile',
      name: 'myprofileupdate',
      component: MyProfileUpdate,
    },
    {
      path: '/inventory',
      name: 'inventory',
      component: InventoryView,
    },
    {
      path: '/:catchAll(.*)',
      name: 'error', // 404 페이지
      component: ErrorView,
    },
  ],
});

// 라우터 가드 설정
router.beforeEach((to, from, next) => {
  const userStore = useUserStore();
  // 회원가입 1단계 완료 여부 확인
  if (to.name === 'techstack' && !userStore.registId) {
    alert('먼저 회원 가입 1단계를 완료해주세요.');
    next({ name: 'userregist' }); // 1단계로 리다이렉트
  } else {
    const accessRoutes = [
      // 로그인 없이도 접근 가능한 URL
      'mainview',
      'login',
      'authenticationloading',
      'findemail',
      'foundemail',
      'findpwd',
      'foundpwd',
      'userregist',
      'techstack',
      'questionview',
      'oauth2redirect',
    ];
    if (accessRoutes.includes(to.name) || userStore.isLoggedIn) {
      // 로그인 없이 직접 URL 작성으로 접근할 때를 방지
      next();
    } else {
      alert('로그인이 필요합니다.');
      next('/login'); // 로그인 창으로 리다이렉트
    }
  }
});

router.afterEach(() => {
  window.scrollTo(0, 0);
});

export default router;
