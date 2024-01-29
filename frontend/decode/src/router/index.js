import { createWebHistory, createRouter } from 'vue-router';

// Views
import MainView from '@/views/MainView.vue';
import UserView from '@/views/UserView.vue';
import QuestionBoardView from '@/views/QuestionBoardView.vue';
import QuestionDetailView from '@/views/QuestionDetailView.vue';
import QuestionTitleCreateView from '@/views/QuestionTitleCreateView.vue';
import QuestionCreateView from '@/views/QuestionCreateView.vue';
import MyProfileView from '@/views/MyProfileView.vue'; // 완성하면 components 로 이동
import MyProfileUpdateCheckPwdView from '@/views/MyProfileUpdateCheckPwdView.vue'; // 완성하면 components 로 이동
import MyProfileUpdateView from '@/views/MyProfileUpdateView.vue'; // 완성하면 components 로 이동

// Components
import LoginForm from '@/components/LoginForm.vue';
import UserRegistVue from '@/components/user/UserRegist.vue';
import AuthenticationLoadingVue from '@/components/user/AuthenticationLoading.vue';
import TechStackVue from '@/components/user/TechStack.vue';
import FindEmailVue from '@/components/user/FindEmail.vue';
import FindPwdVue from '@/components/user/FindPwd.vue';
import FoundEmailVue from '@/components/user/FoundEmail.vue';
import FoundPwdVue from '@/components/user/FoundPwd.vue';
import QuestionUpdate from '@/components/question/QuestionUpdate.vue';

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
      path: '/question',
      name: 'questionview',
      component: QuestionBoardView,
    },
    {
      path: '/login',
      name: 'login',
      component: LoginForm,
    },
    {
      path: '/regist',
      name: 'userregist',
      component: UserRegistVue,
    },
    {
      path: '/loading',
      name: 'authenticationloading',
      component: AuthenticationLoadingVue,
    },
    {
      path: '/addUserTag',
      name: 'techstack',
      component: TechStackVue,
    },
    {
      path: '/question/:id',
      name: 'question-detail',
      component: QuestionDetailView,
    },
    {
      path: '/email',
      name: 'findemail',
      component: FindEmailVue,
    },
    {
      path: '/password',
      name: 'findpwd',
      component: FindPwdVue,
    },
    {
      path: '/foundemail',
      name: 'foundemail',
      component: FoundEmailVue,
    },
    {
      path: '/foundpwd',
      name: 'foundpwd',
      component: FoundPwdVue,
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
      path: '/question-update/:id',
      name: 'question-update',
      component: QuestionUpdate,
    },
    {
      path: '/info',
      name: 'myprofile',
      component: MyProfileView, // 완성하면 components > profile 로 이동
    },
    {
      path: '/confirm',
      name: 'myprofileupdatecheckpwd',
      component: MyProfileUpdateCheckPwdView, // 완성하면 components > profile 로 이동
    },
    {
      path: '/profile/:id',
      name: 'myprofileupdate',
      component: MyProfileUpdateView, // 완성하면 components > profile 로 이동
    },
  ],
});

export default router;
