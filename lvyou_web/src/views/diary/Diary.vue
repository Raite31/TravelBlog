<template>
  <div id="diary">
    <Head></Head>
    <!--最外层div-->
    <div class="local_con">
      <!--左边div-->
      <div class="local_con_left">
        <!--搜索 START-->
        <div class="local_con_left_search">
          <div>
            <!--选中样式change_child_1，未选中样式unchange_child_1-->
            <span
              @click="selectBasic('最新游记')"
              :class="
                basic == '最新游记' ? 'change_child_1' : 'unchange_child_1'
              "
              >最新游记</span
            >
            <span
              @click="selectBasic('最热游记')"
              :class="
                basic == '最热游记' ? 'change_child_1' : 'unchange_child_1'
              "
              >最热游记</span
            >
          </div>
          <div>
            <span
              >人均花费:
              <select @change="getDiaryAllByBasic" v-model="money">
                <option v-for="item in moneys" :value="item" :key="item">
                  {{ item }}
                </option>
              </select>
            </span>
            <span
              >出行天数:
              <select @change="getDiaryAllByBasic" v-model="day">
                <option v-for="item in days" :value="item" :key="item">
                  {{ item }}
                </option>
              </select>
            </span>
          </div>
        </div>
        <!--搜索 END-->

        <!--内容的div:一个内容START-->
        <div
          class="local_con_left_content"
          v-for="article in diarys"
          :key="article.id"
        >
          <div class="local_con_left_content_img">
            <img class="img" :src="article.cover" fit="fill" />
          </div>
          <div class="local_con_left_content_text">
            <div @click="goIntoContent(article.id)">
              <a>{{ article.title }}</a>
            </div>
            <div>
              <img :src="article.user.headImg" />
              <!-- 三周D1 第六到十一个视频，解决这个问题 -->
              <div>
                <span style="color: #a8a8a8">作者 </span>:
                {{ article.user.username }}
              </div>
            </div>
            <!--介绍-->
            <div>{{ article.abs }}</div>
            <div>
              <span class="info_view"
                ><i class="el-icon-view"></i>{{ article.watch }}</span
              >
              <span class="info_view"
                ><i class="el-icon-chat-round"></i
                >{{ article.commentCount }}</span
              >
            </div>
          </div>
        </div>
        <!--内容的div:一个内容END-->
        <div class="block">
          <el-pagination
            @current-change="changeCurrent"
            :total="total"
            :page-size="pageSize"
            :current-page.sync="currentPage"
            layout="prev, pager, next"
          >
          </el-pagination>
        </div>
      </div>
      <!--右边div-->
      <div class="local_con_right">
        <div class="lvgl"><i class="el-icon-camera-solid"></i>旅游攻略</div>
        <ul class="lvpm">
          <li class="change_lvpm_q3">
            <em>1</em><strong>北海道</strong><a>雪中泡温泉</a>
          </li>
          <li class="change_lvpm_q3">
            <em>2</em><strong>安徽</strong><a>安徽宏村</a>
          </li>
          <li class="change_lvpm_q3">
            <em>3</em><strong>福建</strong><a>霞浦</a>
          </li>
          <li><em>4</em><strong>乐山</strong><a>峨眉山</a></li>
          <li><em>5</em><strong>四川</strong><a>九寨沟</a></li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
import Head from "@/components/Head.vue";
export default {
  data() {
    return {
      money: "不限",
      day: "不限",
      diarys: [], // 用于接受所有的游记
      currentPage: 1, // 当前页码
      pageSize: 4,
      total: 10,
      basic: "最新游记",
      moneys: ["不限", "40k-50k", "30k-40k", "10k-30k", "1-10k"],
      days: ["不限", "60天-90天", "30天-60天", "10天-30天", "1天-10天"],
    };
  },
  mounted() {
    // 调用getDiaryBasic发送请求
    this.getDiaryAllByBasic();
  },
  methods: {
    selectBasic(val) {
      // 改变basic
      this.basic = val;
      // 发送请求
      this.getDiaryAllByBasic();
    },
    goIntoContent(id) {
      // alert(id);
      // 跳转到游记详情页面
      this.$router.push({
        path: "/diaryContent",
        query: { id: id },
      });
    },
    getDiaryAllByBasic() {
      // 定义参数
      let param =
        "currentPage=" +
        this.currentPage +
        "&pageSize=" +
        this.pageSize +
        "&basic=" +
        this.basic +
        "&money=" +
        this.money +
        "&day=" +
        this.day;
      // 定义方法
      this.postRequest("/getDiaryByBasic?" + param)
        .then((success) => {
          this.diarys = success.data.list; // 更新游记信息
          // console.log(success.data.list);
          this.total = success.data.total; // 更新总页数
        })
        .catch((error) => {});
    },
    changeCurrent() {
      // 发送请求查询数据
      this.getDiaryAllByBasic();
      // alert(11);
    },
  },
  components: {
    Head,
  },
};
</script>

