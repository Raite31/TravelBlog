<template>
  <div id="index">
    <Head></Head>
    <el-container id="el-container">
      <el-aside id="el-aside" width="150px">
        <a
          :class="index == 1 ? 'router-link-exact-active' : ''"
          @click="selectNav(1, '/diary_statistics')"
          >游记数据统计</a
        >
        <a
          :class="index == 2 ? 'router-link-exact-active' : ''"
          @click="selectNav(2, '/epidemic')"
          >疫情数据统计</a
        >
      </el-aside>
      <el-main id="el-main">
        <router-view></router-view>
      </el-main>
    </el-container>
  </div>
</template>

<script>
import Head from "@/components/Head.vue";
export default {
  data() {
    return {
      index: 1,
    };
  },
  methods: {
    selectNav(index, url) {
      this.index = index;
      this.$router.push(url);
    },
  },
  components: {
    Head,
  },
};
</script>

<style lang="scss">
#index {
  .router-link-exact-active {
    background: #ffa500;
    color: white !important;
  }
  a {
    color: black;
    text-decoration: none;
  }
  background-color: #d0d0d0;
  #el-container {
    margin-top: 10px;
    min-height: 100vh;
    height: auto !important;
    #el-aside {
      background-color: white;
      display: flex;
      flex-direction: column;
      justify-content: flex-start;
      align-items: center;
      a {
        width: 100%;
        height: 100px;
        line-height: 100px;
        border-bottom: 1px solid #dfdfdf;
        font-size: 1em;
        font-weight: bold;
      }
    }
    #el-main {
      background-color: white;
      margin-left: 15px;
    }
  }
}
</style>