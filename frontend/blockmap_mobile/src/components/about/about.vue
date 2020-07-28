<template>
  <div id="about"> <!-- 关于我们页面 -->
    <yd-navbar title="About us"> <!-- 顶部导航栏 -->
    </yd-navbar>
    <yd-button shape="angle" size="small" bgcolor="#C0C0C0" @click.native="backToHome" class="back">
      <img src="../../assets/back.png" alt="no resource" width="24" height="24"/>
    </yd-button> <!-- 返回按钮 #2db7f5 -->
  </div>
</template>

<script>
export default {
  name: 'about',
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
</style>
