import { createWebHistory, createRouter } from 'vue-router';

const router = createRouter({
  history: createWebHistory(),
  routes: [
    // path별 component를 추가한다.
    // { path: '/', name: '', component: '' },
  ],
});

export default router;
