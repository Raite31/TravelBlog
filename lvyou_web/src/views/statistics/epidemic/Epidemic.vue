<template>
  <div id="epidemic">
    <div class="container">
      <div class="container-page container-nav">
        <div class="container-nav-div">
          <div>新型冠状病毒肺炎</div>
          <div>疫情实时大数据报告</div>
          <div>数据来源国家和省市卫健委</div>
        </div>
        <div class="container-nav-div">
          <div @click="select_city">切换地区</div>
        </div>
      </div>
      <!--底部显示START-->
      <div class="container-page epidemic">
        <div class="epidemic-title">
          <a class="change-epidemic-title" href="javascript:;">国内疫情</a>
          <a href="javascript:;">国外疫情</a>
        </div>
        <!--数据START-->
        <div class="epidemic-china">
          <div class="title">国内疫情</div>
          <!--数据播报START-->
          <div class="epidemic-china-data">
            <div class="info">
              <div>
                <div>新增确诊</div>
                <div class="data">{{ today.confirm }}</div>
              </div>
              <div>
                <div>新增境外</div>
                <div class="data">{{ today.input }}</div>
              </div>
              <div>
                <div>新增治愈</div>
                <div class="data blue">{{ today.heal }}</div>
              </div>
              <div>
                <div>新增死亡</div>
                <div class="data">{{ today.dead }}</div>
              </div>
            </div>

            <div class="info">
              <div>
                <div>累计确诊</div>
                <div class="data">{{ total.confirm }}</div>
              </div>
              <div>
                <div>累计境外</div>
                <div class="data blue">{{ total.input }}</div>
              </div>
              <div>
                <div>累计治愈</div>
                <div class="data" style="color: #10aeb5">{{ total.heal }}</div>
              </div>
              <div>
                <div>累计死亡</div>
                <div class="data">{{ total.dead }}</div>
              </div>
            </div>
          </div>
          <!--数据播报END-->

          <!--地图START-->
          <div class="map-container">
            <!--地图按钮-->
            <div class="map-input">
              <div class="change-ma nput">现有确诊</div>
              <div>新增确诊</div>
            </div>
            <!--地图数据-->
            <div id="china-map" class="map-data"></div>
          </div>

          <!--地图END-->
        </div>
        <!--数据END-->

        <!--结束START-->
        <div class="epidemic-end">
          <div>上百度APP搜索 新型冠状病毒肺炎</div>
          <div>了解最新进展及权威预防知识</div>
        </div>
        <!--结束END-->
      </div>
      <!--数据END-->
    </div>
    <!--底部显示-->
  </div>
</template>

<script>
import "echarts/lib/chart/map";
import "echarts/map/js/china";
export default {
  data() {
    return {
      today: {
        confirm: "",
        input: "",
        heal: "",
        dead: "",
      },
      total: {
        confirm: "",
        input: "",
        heal: "",
        dead: "",
      },
      //   大的数据
      epidemicData: {},
    };
  },
  mounted() {
    // 一开始获取数据;
    this.getData();
  },

  methods: {
    // 切换城市跳转
    select_city() {
      this.$router.push("/select_city");
    },
    // 获取疫情数据
    getData() {
      $.ajax({
        type: "GET",
        url: "/data2.json",
        success: (success) => {
          console.log(success);
          Object.assign(this.total, success.data.chinaTotal.total);
          Object.assign(this.today, success.data.chinaTotal.today);
          // 大的数据
          this.epidemicData = success.data;
          this.createMap();
        },
      });
    },
    // 创建地图
    createMap() {
      //疫情数据
      let epidemicData = {};
      //数据拷贝
      Object.assign(epidemicData, this.epidemicData);
      // 1.初始化echarts
      var mycharts = this.$echarts.init(document.getElementById("china-map"));
      //   2. 图表数据
      var option = {
        tooltip: {
          // show: false //不显示提示标签
          triggerOn: "mousemove|click",
          enterable: true,
          formatter: function (params) {
            //获取所有省份
            let children = epidemicData.areaTree[2].children;
            //每个省份疫情累积量
            let confirm = 0;
            //遍历找到鼠标悬停的省份一致的数据
            children.forEach((element) => {
              if (params.name == element.name) {
                confirm = element.total.confirm;
              }
            });

            return (
              "地区:" + params.name + "<br/>" + "确诊:" + confirm + "<br/>"
            );
          }, //提示标签格式

          backgroundColor: "rgba(50, 50, 50, 0.7);", //提示标签背景颜色

          textStyle: { color: "#fff" }, //提示标签字体颜色
        },

        series: [
          {
            type: "map", //类型地图
            mapType: "china", //应用中国地图，在引入china.js中已经注册
            label: {
              normal: {
                show: true, //显示省份标签
                textStyle: { color: "#222222" }, //省份标签字体颜色
              },

              emphasis: {
                //对应的鼠标悬浮效果

                show: true,

                textStyle: { color: "#800080" }, //移入时的字体颜色，不是悬浮框
              },
            },

            itemStyle: {
              //原本的颜色
              normal: {
                borderWidth: 0.5, //区域边框宽度
                borderColor: "#009fe8", //区域边框颜色
                areaColor: "#ffffff", //区域颜色
              },

              emphasis: {
                //高亮颜色
                borderWidth: 0.5,
                borderColor: "#4b0082",
                areaColor: "#ffdf3f",
              },
            },
            data: [{ name: "哈尔滨", value: [126.63, 45.75] }],
          },
        ],
      };
      //   3. 应用图表数据
      mycharts.setOption(option);
    },
  },
};
</script>

