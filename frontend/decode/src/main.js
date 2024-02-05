import { createApp } from 'vue';
import { createPinia } from 'pinia';
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate';

import vuetify from './plugins/vuetify';
import { loadFonts } from './plugins/webfontloader';
import '@/components/common/fonts.css'; // 폰트 적용

import App from './App.vue';
import router from './router';
import axios from 'axios';

loadFonts();

const app = createApp(App);
const pinia = createPinia();

pinia.use(piniaPluginPersistedstate); // 새로고침해도 로그인 풀리지 않게

app.use(vuetify);
app.use(pinia);
app.use(router);

app.config.globalProperties.$axios = axios;

app.mount('#app');
