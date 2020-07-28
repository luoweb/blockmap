<template>
  <div id="login"> <!-- 登录页面 -->
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
      <yd-button size="large" bgcolor="#1E90FF" color="#FFF" @click.native="toLogin">Sign In</yd-button> <!-- 登录按钮 -->
      <yd-button size="large" bgcolor="#1E90FF" color="#FFF">Register</yd-button> <!-- 注册按钮 -->
    </div>
  </div>
</template>

<script>
export default {
  name: 'login',
  data () { // 数据
    return {
      username: '', // 用户名
      password: '' // 密码
    }
  },
  methods: { // 方法
    toLogin () { // 登录函数
      this.$router.push('/')
    }
  //   enterKey (event) { // 监听enter键
  //     const componentName = this.$options.name
  //     if (componentName === 'login') {
  //       const code = event.keyCode
  //       if (code === 13) {
  //         this.toLogin()
  //       }
  //     }
  //   },
  //   enterKeyupDestroyed () { // 销毁监听器
  //     document.removeEventListener('keyup', this.enterKey)
  //   },
  //   enterKeyup () { // 安装监听器
  //     document.addEventListener('keyup', this.enterKey)
  //   }
  // },
  // mounted () {
  //   this.enterKeyup()
  // },
  // destroyed () {
  //   this.enterKeyupDestroyed()
  }
}
</script>

<style scoped>
#login{ /*全局*/
  width: 100%; /*占满*/
  height: 100%;
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
  position: fixed; /*相对父组件位置*/
  top: 20vh; /*上边距离*/
  width: 90%; /*宽度为整屏90%*/
  text-align: center; /*文字居中*/
  text-shadow: 0.1rem 0.1rem 0.1rem #FFF; /*文字效果*/
}
</style>
