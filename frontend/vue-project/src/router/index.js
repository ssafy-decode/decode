import { createWebHistory, createRouter } from 'vue-router';
import MainView from '../views/MainView.vue';
import UserView from '../views/UserView.vue';
import QuestionBoardView from '../views/QuestionBoardView.vue';
import UserRegistView from '../views/UserRegistView.vue';

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
      path: '/questionboard',
      name: 'questionboardview',
      component: QuestionBoardView,
    },
    {
      path: '/userregist',
      name: 'userregistview',
      component: UserRegistView,
    },
  ],
});

export default router;
