<template>
  <div id="login"> <!-- 登录页面 -->
    <yd-navbar title="Login"> <!-- 顶部导航栏 -->
      <router-link to="/" slot="left">
        <yd-navbar-back-icon></yd-navbar-back-icon>
      </router-link>
    </yd-navbar>
    <div class="login_content"> <!-- 剩余的登录页面内容 -->
      <div class="logo">
        <label>blockMap</label>
      </div>
      <yd-cell-group> <!-- 用户名密码框 -->
        <yd-cell-item>
          <yd-icon name="ucenter-outline" slot="icon"></yd-icon>
          <span slot="left" class="input">username:</span>
          <yd-input slot="right" v-model="username"></yd-input>
        </yd-cell-item>
        <yd-cell-item>
          <yd-icon name="shield-outline" slot="icon"></yd-icon>
          <span slot="left" class="input">password:</span>
          <yd-input slot="right" type="password" v-model="password"></yd-input>
        </yd-cell-item>
      </yd-cell-group>
      <yd-button size="large" bgcolor="#1E90FF" color="#FFF">Sign In</yd-button> <!-- 登录按钮 -->
      <yd-button size="large" bgcolor="#1E90FF" color="#FFF">Register</yd-button> <!-- 注册按钮 -->
    </div>
  </div>
</template>

<script>
export default {
  name: 'login',
  data () {
    return {
      username: '',
      password: ''
    }
  }
}
</script>

<style scoped>
#login{ /*全局*/
  width: 100vw; /*占满*/
  height: 100vh;
  background-color: #0c0c23; /*背景颜色*/;
}
.login_content{ /*登陆页除导航栏外内容*/
  padding-left: 0.4rem; /*左内边距*/
  padding-right: 0.4rem; /*右内边距*/
  padding-top: 30vh;/*4rem;上内边距*/
}
.input{ /*密码和用户框前提示*/
  text-align: center; /*居中*/
  width: 1.8rem; /*宽度*/
}
label{ /*logo*/
  color: white; /*字体颜色*/
  font-size: 0.7rem; /*字体大小*/
  position: absolute; /*相对父组件位置*/
  top: 25vh; /*上边距离*/
  left: 30vw; /*左边距离*/
  text-shadow: 0.1rem 0.1rem 0.1rem #FFF; /*文字效果*/
}
</style>
