import { createApp } from 'vue';
import vuetify from './plugins/vuetify';
import { loadFonts } from './plugins/webfontloader';
import { createPinia } from 'pinia';
import App from './App.vue';
import router from './router';
import axios from 'axios';
import '@/components/common/fonts.css'; // 폰트 적용

loadFonts();

const pinia = createPinia();
const app = createApp(App);
app.use(vuetify);
app.use(pinia);
app.use(router);
app.config.globalProperties.$axios = axios;
app.mount('#app');
