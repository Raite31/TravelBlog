<template>
  <div id="diary_statistics">
    <!-- 热门城市 -->
    <div id="echart1" ref="echart1">1</div>
    <div id="echart2" ref="echart2">2</div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      echarts1Data: [],
      echarts2Data: [],
    };
  },
  mounted() {
    // 获取热门城市的数据
    this.getEcharts1Data();
    this.getEcharts2Data();
  },
  methods: {
    // 获取数据
    getEcharts2Data() {
      this.postRequest("/countMoney")
        .then((success) => {
          this.echarts2Data = success.data;
          this.createEchart2();
        })
        .catch((error) => {});
    },
    getEcharts1Data() {
      this.postRequest("/hotCity")
        .then((success) => {
          let data = success.data;
          if (data != null) {
            data.forEach((element) => {
              let obj = { value: element.watch, name: element.place };
              this.echarts1Data.push(obj);
              //   console.log("obj:", obj);
              //   console.log("看看：", this.echarts1Data);
            });
          }
          this.createEchart1();
        })
        .catch((error) => {
          console.log("失败");
        });
    },
    // 创建图表
    createEchart1() {
      // 1. 获取节点
      const chart1 = this.$refs.echart1;
      //   2. echarts的初始化
      const myEchart1 = this.$echarts.init(chart1);
      //   3. 获取图表信息
      const option = {
        title: {
          text: "热门城市",
          subtext: "热门城市统计",
          left: "center",
        },
        tooltip: {
          trigger: "item",
        },
        legend: {
          orient: "vertical",
          left: "left",
        },
        series: [
          {
            name: "数量",
            type: "pie",
            radius: "50%",
            data: this.echarts1Data,
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: "rgba(0, 0, 0, 0.5)",
              },
            },
          },
        ],
      };
      // 4. 设置echarts的图片信息
      myEchart1.setOption(option);
    },

    createEchart2() {
      const chart2 = this.$refs.echart2;
      const myEchart2 = this.$echarts.init(chart2);
      const option = {
        title: {
          text: "花费统计",
        },
        xAxis: {
          type: "category",
          data: ["50k以上", "40k-50k", "10k-30k", "1-10k"],
        },
        yAxis: {
          type: "value",
        },
        series: [
          {
            data: this.echarts2Data,
            type: "line",
          },
        ],
      };
      myEchart2.setOption(option);
    },
  },
};
</script>

<style lang="scss">
#diary_statistics {
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  #echart1 {
    width: 70%;
    height: 400px;
    // background: red;
  }
  #echart2 {
    width: 70%;
    height: 400px;
    // background: blue;
  }
}
</style>