<template>
  <div id="diaryContent">
    <!--顶部个人信息START-->
    <div class="top_user_info_title">
      <!--用户信息-->
      <div class="user_info">
        <div>
          <span style="margin-right: 10px">{{ article.user.username }}</span>
          <span>LV.1</span> <img :src="require('@/assets/btn_sfollow.gif')" />
          {{ article.writeTime }}
        </div>
        <div class="user_info_gn">
          <div>
            <i v-if="showInput" @click="updateDiary" class="el-icon-edit"></i>
          </div>
          <div>
            <i
              v-if="showInput"
              @click="deleteDiary()"
              class="el-icon-delete"
            ></i>
          </div>
        </div>
      </div>
      <!--用户图片-->
      <div class="user_img">
        <div>
          <img :src="article.user.headImg" />
        </div>
      </div>
    </div>
    <!--顶部个人信息END-->

    <!--local_con START-->
    <div class="local_con">
      <!--左边start-->
      <div class="local_con_left">
        <!--出发信息-->
        <div class="local_con_left_goinfo">
          <ul>
            <li>
              <i class="iconfont icon-shijian"></i>出发时间<span>/</span
              >{{ article.goTime }}
            </li>
            <li>
              <i class="iconfont icon-gerenfill"></i>人物<span>/</span
              >{{ article.accompany }}
            </li>
            <li>
              <i class="iconfont icon--"></i>出发天数<span>/</span
              >{{ article.day }}天
            </li>
            <li>
              <i class="iconfont icon-qiantianchong"></i>人均费用<span>/</span
              >{{ article.money }}RMB
            </li>
          </ul>
        </div>

        <!--顶部标题START-->
        <div class="local_con_left_sousuo">
          {{ article.title }}
        </div>
        <!--顶部标题END-->

        <!--内容-->
        <div class="local_con_left_content">
          <!--旅游攻略内容-->
          <div id="content"></div>
          <div><i class="iconfont icon-end"></i></div>
          <!--输入评论框Start-->
          <div class="pl_input">
            <div>
              本文著作权归驴窝窝 所有任何形式转载请联系作者 ©2022 驴窝窝
            </div>
            <div class="pl_input_sr">
              <div>
                <div class="wb_img"><i class="iconfont icon-txxg"></i></div>
                <div class="wb_txt">
                  <textarea
                    v-model="plcontent_input"
                    required
                    rows="5"
                    cols="20"
                    name="pl_input"
                    placeholder="输入你的评论"
                  ></textarea>
                </div>
              </div>
              <div>
                <input
                  @click="insertComment()"
                  type="submit"
                  value="评论"
                  style="background-color: orange"
                />
              </div>
            </div>
          </div>
          <!--输入评论框End-->
        </div>

        <!--评论infoshow-->
        <div class="pl_showinf">
          <div class="pl_showinf_total">评论 ({{ this.comments.length }})</div>
          <div
            class="pl_showinf_text"
            v-for="comment in comments"
            :key="comment.id"
          >
            <div class="pl_showinf_text_img">
              <img :src="comment.user.headImg" />
            </div>
            <div class="pl_showinf_text_info">
              <div>{{ comment.user.username }}</div>
              <div>{{ comment.time }}</div>
              <div>{{ comment.content }}</div>
            </div>
          </div>
        </div>
      </div>

      <!--右边start-->
      <div class="local_con_right">
        <!--地址文字-->
        <span
          >相关目的地：<span>中国 {{ article.place }}</span>
        </span>
        <!--地址的图片-->
        <div>
          <img
            :src="require('@/assets/cs.jpg')"
            class="local_con_right_div_img"
          />
          <strong class="local_con_right_div_info"> {{ article.place }}</strong>
        </div>
      </div>
      <!--右边end-->
    </div>
    <!--local_con END-->

    <!--提示是否删除该文章-->
  </div>
</template>

