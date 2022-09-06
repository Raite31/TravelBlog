<template lang="">
     <div id="navbar">
    <div class="lww_header">
      <div class="header_wrap">
        <!--logo start-->
        <div class="header_logo">
          <a href="javascript:;" class="lww_logo"></a>
        </div>
        <!--logo end-->
        <ul class="header_nav">
          <li @click="click_nav(1)" @mouseenter="select_nav(1)" :class="nav_select==1?'header_nav_hover':''">
            <router-link to="/home">首页</router-link>
          </li>
          <li @click="click_nav(2)" @mouseenter="select_nav(2)" :class="nav_select==2?'header_nav_hover':''">
            <router-link to="/strategy">旅游攻略</router-link> 
          </li>
          <li @click="click_nav(3)" @mouseenter="select_nav(3)" :class="nav_select==3?'header_nav_hover':''">
            <router-link to="/destination">目的地</router-link> 
            
          </li>
          <li @click="click_nav(4)" @mouseenter="select_nav(4)" :class="nav_select==4?'header_nav_hover':''">
            <router-link to="/diary">旅游日记</router-link> 
          </li>
          <li @click="click_nav(5)" @mouseenter="select_nav(5)" :class="nav_select==5?'header_nav_hover':''">
            <router-link to="/statistics">出游查询</router-link> 
          </li>
          <li @click="click_nav(6)" @mouseenter="select_nav(6)" :class="nav_select==6 ?'header_nav_hover':''">
              <a :class="index == 6 ? 'onclickClass' : ''"
              >社区</a
            >
          </li>
        </ul>
        <div class="header_search">
          <input type="text" />
          <a></a>
        </div>
        <!--登录的显示start-->
        <div class="login_info">
          <div class="header_daka">
            <a href="javascript:;">打卡</a>
          </div>
          <div class="header_msg">
            消息
            <i></i>
          </div>
          <a href="javascript:;" @click="dialogVisible = true">
            <img :src="user.headImg" id="login_user_headUrl" />
          </a>
          <a
            href="javascript:;"
            title="退出登录"
            rel="nofollow"
            id="logoutbtn"
            >退出登录</a
          >
        </div>
      </div>
    </div>

    <el-dialog
  title="修改用户信息"
  :visible.sync="dialogVisible"
  width="30%"
  :before-close="handleClose">
  <!-- action是发送请求 -->
  <el-upload 
  :show-file-list="false"
  :before-upload="beforeAvatarUpload" 
  :on-success="uploadSuccess"
  :on-error="uploadError"
  :data="this.user"
  action="http://localhost:8081/uploadUserImg" >
    <el-image
      :src="user.headImg"
      fit="fit"></el-image>
  </el-upload>
  
  <el-input class="update_username" v-model="user.username" placeholder="请输入用户名"></el-input>
  <el-input v-model="user.password" placeholder="请输入更新密码"></el-input>

  <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="update_click">修 改</el-button>
  </span>
</el-dialog>
  </div>
