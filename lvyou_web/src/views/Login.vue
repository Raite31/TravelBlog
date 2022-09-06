<template lang="">
    <section class="section_background">

    <div class="section_background_sr">
        <input id="username" type="text" name="username"  v-model="username"/>
        <input id="password" type="password" name="password" v-model="password"/>
        <!-- 验证码 -->
        <div class="code-container">
            <input v-model="code" type="text" id="code" placeholder="请输入验证码" />
            <img :src="codeImg" width="190px" @click="codeClick" />
        </div>
        <a class="wj">忘记密码</a>
        <input id="login_sumbit" type="submit" @click="login_click" value="登录"/>
    </div>
    <div class="wjmm">
        还没有账号？
        <a href="./regist.html">马上注册</a>
    </div>
</section> 
</template>
<script>
import axios from "axios";

export default {
  data() {
    return {
      id: 123,
      username: "zhangsan",
      password: "123",
      code: "",
      codeImg: "http://localhost:8081/codeImg?date" + new Date(),
    };
  },
  methods: {
    codeClick() {
      //   console.log("刷新验证码");
      this.codeImg = "http://localhost:8081/codeImg?date" + new Date();
    },
    login_click() {
      // axios({
      //   url: "http://localhost:8081/login",
      //   data: {
      //     username: this.username,
      //     password: this.password,
      //     code: this.code,
      //   },
      //   method: "POST",
      // })
      this.postRequest("/login", {
        username: this.username,
        password: this.password,
        code: this.code,
      })
        .then((res) => {
          // 引用element-ui消息
          // this.$message.success(res.data); // 放到api.js中去做了
          if (res.code == 200) {
            // console.log(res.data);
            // 去除后端的数据，存到sessionStorage作用域中
            sessionStorage.setItem("user", JSON.stringify(res.data));
            this.$router.replace("/home"); // 通过路由跳转到Home组件中
          }
        })
        .catch((error) => {
          // this.$message.error(res.data);
        });
    },
  },
};
</script>
<style lang="scss">
.section_background {
  width: 100vw;
  height: 100vh;
  background-image: url(@/assets/background_login.jpg);
  background-size: cover;
  .code-container {
    width: 390px;
    height: 48px;
    display: flex;
    margin-bottom: 15px;
    justify-content: flex-start;
    align-items: center;
  }
  #code {
    width: 200px;
    height: 48px;
    border-radius: 0.3125rem;
    border: 1px solid #c0c0c0;
    font-size: 18px;
    padding-left: 10px;
    color: gray;
    outline-style: none;
  }
  .section_background_sr {
    width: 500px;
    height: 400px;
    background-color: white;
    position: absolute;
    top: 40%;
    left: 40%;
    transform: translate(-50%, -50%);
    border-radius: 0.625rem;

    display: flex;
    display: -webkit-flex; /*需要webkit内核*/
    flex-direction: column;
    justify-content: center;
    align-items: center;
  }

  .section_background_sr > #username,
  .section_background_sr > #password {
    width: 390px;
    height: 48px;
    border-radius: 0.3125rem;
    border: 1px solid #c0c0c0;
    margin-bottom: 15px;
    font-size: 18px;
    padding-left: 10px;
    color: Gray;
    outline-style: none;
  }

  .section_background_sr > .wj {
    color: #ffa500;
    font-weight: 700;
    margin-left: 330px;
    cursor: pointer;
    /*双击不选中*/
    user-select: none;
    margin-bottom: 15px;
  }

  .section_background_sr > #login_sumbit {
    width: 400px;
    height: 48px;
    border-radius: 0.3125rem;
    background-color: #ffa500;
    border: white 0rem;
    margin-bottom: 15px;
    cursor: pointer;
    font-size: 20px;
    color: white;
    font-weight: 900;
  }

  .wjmm {
    background-color: rgba(0, 0, 0, 0.4);
    width: 180px;
    height: 30px;
    display: flex;
    display: -webkit-flex;
    justify-content: center;
    align-items: center;
    color: white;
    border-radius: 50px;
    font-size: 15px;

    user-select: none;

    position: absolute;
    top: 65%;
    left: 40%;
    transform: translate(-50%, -50%);
    border-radius: 0.625rem;
    text-shadow: 1px 1px 1px rgba(0, 0, 0, 0.4);
  }

  .wjmm > a {
    color: #ffa500;
    outline: none;
    text-decoration: none;
  }
  .error_msg {
    width: 400px;
    height: 24px;
    color: red;
    margin-bottom: 20px;
    font-size: 15px;
  }
}
</style>