<script>
export default {
  data() {
    return {
      //评论信息
      comments: [{ user: {} }],
      // 文章内容
      article: { user: {} },
      plcontent_input: "",
      showInput: false, // 控制删除和修改按钮的显示和隐藏
    };
  },
  mounted() {
    // 1.获取从游记列表点击标题时携带的ID
    let id = this.$route.query.id;
    // 发送请求，根据id获取文章信息
    this.queryDiaryById(id);
    // 改变showInput的显示与隐藏
    setTimeout(() => {
      // 因为是异步 所以这里不一定比上面的语句慢，就会导致不是我们想要的效果
      let uid = JSON.parse(sessionStorage.getItem("user")).id; // 获取当前登录用户的id
      let artUid = this.article.uid; // 获取文章的作者id
      if (uid == artUid) {
        this.showInput = true;
      }
    }, 500);
  },
  methods: {
    updateDiary() {
      // 跳往发布页，携带ID信息
      this.$router.push({ path: "/pushDiary", query: { id: this.article.id } });
    },
    queryDiaryById(id) {
      this.postRequest("/queryDiaryById?id=" + id)
        .then((success) => {
          // console.log(success.data);
          // 将返回值赋值给arrticle'属性
          this.article = success.data;
          // 将文章内容更新
          let content = document.getElementById("content");
          // console.log(success.data.content);
          content.innerHTML = success.data.content;
          // 不知道为啥就是加不上去，且视频也没说
          //评论信息赋值给comments
          this.comments = success.data.commentList;
          // console.log(success.data.commentList);
        })
        .catch((error) => {});
    },
    insertComment() {
      // alert("222");
      // 准备数据
      let comment = {
        content: this.plcontent_input, // 内容
        uid: JSON.parse(sessionStorage.getItem("user")).id, // 用户id
        artId: this.article.id, // 文章id
        type: 0,
      };
      // 发送请求
      this.postRequest("/insertComment", comment)
        .then((success) => {
          this.queryDiaryById(this.article.id);
        })
        .catch((error) => {});
    },
    // 删除游记 最好有个弹窗
    deleteDiary() {
      // console.log("111111111");
      // 获取当前文章id
      let id = this.$route.query.id;
      // console.log(id);
      // 发送删除请求
      this.postRequest("/deleteDiary?id=" + id)
        .then((success) => {
          if (success.code == 200 && success.code) {
            this.$router.replace("/diary");
          }
        })
        .catch((error) => {
          // 就算什么都不加 也会自动报错的
        });
    },
  },
};
</script>