</template>
<script>
import axios from "axios";
export default {
  data() {
    return {
      index: 1,
      nav_select: 0,
      user: {
        id: "",
        username: "",
        password: "",
        headImg: "",
      },
      dialogVisible: false,
    };
  },
  mounted() {
    // 从sessionStorage中取出user并转为JSON
    let user = JSON.parse(sessionStorage.getItem("user"));
    // 拷贝
    Object.assign(this.user, user);
    // console.log(this.user);
  },
  methods: {
    uploadSuccess(success) {
      // 上传头像成功
      console.log(success);
      // 重新设置头像信息
      this.user.headImg = success.data;
      // alert(success.data);
      this.$message.success(success.msg);
      // 更新sessonStorage
      this.user.headImg = success.data;
      sessionStorage.setItem("user", JSON.stringify(this.user));
    },
    uploadError() {
      this.$message.error("更新头像失败");
    },
    beforeAvatarUpload(file) {
      const isJPG = file.type === "image/jpeg";
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error("上传头像图片只能是 JPG 格式!");
      }
      if (!isLt2M) {
        this.$message.error("上传头像图片大小不能超过 2MB!");
      }
      return isJPG && isLt2M;
    },
    update_click() {
      // 发送请求
      this.postRequest("/updateUser", this.user)
        .then((success) => {
          // 发送请求成功
          this.dialogVisible = false;
        })
        .catch((error) => {
          // 发送请求失败
        });
    },
    select_nav(val) {
      this.nav_select = val;
    },
    click_nav(val) {
      this.index = val;
    },
    handleClose(done) {
      this.$confirm("确认关闭？")
        .then((_) => {
          done();
        })
        .catch((_) => {});
    },
  },
};
</script>
<style lang="scss">
#navbar {
  .router-link-exact-active {
    background-color: #ffa500;
    color: #fff !important;
  }
  .update_username {
    margin-top: 20px;
    margin-bottom: 30px;
  }
  .lww_header {
    width: 100%;
    background-color: white;
    height: 60px;
    /*阴影:左，下，左右*/
    box-shadow: 0px 3px 0px #d0d0d0;
    /*定位*/
    display: flex;
    display: -webkit-flex;
    justify-content: center;
    align-items: center;
    user-select: none;
  }

  .header_wrap {
    min-width: 80vw;
    width: auto !important;
    height: 65px;
    display: flex;
  }

  .header_logo {
    width: 130px;
    height: 65px;
    background-image: url("@/assets/header-small-sprites3.png");
    background-size: 120px 38px;
    /*居中图片*/
    background-position: center;
    background-repeat: no-repeat;
  }

  .header_nav {
    margin: 0px;
    padding-left: 40px;
    display: block;
    width: 450px;
    height: 65px;
    line-height: 65px;
    display: flex;
    display: -webkit-flex;
    text-align: center;
    justify-content: center;
    align-items: center;
    list-style: none;
  }

  .header_nav > li > a {
    display: inline-block;
    width: 100%;
    height: 100%;
  }

  .header_nav > li,
  .header_nav > li > a {
    list-style: none;
    flex: 1;
    font-size: 14px;
    cursor: pointer;
    text-decoration: none;
    color: #282828;
  }

  .header_nav_hover {
    border-bottom: 3px solid #ffa500;
  }

  .header_search {
    width: 130px;
    height: 30px;
    background-color: #efefef;
    display: flex;
    display: -webkit-flex;
    justify-content: center;
    align-items: center;
    margin-top: 15px;
    border-radius: 0.525rem;
    margin-left: 10px;
  }

  .header_search > input {
    width: 100%;
    height: 100%;
    border: 0px;
    font-size: 18px;
    background-color: #efefef;
    /*取消边框点击出现样式*/
    outline-style: none;
    padding-top: 0px;
    padding-bottom: 0px;
    padding-bottom: 0px;
    padding-left: 10px;
    border-radius: 0.525rem;
    color: #c0c0c0;
  }

  .header_search > a {
    font-size: 22px;
    font-weight: 800;
    padding-right: 5px;
    padding-left: 5px;
    cursor: pointer;
  }

  /*未登录显示*/
  .login-out {
    width: 290px;
    height: 65px;
    color: orange;
    display: flex;
    display: -webkit-flex;
    justify-content: flex-end;
    align-items: center;
  }

  .login-out > a:nth-child(1),
  .login-out > a:nth-child(2),
  .login-out > a:nth-child(3) {
    font-size: 30px;
  }

  .login-out > a {
    color: orange;
    text-decoration: none;
    margin-left: 5px;
    margin-right: 5px;
    font-size: 14px;
  }

  .login-out > span {
    color: #efefef;
  }

  /*登录显示*/
  .login_info {
    width: 290px;
    height: 65px;
    display: flex;
    display: -webkit-flex;
    justify-content: flex-end;
    align-items: center;
  }

  .login_info > a {
    font-size: 12px;
    color: #666;
    text-decoration: none;
    margin-left: 5px;
    margin-right: 5px;
  }

  .header_daka {
    width: 60px;
    height: 20px;
    background-color: #1493e3;
    display: flex;
    display: -webkit-flex;
    justify-content: center;
    align-items: center;
    margin-right: 10px;
    font-size: 12px;
    border-radius: 0.3rem;
  }

  .header_daka > a {
    color: white;
    text-decoration: none;
  }

  .header_msg {
    width: 50px;
    color: #666;
    font-size: 12px;
    padding: 0px;
    cursor: pointer;
    margin-left: 10px;
    margin-right: 10px;
  }

  .header_msg > i {
    font-size: 15px;
    padding: 0px;
  }

  .login_info > a:nth-child(3) > img {
    width: 33px;
    height: 33px;
    border-radius: 50%;
  }
  .onclickClass {
    background-color: orange;
    color: white !important;
  }
}
</style>