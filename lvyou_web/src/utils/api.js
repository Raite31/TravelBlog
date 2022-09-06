import axios from "axios";
import { Message } from "element-ui";
import routers from "../router/index";

let base = "http://localhost:8081";
export const postRequest = (url, params) => {
  return axios({
    url: `${base}${url}`,
    data: params,
    withCredentials: true, // 携带cookie或信息去后端，这里设置为true则保持相同的session
    method: "post", // !!我恨你！！这样写一定要加上这个才能请求后端成功
  });
};

// 响应拦截器
axios.interceptors.response.use(
  (res) => {
    console.log(res);
    if (res.status == 200 && res.data.code == 200) {
      Message.success({ message: res.data.msg, duration: 500 });
      return res.data;
      console.log("1111");
    } else if (res.status == 200 && res.data.code == 401) {
      Message.error({ message: res.data.msg, duration: 500 });
      // 跳转到登录页
      routers.replace("/");
    } else {
      Message.error({ message: res.data.msg, duration: 500 });
    }
  },
  (error) => {
    if (error.response.status == 500) {
      Message.error({ message: "服务器错误" });
    } else if (error.response.status == 404) {
      Message.error({ message: "页面走丢了……" });
    } else if (error.response.status == 401) {
      Message.error({ message: "权限不足，无法访问" });
    } else {
      Message.error({ message: "未知异常，无法访问" });
    }
  }
);
