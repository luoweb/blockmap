<template>
  <div id="about"> <!-- 关于我们页面 -->
    <yd-navbar title="About us"> <!-- 顶部导航栏 -->
    </yd-navbar>
    <div class="logo">
      <img src="../../assets/earth.png" alt="no resource" width="200" height="150"/>
    </div>
    <div class="about_content">
      <yd-cell-group>
        <yd-cell-item>
          <span slot="left">Version</span>
          <span slot="right">1.1.1</span>
        </yd-cell-item>
        <yd-cell-item arrow @click.native="showMobile = true" class="select">
          <span slot="left">Functions of APP Website</span>
        </yd-cell-item>
        <yd-cell-item arrow @click.native="showPC = true" class="select">
          <span slot="left">Functions of PC Website</span>
        </yd-cell-item>
        <yd-cell-item>
          <span slot="left">Copyright</span>
          <span slot="right">ICBC</span>
        </yd-cell-item>
      </yd-cell-group>
    </div>
    <yd-popup v-model="showMobile" position="center" width="80%" height="50%"> <!-- app功能介绍弹窗 -->
      <div class="popup">
        <p>App website is for individual users and we have the following functions in it:</p>
        <p>1.Path planning:</p>
        <img src="../../assets/about_first.png" alt="no resource" width="200" height="200"/>
        <p>2.Get DID identity:</p>
        <img src="../../assets/about_second.png" alt="no resource" width="200" height="230"/>
        <p>3.Epidemic report:</p>
        <img src="../../assets/about_third.png" alt="no resource" width="200" height="120"/>
        <p>4.Risk summary for everyday:</p>
        <img src="../../assets/about_fourth.png" alt="no resource" width="200" height="200"/>
      </div>
    </yd-popup>
    <yd-popup v-model="showPC" position="center" width="80%" height="50%"> <!-- app功能介绍弹窗 -->
      <div class="popup">
        <p>PC website is for superintendents and we have the following functions in it:</p>
        <p>1.Employee overview:</p>
        <img src="../../assets/about_fifth.png" alt="no resource" width="200" height="100"/>
        <p>2.Branch management:</p>
        <img src="../../assets/about_sixth.png" alt="no resource" width="200" height="100"/>
        <p>3.Information audit:</p>
        <img src="../../assets/about_seventh.png" alt="no resource" width="200" height="100"/>
        <p>4.Epidemic report:</p>
        <img src="../../assets/about_eighth.png" alt="no resource" width="200" height="100"/>
        <p>5.Epidemic data statistical analysis:</p>
        <img src="../../assets/about_ninth.png" alt="no resource" width="200" height="100"/>
      </div>
    </yd-popup>
    <yd-button shape="angle" size="small" bgcolor="#C0C0C0" @click.native="backToHome" class="back">
      <img src="../../assets/back.png" alt="no resource" width="24" height="24"/>
    </yd-button> <!-- 返回按钮 #2db7f5 -->
  </div>
</template>

<script>
export default {
  name: 'about',
  data () {
    return {
      showMobile: false,
      showPC: false
    }
  },
  methods: { // 函数
    backToHome () { // 返回地图主页
      this.$router.push('/')
    }
  }
}
</script>

<style scoped>
.back{ /*返回按钮*/
  position: absolute;
  z-index: 99; /*在最高层*/
  opacity: 0.5; /*设置透明度*/
  top: 1.5rem; /*离手机顶部的距离*/
}
.about_content{ /*内容*/
  position: fixed; /*绝对定位*/
  top: 45vh; /*上边距离*/
  width: 100%; /*宽度*/
  padding-left: 0.5rem; /*左内边距*/
  padding-right: 0.5rem; /*右内边距*/
}
.logo{ /*地球图标*/
  position: fixed; /*绝对定位*/
  top: 15vh; /*上边距离*/
  left: 50%; /*与下面的平移结合，使图片居中*/
  transform:translate(-100px); /*图片宽度高度均为200px*/
  -webkit-transform: translateX(-100px); /*兼容Safari*/
}
p{
  font-size: 0.3rem; /*字体大小*/
  margin-top: 0.3rem; /*上外边距*/
}
.popup{
  padding-bottom: .24rem; /*下内边距*/
  padding-left: 0.5rem; /*左内边距*/
  padding-right: 0.5rem; /*右内边距*/
  height: 7rem; /*限制高度*/
  background-color: white; /*背景颜色*/
  overflow: scroll /*超出部分滚动显示*/
}
.select:active{ /*选项点击效果*/
  background-color: lightgrey;
}
</style>
