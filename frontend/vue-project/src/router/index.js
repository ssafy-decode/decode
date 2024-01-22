import { createWebHistory, createRouter } from 'vue-router';
import MainView from '../views/MainView.vue';
import LoginForm from '@/components/LoginForm.vue';
import UserView from '@/views/UserView.vue';
import QuestionBoardView from '@/views/QuestionBoardView.vue';
import UserRegistView from '@/views/UserRegistView.vue';
import QuestionCreateView from '@/views/QuestionCreateView.vue';
import AnswerCreateView from '@/views/AnswerCreateView.vue';

const router = createRouter({
  history: createWebHistory(),
  routes: [
    // {
    //   path: '/',
    //   name: 'mainview',
    //   component: MainView,
    // },
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
      path: '/regist',
      name: 'registview',
      component: UserRegistView,
    },
    {
      path: '/test',
      name: 'TestView',
      component: AnswerCreateView,
    },
  ],
});

export default router;
