<template>
  <div id="srategy">
    <Head></Head>
    <div class="local_con">
      <!--左边start-->
      <div class="local_con_left" style="padding-bottom: 30px">
        <!--顶部搜索Start-->
        <div class="local_con_left_sousuo">
          <ul>
            <!--  <li class="change_local_con_left_sousuo_li">全部</li> -->
            <li
              v-for="(item, index) in classify"
              :key="index"
              :class="
                index == row
                  ? 'change_local_con_left_sousuo_li'
                  : 'unchange_local_con_left_sousuo_li'
              "
              @click="selectArticle(item, index)"
            >
              {{ item }}
            </li>
          </ul>
          <span><label>更多</label></span>
        </div>
        <!--顶部搜索END-->

        <!--左边内容Start-->
        <div class="local_con_left_content">
          <!--每一个子内容START-->
          <div v-for="article in articles" :key="article.id">
            <img class="local_con_left_content_img" :src="article.cover" />
            <div class="local_con_left_content_title">
              <div>
                <a @click="goIndexContent(article.id)">{{ article.title }}</a>
              </div>
              <div>
                {{ article.abs }}
              </div>
              <div>
                <span
                  ><i class="el-icon-location-outline"></i
                  >{{ article.place }}</span
                >
                <span><i class="el-icon-view"></i>{{ article.watch }}</span>
              </div>
            </div>
          </div>
          <!--每一个子内容END-->
        </div>
        <!--左边内容END-->
      </div>
      <!--左边end-->

      <!--右边start-->
      <div class="local_con_right">
        <span>本周热卖</span>
        <!--右边每一个热卖Start-->
        <div v-for="hotsell in hotsells" :key="hotsell.id">
          <img :src="hotsell.img" class="local_con_right_div_img" />
          <div class="local_con_right_div_content">
            <div>
              {{ hotsell.title }}
            </div>
            <div>￥{{ hotsell.price }}</div>
          </div>
        </div>
        <!--右边每一个热卖-->
      </div>
      <!--右边end-->
    </div>
  </div>
</template>

<script>
import Head from "@/components/Head.vue";
export default {
  data() {
    return {
      row: 1,
      classify: ["全部", "星空", "海", "沙漠", "草原"],
      clf: "全部",
      articles: [
        {
          id: 1,
          abs: "第一篇旅游攻略",
          cover: "http://localhost:8081/image/home/1.jpg",
          place: "佛山",
          watch: 100,
          price: 1000,
          title: "佛山之旅",
        },
        {
          id: 2,
          abs: "第二篇旅游攻略",
          cover: "http://localhost:8081/image/home/2.jpg",
          place: "佛山",
          watch: 100,
          price: 1000,
          title: "佛山之旅",
        },
        {
          id: 3,
          abs: "第三篇旅游攻略",
          cover: "http://localhost:8081/image/home/3.jpg",
          place: "佛山",
          watch: 100,
          price: 1000,
          title: "佛山之旅",
        },
      ],
      hotsells: [
        {
          id: 1,
          title: "热卖信息1",
          price: 1000,
          img: "http://localhost:8081/image/home/1.jpg",
        },
        {
          id: 2,
          title: "热卖信息2",
          price: 1000,
          img: "http://localhost:8081/image/home/2.jpg",
        },
        {
          id: 3,
          title: "热卖信息3",
          price: 1000,
          img: "http://localhost:8081/image/home/3.jpg",
        },
      ],
    };
  },
  mounted() {
    this.selectStrategyAll();
  },
  methods: {
    selectStrategyAll() {
      let param = "classify=" + this.clf;
      this.postRequest("/selectStrategyAll?" + param)
        .then((res) => {
          // console.log(res.data);
          // 分别取出arrayList的热卖信息和评论
          this.articles = res.data[0];
          this.hotsells = res.data[1];
        })
        .catch((error) => {});
    },
    selectArticle(item, index) {
      this.row = index;
      this.clf = item;
      // console.log(this.clf);
      // 发送请求
      this.selectStrategyAll();
    },
  },
  components: {
    Head,
  },
};
</script>

<style lang="scss" scoped>
.local_con {
  width: 80vw;
  height: auto !important;

  margin: 30px auto;
}

/*左边信息*/
.local_con_left {
  width: 76%;
  float: left;
  background-color: white;
  margin-right: 4%;
  margin-top: 10px;
  height: auto !important;
}

