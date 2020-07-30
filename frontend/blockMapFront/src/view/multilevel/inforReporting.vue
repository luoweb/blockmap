<template>
  <div>
    <Table :columns="columns12" :data="data6">
<!--      <template slot-scope="{ row }" slot="name">-->
<!--        <strong>{{ row.name }}</strong>-->
<!--      </template>-->
      <template slot-scope="{ row, index }" slot="action">
        <Button v-if="row.status!=='finished'" type="success" size="small" style="margin-right: 5px" @click="show(row,index)">Audit</Button>
      </template>
    </Table>
    <div>
      <Page :total="9" :page-size="pageSize" :current="pageCurrent" @on-change="changePage" style="position: absolute;z-index: 99;margin-top:20px"></Page>
    </div>
  </div>
</template>
<script>
    export default {
        name:'inforReporting',
        data () {
            return {
                pageCurrent: 1,
                pageSize: 10,
                columns12: [
                    {
                        title: 'Name',
                        key: 'name',
                        align: 'center'
                    },
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
                        title:'Description',
                        key: 'description',
                        align:'center'
                    },
                    {
                      title:'Date',
                      key:'date',
                      align:'center'
                    },
                    {
                      title:'Status',
                      key:'status',
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
                        name: 'An Di',
                        branch: '11th Avenue',
                        tel: '001-896854',
                        address: 'New York 11th Avenue',
                        date: '2020-07-03',
                        status:'pending',
                        description:'fever,need to see a doctor'
                    },
                    {
                        name: 'Li Aiqin',
                        branch: 'Park Avenue',
                        tel: '002-4696645',
                        address: 'New York No. 1 Park Avenue',
                        date: '2020-07-12',
                        status:'pending',
                        description:'uncomfortable'
                    }
                ]
            }
        },
        methods: {
            show (row,index) {
                row.status = 'finished';
            },

            changePage (page) {
                this.pageCurrent = page;
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