<style lang="scss">
#epidemic {
  background-color: #10aeb5;

  .container {
    min-height: 200vh;
    height: auto !important;
    position: relative;
  }

  .container-page {
    width: 50vw;
    min-height: 20vh;
    position: absolute;
    top: 0%;
    left: 50%;
    transform: translate(-50%, 0%);
    z-index: 99;
  }

  .container-nav {
    height: 200px;
    display: flex;
    display: -webkit-flex;
    justify-content: space-around;
    align-items: flex-start;
    background-image: url("@/assets/epidemic_bg.png");
    background-size: cover;
    background-repeat: no-repeat;
  }

  .container-nav-div {
    color: #afdade;
    flex: auto;
    text-align: left;
  }

  .container-nav-div:nth-child(1) > div:nth-child(1) {
    font-size: 10px !important;
    color: #c4faff;
  }

  .container-nav-div:nth-child(1) > div:nth-child(2) {
    color: #afdade;
    font-size: 18px;
    font-weight: bold;
    mask-image: -webkit-gradient(
      linear,
      0 0,
      0 bottom,
      from(rgba(0, 0, 255, 0)),
      to(#afdade)
    );
  }

  .container-nav-div:nth-child(1) > div:nth-child(3) {
    width: 150px;
    font-size: 6px !important;
    color: white;
    border-radius: 15px;
    background-color: rgba(9, 148, 167, 0.5);
    padding-left: 5px;
    margin-top: 10px;
    cursor: pointer;
  }

  .container-nav-div > div {
    padding-top: 5px;
    padding-bottom: 5px;
  }

  .container-nav-div:nth-child(2) {
    display: flex;
    display: -webkit-flex;
    justify-content: flex-end;
    align-items: flex-start;
    margin-right: 10px;
  }

  .container-nav-div:nth-child(2) > div {
    margin-top: 10px;
    border-radius: 15px;
    background-color: #e5feff;
    font-size: 5px !important;
    padding-left: 10px;
    padding-right: 10px;
    color: #104345;
  }

  .epidemic {
    top: 20vh;
    background-color: white;
    border-radius: 5px;
    height: auto !important;
  }

  .epidemic-title {
    width: 100%;
    height: 40px;
    border-bottom: 1px solid #efefef;
    display: flex;
    display: -webkit-flex;
    justify-content: space-around;
    align-items: center;
  }

  .epidemic-title > a {
    text-decoration: none;
    font-weight: 400;
    font-size: 14px;
    color: #000;
  }

  .change-epidemic-title {
    height: 100%;
    color: #10aeb5 !important;
    font-weight: 700 !important;
    display: flex;
    display: -webkit-flex;
    align-items: center;
    box-shadow: 0px 2px 0px #10aeb5;
  }

  .epidemic-china {
    width: 100%;
    height: auto !important;
    margin-top: 20px;
  }

  .epidemic-china > .title {
    width: 98%;
    height: 20px;
    padding-left: 2%;
    font-weight: bold;
    font-size: 14px;
    margin-bottom: 10px;
  }

  .epidemic-china .info {
    width: 80%;
    height: 40px;
    margin-bottom: 5px;
    display: flex;
    display: -webkit-flex;
    justify-content: space-around;
    align-items: center;
    font-size: 15px;
    color: #222222;
    padding-bottom: 10px;
    padding-top: 10px;
  }

  .epidemic-china-data {
    display: flex;
    display: -webkit-flex;
    flex-direction: column;
    justify-content: flex-start;
    align-items: center;

    margin-bottom: 20px;
  }

  .epidemic-china-data .data {
    font-size: 17px;
    text-align: center;
    font-weight: 800;
    color: #ff6a57;
  }

  .blue {
    color: #476da0 !important;
  }

  .map-container {
    width: 100%;
    height: 690px;
    background-color: rgb(248, 249, 250);
    display: flex;

    display: -webkit-flex;
    flex-direction: column;
    justify-content: flex-start;
    align-items: center;
  }

  .map-input {
    width: 98%;
    display: flex;
    display: -webkit-flex;
    justify-content: space-between;
    align-items: center;
    height: 45px;
    background-color: #e4e6eb;
    display: flex;
    display: -webkit-flex;
    justify-content: center;
    align-items: center;
  }

  .map-input > div {
    width: 49%;
    height: 40px;
    display: flex;
    display: -webkit-flex;
    justify-content: center;
    align-items: center;
    border-radius: 5px;
  }

  .change-map-input {
    background-color: white;
    font-weight: bold;
  }

  /*地图数据*/
  .map-data {
    width: 98%;
    height: 508px;
    margin-top: 20px;
    margin-top: 30px !important;
    background-color: rgb(248, 249, 250);
  }

  /*结束*/
  .epidemic-end {
    width: 100%;
    height: 70px;
    padding-top: 20px;
    background-color: #10aeb5;
    display: flex;
    color: white;
    display: -webkit-flex;
    flex-direction: column;
    justify-content: flex-start;
    align-items: center;
    font-size: 14px;
  }
}
</style>