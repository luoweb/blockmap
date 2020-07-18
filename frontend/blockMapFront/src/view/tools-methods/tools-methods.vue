<template>
  <Table :columns="columns12" :data="data6">
    <template slot-scope="{ row }" slot="name">
      <strong>{{ row.name }}</strong>
    </template>
    <template slot-scope="{ row, index }" slot="action">
      <Button type="primary" size="small" style="margin-right: 5px" @click="show(index)">View</Button>
      <Button type="error" size="small" @click="remove(index)">Delete</Button>
    </template>
  </Table>
</template>
<script>
    export default {
        name:'tools_method',
        data () {
            return {
                columns12: [
                    {
                        title: 'Organization',
                        key: 'org'
                    },
                    {
                        title: 'Tel',
                        key: 'tel'
                    },
                    {
                        title: 'Address',
                        key: 'address'
                    },
                    {
                        title: 'Epidemic Risk',
                        key: 'risk'
                    },
                    {
                        title: 'Action',
                        slot: 'action',
                        width: 150,
                        align: 'center'
                    }
                ],
                data6: [
                    {
                        org: 'Organization A',
                        tel: '001-896854',
                        address: 'New York No. 1 Lake Park',
                        risk: 'High'
                    },
                    {
                        org: 'Organization B',
                        tel: '010-949948',
                        address: 'London No. 1 Lake Park',
                        risk: 'Low'
                    },
                    {
                        org: 'Organization C',
                        tel: '084-2339767',
                        address: 'Sydney No. 1 Lake Park',
                        risk: 'High'
                    },
                    {
                        org: 'Organization D',
                        tel: '048-37279340',
                        address: 'Ottawa No. 2 Lake Park',
                        risk: 'Middle'
                    },
                    {
                        org: 'Organization E',
                        tel: '002-8439340',
                        address: 'Pris No. 3 Lake Park',
                        risk: 'Low'
                    },
                    {
                        org: 'Organization F',
                        tel: '017-960776',
                        address: 'Berlin No. 2 Lake Park',
                        risk:'Low'
                    },
                    {
                        org: 'Organization G',
                        tel: '084-3420830',
                        address: 'Bangkok No. 1 Lake Park',
                        risk: 'Low'
                    }
                ]
            }
        },
        methods: {
            show (index) {
                this.$Modal.info({
                    title: 'Organization Info',
                    content: `Organization：${this.data6[index].org}<br>Tel：${this.data6[index].tel}<br>Address：${this.data6[index].address}<br>Epidemic Risk : ${this.data6[index].risk}`
                })
            },
            remove (index) {
                this.data6.splice(index, 1);
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
