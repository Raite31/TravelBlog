<template>
  <div id="city_epidemic" class="container">
    <div class="container-page container-nav">
      <div class="container-nav-div">
        <div>新型冠状病毒肺炎</div>
        <div>疫情实时大数据报告</div>
        <div><a href="/epidemic.html">去疫情首页</a></div>
      </div>
      <div class="container-nav-div">
        <div>切换地区</div>
      </div>
    </div>
    <!--底部显示START-->
    <div class="container-page epidemic">
      <div class="epidemic-title">
        <a class="change-epidemic-title" href="javascript:;">疫情动态</a>
        <a href="javascript:;">本地播报</a>
      </div>
      <!--数据START-->
      <div class="epidemic-china">
        <div class="title">北京市-西城区</div>
        <!--数据播报START-->
        <div class="epidemic-china-data">
          <div class="info">
            <div>
              <div>新增本土</div>
              <div class="data">{{ city.today.confirm }}</div>
            </div>
            <div>
              <div>新增治愈</div>
              <div class="data">{{ city.today.heal }}</div>
            </div>
            <div>
              <div>新增死亡</div>
              <div class="data blue">{{ city.today.dead }}</div>
            </div>
          </div>

          <div class="info">
            <div>
              <div>累计确诊</div>
              <div class="data">{{ city.total.confirm }}</div>
            </div>
            <div>
              <div>累计治愈</div>
              <div class="data" style="color: #10aeb5">
                {{ city.total.heal }}
              </div>
            </div>
            <div>
              <div>累计死亡</div>
              <div class="data blue">{{ city.total.dead }}</div>
            </div>
          </div>
        </div>
        <!--数据播报END-->

        <!--地图START-->
        <div class="map-container">
          <!--地图按钮-->
          <div class="map-input">
            <div class="change-map-input">现有确诊</div>
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
</template>

<script>
import { getData } from "ajv/dist/compile/validate";

export default {
  data() {
    return {
      city: {},
      provinces: [],
    };
  },
  mounted() {
    this.getData();
  },
  methods: {
    getData() {
      $.ajax({
        type: "GET",
        url: "/data2.json",
        success: (success) => {
          // 1. 先获取省份名和城市名
          let city = this.$route.query.city;
          let provinceName = this.$route.query.provinceName;
          //   2. 获取所有的省份信息
          this.provinces = success.data.areaTree[2].children;
          //   3. 遍历查询对应的城市
          this.provinces.forEach((element) => {
            if (element.name == provinceName) {
              // 遍历该省所有城市，找到查询的城市
              element.children.forEach((element) => {
                if (element.name == city) {
                  this.city = element; // 为城市重新赋值
                }
              });
            }
          });
          //   将null改为0
          if (this.city.today.confirm == null) this.city.today.confirm = 0;
          if (this.city.today.dead == null) this.city.today.dead = 0;
          if (this.city.today.heal == null) this.city.today.heal = 0;
        },
      });
    },
  },
};
</script>

<style lang="scss">
#city_epidemic {
  width: 100%;
  height: 150vh;
  background-color: #10aeb5;
  position: relative;

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
    text-align: left;
    align-items: flex-start;
    background-image: url("@/assets/epidemic_bg.png");
    background-size: cover;
    background-repeat: no-repeat;
  }

  .container-nav-div {
    color: #afdade;
    flex: auto;
  }

  .container-nav-div:nth-child(1) > div:nth-child(1) {
    font-size: 10px !important;
    color: #c4faff;
  }

  .container-nav-div:nth-child(1) > div:nth-child(2) {
    color: #afdade;
    font-size: 2rem;
    font-weight: bold;
    mask-image: -webkit-gradient(
      linear,
      0 0,
      0 bottom,
      from(rgba(0, 0, 255, 0)),
      to(#afdade)
    );
  }

  a {
    color: white;
    text-decoration: none;
  }

  .container-nav-div:nth-child(1) > div:nth-child(3) {
    width: 150px;
    font-size: 6px !important;
    color: white;
    border-radius: 15px;
    background-color: rgba(9, 148, 167, 0.5);
    padding-left: 5px;
    margin-top: 10px;
    padding-left: 10px;
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
    text-align: left;
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