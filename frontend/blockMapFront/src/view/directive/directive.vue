<template>
  <div>
    <Table :columns="columns12" :data="data6">
      <template slot-scope="{ row }" slot="name">
        <strong>{{ row.name }}</strong>
      </template>
      <template slot-scope="{ row, index }" slot="action">
        <Button type="primary" size="small" style="margin-right: 5px" @click="show(index)">View</Button>
        <Button type="error" size="small" @click="remove(index)">Delete</Button>
      </template>
    </Table>
    <div>
      <Page :total="9" :page-size="pageSize" :current="pageCurrent" @on-change="changePage" style="position: absolute;z-index: 99;margin-top:20px"></Page>
    </div>
  </div>
</template>
<script>
    export default {
        name:'directive_page',
        data () {
            return {
                pageCurrent: 1,
                pageSize: 10,
                columns12: [
                    {
                        title: 'Branch',
                        key: 'branch',
                        align:'center'
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
                        title:'Number of Employees',
                        key: 'number',
                        align:'center'
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
                        branch: 'branch1',
                        tel: '001-896854',
                        address: 'New York No. 1 Lake Park',
                        number:3
                    },
                    {
                        branch: 'branch2',
                        tel: '010-949948',
                        address: 'London No. 1 Lake Park',
                        number:1
                    },
                    {
                        branch: 'branch3',
                        tel: '084-2339767',
                        address: 'Sydney No. 1 Lake Park',
                        number: 1
                    },
                    {
                        branch: 'branch4',
                        tel: '048-37279340',
                        address: 'Ottawa No. 2 Lake Park',
                        number: 2
                    },
                    {
                        branch: 'branch5',
                        tel: '002-8439340',
                        address: 'Pris No. 3 Lake Park',
                        number: 2
                    }
                ]
            }
        },
        methods: {
            show (index) {
                this.$Modal.info({
                    title: 'Branch Info',
                    content: `Branch：${this.data6[index].branch}<br>Tel：${this.data6[index].tel}<br>Address：${this.data6[index].address}<br>Epidemic Risk : ${this.data6[index].risk}`
                })
            },
            remove (index) {
                this.data6.splice(index, 1);
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