/*左边内容*/
.local_con_left_content {
  width: 100%;
  min-height: 430px;
  height: auto !important;
}

/*每一个内容*/
.local_con_left_content > div {
  width: 100%;
  height: 200px;
  display: flex;
  display: -webkit-flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 15px;
  cursor: pointer;
}
.local_con_left_content > div:hover {
  background: #e5e5e5;
}
.changelocal_con_left_content {
  background-color: #f6f6f6;
}

/*照片*/
.local_con_left_content_img {
  width: 30%;
  height: 80%;
}

/*内部标题*/
.local_con_left_content_title {
  width: 60%;
  height: 80%;
  margin-left: 2%;
  display: flex;
  display: -webkit-flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: flex-start;
  text-align: left;
}

.local_con_left_content_title > div:nth-child(1) {
  font-size: 18px;
  color: #333;
  width: 100%;

  height: 18px;
  line-height: 18px;
  overflow: hidden;
}

.local_con_left_content_title > div:nth-child(2) {
  width: 100%;
  font-size: 14px;
  height: 88px;
  line-height: 22px;
  color: #666666;
  overflow: hidden;
}

.local_con_left_content_title > div:nth-child(3) {
  width: 100%;
  height: 30px;
  line-height: 30px;
  color: #666;
  font-size: 14px;
}

.local_con_left_content_title > div:nth-child(3) > span:nth-child(1) {
  margin-right: 10px;
}

/*左边顶部搜索框*/
.local_con_left_sousuo {
  width: 100%;
  min-height: 50px;
  height: auto !important;
  box-shadow: 0px 2px 0px #e5e5e5;
  display: flex;
  display: -webkit-flex;
  justify-content: space-around;
  align-items: flex-start;
}

.local_con_left_sousuo > ul {
  margin: 0px;
  padding: 0px;
  width: 80%;
  min-height: 50px;
  height: auto !important;
}

.local_con_left_sousuo > ul > li {
  list-style: none;
  width: 15%;
  height: 22px;
  font-size: 12px;
  line-height: 22px;
  text-align: center;
  border-radius: 3.125rem;
  cursor: pointer;
  float: left;
  margin-right: 3%;
  margin-bottom: 10px;
  border: 1px solid #e9e9e9;
}

/*li选中和不选中的样式*/
.change_local_con_left_sousuo_li {
  color: white;
  background-color: orange;
  border: 0px;
}

.unchange_local_con_left_sousuo_li {
  background-color: #f6f6f6;
  color: #666;
}

.local_con_left_sousuo > span {
  width: 20%;
  height: 50px;
  font-size: 12px;
  text-align: center;
}

.local_con_left_sousuo > span > label {
  user-select: none;
  cursor: pointer;
  color: orange;
}

/*右边信息*/
.local_con_right {
  float: left;
  width: 18%;
  text-align: left;
  padding-top: 0px;
  min-height: 600px;
  height: auto !important;
}

.local_con_right > span:nth-child(1) {
  font-size: 18px;
  line-height: 18px;
  padding: 0px;
  margin: 0px;
  color: #333;
}

.local_con_right > div {
  margin-top: 15px;
  margin-bottom: 5px;
  width: 100%;
  height: 70px;
  display: flex;
  display: -webkit-flex;
  align-items: center;
  justify-content: space-around;
}

.local_con_right_div_img {
  width: 40%;
  height: 100%;
}

.local_con_right_div_content {
  width: 60%;
  height: 100%;
}

.local_con_right_div_content > div:nth-child(1) {
  width: 90%;
  height: 27.5px;
  padding-left: 10%;
  font-size: 14px;
  color: #333;
  /*使用以下可以使得多行文本用..代替:只允许在内核为谷歌的情况下使用
    ie并不兼容.使用了
    line-height:14px;
    max-height:27.5px;
    弥补ie的不足。使得了虽然没有省略号但是不溢出
    */
  overflow: hidden;
  text-overflow: ellipsis;
  -ms-text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-box-orient: vertical; /**/
  -webkit-line-clamp: 2; /* 控制显示的行数 */
  line-height: 14px; /* 对不支持浏览器的 */
  max-height: 27.5px; /* 对不支持浏览器的弥补 */
}

.local_con_right_div_content > div:nth-child(2) {
  width: 90%;
  padding-left: 10%;
  font-size: 14px;
  height: 30px;
  line-height: 42px;
  color: orange;
  font-weight: 500;
  margin-top: 12px;
}
</style>