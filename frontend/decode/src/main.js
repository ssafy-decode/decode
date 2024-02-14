import { createApp } from 'vue';
import { createPinia } from 'pinia';
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate';

import vuetify from './plugins/vuetify';
import { loadFonts } from './plugins/webfontloader';
import '@/components/common/fonts.css'; // 폰트 적용
import { library } from '@fortawesome/fontawesome-svg-core';
import { faMicrophone, faMicrophoneSlash, faDesktop, faExpand, faDoorOpen } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';

import App from './App.vue';
import router from './router';
import axios from 'axios';

loadFonts();

library.add(faMicrophone, faMicrophoneSlash, faDesktop, faExpand, faDoorOpen);
const app = createApp(App);
const pinia = createPinia();

pinia.use(piniaPluginPersistedstate); // 새로고침해도 로그인 풀리지 않게
app.component('font-awesome-icon', FontAwesomeIcon); // 컴포넌트 등록 코드 변경

app.use(vuetify);
app.use(pinia);
app.use(router);

app.config.globalProperties.$axios = axios;

app.mount('#app');
