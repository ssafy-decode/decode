import { createApp } from 'vue';
import vuetify from './plugins/vuetify';
import { loadFonts } from './plugins/webfontloader';
import { createPinia } from 'pinia';
import App from './App.vue';
import router from './router';
import axios from 'axios';

loadFonts();

const pinia = createPinia();
const app = createApp(App);
app.use(vuetify);
app.use(pinia);
app.use(router);
// axios에서 기본적으로 사용할 서버 주소 설정
// axios.defaults.baseURL = process.env.VUE_APP_BACKEND_URL;
// axios.defaults.withCredentials = true;
// vue 전역에서 axios 사용하기 위해 설정 => this.$axios로 접근 가능
app.config.globalProperties.$axios = axios;
app.mount('#app');
