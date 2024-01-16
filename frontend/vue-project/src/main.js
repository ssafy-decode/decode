import { createApp } from 'vue';
import App from './App.vue';
import vuetify from './plugins/vuetify';
import { loadFonts } from './plugins/webfontloader';
import { createRouter, createWebHistory } from 'vue-router';
import { createPinia } from 'pinia';

loadFonts();

const pinia = createPinia();
const app = createApp(App);
const router = createRouter({
  history: createWebHistory(),
  routes: [],
});
app.use(router);
app.use(pinia);
app.use(vuetify);
app.mount('#app');