<style lang="scss">
#diary {
  .local_con {
    width: 80vw;
    height: auto;
    max-height: 3000px !important;
    margin: 30px auto;
  }

  /*左边信息*/
  .local_con_left {
    width: 76%;
    height: auto;
    float: left;
    margin-right: 4%;
    min-height: 600px;
    /**后面要修改成的高度**/
    /* height: auto;
    max-height:3000px !important; */
  }

  /*搜索*/
  .local_con_left_search {
    width: 100%;
    height: 100px;
  }

  /*按最新和最热搜索*/
  .local_con_left_search > div:nth-child(1) {
    width: 100%;
    height: 50px;
    display: flex;
    display: -webkit-flex;
    justify-content: flex-start;
    align-items: flex-start;
  }

  .local_con_left_search > div:nth-child(1) > span {
    font-size: 16px;
    margin-right: 10px;
    cursor: pointer;
  }

  /*点击查询最新游记或最热游记要用的样式*/
  .change_child_1 {
    color: #ff9d00;
  }

  .unchange_child_1 {
    color: #333;
  }

  /*花费和人均天数*/
  .local_con_left_search > div:nth-child(2) {
    width: 100%;
    height: 50px;
    display: flex;
    display: -webkit-flex;
    justify-content: flex-start;
    align-items: flex-start;
  }

  .local_con_left_search > div:nth-child(2) > span {
    font-size: 12px;
    font-family: Arial, "Lucida Grande", "Microsoft Yahei", "Hiragino Sans GB",
      "Hiragino Sans GB W3", SimSun, "PingFang SC", STHeiti;
    color: #666;
    margin-right: 10px;
  }

  /*内容盒子div*/
  .local_con_left_content {
    width: 100%;
    min-height: 220px;
    height: auto !important;
    border-bottom: 2px #efefef dotted;
    display: flex;
    display: -webkit-flex;
    align-items: center;
    justify-content: space-between;
  }

  .local_con_left_content_img {
    width: 30%;
    height: 180px;
    text-align: left;
    .img {
      width: 100%;
      height: 100%;
    }
  }

  .local_con_left_content_img > img {
    height: 180px;
  }

  .local_con_left_content_text {
    width: 68%;
    height: 180px;
    text-align: left;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    align-items: flex-start;
  }

  .local_con_left_content_text > div:nth-child(1) > a {
    width: 50%;
    height: 25px;
    font-size: 20px;
    line-height: 25px;
    display: inline-block;
    color: #ff7200;
    cursor: pointer;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
  }

  .local_con_left_content_text > div:nth-child(1) > a:hover {
    text-decoration: underline;
  }
  .local_con_left_content_text > div {
    width: 100%;
    .info_view {
      color: #999;
      font-size: 1em;
      margin-right: 10px;
    }
  }

  .local_con_left_content_text > div:nth-child(2) {
    display: flex;
    display: -webkit-flex;
    justify-content: flex-start;
    align-items: center;
    width: 100%;
    height: 25px;
  }

  .local_con_left_content_text > div:nth-child(2) > img {
    width: 20px;
    height: 20px;
    margin-right: 10px;
  }

  .local_con_left_content_text > div:nth-child(2) > div {
    line-height: 20px;
    font-size: 12px;
    color: #ff7200;
  }

  .local_con_left_content_text > div:nth-child(3) {
    height: 48px;
    margin: 8px 0;
    font-size: 12px;
    line-height: 24px;
    overflow: hidden;
    color: #666;
  }

  /*右边信息*/
  .local_con_right {
    float: left;
    width: 20%;
    min-height: 600px;
    height: auto !important;
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    justify-content: flex-start;
    text-align: left;
  }

  .lvgl {
    width: 100%;
    height: 30px;
    line-height: 30px;
    font-size: 16px;
    color: #666;
  }

  .lvgl > i {
    margin-right: 10px;
    color: #6fb6f3;
    font-weight: 500;
  }

  .lvpm {
    width: 100%;
    height: auto;
    list-style: none;
    color: #666;
  }

  .lvpm > li {
    list-style: none;
    line-height: 30px;
  }

  .lvpm > li > em {
    font-size: 20px;
    font-family: Letter Gothic Std, Verdana, Geneva, sans-serif;
    /* text-align: right; */
    font-weight: bold;
    margin-right: 10px;
    overflow: hidden;
  }

  .change_lvpm_q3 {
    color: #ffa800;
  }

  .lvpm > li > strong {
    font-size: 14px;
    font-weight: normal;
    margin-right: 19px;
  }

  .lvpm > li > a {
    color: #999;
    cursor: pointer;
    font-size: 12px;
  }
}
</style>