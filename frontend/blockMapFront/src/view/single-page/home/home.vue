<template>
  <div>
    <Row :gutter="20">
      <i-col :xs="12" :md="8" :lg="6" v-for="(infor, i) in inforCardData" :key="`infor-${i}`" style="height: 120px;padding-bottom: 10px;">
        <infor-card shadow :color="infor.color" :icon="infor.icon" :icon-size="46" style="background:rgba(0,0,0,.5);">
          <count-to :end="infor.count" count-class="count-style"/>
          <p>{{$t(infor.title)}}</p>
        </infor-card>
      </i-col>
    </Row>
    <Row :gutter="20" style="margin-top: 10px;">
      <i-col :md="24" :lg="16" style="margin-bottom: 20px;">
        <Card shadow style="background:rgba(0,0,0,.5);">
          <div style="text-align: center;">
            <span style="color:#516b91;font-weight: bolder;font-size: large">{{$t('barComp')}}</span>
          </div>
          <div id="bar" style="height:300px"></div>
<!--          <chart-bar style="height: 300px;" text="疫情风险图" :value="barData"/>-->
        </Card>
      </i-col>
      <i-col :md="24" :lg="8" style="margin-bottom: 20px;">
        <Card shadow style="background:rgba(0,0,0,.5);">
          <div style="text-align: center;">
            <span style="color:#516b91;font-weight: bolder;font-size: large">{{$t('pieComp')}}</span>
          </div>
          <div id="pie" style="height:300px"></div>
        </Card>
      </i-col>

    </Row>
    <Row :gutter="20" style="margin-top: 10px;">
      <i-col :md="24" :lg="16" style="margin-bottom: 20px;">
        <Card shadow style="background:rgba(0,0,0,.5);">
          <div style="text-align: center;">
            <span style="color:#516b91;font-weight: bolder;font-size: large">{{$t('rank')}}</span>
          </div>
          <div id="rank" style="height:300px"></div>
        </Card>
      </i-col>
      <i-col :md="24" :lg="8" style="margin-bottom: 20px;">
        <Card shadow style="background:rgba(0,0,0,.5);">
          <div style="text-align: center;margin-bottom:20px">
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
            {title: 'chinaC', icon: 'md-person-add', count: 803, color: '#2d8cf0'},
            {title: 'chinaS', icon: 'md-person-add', count: 232, color: '#19be6b'},
            {title: 'worldC', icon: 'md-globe', count: 142, color: '#ed3f14'},
            {title: 'worldS', icon: 'md-globe', count: 657, color: '#ff9900'}
        ],
        pieOption: {
            legend: {
                orient: 'vertical',
                left: 10,
                textStyle: {
                    color: '#516b91'
                },
                data: ['Accommodation', 'Financial', 'Estate', 'Resident', 'Leasing']
            },
            series: [
                {
                    name: '访问来源',
                    type: 'pie',
                    radius: ['50%', '70%'],
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
                        {value: 335, name: 'Financial'},
                        {value: 310, name: 'Estate'},
                        {value: 234, name: 'Resident'},
                        {value: 135, name: 'Leasing'},
                        {value: 1548, name: 'Accommodation'}
                    ]
                }
            ]
        },
        barOption: {
                  xAxis: {
                      type: 'category',
                      data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
                      axisLine: {
                          lineStyle: {
                              color: '#516b91'
                          }
                      }
                  },
                  yAxis: {
                      type: 'value',
                      splitLine:{show:false},
                      axisLine: {
                          lineStyle: {
                              color: '#516b91'
                          }
                      }
                  },
                  series: [{
                      data: [820, 932, 901, 934, 1290, 1330, 1320],
                      lineStyle:{normal:{color:'#ffffff'}},
                      type: 'line',
                      smooth: true
                  }],

          },
        rankOption: {
            dataset: {
                source: [
                    ['work resumption index', 'outlets'],
                    [0.785, 'outlets047'],
                    [0.824, 'outlets043'],
                    [0.839, 'outlets059'],
                    [0.846, 'outlets123'],
                    [0.889, 'outlets008'],
                    [0.908, 'outlets019'],
                    [0.920, 'outlets061'],
                    [0.943, 'outlets154'],
                    [0.967, 'outlets090'],
                    [0.987, 'outlets106']
                ]
            },
            grid: {containLabel: true},
            xAxis: {
                name: 'index',
                axisLine: {
                    lineStyle: {
                        color: '#516b91'
                    }
                }
            },
            yAxis: {
                name:'outlets',
                type: 'category',
                axisLine: {
                    lineStyle: {
                        color: '#516b91'
                    }
                }
            },
            series: [
                {
                    type: 'bar',
                    encode: {
                        x: 'work resumption index',
                        y: 'outlets'
                    },
                    itemStyle: {
                        normal: {
                            color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                                offset: 0,
                                color: '#2378f7'
                            }, {
                                offset: 1,
                                color: '#83bff6'
                            }])

                        }
                    }
                }
            ]
        },
        columns12: [
            {
                title: 'Area',
                key: 'area'
            },
            {
                title: 'Infections',
                key: 'infections'
            },
            {
                title: 'Suspected Infections',
                key: 'suspected'
            }
        ],
        data6: [
            {
                area: 'Area1',
                infections: 0,
                suspected: 0
            },
            {
                area: 'Area2',
                infections: 3,
                suspected: 6
            },
            {
                area: 'Area3',
                infections: 0,
                suspected: 0
            },
            {
                area: 'Area5',
                infections: 0,
                suspected: 0
            },
            {
                area: 'Area6',
                infections: 1,
                suspected: 2
            },
            {
                area: 'Area8',
                infections: 0,
                suspected: 0
            },
        ]
    }
  },
    methods: {
      init () {
          var pieData = echarts.init(document.getElementById('pie'));
          pieData.setOption(this.pieOption);
          var barData = echarts.init(document.getElementById('bar'));
          barData.setOption(this.barOption);
          var rankData = echarts.init(document.getElementById('rank'));
          rankData.setOption(this.rankOption);

          window.onresize = function () {
              pieData.resize();
              barData.resize();
              rankData.resize();
          }
      },
    },
    computed: {
      chinaC: function() {
          return this.$t("chinaC")
      }
    },

  mounted () {
      this.init()
  }
}
</script>

<style lang="less">
.count-style{
  font-size: 50px;
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
