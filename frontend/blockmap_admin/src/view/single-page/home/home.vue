<template>
  <div>
    <Row :gutter="20">
      <i-col :xs="12" :md="8" :lg="6" v-for="(infor, i) in inforCardData" :key="`infor-${i}`" style="height: 130px;padding-bottom: 10px;">
        <infor-card shadow :color="infor.color" :icon="infor.icon" :icon-size="46" style="background:rgba(0,0,0,.5);">
          <count-to :end="infor.count" count-class="count-style"/>
          <p>{{$t(infor.title)}}</p>
        </infor-card>
      </i-col>
    </Row>
    <Row :gutter="20" style="margin-top: 10px;">
      <i-col :md="24" :lg="16" style="margin-bottom: 10px;">
        <Card shadow style="background:rgba(0,0,0,.5);">
          <div style="text-align: center;">
            <span style="color:#516b91;font-weight: bolder;font-size: large">{{$t('barComp')}}</span>
          </div>
          <div id="bar" style="height:250px"></div>
        </Card>
      </i-col>
      <i-col :md="24" :lg="8" style="margin-bottom: 10px;">
        <Card shadow style="background:rgba(0,0,0,.5);">
          <div style="text-align: center;margin-bottom:10px;">
            <span style="color:#516b91;font-weight: bolder;font-size: large">{{$t('rank')}}</span>
          </div>
          <Table :columns="columns11" :data="data5" style="background:rgba(0,0,0,.5);">
            <template slot-scope="{ row }" slot="area">
            </template>
          </Table>

        </Card>
      </i-col>
    </Row>
    <Row :gutter="20" style="margin-top: 10px;">
      <i-col :md="24" :lg="16" style="margin-bottom: 10px;">
        <Card shadow style="background:rgba(0,0,0,.5);">
          <div style="text-align: center;">
            <span style="color:#516b91;font-weight: bolder;font-size: large">{{$t('people')}}</span>
          </div>
          <div id="people" style="height:300px"></div>
        </Card>
      </i-col>
      <i-col :md="24" :lg="8" style="margin-bottom: 10px;">
        <Card shadow style="background:rgba(0,0,0,.5);">
          <div style="text-align: center;margin-bottom:20px">
            <span style="color:#516b91;font-weight: bolder;font-size: large">{{$t('pieComp')}}</span>
          </div>
          <div id="pie" style="height:280px"></div>
        </Card>
      </i-col>
    </Row>

    <Row :gutter="20" style="margin-top: 10px;">
      <i-col :md="24" :lg="16" style="margin-bottom: 20px;">
        <Card shadow style="background:rgba(0,0,0,.5);">
          <div style="text-align: center;">
            <span style="color:#516b91;font-weight: bolder;font-size: large">{{$t('pipComp')}}</span>
          </div>
          <div id="pip" style="height:250px"></div>
        </Card>
      </i-col>
      <i-col :md="24" :lg="8" style="margin-bottom: 20px;">
        <Card shadow style="background:rgba(0,0,0,.5);">
          <div style="text-align: center;margin-bottom: 10px">
            <span style="color:#516b91;font-weight: bolder;font-size: large">{{$t('areaList')}}</span>
          </div>
          <Table :columns="columns12" :data="data6" style="background:rgba(0,0,0,.5);">
            <template slot-scope="{ row }" slot="area">
            </template>
          </Table>
        </Card>
      </i-col>
    </Row>
  </div>
</template>

