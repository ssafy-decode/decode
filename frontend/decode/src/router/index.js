import { createWebHistory, createRouter } from 'vue-router';

// **백엔드 명세서 url과 다르게 작성할 것!**

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

// Components
import LoginForm from '@/components/LoginForm.vue';
import UserRegist from '@/components/user/UserRegist.vue';
import AuthenticationLoading from '@/components/user/AuthenticationLoading.vue';
import TechStack from '@/components/user/TechStack.vue';
import FindEmail from '@/components/user/FindEmail.vue';
import FindPwd from '@/components/user/FindPwd.vue';
import FoundEmail from '@/components/user/FoundEmail.vue';
import FoundPwd from '@/components/user/FoundPwd.vue';
import MyProfile from '@/components/profile/MyProfile.vue';
import MyProfileUpdateCheckPwd from '@/components/profile/MyProfileUpdateCheckPwd.vue';
import MyTagUpdate from '@/components/profile/MyTagUpdate.vue';
import MyProfileUpdate from '@/components/profile/MyProfileUpdate.vue';
import MyInventory from '@/components/shop/MyInventory.vue';
import QuestionUpdate from '@/components/question/QuestionUpdate.vue';
import OtherProfile from '@/components/rank/OtherProfile.vue';

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
      path: '/answer-create',
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
      path: '/mypage',
      name: 'myprofile',
      component: MyProfile,
    },
    {
      path: '/checkpwd',
      name: 'myprofileupdatecheckpwd',
      component: MyProfileUpdateCheckPwd,
    },
    {
      path: '/updatetechstack',
      name: 'mytagupdate',
      component: MyTagUpdate,
    },
    {
      path: '/updatepwd',
      name: 'myprofileupdate',
      component: MyProfileUpdate,
    },
    {
      path: '/detail',
      name: 'otherprofile',
      component: OtherProfile,
    },
    {
      path: '/inventory',
      name: 'myinventory',
      component: MyInventory,
    },
  ],
});

export default router;