<style lang="scss">
#diaryContent {
  /*顶部个人信息*/
  .top_user_info_title {
    width: 100%;
    height: 600px;
    margin-top: 10px;
    background-image: url("@/assets/travel_journal_content.jpeg");
    background-size: cover;
    background-repeat: no-repeat;
    /*相对定位*/
    position: relative;
  }

  .user_info {
    position: absolute;
    width: 100%;
    height: 80px;
    top: 520px;
    background-color: white;
    display: flex;
    display: -webkit-flex;
    justify-content: space-between;
    border-bottom: 2px solid #efefef;
    align-items: center;
  }

  .user_info > div:nth-child(1) {
    width: 500px;
    z-index: 8;
  }

  .user_info > div:nth-child(1) > img {
    cursor: pointer;
  }

  .user_info > div {
    width: 300px;
    height: 80px;
    float: left;
    font-size: 14px;
    margin-left: 16rem;
    overflow: hidden;
    font-weight: normal;
    display: flex;
    display: -webkit-flex;
    align-items: center;
    justify-content: flex-start;
    color: #acacac;
  }

  .user_info > div > span:nth-child(1) {
    color: #ff7200;
    margin-right: 5px;
  }

  .user_info > div > span:nth-child(2) {
    color: #ea3c1a;
    font-family: Verdana, Arial, Helvetica, STHeiti;
    font-size: 10px;
    font-weight: bold;
    margin-right: 5px;
  }

  .user_info > div > img {
    margin-right: 10px;
  }

  .user_info_gn > div {
    width: 121px;
    height: 100%;
    float: left;
    border-left: 1px solid #efefef;
    cursor: pointer;
    display: flex;
    z-index: 10;
    display: -webkit-flex;
    justify-content: center;
    align-items: center;
  }

  .user_info_gn > div > i {
    font-size: 30px;
    cursor: pointer;
    font-weight: 600;
  }

  .user_info_gn > div:nth-child(1) {
    color: #1593e3;
  }

  .user_info_gn > div:nth-child(2) {
    flex: 1;
    color: #ea3c1a;
  }

  /*用户头像*/
  .user_img {
    position: absolute;
    width: 100%;
    height: 150px;
    top: 86%;
    left: 50%;
    transform: translate(-50%, -50%);
  }

  .user_img > div {
    width: 150px;
    height: 150px;
    clip-path: circle(50%);
    margin-left: 80px;
  }
  .user_img > div > img {
    width: 150px;
    height: 150px;
  }
  /* @media screen and (min-width: 320px) and (max-width: 1156px) {
	.top_user_info_title{
		width: 1050px;
		height: 600px;
		margin-top: 10px;
		background-image:url(../images/travel_journal_content.jpeg);
		background-size:cover;
		background-repeat: no-repeat;
		position: relative;
	}
	.user_img>div{
		width: 150px;
		height: 150px;
		clip-path: circle(50%);
		background-color: yellow;
		margin-left: 1px;
	}
} */

  /*内容最大的盒子*/

  .local_con {
    width: 100%;
    height: auto;
    margin: 30px auto;
  }

  /*左边信息*/
  .local_con_left {
    width: 70%;
    float: left;
    background-color: white;
    margin-right: 4%;
    margin-left: 6%;
    margin-top: 10px;
    height: auto;
  }

  /*标题*/
  .local_con_left_sousuo {
    width: 100%;
    min-height: 50px;
    height: 50px;
    overflow: hidden;
    font-weight: 900;
    display: flex;
    display: -webkit-flex;
    justify-content: flex-start;
    font-size: 30px;
    line-height: 50px;
    margin-top: 60px;
    color: #333;
  }

  /*出发时间/出发天数/人物/人均费用/人数*/
  .local_con_left_goinfo {
    width: 100%;
    height: 100px;
    border: 1px dashed #e5e5e5;
    display: flex;
    display: -webkit-flex;
    justify-content: flex-start;
    align-items: center;
  }

  .local_con_left_goinfo > ul {
    padding: 0px;
    width: 100%;
    height: 100%;
    display: flex;
    flex-wrap: wrap;
    justify-content: flex-start;
    align-items: flex-start;
  }

  .local_con_left_goinfo > ul > li {
    list-style: none;
    width: 30%;
    margin-left: 3%;
    margin-top: 18px;
    color: #666;
    height: 22px;
    line-height: 22px;
    font-size: 14px;
    color: #fe9e15;
    display: flex;
    display: -webkit-flex;
    align-items: center;
  }

  .local_con_left_goinfo > ul > li > i {
    font-size: 22px;
  }

  .local_con_left_goinfo > ul > li > span {
    margin-left: 5px;
    margin-right: 5px;
  }

  /*内容*/

  /*内容最大盒子*/
  .local_con_left_content {
    width: 100%;
    min-height: 300px;
    height: auto !important;
    margin-top: 40px;
    display: flex;
    display: -webkit-flex;
    flex-direction: column;
    justify-content: flex-start;
    align-items: flex-start;
  }

  /*旅游攻略内容*/
  .local_con_left_content > div:nth-child(1) {
    width: 100%;
    height: auto;
  }

  .local_con_left_content > div:nth-child(2) {
    width: 100%;
    height: 30px;
    text-align: center;
    margin-top: 50px;
  }

  .local_con_left_content > div:nth-child(2) > i {
    font-size: 30px;
    color: #d2d2d2;
  }

  /*输入框css*/
  .pl_input {
    width: 100%;
    height: 200px;
    border-top: 1px solid #d2d2d2;
    border-bottom: 1px solid #d2d2d2;
  }

  .pl_input > div:nth-child(1) {
    color: #d1d1d1;
    font-size: 12px;
  }

  .pl_input_sr {
    width: 100%;
    height: 70%;
    display: flex;
    display: -webkit-flex;
    flex-direction: column;
    justify-content: flex-end;
    align-items: flex-start;
  }

  .pl_input_sr > div:nth-child(1) {
    width: 100%;
    height: 50%;
    display: flex;
    display: -webkit-flex;
    justify-content: space-between;
    align-items: center;
  }

  /*文本域*/
  .wb_img {
    width: 10%;
    height: 100%;
    display: flex;
    display: -webkit-flex;
    align-items: flex-start;
    justify-content: center;
  }

  .wb_img > i {
    font-size: 50px;
    color: #dddddd;
  }

  .wb_txt {
    width: 87%;
    height: 100%;
  }

  .wb_txt > textarea {
    width: 98%;
    height: 100%;
    border-radius: 0.75rem;
    border: 0.5px orange solid;
    outline: none;
    resize: none;
    font-size: 18px;
    padding-left: 10px;
    padding-top: 10px;
  }

  /*按钮*/
  .pl_input_sr > div:nth-child(2) {
    width: 100%;
    height: 30%;
    display: flex;
    display: -webkit-flex;
    justify-content: flex-end;
    align-items: center;
  }

  .pl_input_sr > div:nth-child(2) > input {
    width: 120px;
    height: 30px;
    border-radius: 0.3125rem;
    cursor: pointer;
    background-color: #d2d2d2;
    color: white;
    border: 0px;
    font-size: 15px;
    margin-top: 50px;
  }

  /*评论showinfo*/
  .pl_showinf {
    width: 100%;
    min-height: 400px;
    height: auto !important;
  }

  .pl_showinf_total {
    width: 100%;
    height: 100px;
    font-size: 24px;
    overflow: hidden;
    line-height: 100px;
    text-align: left;
    color: #333;
    font-weight: normal;
  }

  .pl_showinf_text {
    width: 100%;
    min-height: 100px;
    height: auto !important;
    border-bottom: #d1d1d1 1px solid;
    display: flex;
    display: -webkit-flex;
    justify-content: flex-start;
    align-items: flex-start;
    margin-top: 15px;
  }

  .pl_showinf_text_img {
    width: 80px;
    height: 80px;
    clip-path: circle(50%);
  }

  .pl_showinf_text_img > img {
    width: 100%;
    height: 100%;
  }

  .pl_showinf_text_info {
    width: 90%;
    height: 100%;
    margin-left: 2%;
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    text-align: left;
    align-items: flex-start;
  }

  .pl_showinf_text_info > div:nth-child(1) {
    width: 100%;
    color: #333;
    font-weight: normal;
    line-height: 28px;
    height: 28px;
    font-size: 18px;
  }

  .pl_showinf_text_info > div:nth-child(2) {
    font-size: 12px;
    color: #999;
    font-weight: normal;
    line-height: 14px;
    height: 14px;
    width: 100%;
  }

  .pl_showinf_text_info > div:nth-child(3) {
    flex: 1;
    font-size: 14px;
    margin-top: 12px;
    color: #666;
    line-height: 28px;
  }

  /*右边信息*/
  .local_con_right {
    float: left;
    width: 18%;
    margin-right: 2%;
    padding-top: 0px;
    min-height: 600px;
    height: auto !important;
    text-align: left;
  }

  /*城市的div*/

  .local_con_right > div {
    margin-top: 15px;
    margin-bottom: 5px;
    width: 100%;
    height: 150px;
    display: flex;
    display: -webkit-flex;
    align-items: center;
    justify-content: space-around;
    position: relative;
    margin-top: 20px;
  }

  /*地址文字*/
  .local_con_right > span {
    font-size: 14px;
  }

  .local_con_right > span > span {
    color: #ff8a10;
  }

  /*地址照片*/
  .local_con_right_div_img {
    width: 100%;
    height: 100%;
    position: absolute;
    z-index: 1;
  }

  .local_con_right_div_info {
    position: absolute;
    font-size: 30px;
    z-index: 3;
    line-height: 30px;
    height: 30px;
    top: 100px;
    color: white;
    left: 10px;
  }
  #tishi {
    width: 500px;
    height: 250px;
    border-radius: calc(0.875rem);
    background-color: white;
    border: 1px solid #d0d0d0;
    position: fixed;
    top: 40%;
    left: 50%;
    transform: translate(-50%, -50%);
    align-items: center;
    display: flex;
    display: -webkit-flex;
    flex-direction: column;
    font-weight: 500;
    justify-content: space-between;
    align-items: center;
    font-size: 1.125rem;
  }
  #tishi > div:nth-child(1) {
    width: 90%;
    text-align: center;
    height: 80px;
    line-height: 80px;
    color: black;
    border-bottom: 1px solid #e0e0e0;
  }
  #tishi > div:nth-child(2) {
    width: 90%;
    height: 80px;
    line-height: 80px;
  }
  #tishi > input {
    width: 150px;
    height: 50px;
    margin-bottom: 30px;
    border-radius: 50rem;
    border: 0px;
    font-size: 18px;
    color: white;
    cursor: pointer;
    background-color: #1493e3;
  }
}
</style>