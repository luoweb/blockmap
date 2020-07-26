// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import 'vue-ydui/dist/ydui.flexible' // 适配手机屏幕，使用rem方案
import YDUI from 'vue-ydui' // 导入vue-ydui库
import 'vue-ydui/dist/ydui.rem.css' // 使用vue-ydui库必须引入，且选用适配为rem的组件

Vue.config.productionTip = false
Vue.use(YDUI) // 全局引用vue-ydui库

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
