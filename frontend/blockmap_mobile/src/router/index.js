import Vue from 'vue'
import Router from 'vue-router'
import home from '@/components/home' // 导入地图主页
import summary from '@/components/summary/summary' // 导入当日风险等级总结页
import login from '@/components/login/login' // 导入登录页

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'home',
      component: home
    },
    {
      path: '/summary',
      name: 'mysummary',
      component: summary
    },
    {
      path: '/login',
      name: 'login',
      component: login
    }
  ]
})
