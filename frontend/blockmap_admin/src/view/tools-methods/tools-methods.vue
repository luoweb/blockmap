<template>
  <div>

    <Row :gutter="20" style="margin-top: 10px;">
      <i-col :md="24" :lg="12" style="margin-bottom: 10px;">
        <Card shadow style="background:rgba(0,0,0,.5);">
          <div style="text-align: center;margin-bottom: 10px;">
            <span style="color:#516b91;font-weight: bolder;font-size: large;">{{$t('pieFir')}}</span>
          </div>
          <div id="pie1" style="height:250px"></div>
        </Card>
      </i-col>
      <i-col :md="24" :lg="12" style="margin-bottom: 10px;">
        <Card shadow style="background:rgba(0,0,0,.5);">
          <div style="text-align: center;margin-bottom:10px;">
            <span style="color:#516b91;font-weight: bolder;font-size: large">{{$t('pieSec')}}</span>
          </div>
          <Row>
            <i-col :md="24" :lg="8"  class="selectArea">
              <div style="margin-bottom:10px;width:90%;text-align: center;margin-top:60px">
                <span style="color:#516b91;font-weight: bolder;font-size: medium;">{{$t('chooseBranch')}}</span>
              </div>
              <i-select v-model="model1"  @on-change="refreshPie2">
                <Option  class='option1' v-for="item in cityList" :value="item.value">{{ item.label }}</Option>
              </i-select>
            </i-col>
            <i-col :md="24" :lg="16">
              <div id="pie2" style="height:250px"></div>
            </i-col>
          </Row>

        </Card>
      </i-col>
    </Row>
    <Card shadow style="background:rgba(0,0,0,.5);">
  <i-table :columns="columns6" :data="data5">
    <template slot-scope="{ row }" slot="name">
      <strong>{{ row.name }}</strong>
    </template>
    <template slot-scope="{ row, index }" slot="action">
      <Button type="primary" size="small" style="margin-right: 5px" @click="show(index)">View</Button>
      <Button type="error" size="small" @click="remove(index)">Delete</Button>
    </template>
  </i-table>
    <div>
      <Page :total="9" :page-size="pageSize" :current="pageCurrent" @on-change="changePage" style="margin-top:20px"></Page>
    </div>
    </Card>
    </div>
