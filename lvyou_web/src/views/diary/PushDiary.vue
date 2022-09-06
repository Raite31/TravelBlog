<template>
  <div id="push">
    <el-form ref="formName" :rules="rules" :model="diary">
      <!-- :model是  -->
      <el-form-item prop="title">
        <el-input v-model="diary.title" placeholder="请输入标题"></el-input>
      </el-form-item>
      <!-- 封面 -->
      <div class="img-upload">
        <!-- 没上传时候的+号 -->
        <el-upload
          action="http://localhost:8081/uploadImage"
          :on-success="uploadImageSuccess"
          :show-file-list="false"
          :data="{ folder: folder }"
        >
          <i v-if="!showImg" class="el-icon-plus"></i>
        </el-upload>
        <!-- 上传后的图片 -->
        <el-image
          @click="previewImg(imgUrl)"
          v-if="showImg"
          class="img"
          :src="imgUrl"
        ></el-image>
        <!-- 清除图片 -->
        <i
          @click="deleteImg"
          v-if="showImg"
          class="deleteImg el-icon-delete"
        ></i>
      </div>
      <!-- 简介 -->
      <el-form-item prop="abs">
        <el-input v-model="diary.abs" placeholder="请输入简介"></el-input>
      </el-form-item>
      <div class="other">
        <!-- 地理位置 -->
        <el-form-item prop="place">
          <el-select v-model="diary.place" placeholder="请选择地理位置">
            <el-option label="广州" value="广州"></el-option>
            <el-option label="上海" value="上海"></el-option>
            <el-option label="佛山" value="佛山"></el-option>
            <el-option label="长沙" value="长沙"></el-option>
          </el-select>
        </el-form-item>
        <!-- 出发时间 -->
        <el-form-item prop="goTime">
          <el-date-picker
            v-model="diary.goTime"
            type="date"
            placeholder="出发时间"
          ></el-date-picker>
        </el-form-item>
        <!-- 分类 -->
        <el-form-item prop="classify">
          <el-select v-model="diary.classify" placeholder="分类">
            <el-option label="星空" value="星空"></el-option>
            <el-option label="海" value="海"></el-option>
            <el-option label="沙漠" value="沙漠"></el-option>
            <el-option label="草原" value="草原"></el-option>
          </el-select>
        </el-form-item>
        <!-- 随同人员 -->
        <el-form-item prop="accompany">
          <el-select v-model="diary.accompany" placeholder="随同人员">
            <el-option label="情侣" value="情侣"></el-option>
            <el-option label="朋友" value="朋友"></el-option>
            <el-option label="父母" value="父母"></el-option>
            <el-option label="草原" value="草原"></el-option>
          </el-select>
        </el-form-item>
      </div>
      <!-- 所需天数 -->
      <el-form-item prop="day">
        <el-input
          v-model.number="diary.day"
          placeholder="请输入所需天数"
        ></el-input>
      </el-form-item>
      <!-- 金额 -->
      <el-form-item prop="money">
        <el-input
          v-model.number="diary.money"
          placeholder="请输入所需金额"
        ></el-input>
      </el-form-item>
      <!-- 富文本编辑器 -->
      <div id="editor">
        <umeditor v-model="content" @receiveUM="receiveUM" />
      </div>
      <!-- 提交按钮 -->
      <el-form-item class="submit">
        <el-button @click="submit" type="primary" plain>发布</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      rules: {
        title: [{ required: true, message: "请输入标题", trigger: "blur" }],
        abs: [{ required: true, message: "请输入简介", trigger: "blur" }],
        place: [{ required: true, message: "请选择地点", trigger: "blur" }],
        goTime: [{ required: true, message: "请选择时间", trigger: "blur" }],
        classify: [{ required: true, message: "请选择分类", trigger: "blur" }],
        accompany: [
          { required: true, message: "请选择随同人员", trigger: "blur" },
        ],
        day: [
          { required: true, message: "请输入天数", trigger: "blur" },
          { type: "number", message: "天数必须为数字值" },
        ],
        money: [
          { required: true, message: "请输入金额", trigger: "blur" },
          { type: "number", message: "所需金额必须为数字值" },
        ],
      },
      editor: "",
      content: "<p>everyone is welcome to join</p>",
      imgUrl: "",
      showImg: false,
      folder: "", // 文件夹名
      diary: {
        id: "",
        title: "",
        abs: "",
        place: "",
        cover: "",
        content: "",
        watch: 0,
        classify: "",
        uid: "",
        goTime: "",
        day: "",
        money: "",
        accompany: "",
        writeTime: "",
        folder: "",
      },
    };
  },
  mounted() {
    // 先获取文章id
    let id = this.$route.query.id;
    // console.log(id);
    if (id == null || id == "" || id == undefined) {
      let time = new Date().getTime(); // 时间戳
      let random = Math.ceil(Math.random() * 10); // 生成一个随机数，防止真的在同一时间戳有多人上传
      let name = time * random; // 最终获得的
      this.folder = "diary/" + name + "/"; // 赋值
      // console.log("添加");
    } else {
      // console.log("修改");
      this.queryDiaryByID(id);
    }
  },
  methods: {
    // 通过文章id查询文章信息
    queryDiaryByID(id) {
      // 通过异步请求获取数据
      this.postRequest("/queryDiaryById?id=" + id)
        .then((success) => {
          // 重新为diary对象赋值（回显
          this.diary = success.data;
          // 封面和富文本的回显
          this.showImg = true;
          this.imgUrl = success.data.cover;
          this.content = success.data.content;
          this.folder = success.data.folder;
        })
        .catch((error) => {});
    },
    // 提交
    submit() {
      this.$refs["formName"].validate((valid) => {
        if (valid) {
          // 继续校验是否有封面;
          if (
            this.imgUrl == null ||
            this.imgUrl == "" ||
            this.imgUrl == undefined
          ) {
            this.$message.error("封面必须上传");
            return false;
          } else if (
            // 校验内容
            this.content == null ||
            this.content == "" ||
            this.content == undefined
          ) {
            this.$message.error("内容不能为空");
            return false;
          } else {
            // 上传图片并插入数据
            // alert("submit success");
            this.uploadEditorImage();
          }

          // this.uploadEditorImage();
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    // 上传富文本编辑器的图片
    uploadEditorImage() {
      // console.log("1111111111");
      // 将富文本编辑器的预览图片替换成本地存储
      const images = this.editor.getFileFormData();
      const formData = new FormData();
      if (images != null) {
        images.forEach((element) => {
          formData.append("upfile[]", element);
        });
        // 发送post请求
        this.postRequest("/uploadEditorImage?folder=" + this.folder, formData)
          .then((res) => {
            // 打印测试
            console.log("测试返回数据");
            console.log(res.data);
            // 返回网络路径<数组> 重新替换掉富文本编辑器的图片路径
            this.editor.replaceImageUrl(res.data);
          })
          .catch((error) => {});
      }
      // 正常开始插入数据
      // 定时器的作用是让插入数据延迟，以确保拿到编辑器内的本地路径的图片后才插入数据
      setTimeout(() => {
        // 获取路由中的id
        let id = this.$route.query.id;
        if (id == null || id == "" || id == undefined) {
          // 判断是否是添加
          this.insert();
        } else {
          // 不为空，点击发布则是修改
          this.update();
        }
      }, 500);
    },
    update() {
      this.diary.cover = this.imgUrl;
      // console.log(this.diary.cover);
      this.diary.content = this.content;
      // console.log(this.diary.content);
      this.diary.uid = JSON.parse(sessionStorage.getItem("user")).id;
      this.diary.folder = this.folder;
      // 发送修改请求
      this.postRequest("/updateDiary", this.diary)
        .then((success) => {
          if (success.code == 200) {
            // 新增的内容成功跳转到游记
            this.$router.replace("/diary");
          }
        })
        .catch((error) => {});
    },
    // 富文本编辑器插入游记后，从它的源码可以看到图片是存在栈中的，栈又是在内存中的，那么清一下内存这个图片就没了，所以要想办法把图片存到服务器硬盘中
    insert() {
      // 准备好数据
      this.diary.cover = this.imgUrl;
      this.diary.content = this.content;
      this.diary.uid = JSON.parse(sessionStorage.getItem("user")).id;
      this.diary.folder = this.folder;
      // 发送插入数据请求
      this.postRequest("/insertDiary", this.diary)
        .then((success) => {
          if (success.code == 200) {
            // 新增的内容成功跳转到游记
            this.$router.replace("/diary");
          }
        })
        .catch((error) => {});
    },
    // 删除封面图片
    deleteImg() {
      // 准备参数
      let params = "path= " + this.imgUrl;
      this.postRequest("/deleteCover?" + params)
        .then((success) => {
          this.showImg = false;
          this.imgUrl = "";
        })
        .catch((error) => {});
    },
    // 初始化富文本编辑器
    receiveUM(editor) {
      this.editor = editor;
    },
    // 预览图片
    previewImg(url) {
      this.$hevueImgPreview(url);
    },
    // 上传图片
    uploadImageSuccess(success) {
      this.imgUrl = success.data; // 将http://localhost:8081/diary/文件夹/图片赋值给imgUrl
      this.showImg = true;
    },
  },
};
</script>

<style lang="scss">
#push {
  width: 80vw;
  height: 600px;
  margin: 0px auto;
  padding-top: 50px;
  padding-bottom: 20px;
  .other {
    width: 80vw;
    height: 100px;
    margin-bottom: 10px;
    margin-top: 10px;
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
  .img-upload:hover {
    filter: blur(0px);
  }
  .img-upload {
    width: 80vw;
    height: 300px;
    background-color: #dfdfdf;
    margin-bottom: 20px;
    font-size: 5em;
    display: flex;
    cursor: pointer;
    justify-content: center;
    align-items: center;
    filter: blur(2px);
    position: relative;
    .img {
      width: 50%;
      height: 150px;
    }
    .deleteImg {
      position: absolute; /* 相对右边 */
      right: 4px;
      top: 5px;
      font-size: 0.3em;
    }
  }
  .submit {
    text-align: left;
    height: 100px;
    line-height: 50px;
    margin-top: 20px;
  }
}
</style>