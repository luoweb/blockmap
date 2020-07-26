<template>
  <div>
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
      <Page :total="9" :page-size="pageSize" :current="pageCurrent" @on-change="changePage" style="position: absolute;z-index: 99;margin-top:20px"></Page>
    </div>

    </div>
</template>
<script>
    export default {
        name:'tools_method',
        data () {
            return {
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
                                label: 'branch1',
                                value: 'branch1'
                            },
                            {
                                label: 'branch2',
                                value: 'branch2'
                            },
                            {
                                label: 'branch3',
                                value: 'branch3'
                            },
                            {
                                label: 'branch4',
                                value: 'branch4'
                            },
                            {
                                label: 'branch5',
                                value: 'branch5'
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
                        branch: 'branch1',
                        tel: '001-896854',
                        address: 'New York No. 1 Lake Park',
                        risk: 'High'
                    },
                    {
                        name: 'Wen Kaidi',
                        gender:'female',
                        branch: 'branch1',
                        tel: '010-949948',
                        address: 'London No. 1 Lake Park',
                        risk: 'Low'
                    },
                    {
                        name: 'Wang Xia',
                        gender: 'female',
                        branch: 'branch2',
                        tel: '084-2339767',
                        address: 'Sydney No. 1 Lake Park',
                        risk: 'High'
                    },
                    {
                        name: 'Li Aiqin',
                        gender:'female',
                        branch: 'branch1',
                        tel: '048-37279340',
                        address: 'Ottawa No. 2 Lake Park',
                        risk: 'Low'
                    },
                    {
                        name: 'Li Dakang',
                        gender:'male',
                        branch: 'branch3',
                        tel: '002-8439340',
                        address: 'Pris No. 3 Lake Park',
                        risk: 'Low'
                    },
                    {
                        name: 'An di',
                        gender: 'male',
                        branch: 'branch4',
                        tel: '017-960776',
                        address: 'Berlin No. 2 Lake Park',
                        risk:'Low'
                    },
                    {
                        name: 'Han Dajin',
                        gender:'male',
                        branch: 'branch5',
                        tel: '084-3420830',
                        address: 'Bangkok No. 1 Lake Park',
                        risk: 'Low'
                    },
                    {
                        name: 'Bian Deli',
                        gender: 'male',
                        branch: 'branch5',
                        tel: '001-896854',
                        address: 'Mumbai No. 1 Lake Park',
                        risk: 'High'
                    },
                    {
                        name: 'Liu Qi',
                        gender:'female',
                        branch: 'branch4',
                        tel: '010-949948',
                        address: 'Houston No. 1 Lake Park',
                        risk: 'Middle'
                    }
                ],
                data6: [
                    {
                        name: 'Bian Deli',
                        gender: 'male',
                        branch: 'branch5',
                        tel: '001-896854',
                        address: 'Mumbai No. 1 Lake Park',
                        risk: 'High'
                    },
                    {
                        name: 'Liu Qi',
                        gender:'female',
                        branch: 'branch4',
                        tel: '010-949948',
                        address: 'Houston No. 1 Lake Park',
                        risk: 'Low'
                    },
                    {
                        name: 'Qiu Bingyi',
                        gender: 'female',
                        branch: 'branch1',
                        tel: '084-2339767',
                        address: 'Johannesburg No. 1 Lake Park',
                        risk: 'High'
                    }
                ]
            }
        },
        methods: {
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
            }
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
  .ivu-page-item {
    background-color: rgba(0,0,0,0);
  }
  .ivu-page-prev {
    background-color: rgba(0,0,0,0);
  }
</style>
