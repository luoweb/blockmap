<template>
  <div id="did"> <!-- 数字身份页面 -->
    <yd-navbar title="DID identity"> <!-- 顶部导航栏 -->
    </yd-navbar>
    <label>姓名：{{trueName}}</label>
    <span>Update by {{date}}</span>
    <div class="realcode">
      <img src="../../assets/realCode.png" alt="no resource" width="200" height="200"/>
    </div>
    <button class="getdid" v-on:click="getDidCode">press to get DID code</button>
    <div style="background-color: white; bottom: 0; width: 100%; position: fixed">
      <div style="padding-left: 10vw; padding-right: 10vw; padding-top: 1vh; padding-bottom: 0.5vh">
        <button class="mybutton">
          <img src="../../assets/aid.png" alt="no resource" width="170" height="40"/>
        </button>
      </div>
      <div style="padding-left: 10vw; padding-right: 10vw; padding-top: 1vh; padding-bottom: 0.5vh">
        <button class="mybutton">
          <img src="../../assets/hospital.png" alt="no resource" width="170" height="40"/>
        </button>
      </div>
      <div style="padding-left: 10vw; padding-right: 10vw; padding-top: 1vh; padding-bottom: 1vh">
        <button class="mybutton">
          <img src="../../assets/control.png" alt="no resource" width="170" height="40"/>
        </button>
      </div>
    </div>
    <yd-button shape="angle" size="small" bgcolor="#C0C0C0" @click.native="backToHome" class="back">
      <img src="../../assets/back.png" alt="no resource" width="24" height="24"/>
    </yd-button> <!-- 返回按钮 #2db7f5 -->
  </div>
</template>

<script>
export default {
  name: 'did',
  data () { // 数据
    return {
      trueName: '卫晓欣', // 姓名
      date: '2020/7' // 日期
    }
  },
  methods: { // 方法
    backToHome () { // 返回地图主页
      this.$router.push('/')
    },
    getDidCode () { // 获取DID码
      let button = document.getElementsByClassName('getdid')[0] // 取得获取did按钮
      button.style.display = 'none' // 按钮消失
    }
  }
}
</script>

<style scoped>
#did{ /*全局*/
  width: 100%; /*占满*/
  height: 100%;
  background-color: #a1de76;
}
.back{ /*返回按钮*/
  position: absolute;
  z-index: 99; /*在最高层*/
  opacity: 0.5; /*设置透明度*/
  top: 1.5rem; /*离手机顶部的距离*/
}
.realcode{ /*数字码*/
  position: fixed; /*绝对定位*/
  top: 25vh;  /*上边距离*/
  left: 50%; /*与下面的平移结合，使图片居中*/
  transform:translate(-100px); /*图片宽度高度均为200px*/
  -webkit-transform: translateX(-100px); /*兼容Safari*/
}
.getdid{ /*获取DID码按钮*/
  position: fixed; /*绝对定位*/
  top: 25vh; /*上边距离*/
  left: 50%; /*与下面的平移结合，使图片居中*/
  transform:translate(-100px); /*图片宽度高度均为200px*/
  -webkit-transform: translateX(-100px); /*兼容Safari*/
  width: 200px; /*与图片重合*/
  height: 200px;
  z-index: 20; /*置于上层*/
  font-weight: bold; /*文字加粗*/
  background-color: white; /*背景颜色*/
}
.getdid:active{ /*按动按钮效果*/
  background-color: lightgrey; /*背景颜色*/
}
label{
  position: fixed; /*绝对定位*/
  top: 19vh; /*上边距离*/
  width: 100%; /*宽度*/
  font-size: 0.4rem; /*字体大小*/
  color: white; /*字体颜色*/
  text-align: center; /*居中*/
}
span{
  position: fixed; /*绝对定位*/
  top: 13vh; /*上边距离*/
  width: 80%; /*宽度*/
  font-size: 0.5rem; /*字体大小*/
  color: white; /*字体颜色*/
  text-align: center; /*居中*/
  margin-left: 10%; /*根据宽度80%计算左外边距*/
}
.mybutton{ /*码下方按钮*/
   width: 80vw; /*宽度*/
   height: 1rem; /*高度*/
   font-size: 0.3rem; /*字体大小*/
   color: grey; /*字体颜色*/
   font-weight: bold; /*加粗*/
   border: 1px solid grey;
}
.mybutton:active{
  opacity: 0.5;
}
</style>
