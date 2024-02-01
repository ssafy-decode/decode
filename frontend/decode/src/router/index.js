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

// Components
import LoginForm from '@/components/LoginForm.vue';
import UserRegistVue from '@/components/user/UserRegist.vue';
import AuthenticationLoadingVue from '@/components/user/AuthenticationLoading.vue';
import TechStackVue from '@/components/user/TechStack.vue';
import FindEmailVue from '@/components/user/FindEmail.vue';
import FindPwdVue from '@/components/user/FindPwd.vue';
import FoundEmailVue from '@/components/user/FoundEmail.vue';
import FoundPwdVue from '@/components/user/FoundPwd.vue';
import MyProfileVue from '@/components/profile/MyProfile.vue';
import MyProfileUpdateCheckPwdVue from '@/components/profile/MyProfileUpdateCheckPwd.vue';
import MyTagUpdateVue from '@/components/profile/MyTagUpdate.vue';
import MyProfileUpdateVue from '@/components/profile/MyProfileUpdate.vue';
import RankListVue from '@/components/rank/RankList.vue';
// import ItemShopVue from '@/components/shop/ItemShop.vue';
import MyInventoryVue from '@/components/shop/MyInventory.vue';
import QuestionUpdate from '@/components/question/QuestionUpdate.vue';
import OtherProfileVue from '@/components/rank/OtherProfile.vue';

// 재화 거 : 추후 수정
import ShopView from '@/views/ShopView.vue';
import ExchangeTabVue from '@/components/shop/ExchangeTab.vue';
import ShopTabVue from '@/components/shop/ShopTab.vue';
import ProductComponentVue from '@/components/shop/ProductComponent.vue';

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
      path: '/login',
      name: 'login',
      component: LoginForm,
    },
    {
      path: '/regist/1',
      name: 'userregist',
      component: UserRegistVue,
    },
    {
      path: '/loading',
      name: 'authenticationloading',
      component: AuthenticationLoadingVue,
    },
    {
      path: '/regist/2',
      name: 'techstack',
      component: TechStackVue,
    },
    {
      path: '/board/:id',
      name: 'question-detail',
      component: QuestionDetailView,
    },
    {
      path: '/findemail',
      name: 'findemail',
      component: FindEmailVue,
    },
    {
      path: '/findpwd',
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
      path: '/answer-create',
      name: 'answer-create',
      component: AnswerCreateView,
    },
    {
      path: '/question-update/:id',
      name: 'question-update',
      component: QuestionUpdate,
    },
    {
      path: '/mypage',
      name: 'myprofile',
      component: MyProfileVue,
    },
    {
      path: '/checkpwd',
      name: 'myprofileupdatecheckpwd',
      component: MyProfileUpdateCheckPwdVue,
    },
    {
      path: '/updatetechstack',
      name: 'mytagupdate',
      component: MyTagUpdateVue,
    },
    {
      path: '/updatepwd',
      name: 'myprofileupdate',
      component: MyProfileUpdateVue,
    },
    {
      path: '/rank',
      name: 'ranklist',
      component: RankListVue,
    },
    {
      path: '/detail',
      name: 'otherprofile',
      component: OtherProfileVue,
    },
    // {
    //   path: '/shop',
    //   name: 'itemshop',
    //   component: ItemShopVue,
    // },
    {
      path: '/inventory',
      name: 'myinventory',
      component: MyInventoryVue,
    },
    // 재화 거 : 추후 수정
    {
      path: '/shop',
      name: 'shop',
      component: ShopView,
    },
    // {
    //   path: '/exchangetab',
    //   name: 'exchangetab',
    //   component: ExchangeTabVue,
    // },
    // {
    //   path: '/shoptab',
    //   name: 'shoptab',
    //   component: ShopTabVue,
    // },
    {
      path: '/productcomponent',
      name: 'productcomponent',
      component: ProductComponentVue,
    },
  ],
});

export default router;
