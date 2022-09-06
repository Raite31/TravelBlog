const { defineConfig } = require("@vue/cli-service");
module.exports = defineConfig({
  transpileDependencies: true,
});

// 前端跨域
module.exports = {
  devServer: {
    host: "localhost",
    port: 8080,
    proxy: {
      "/": {
        ws: false,
        target: "http://localhost:8081",
        changeOrigin: true,
      },
    },
  },
};
