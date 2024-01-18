import { createApp } from 'vue';
import vuetify from './plugins/vuetify';
import { loadFonts } from './plugins/webfontloader';
import { createPinia } from 'pinia';
import App from './App.vue';
import router from './router';

loadFonts();

const pinia = createPinia();
const app = createApp(App);
app.use(vuetify);
app.use(pinia);
app.use(router);
app.mount('#app');
