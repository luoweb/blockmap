<template>
  <div id="information"> <!-- 个人信息页面 -->
    <yd-navbar title="Your personal information"> <!-- 顶部导航栏 -->
    </yd-navbar>
    <yd-cell-group title="Account Information"> <!-- 账号信息 -->
      <yd-cell-item>
        <span slot="left">portrait</span> <!-- 头像 -->
        <img src="../../assets/portrait_xiaoxin.jpg" alt="no resource" width="64" height="64" slot="right"/>
      </yd-cell-item>
      <yd-cell-item>
        <span slot="left">username</span> <!-- 用户名 -->
        <span slot="right">{{userName}}</span>
      </yd-cell-item>
      <yd-cell-item>
        <span slot="left">e-mail</span> <!-- 邮箱 -->
        <span slot="right">{{email}}</span>
      </yd-cell-item>
    </yd-cell-group>
    <yd-cell-group title="Other Information"> <!-- 其他信息 -->
      <yd-cell-item>
        <span slot="left">real name</span> <!-- 姓名 -->
        <span slot="right">{{trueName}}</span>
      </yd-cell-item>
      <yd-cell-item>
        <span slot="left">sex</span> <!-- 性别 -->
        <span slot="right">{{sex}}</span>
      </yd-cell-item>
      <yd-cell-item>
        <span slot="left">company belongs to</span> <!-- 所属公司 -->
        <span slot="right">{{company}}</span>
      </yd-cell-item>
    </yd-cell-group>
    <yd-button size="large" type="danger">modify personal information</yd-button>
    <yd-button shape="angle" size="small" bgcolor="#C0C0C0" @click.native="backToHome" class="back">
      <img src="../../assets/back.png" alt="no resource" width="24" height="24"/>
    </yd-button> <!-- 返回按钮 #2db7f5 -->
  </div>
</template>

<script>
export default {
  name: 'information',
  data () { // 数据
    return {
      userName: 'xiaoxin', // 用户名
      trueName: '卫晓欣', // 姓名
      sex: '女', // 性别
      company: 'ICBC', // 上班所在公司
      email: '0123456789@qq.com' // 邮箱
    }
  },
  methods: {
    backToHome () { // 返回地图主页
      this.$router.push('/')
    }
  }
}
</script>

<style scoped>
/*
#information{
  width: 100%;
  height: 100%;
}
*/
.back{ /*返回按钮*/
  position: absolute;
  z-index: 99; /*在最高层*/
  opacity: 0.5; /*设置透明度*/
  top: 1.5rem; /*离手机顶部的距离*/
}
</style>
