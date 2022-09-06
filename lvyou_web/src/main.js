import Vue from "vue";
import App from "./App.vue";
import "./registerServiceWorker";
import router from "./router";
import store from "./store";
import { postRequest } from "./utils/api";
import ElementUI from "element-ui";
import "element-ui/lib/theme-chalk/index.css";
import hevueImgPreview from "hevue-img-preview";
import editor from "@blog1997/vue-umeditor";
import * as echarts from "echarts";
Vue.prototype.$echarts = echarts;
Vue.use(editor);
Vue.use(hevueImgPreview);
Vue.use(ElementUI);

// 配置全局属性
Vue.prototype.postRequest = postRequest;

Vue.config.productionTip = false;
new Vue({
  router,
  store,
  render: (h) => h(App),
}).$mount("#app");