<script>
import InforCard from '_c/info-card'
import CountTo from '_c/count-to'
import { ChartPie, ChartBar } from '_c/charts'
import echarts from 'echarts'
// import Example from './example.vue'
export default {
  name: 'home',
  components: {
    InforCard,
    CountTo,
    ChartPie,
    ChartBar,
    // Example
  },
  data () {
    return {
        inforCardData: [
            {title: 'globalNC', icon: 'md-person-add', count: 210058, color: '#2d8cf0'},
            {title: 'globalC', icon: 'ios-people', count: 116352695, color: '#ff9900'},
            {title: 'death', icon: 'md-person', count: 647944, color: '#ed3f14'},
            {title: 'secure', icon: 'md-medkit', count: 9972512, color: '#19be6b'}
        ],
        //复工行业比较饼图
        pieOption: {
            legend: {
                orient: 'vertical',
                left: 10,
                textStyle: {
                    color: '#516b91'
                },
                data: ['accommodation', 'finance', 'estate', 'retail', 'tourism','public service','IT','chemical']
            },
            series: [
                {
                    name: '访问来源',
                    type: 'pie',
                    radius: ['60%', '80%'],
                    avoidLabelOverlap: false,
                    label: {
                        show: false,
                        position: 'center'
                    },
                    emphasis: {
                        label: {
                            show: true,
                            fontSize: '30',
                            fontWeight: 'bold'
                        }
                    },
                    labelLine: {
                        show: false
                    },
                    data: [
                        {value: 335, name: 'finance'},
                        {value: 310, name: 'estate'},
                        {value: 1234, name: 'public service'},
                        {value: 135, name: 'retail'},
                        {value: 548, name: 'accommodation'},
                        {value: 133, name: 'tourism'},
                        {value: 1148, name: 'IT'},
                        {value: 987, name: 'chemical'}
                    ]
                }
            ]
        },
        //复工情况随时间变化图
        barOption: {
            xAxis: {
                type: 'category',
                boundaryGap: false,
                axisLine: {
                    lineStyle: {
                        color: '#516b91'
                    }
                },
                splitLine:{
                    show:false
                }
            },
            yAxis: {
                type: 'value',
                boundaryGap: [0, '30%'],
                axisLine: {
                    lineStyle: {
                        color: '#516b91'
                    }
                },
                splitLine:{
                    show:false
                }
            },
            visualMap: {
                type: 'piecewise',
                show: false,
                dimension: 0,
                seriesIndex: 0,
                pieces: [{
                    gt: 0,
                    lt: 8,
                    color: 'rgba(30,144,255,0.5)'
                }]
            },
            series: [
                {
                    type: 'line',
                    smooth: 0.6,
                    symbol: 'none',
                    lineStyle: {
                        color: 'blue',
                        width: 0
                    },
                    areaStyle: {},
                    data: [
                        ['2020-07-20', 200],
                        ['2019-07-21', 400],
                        ['2019-07-22', 650],
                        ['2019-07-23', 500],
                        ['2019-07-24', 250],
                        ['2019-07-25', 300],
                        ['2019-07-26', 450]
                    ]
                }
            ]
          },

        //分店客流量柱状图
        peopleOption:{
            color:'rgba(186,85,211,0.5)',
            tooltip: {
                trigger: 'axis',
                axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                    type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                },
                formatter: function (params) {
                    var tar = params[1];
                    return tar.name + '<br/>' + tar.seriesName + ' : ' + tar.value;
                }
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            xAxis: {
                type: 'category',
                axisLine: {
                    lineStyle: {
                        color: '#516b91'
                    }
                },
                splitLine:{
                    show:false
                },
                data: ['Total', 'Area A', 'Area B', 'Area C', 'Area D', 'Area E','Area F','Area G']
            },
            yAxis: {
                type: 'value',
                axisLine: {
                    lineStyle: {
                        color: '#516b91'
                    }
                },
                splitLine:{
                    show:false
                }
            },
            series: [
                {
                    name: '辅助',
                    type: 'bar',
                    stack: '总量',
                    itemStyle: {
                        barBorderColor: 'rgba(0,0,0,0)',
                        color: 'rgba(0,0,0,0)'
                    },
                    emphasis: {
                        itemStyle: {
                            barBorderColor: 'rgba(0,0,0,0)',
                            color: 'rgba(0,0,0,0)'
                        }
                    },
                    data: [0, 1700, 1400, 1200, 500, 200, 150, 0]
                },
                {
                    name: 'Number of Passengers',
                    type: 'bar',
                    stack: '总量',
                    label: {
                        show: true,
                        position: 'inside'
                    },
                    data: [{
                     value:2900,
                     itemStyle:{
                         color:'#8A2BE2'
                     }
                    }, 1200, 300, 200, 700, 300, 50, 150]
                }
            ]

        },
        //复工企业人数气泡图
        pipOption:{
            // legend: {
            //     right: 10,
            //     data: ['1990']
            // },
            xAxis: {
                show:false,
                splitLine: {
                    show:false
                }
            },
            yAxis: {
                show:false,
                splitLine: {
                    show:false
                },
                scale: true
            },
            series: [{
                data: [
                    [52,4.3,104,'Company A'],[16,3,119,'Company B'],[70,8.5,48,'Company C'],
                    [2,1.2,60,'Company D'],[57,8.6,56,'Company E'],[62,2.5,76,'Company F'],
                    [7,9,53,'Company G'],[35,6.9,179,'Company H'],[21,9.2,82,'Company I']
                ],
                type: 'scatter',
                symbolSize: function (data) {
                    return data[2];
                },
                itemStyle: {
                    normal:{
                        shadowBlur: 10,
                        shadowColor: 'rgba(100,149,237, 0.5)',
                        color:'rgba(30,144,255,0.5)',
                        shadowOffsetY: 5,
                        label: {
                            show: true,
                            formatter: function (param) {
                                return param.data[3];
                            },
                            position: 'inside',
                            textStyle: {
                                fontSize: 15
                            }
                        }
                    }
                }
            }]

        },
        //区域感染情况表格
        columns12: [
            {
                title: 'Area',
                key: 'area',
                align:"center"
            },
            {
                title: 'Infections',
                key: 'infections',
                align:"center"
            },
            {
                title: 'Suspected Infections',
                key: 'suspected',
                align:"center"
            }
        ],
        data6:[],

        columns11: [
            {
                title: 'Rank',
                key: 'rank',
                width: '100px',
                render: (h, params) => {
                    return h('div', [
                        h('Icon',{
                        props: {
                            type: 'md-podium'
                        },
                        style: {
                            color:'#32CD32',
                            marginRight: '5px'
                        }
                    }),
                        h('span',{
                            style: {
                                fontWeight: 'bolder'
                            }
                        },params.row.rank)
                    ]);
                }
            },
            {
                title: 'Branch',
                key: 'branch',
                align:"center"
            },
            {
                title: 'Recommend Index',
                key: 'index',
                width:'150px',
                align:"center"
            }

        ],
        data5: [
            {
                branch: '11th Avenue',
                rank: 1,
                index: 9.79
            },
            {
                branch: 'Cranberry street',
                rank: 2,
                index: 9.54
            },
            {
                branch: 'Madison Avenue',
                rank: 3,
                index: 9.48
            },
            {
                branch: 'Lexington Avenue',
                rank: 4,
                index: 9.27
            },
            {
                branch: 'Minetta Street',
                rank: 5,
                index: 9.21
            }
        ]
    }
  },
    methods: {
      mockData6 (){
          let data = [];
          let areaList = ['Area A','Area B','Area C','Area D','Area E'];
          function random(min,max){
              return Math.floor(Math.random() * (max - min)) + min;
          }
          for(let i = 0; i<5;i++){
              data.push({
                  area:areaList[i],
                  infections:random(0,200),
                  suspected: random(0,300)
              })
          }
          return data;
      },
      init () {
          var barData = echarts.init(document.getElementById('bar'));
          barData.setOption(this.barOption);

          var peopleData = echarts.init(document.getElementById('people'));
          peopleData.setOption(this.peopleOption);
          var pieData = echarts.init(document.getElementById('pie'));
          pieData.setOption(this.pieOption);

          var pipData = echarts.init(document.getElementById('pip'));
          pipData.setOption(this.pipOption);

          window.onresize = function () {
              pieData.resize();
              barData.resize();
              peopleData.resize();
              pipData.resize();
          }
      },
    },

  mounted () {
      this.data6 = this.mockData6();
      setTimeout(()=>{this.init()});

  }
}
</script>

<style lang="less">
  /*.content-outer>>> .count-to-count-text .count-style{*/
  /*  */
  /*}*/
.count-style{
  font-size: 32px;
}
/*底色*/
.ivu-table {
  background-color: rgba(0,0,0,0);
  color: #8493ce;
}
.ivu-table:before,.ivu-table:after{
  background-color: rgba(0,0,0,0);
}
.ivu-table td, .ivu-table th{
  height: 40px;
  background-color: rgba(0,0,0,0);
  border-bottom: 1px solid #283150;
}
/*头部th*/
.ivu-table-header th{
  color:#b0bbe8;
  font-weight: bold;
  background-color: #26292E;
  border: none;
}
.ivu-table-row-hover td {
  background-color: #26292E !important;
}
.ivu-table-wrapper {
  border: 0;
}
</style>

<style scoped>
  .ivu-tag .ivu-tag-dot .ivu-tag-checked {
    background-color: rgba(0,0,0,0);
  }
</style>
