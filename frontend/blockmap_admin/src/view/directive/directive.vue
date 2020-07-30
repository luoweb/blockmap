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
    <Row>
      <i-col :md="24" :lg="12" style="margin-bottom: 10px;">
        <div>
          <Page :total="9" :page-size="pageSize" :current="pageCurrent" @on-change="changePage" style="margin-top:20px"></Page>
        </div>
      </i-col>
      <i-col :md="24" :lg="12" style="margin-bottom: 10px;">
        <Button type="success" style="margin-top:20px;float:right;margin-right:30px" @click="branchAdd">Add branch</Button>
      </i-col>
    </Row>

    <Modal title="Please add new branch!" v-model="branchAddition" @on-cancel="addFirstNo">
      <Form :model="formItemAdd" ref="formItemAdd" :rules="ruleValidate">
        <FormItem label="Branch name" prop="name"><Input v-model="formItemAdd.name"></Input></FormItem>
        <FormItem label="Tel" prop="tel"><Input v-model="formItemAdd.tel"></Input></FormItem>
        <FormItem label="Address" prop="addr"><Input v-model="formItemAdd.addr"></Input></FormItem>
        <FormItem label="Number of employees" prop="num"><InputNumber v-model="formItemAdd.num"></InputNumber></FormItem>
      </Form>
      <div slot="footer">
        <Button type="primary" size="large" @click="addFirstYes">Yes</Button>
        <Button type="warning" size="large" @click="addFirstNo">No</Button>
      </div>
      <Modal v-model="addSecondShow">Confirmed?
        <div slot="footer">
          <Button type="primary" size="large" @click="addSecondYes">Yes</Button>
          <Button type="warning" size="large" @click="addSecondNo">No</Button>
        </div>
      </Modal>
    </Modal>


  </div>
</template>
<script>
    import {validate} from "../../libs/util";

    export default {
        name:'directive_page',
        data () {
            const validateNumber = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('Please enter the number of employees'))
                } else if(value<0){
                    callback(new Error('Invalid input'))
                }
                else callback()
            };
            const validateName = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('Please enter the branch name'))
                } else callback()
            };
            const validateTel = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('Please enter the telephone number'))
                } else callback()
            };
            const validateAddress = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('Please enter the branch address'))
                } else callback()
            };

            return {
                ruleValidate: {
                    num: [
                        {
                            validator: validateNumber, trigger: 'blur'
                        }
                    ],
                    name: [
                        {
                            validator: validateName, trigger: 'blur'
                        }
                    ],
                    tel: [
                        {
                            validator: validateTel, trigger: 'blur'
                        }
                    ],
                    addr: [
                        {
                            validator: validateAddress, trigger: 'blur'
                        }
                    ]
                },
                formItemAdd:{
                    name: '',
                    tel: '',
                    addr:'',
                    num:0
                },
                branchAddition: false,
                addSecondShow: false,
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
                        branch: 'Park Avenue',
                        tel: '001-896854',
                        address: 'New York No. 1 Lake Park',
                        number:3
                    },
                    {
                        branch: 'Bank Street',
                        tel: '010-949948',
                        address: 'London No. 1 Lake Park',
                        number:1
                    },
                    {
                        branch: '11th Avenue',
                        tel: '084-2339767',
                        address: 'Sydney No. 1 Lake Park',
                        number: 1
                    },
                    {
                        branch: 'Cranberry Street',
                        tel: '048-37279340',
                        address: 'Ottawa No. 2 Lake Park',
                        number: 2
                    },
                    {
                        branch: 'Madison Avenue',
                        tel: '092-00594812',
                        address: 'Pris No. 3 Lake Park',
                        number: 2
                    },
                    {
                        branch: 'Lexington Avenue',
                        tel: '002-8439340',
                        address: 'Pris No. 3 Lake Park',
                        number: 2
                    },
                    {
                        branch: 'Minetta Street',
                        tel: '042-6557888',
                        address: 'Pris No. 3 Lake Park',
                        number: 2
                    },
                    {
                        branch: 'Broadway',
                        tel: '030-4643360',
                        address: 'Pris No. 3 Lake Park',
                        number: 2
                    }
                ]
            }
        },
        methods: {
            branchAdd(){
                this.$refs['formItemAdd'].resetFields();
                this.branchAddition = true;
            },
            addFirstNo(){
                this.branchAddition = false;
            },
            async addFirstYes(){
                try{
                    await validate(this.$refs['formItemAdd']);
                    this.addSecondShow= true;
                } catch(e){
                    this.$Message.error('Fail and check your input!')
                    console.log(e)
                }
            },
            addSecondYes(){
                this.data6.push({
                    branch: this.formItemAdd.name,
                    tel: this.formItemAdd.tel,
                    address: this.formItemAdd.addr,
                    number: this.formItemAdd.num

                });
                this.$Message.success('Success to add!');
                this.branchAddition = false;
                this.addSecondShow = false;
            },
            addSecondNo() {
                this.addSecondShow = false
            },

            show (index) {
                this.$Modal.info({
                    title: 'Branch Info',
                    content: `Branch：${this.data6[index].branch}<br>Tel：${this.data6[index].tel}<br>Address：${this.data6[index].address}<br>Number of Employees: ${this.data6[index].number}`
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
