<template>
  <div id="city_select" class="container">
    <div class="province">
      <a
        href="javascript:;"
        @click="showCity(item.name)"
        v-for="item in provinces"
        :key="item.name"
        >{{ item.name }}</a
      >
    </div>
    <div class="city">
      <a
        href="javascript:;"
        @click="queryCityEpidemic(item.name)"
        v-for="item in city"
        :key="item.name"
        >{{ item.name }}</a
      >
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      provinces: [],
      city: [],
      provinceName: "",
    };
  },
  mounted() {
    this.getData();
  },
  methods: {
    queryCityEpidemic(city) {
      this.$router.push({
        path: "/city_epidemic",
        query: { city: city, provinceName: this.provinceName },
      });
    },
    // 点击显示所有城市
    showCity(provinceName) {
      this.provinceName = provinceName;
      // 遍历找到对应省份
      this.provinces.forEach((element) => {
        if (element.name == provinceName) {
          this.city = element.children;
        }
      });
    },
    getData() {
      $.ajax({
        type: "GET",
        url: "/data2.json",
        success: (success) => {
          // 获取省份
          this.provinces = success.data.areaTree[2].children;
        },
      });
    },
  },
};
</script>

<style lang="scss">
#city_select {
  width: 100vw;
  height: auto;
  background-color: red;
  display: flex;
  display: -webkit-flex;
  justify-content: space-around;
  align-items: flex-start;
  .province {
    width: 10vw;
    height: 100vh;
    overflow-y: scroll;
    /*出现滚动条*/
    display: flex;
    display: -webkit-flex;
    flex-direction: column;
    justify-content: flex-start;
    align-items: center;
    background-color: #f5f6f7;
  }

  .province a {
    width: 100%;
    height: 50px;
    text-align: center;
    line-height: 50px;
    font-size: 16px;
    text-decoration: none;
    color: #333;
  }

  .city {
    width: 90vw;
    height: 100vh;
    overflow-y: scroll;
    display: flex;
    display: -webkit-flex;
    flex-direction: column;
    justify-content: flex-start;
    align-items: center;
    background-color: #f5f6f7;
  }

  .city a {
    width: 95%;
    padding-left: 5%;
    height: 50px;
    text-align: left;
    line-height: 50px;
    font-size: 16px;
    text-decoration: none;
    color: #333;
    font-weight: inherit;
    font-style: inherit;
    font-family: inherit;
    font-size: 100%;
    vertical-align: baseline;
  }
}
</style>