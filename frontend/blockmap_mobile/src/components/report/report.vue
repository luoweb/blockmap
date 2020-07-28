<template>
  <div id="report"> <!-- 信息上报页面 -->
    <yd-navbar title="Epidemic report"> <!-- 顶部导航栏 -->
    </yd-navbar>
    <yd-cell-group title="Basic Information"> <!-- 基本信息 -->
      <yd-cell-item>
        <span slot="left">real name：</span> <!-- 姓名： -->
        <yd-input slot="right" required v-model="yourName" v-bind:readonly="isname" :show-clear-icon="false"></yd-input>
      </yd-cell-item>
      <yd-cell-item>
        <span slot="left">telephone：</span> <!-- 手机号码： -->
        <yd-input slot="right" v-model="phoneNumber" ref="phoneNumber" required regex="mobile"></yd-input>
      </yd-cell-item>
      <yd-cell-item>
        <span slot="left">e-mail：</span> <!-- 邮箱： -->
        <yd-input slot="right" v-model="mailBox" ref="mailBox" required regex="email" v-bind:readonly="isemail" :show-clear-icon="false"></yd-input>
      </yd-cell-item>
      <yd-cell-item>
        <span slot="left">card number：</span> <!-- 身份证号： -->
        <yd-input slot="right" required v-model="cardNumber"></yd-input>
      </yd-cell-item>
      <yd-cell-item arrow>
        <span slot="left">sex：</span> <!-- 性别： -->
        <select slot="right" v-model="sex">
          <option value="male">male</option> <!-- 男 -->
          <option value="female">female</option> <!-- 女 -->
        </select>
      </yd-cell-item>
      <yd-cell-item>
        <span slot="left">age：</span> <!-- 年龄： 出生日期： birth date：-->
        <!--<yd-datetime v-model="birthDate" type="date" slot="right" start-date="1900-01-01"></yd-datetime>-->
        <yd-input slot="right" required v-model="age"></yd-input>
      </yd-cell-item>
    </yd-cell-group>
    <yd-cell-group title="Resident Address"> <!-- 居住地址 -->
      <yd-cell-item>
        <span slot="left">country：</span> <!-- 国家： -->
        <yd-input slot="right" required v-model="country"></yd-input>
      </yd-cell-item>
      <yd-cell-item>
        <span slot="left">resident address：</span> <!-- 详细地址： -->
        <yd-input slot="right" required v-model="address"></yd-input>
      </yd-cell-item>
    </yd-cell-group>
    <yd-cell-group title="Body Condition"> <!-- 健康状况 -->
      <yd-cell-item type="radio">
        <span slot="left">healthy</span> <!-- 健康 -->
        <input slot="right" type="radio" value="health" v-model="bodyHealth"/>
      </yd-cell-item>
      <yd-cell-item type="radio">
        <span slot="left">sick</span> <!-- 确诊 -->
        <input slot="right" type="radio" value="sick" v-model="bodyHealth"/>
      </yd-cell-item>
      <yd-cell-item type="radio">
        <span slot="left">be isolated</span> <!-- 隔离 -->
        <input slot="right" type="radio" value="isolate" v-model="bodyHealth"/>
      </yd-cell-item>
    </yd-cell-group>
    <div style="padding-right: 0.3rem; padding-left: 0.3rem">
      <yd-button size="large" bgcolor="#1E90FF" color="#FFF" @click.native="submitReport">submit</yd-button> <!-- 提交 -->
    </div>
    <yd-button shape="angle" size="small" bgcolor="#C0C0C0" @click.native="backToHome" class="back">
      <img src="../../assets/back.png" alt="no resource" width="24" height="24"/>
    </yd-button> <!-- 返回按钮 #2db7f5 -->
  </div>
</template>

<script>
export default {
  name: 'report',
  data () { // 数据
    return {
      yourName: '卫晓欣', // 姓名
      phoneNumber: '', // 手机号（可以添加验证）
      mailBox: '0123456789@qq.com', // 邮箱（可以添加验证）
      cardNumber: '', // 身份证号
      sex: 'female', // 性别
      age: '', // 年龄
      // birthDate: '', // 出生日期
      country: '', // 国家
      address: '', // 详细地址
      bodyHealth: '', // 身体健康状况
      isname: true,
      isemail: true
    }
  },
  methods: { // 方法
    backToHome () { // 返回地图主页
      this.$router.push('/')
    },
    submitReport () { // 信息上报
      this.$dialog.toast({ // 显示上传成功框（上传成功）
        mes: '上传成功',
        timeout: 1000,
        icon: 'success'
      })
    }
  }
}
</script>

<style scoped>
/*
#report{
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
select{ /*选择框*/
  color: black; /*字体颜色*/
}
</style>
