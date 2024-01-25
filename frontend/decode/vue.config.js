const path = require('path');
const { defineConfig } = require('@vue/cli-service');
module.exports = defineConfig({
  transpileDependencies: true,
  lintOnSave: false,
  configureWebpack: {
    resolve: {
      alias: {
        '@': path.resolve(__dirname, 'src'),
      },
    },
  },
  pluginOptions: {
    vuetify: {
      // https://github.com/vuetifyjs/vuetify-loader/tree/next/packages/vuetify-loader
    },
  },
  publicPath: '/',
  chainWebpack: (config) => {
    config.plugin('define').tap((args) => {
      args[0]['__VUE_PROD_HYDRATION_MISMATCH_DETAILS__'] = true;
      return args;
    });
  },
  // 웹소켓을 쓰지 않는다면 주석 풀기
  // target = 백엔드 port
  // changeorigin = true면 cors 문제 해결
  // devServer: {
  //   proxy: {
  //     '/': {
  //       target: 'http://localhost:80/decode',
  //       changeOrigin: true,
  //       ws: false,
  //     },
  //   },
  // },
});
