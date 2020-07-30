<template> <!-- 当天风险等级总结模块 -->
    <div id="summary">
      <div class="circle">
        <i-circle :percent="percent" size="150" class="pcircle">
          <span class="percent">{{percent}}%</span>
          <h5>Risk Index</h5>
        </i-circle>
        <div class="right_circle">
          <Icon type="md-pin" />
          <label class="location">{{location}}</label>
          <h4 class="depart">{{depart}}</h4>
          <h4 class="arrive">{{arrive}}</h4>
          <label class="risk">{{risk}}</label>
        </div>
      </div>
      <div class="overview">
        <p class="title">Today path overview</p> <!-- title -->
      </div>
      <div class="advice">
        <p class="title">Factors and Suggestions</p> <!-- title -->
        <Input type="textarea" readonly autosize class="adviceInput" v-model="suggestion"/>
      </div>
    </div>
</template>

<script>
export default {
  name: 'summary',
  data () { // 属性（数据）
    return {
      percent: 2, // 控制环形环进度
      timer: null, // 定时器
      num: null, // 用于环形动画加载，加载到某个阙值
      suggestion: 'Epidemic prevention and control: it is recommended to borrow masks and wash hands ' +
        'frequently \n①: Population density;\n②: distance to the case hospital', // 建议栏内容
      location: 'huimei Garden, Haizhu District', // 位置定位内容
      risk: 'The risk now is low', // 风险评估内容
      depart: 'Depart at: 00:23', // 出发时间
      arrive: 'Arrive at: 05:26' // 到达时间
    }
  },
  mounted () { // 初步初始化后
    this.percentMovie(20)
  },
  methods: {
    percentMovie (num) { // 环形百分比动画实现函数
      this.num = num
      this.timer = setInterval(this.percentAdd, 50) // 50ms执行一次
    },
    percentAdd () {
      if (this.percent < this.num) { // 百分比增加函数
        this.percent += 1
      } else {
        clearInterval(this.timer)
      }
    }
  }
}
</script>

<style scoped>
#summary{ /*整块*/
  position: absolute; /*嵌入使用*/
  width: 100%; /*父组件宽度100%*/
  height: 100%; /*父组件高度100%*/
}
.percent{ /*环的百分比显示*/
  font-size: 2em; /*设置字体大小*/
}
.circle{ /*百分比部分*/
  position: absolute; /*相对全局summary位置*/
  top: 50px; /*距离顶部50px*/
  width: 700px; /*宽度700px*/
  height: 150px; /*高度150px*/
  left: 25%; /*距离全局summary左边25%宽度*/
}
.pcircle{ /*百分比环部分*/
  position: absolute; /*相对整个百分比部分的位置*/
  left: 50px; /*距离左边50px*/
}
.right_circle{ /*百分比右边的文字部分*/
  position: absolute; /*相对整个百分比部分的位置*/
  right: 200px; /*距离右边200px*/
  top: 0; /*距离顶部0*/
}
.depart{ /*depart文字设置*/
  position: absolute; /*相对百分比右边部分的位置*/
  color: #5cadff;
  top: 40px; /*距离顶部40px*/
  left: 55px; /*距离左边55px*/
}
.arrive{ /*arrive文字设置*/
  position: absolute; /*相对百分比右边部分的位置*/
  color: #5cadff;
  top: 80px; /*距离顶部80px*/
  left: 55px; /*距离左边55px*/
}
.risk{ /*risk部分文字*/
  position: absolute; /*相对百分比右边部分的位置*/
  top: 120px; /*距离顶部120px*/
  left: 50px; /*距离左边50px*/
}
.overview{ /*overview部分*/
  position: absolute; /*相对全局summary位置*/
  top: 250px; /*距离顶部250px*/
  width: 700px; /*宽度700px*/
  height: 150px; /*高度150px*/
  left: 25%; /*距离全局summary左边25%宽度*/
}
.advice{ /*建议部分*/
  position: absolute; /*相对全局summary位置*/
  top: 450px; /*距离顶部450x*/
  width: 700px; /*宽度700px*/
  height: 150px; /*高度150px*/
  left: 25%; /*距离全局summary左边25%宽度*/
}
.title{ /*所有标题*/
  background-color: #eeeeee; /*设置背景颜色*/
}
.location{ /*定位内容*/
  font-weight: bold; /*字体加粗*/
}
</style>
