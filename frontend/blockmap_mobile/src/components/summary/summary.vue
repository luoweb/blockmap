<template>
  <div id="mysummary"> <!-- 风险等级总结页面 -->
    <yd-navbar title="Today's summary"> <!-- 顶部导航栏 -->
      <router-link to="/" slot="left">
        <yd-navbar-back-icon></yd-navbar-back-icon>
      </router-link>
    </yd-navbar>
    <div class="summary_content">
      <yd-flexbox direction="vertical"> <!-- 上半部分页面内容 -->
        <div> <!-- 定位显示 -->
          <yd-icon name="location"></yd-icon>
          <label class="des_location">{{location}}</label>
        </div>
        <yd-flexbox> <!-- 进度圈区 -->
          <div class="progress"> <!-- 进度圈 -->
            <yd-progressbar :progress="percent" trail-width="6" trail-color="#008000">
              <span class="percent">{{Math.floor(percent*100)}}%</span>
              <span class="des_progress">Risk Index</span>
            </yd-progressbar>
          </div>
          <div>
            <p>{{depart}}</p>
            <p>{{arrive}}</p>
            <yd-flexbox>
              <p>Detect:</p>
              <yd-switch size="small"></yd-switch>
            </yd-flexbox>
          </div>
        </yd-flexbox>
        <label class="des_assess">{{risk}}</label>
        <yd-flexbox> <!-- 三标签 -->
          <yd-flexbox direction="vertical" class="tag_three">
            <yd-icon name="time"></yd-icon>
            <h5>High Risk T</h5>
            <h5>{{highRiskTime}}</h5>
          </yd-flexbox>
          <yd-flexbox direction="vertical" class="tag_three">
            <yd-icon name="time"></yd-icon>
            <h5>Low Risk T</h5>
            <h5>{{lowRiskTime}}</h5>
          </yd-flexbox>
          <yd-flexbox direction="vertical" class="tag_three">
            <yd-icon name="time"></yd-icon>
            <h5>Medium Risk T</h5>
            <h5>{{mediumRiskTime}}</h5>
          </yd-flexbox>
        </yd-flexbox>
      </yd-flexbox>
      <yd-cell-group title="Today path overview"> <!-- 路径概要栏 -->
        <yd-cell-item>
          <div slot="right">
            <yd-flexbox>
              <div class="tip_three">
                <button style="background-color: #008000; width: 0.3rem; height: 0.2rem; border: none"></button>
                <label>High Risk</label>
              </div>
              <div class="tip_three">
                <button style="background-color: #32CD32; width: 0.3rem; height: 0.2rem; border: none"></button>
                <label>Medium Risk</label>
              </div>
              <div class="tip_three">
                <button style="background-color: #98FB98; width: 0.3rem; height: 0.2rem; border: none"></button>
                <label>Low Risk</label>
              </div>
            </yd-flexbox>
          </div>
        </yd-cell-item>
        <yd-cell-item>
          <div slot="right">
            <button style="background-color: #008000; width: 0.5rem; height: 1.4rem; border: none;"></button>
            <button style="background-color: #32CD32; width: 0.5rem; height: 1.4rem; border: none; float: left;"></button>
            <button style="background-color: #008000; width: 0.5rem; height: 1.4rem; border: none; float: left;"></button>
            <button style="background-color: #32CD32; width: 0.5rem; height: 1.4rem; border: none; float: left;"></button>
            <button style="background-color: #008000; width: 0.5rem; height: 1.4rem; border: none; float: left;"></button>
            <button style="background-color: #008000; width: 0.5rem; height: 1.4rem; border: none; float: left;"></button>
            <button style="background-color: #98FB98; width: 0.5rem; height: 1.4rem; border: none; float: left;"></button>
            <button style="background-color: #32CD32; width: 0.5rem; height: 1.4rem; border: none; float: left;"></button>
            <button style="background-color: #32CD32; width: 0.5rem; height: 1.4rem; border: none; float: left;"></button>
            <button style="background-color: #008000; width: 0.5rem; height: 1.4rem; border: none; float: left;"></button>
            <button style="background-color: #98FB98; width: 0.5rem; height: 1.4rem; border: none; float: left;"></button>
            <button style="background-color: #32CD32; width: 0.5rem; height: 1.4rem; border: none; float: left;"></button>
          </div>
        </yd-cell-item>
      </yd-cell-group>
      <yd-cell-group title="Factors and Suggestions"> <!-- 建议栏 -->
        <yd-cell-item>
          <yd-textarea slot="right" show-counter v-model="suggestion" disabled></yd-textarea>
        </yd-cell-item>
      </yd-cell-group>
    </div>
  </div>
</template>

<script>
export default {
  name: 'mysummary',
  data () {
    return {
      suggestion: 'Epidemic prevention and control: it is recommended to borrow masks and wash hands ' +
        'frequently \n①: Population density;\n②: distance to the case hospital', // 建议栏内容
      percent: 0, // 控制环形环进度
      timer: null, // 定时器
      num: null, // 用于环形动画加载，加载到某个阙值
      location: 'huimei Garden, Haizhu District', // 位置定位内容
      depart: 'Depart at: 00:23', // 出发时间
      arrive: 'Arrive at: 05:26', // 到达时间
      risk: 'The risk now is low', // 风险评估内容
      highRiskTime: '03:21', // 呆在高风险地区时间
      lowRiskTime: '03:14', // 呆在高风险地区时间
      mediumRiskTime: '01:00' // 呆在高风险地区时间
    }
  },
  mounted () { // 初步初始化后
    this.percentMovie(0.2) // 进度条动画
  },
  methods: { // 方法
    percentMovie (num) { // 环形百分比动画实现函数
      this.num = num
      this.timer = setInterval(this.percentAdd, 50) // 50ms执行一次
    },
    percentAdd () {
      if (this.percent < this.num) { // 百分比增加函数
        this.percent += 0.01
      } else {
        clearInterval(this.timer)
      }
    }
  }
}
</script>

<style scoped>
#mysummary{ /*全局*/
  width: 100%; /*占满*/
  height: 100%;
}
.summary_content{ /*除导航栏部分*/
  padding-top: 0.2rem; /*上内边距*/
  padding-right: 0.5rem; /*右内边距*/
  padding-left: 0.5rem; /*左内边距*/
}
.progress{ /*进度圈*/
  width: 2.2rem; /*宽度*/
  height: 2.2rem; /*高度*/
}
span{
  color: #008000; /*字体颜色*/
}
.percent{ /*进度圈百分比*/
  font-size: 0.6rem; /*设置字体大小*/
}
.des_progress{ /*risk index的描述*/
  font-size: 0.2rem; /*设置字体大小*/
}
.des_location{ /*位置文字描述*/
  font-weight: bold; /*字体加粗*/
  font-size: 0.3rem; /*设置字体大小*/
}
p{
  color: #32CD32; /*字体颜色*/
  margin-top: 0.3rem; /*上外边距*/
  margin-left: 0.8rem; /*左外边距*/
}
.des_assess{ /*风险评估描述*/
  margin-top: 0.35rem; /*上外边距*/
  margin-bottom: 0.35rem; /*下外边距*/
}
.tag_three{ /*第一部分的三标签块*/
  margin-right: 0.2rem; /*右外边距*/
  margin-left: 0.2rem; /*左外边距*/
  margin-bottom: 0.2rem; /*下外边距*/
}
h5{
  font-weight: lighter; /*字体加浅*/
}
.tip_three{ /*第二部分图的标签*/
  margin-right: 0.2rem; /*左外边距*/
  margin-left: 0.2rem; /*右外边距*/
}
</style>