</template>
<script>
    import echarts from 'echarts'
    export default {
        name:'tools_method',
        data () {
            return {
                // model1:'',
                pieData2:{},
                //
                pieOption1 : {

                    tooltip: {
                        trigger: 'item',
                        formatter: '{b} : {c} ({d}%)'
                    },
                    legend: {
                        orient: 'vertical',
                        left: 'left',
                        data: ['Back to office', 'Remote', 'isolated'],
                        textStyle:{
                            fontSize:'18',
                            color: '#516b91',
                            fontWeight:'bolder'
                        }
                    },
                    series: [
                        {
                            // name: '',
                            type: 'pie',
                            radius: '75%',
                            center: ['45%', '60%'],
                            data: [
                                {value: 234, name: 'isolated'},
                                {value: 135, name: 'Remote'},
                                {value: 1548, name: 'Back to office'}
                            ],
                            label:{
                              textStyle: {
                                  fontWeight: 'bolder',
                                  fontSize:'16'
                              }
                            },
                            emphasis: {
                                itemStyle: {
                                    shadowBlur: 10,
                                    shadowOffsetX: 0,
                                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                                }
                            }
                        }
                    ]
                },
                pieOption2 : {

                    tooltip: {
                        trigger: 'item',
                        formatter: '{b} : {c} ({d}%)'
                    },
                    legend: {
                        orient: 'vertical',
                        left: 'left',
                        data: ['Febrile', 'Normal'],
                        textStyle:{
                            fontSize:'18',
                            color: '#516b91',
                            fontWeight:'bolder'
                        }
                    },
                    series: [
                        {
                            // name: '',
                            type: 'pie',
                            radius: '75%',
                            center: ['45%', '60%'],
                            color:['#ff0000','#61A0A8'],
                            data: [
                                {value: this.random(0,70), name: 'Febrile'},
                                {value: this.random(100,250), name: 'Normal'},
                            ],
                            label:{
                                textStyle: {
                                    fontWeight: 'bolder',
                                    fontSize:'16'
                                }
                            },
                            emphasis: {
                                itemStyle: {
                                    shadowBlur: 10,
                                    shadowOffsetX: 0,
                                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                                }
                            }
                        }
                    ]
            },

                pageCurrent: 1,
                pageSize: 10,
                columns6: [
                    {
                        title: 'Name',
                        key: 'name',
                        align:'center'
                    },
                    {
                      title: 'Gender',
                      key: 'gender',
                        align:'center'
                    },
                    {
                        title: 'Branch',
                        key: 'branch',
                        align:'center',
                        filters: [
                            {
                                label: 'Park Avenue',
                                value: 'Park Avenue'
                            },
                            {
                                label: 'Bank Street',
                                value: 'Bank Street'
                            },
                            {
                                label: '11th Avenue',
                                value: '11th Avenue'
                            },
                            {
                                label: 'Cranberry Street',
                                value: 'Cranberry Street'
                            },
                            {
                                label: 'Madison Avenue',
                                value: 'Madison Avenue'
                            },
                            {
                                label: 'Lexington Avenue',
                                value: 'Lexington Avenue'
                            },
                            {
                                label: 'Minetta Street',
                                value: 'Minetta Street'
                            },
                            {
                                label: 'Broadway',
                                value: 'Broadway'
                            }
                        ],
                        filterMethod (value, row) {
                            return row.branch.indexOf(value) > -1;
                        }
                    },
                    {
                        title: 'Tel',
                        key: 'tel',
                        align:'center'
                    },
                    {
                        title: 'Address',
                        key: 'address',
                        align:'center'
                    },
                    {
                        title: 'Epidemic Risk',
                        key: 'risk',
                        align:'center'
                    },
                    {
                        title: 'Action',
                        slot: 'action',
                        width: 150,
                        align: 'center'
                    }
                ],
                data5: [
                    {
                        name: 'Li Aiqin',
                        gender: 'male',
                        branch: 'Park Avenue',
                        tel: '001-896854',
                        address: 'New York No. 1 Lake Park',
                        risk: 'High'
                    },
                    {
                        name: 'Wen Kaidi',
                        gender:'female',
                        branch: 'Park Avenue',
                        tel: '010-949948',
                        address: 'London No. 1 Lake Park',
                        risk: 'Low'
                    },
                    {
                        name: 'Wang Xia',
                        gender: 'female',
                        branch: 'Bank Street',
                        tel: '084-2339767',
                        address: 'Sydney No. 1 Lake Park',
                        risk: 'High'
                    },
                    {
                        name: 'Li Aiqin',
                        gender:'female',
                        branch: 'Park Avenue',
                        tel: '048-37279340',
                        address: 'Ottawa No. 2 Lake Park',
                        risk: 'Low'
                    },
                    {
                        name: 'Li Dakang',
                        gender:'male',
                        branch: 'Broadway',
                        tel: '002-8439340',
                        address: 'Pris No. 3 Lake Park',
                        risk: 'Low'
                    },
                    {
                        name: 'An Di',
                        gender: 'male',
                        branch: '11th Avenue',
                        tel: '017-960776',
                        address: 'Berlin No. 2 Lake Park',
                        risk:'Low'
                    },
                    {
                        name: 'Han Dajin',
                        gender:'male',
                        branch: '11th Avenue',
                        tel: '084-3420830',
                        address: 'Bangkok No. 1 Lake Park',
                        risk: 'Low'
                    },
                    {
                        name: 'Bian Deli',
                        gender: 'male',
                        branch: 'Minetta Street',
                        tel: '001-896854',
                        address: 'Mumbai No. 1 Lake Park',
                        risk: 'High'
                    },
                    {
                        name: 'Liu Qi',
                        gender:'female',
                        branch: 'Cranberry Street',
                        tel: '010-949948',
                        address: 'Houston No. 1 Lake Park',
                        risk: 'Middle'
                    }
                ],
                data6: [
                    {
                        name: 'Bian Deli',
                        gender: 'male',
                        branch: 'Minetta Street',
                        tel: '001-896854',
                        address: 'Mumbai No. 1 Lake Park',
                        risk: 'High'
                    },
                    {
                        name: 'Liu Qi',
                        gender:'female',
                        branch: 'Madison Avenue',
                        tel: '010-949948',
                        address: 'Houston No. 1 Lake Park',
                        risk: 'Low'
                    },
                    {
                        name: 'Qiu Bingyi',
                        gender: 'female',
                        branch: 'Park Avenue',
                        tel: '084-2339767',
                        address: 'Johannesburg No. 1 Lake Park',
                        risk: 'High'
                    }
                ],

                cityList: [
                    {
                        value: '1',
                        label: 'Park Avenue'
                    },
                    {
                        value: '2',
                        label: 'Bank Street'
                    },
                    {
                        value: 3,
                        label: '11th Avenue'
                    },
                    {
                        value: 4,
                        label: 'Cranberry Street'
                    },
                    {
                        value: 5,
                        label: 'Madison Avenue'
                    },
                    {
                        value: 6,
                        label: 'Lexington Avenue'
                    },
                    {
                        value: 7,
                        label: 'Minetta Street'
                    },
                    {
                        value: '8',
                        label: 'Broadway'
                    }
                ],
                model1: '1'

            }
        },
        methods: {
            refreshPie2(){
                var option = this.pieData2.getOption();
                option.series[0].data[0].value = this.random(0,70);
                option.series[0].data[1].value = this.random(100,250);
                this.pieData2.setOption(option);
            }
            ,
            random(min,max){
                return Math.floor(Math.random() * (max - min)) + min;
            },
            show (index) {
                this.$Modal.info({
                    title: 'Employee Info',
                    content: `Name：${this.data5[index].name}<br>Branch：${this.data5[index].branch}<br>Tel：${this.data5[index].tel}<br>Address：${this.data5[index].address}<br>Epidemic Risk : ${this.data5[index].risk}`
                })
            },
            remove (index) {
                this.data5.splice(index, 1);
            },
            changePage (page) {
                // 这里直接更改了模拟的数据，真实使用场景应该从服务端获取数据
                this.pageCurrent = page;
                // if(this.pageCurrent===1) this.tableData = this.data5[]
            },
            init(){
                var pieData1 = echarts.init(document.getElementById('pie1'));
                pieData1.setOption(this.pieOption1);

                var pieData2 = echarts.init(document.getElementById('pie2'));
                this.pieData2 = pieData2;
                pieData2.setOption(this.pieOption2);

                window.onresize = function () {
                    pieData1.resize();
                    pieData2.resize();
                }
            }
        },
        mounted() {
            this.init();
        }
    }
</script>
<style>
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
  /*.selectArea >>> .ivu-select-selection .ivu-select-placeholder{*/
  /*  color:#516b91;*/
  /*  font-weight:bolder;*/
  /*}*/
  .selectArea >>> .ivu-select-selection{
    background-color: rgba(0,0,0,0);
    font-size: larger;
    font-weight: bolder;
    border:none;
    color:#516b91;
  }
  .selectArea >>> .ivu-select-dropdown{
    background-color: rgba(0,0,0,0);
    font-size: larger;
    font-weight: bolder;
  }
  /*.option1{*/
  /*  background-color: rgba(0,0,0,0);*/
  /*}*/
  .ivu-page-item {
    background-color: rgba(0,0,0,0);
  }
  .ivu-page-prev {
    background-color: rgba(0,0,0,0);
  }
</style>
