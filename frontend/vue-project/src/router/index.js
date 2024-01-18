import { createWebHistory, createRouter } from 'vue-router';

// Views
// import MainView from '@/views/MainView.vue';
import QuestionBoardView from '@/views/QuestionBoardView.vue';
// import UserView from '@/views/UserView.vue';

// Components
// login
import LoginForm from '@/components/LoginForm.vue';

// // user
// import UserDetail from '@/components/user/UserDetail.vue';
// import UserRegist from '@/components/question/UserRegist.vue';

// // profile

// // rank

// // chat
// import ChatRoom from '@/components/chat/ChatRoom.vue';

// answer
// import AnswerCreate from '@/components/answer/AnswerCreate.vue';
// import AnswerList from '@/components/answer/AnswerList.vue';
// import AnswerUpdate from '@/components/answer/AnswerUpdate.vue';

// // question
import QuestionList from '@/components/question/QuestionList.vue';

// import QuestionList from '@/components/question/QuestionList.vue';
// import QuestionList from '@/components/question/QuestionList.vue';
// import QuestionList from '@/components/question/QuestionList.vue';
// import QuestionList from '@/components/question/QuestionList.vue';

// shop

// inventory

// path별 component를 추가한다.
// { path: '/', name: '', component: '' },
const routes = [
  { path: '/', name: 'loginform', component: LoginForm },
  // { path: '/', name: 'mainview', component: MainView },
  { path: '/questionboard', name: 'questionboardview', component: QuestionBoardView },
  // { path: '/user', name: 'userview', component: UserView },
  // { path: '/login', name: 'loginform', component: LoginForm },

  // { path: '/userdetail', name: 'userdetail', component: UserDetail },

  // { path: '/userregist', name: 'userregist', component: UserRegist },

  // { path: '/chatroom', name: 'chatroom', component: ChatRoom },
  // { path: '/login', name: 'loginform', component: LoginForm },
  // { path: '/login', name: 'loginform', component: LoginForm },
  // { path: '/login', name: 'loginform', component: LoginForm },
  // { path: '/login', name: 'loginform', component: LoginForm },
  // { path: '/login', name: 'loginform', component: LoginForm },
  // { path: '/login', name: 'loginform', component: LoginForm },
  // { path: '/login', name: 'loginform', component: LoginForm },

  { path: '/questionlist', name: 'questionlist', component: QuestionList },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
