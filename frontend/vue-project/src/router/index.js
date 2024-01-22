import { createWebHistory, createRouter } from 'vue-router';

// Views
import MainView from '@/views/MainView.vue';
import UserView from '@/views/UserView.vue';
import QuestionBoardView from '@/views/QuestionBoardView.vue';

// Components
import LoginForm from '@/components/LoginForm.vue';
import UserRegistVue from '@/components/user/UserRegist.vue';
import AuthenticationLoadingVue from '@/components/user/AuthenticationLoading.vue';
import TechStackVue from '@/components/user/TechStack.vue';

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/',
      name: 'mainview',
      component: MainView,
    },
    {
      path: '/',
      name: 'login',
      component: LoginForm,
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
      path: '/techstack',
      name: 'techstack',
      component: TechStackVue,
    },
  ],
});

export default router;
