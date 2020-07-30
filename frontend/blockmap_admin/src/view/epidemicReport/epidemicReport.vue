<template>
  <div>
<Row :gutter="40">
  <i-col :xs="24" :md="8" :lg="6" >
    <Card shadow style="background:rgba(0,0,0,.5);">
      <div style="text-align: center;margin-bottom: 10px">
        <span style="color:#516b91;font-weight: bolder;font-size: large">{{$t('reporting')}}</span>
      </div>
    <Form :model="formItemAdd" ref="formItemAdd" :rules="ruleValidate" label-position="left">
      <FormItem label="Branch name" prop="name"><Input v-model="formItemAdd.name" placeholder="Please enter the name"></Input></FormItem>
      <FormItem label="Confirmed?" prop="confirmed">
        <i-switch v-model="formItemAdd.confirmed" true-color="#516b91" size="large">
          <span slot="open">Yes</span>
          <span slot="close">No</span>
        </i-switch>
      </FormItem>
      <FormItem label="Symptom Date" prop="date">
        <DatePicker type="date" placeholder="Select date" @on-change="dateFormatAdd"  v-model="formItemAdd.date" ></DatePicker>
      </FormItem>
      <FormItem label="Symptom" prop="symptoms">
        <i-select v-model="formItemAdd.symptoms" multiple placeholder="Please select the symptoms">
          <Option  class='option1' v-for="item in symptomList" :value="item.value">{{ item.label }}</Option>
        </i-select>
      </FormItem>
    </Form>
      <div>
        <Button type="primary" @click="submit" style="margin-right:10px">Submit</Button>
        <Button type="primary" @click="reClear">Reset</Button>
      </div>
    </Card>
  </i-col>
  <i-col :xs="24" :md="16" :lg="18" >
    <Card shadow style="background:rgba(0,0,0,.5);">
      <div style="text-align: center;margin-bottom: 10px">
        <span style="color:#516b91;font-weight: bolder;font-size: large">{{$t('reList')}}</span>
      </div>
    <Table :columns="columns12" :data="data6">
      <template slot-scope="{ row, index }" slot="action">
        <Button type="error" size="small" @click="remove(index)">Delete</Button>
      </template>
    </Table>
    </Card>
  </i-col>
</Row>
  <Modal v-model="addYes">Confirmed?
    <div slot="footer">
      <Button type="primary" size="large" @click="addSecondYes">Yes</Button>
      <Button type="warning" size="large" @click="addSecondNo">No</Button>
    </div>
  </Modal>
  </div>
</template>

<script>
    import {validate} from "../../libs/util";
    export default {
        name: "epidemicReport",
        data(){
            const validateDate = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('Please select the symptom date'))
                } else callback()
            };
            const validateName = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('Please enter the name'))
                } else callback()
            };
            const validateSymptom = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('Please select the symptom'))
                } else callback()
            };
            const validateConfirmed = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('Please choose whether confirmed'))
                } else callback()
            };
            return {
                addYes: false,
                symptomList: [
                    {
                        value: 'fever',
                        label: 'fever'
                    },
                    {
                        value: 'cough',
                        label: 'cough'
                    },
                    {
                        value: 'feeble',
                        label: 'feeble'
                    },
                    {
                        value: 'others',
                        label: 'others'
                    }
                ],
                columns12: [
                    {
                        title: 'Name',
                        key: 'name',
                        align:'center'
                    },
                    {
                        title: 'Confirmed',
                        key: 'confirmed',
                        align:'center'
                    },
                    {
                        title: 'Date',
                        key: 'date',
                        align:'center'
                    },
                    {
                        title:'Symptoms',
                        key: 'symptoms',
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
                        name: 'Wen Kaidi',
                        confirmed: true,
                        date: '2020-04-10',
                        symptoms: 'feeble'
                    },
                    {
                        name: 'Han Dajin',
                        confirmed: false,
                        date: '2020-04-16',
                        symptoms: 'fever'
                    },
                    {
                        name: 'An Di',
                        confirmed: true,
                        date: '2020-05-06',
                        symptoms: 'others'
                    },
                    {
                        name: 'Wang Xia',
                        confirmed: false,
                        date: '2020-05-17',
                        symptoms: 'others'
                    },
                    {
                        name: 'Wen Kaidi',
                        confirmed: true,
                        date: '2020-05-19',
                        symptoms: 'feeble'
                    },{
                        name: 'Han Dajin',
                        confirmed: true,
                        date: '2020-05-28',
                        symptoms: 'fever'
                    },{
                        name: 'Bian Deli',
                        confirmed: false,
                        date: '2020-06-03',
                        symptoms: 'fever,cough'
                    },
                    {
                        name: 'Li Dakang',
                        confirmed: true,
                        date: '2020-06-12',
                        symptoms: 'cough'
                    },
                    {
                        name: 'Liu Qi',
                        confirmed: true,
                        date: '2020-06-21',
                        symptoms: 'fever'
                    },
                    {
                        name: 'Han Dajin',
                        confirmed: false,
                        date: '2020-07-11',
                        symptoms: 'cough'
                    },{
                        name: 'Li Aiqin',
                        confirmed: true,
                        date: '2020-7-13',
                        symptoms: 'fever,cough'
                    },{
                        name: 'Bian Deli',
                        confirmed: true,
                        date: '2020-7-24',
                        symptoms: 'others'
                    },
                ],
                ruleValidate: {

                    name: [
                        {
                            validator: validateName, trigger: 'blur'
                        }
                    ],
                    symptoms: [
                        {
                            validator: validateSymptom, trigger: 'blur'
                        }
                    ],
                    date: [
                        {
                            validator: validateDate, trigger: 'blur'
                        }
                    ],
                    confirmed: [
                        {
                            validator:validateConfirmed,trigger:'blur'
                        }
                    ]
                },
                formItemAdd:{
                    name: '',
                    confirmed: false,
                    symptoms: [],
                    date: ''
                },
            }
        },
        methods:{
            /**
             * 日期类型转换
             **/
            dateFormatAdd (e) {
                this.formItemAdd.date = e
            },
            async submit() {
                try{
                    console.log('eae',this.formItemAdd)
                    await validate(this.$refs['formItemAdd']);
                    this.addYes = true;
                }catch (e) {
                    this.$Message.error('Fail and check your input!');
                    console.log(e)
                }
            },
            reClear() {
                this.$refs['formItemAdd'].resetFields();
            },
            addSecondYes(){
                this.data6.push({
                    name: this.formItemAdd.name,
                    confirmed: this.formItemAdd.confirmed,
                    date: this.formItemAdd.date,
                    symptoms: this.formItemAdd.symptoms.join(',')
                })
                this.$Message.success('Success to add!')
                this.addYes = false;
                this.$refs['formItemAdd'].resetFields();
            },
            addSecondNo(){
              this.addYes = false;
            },
            remove (index) {
                this.data6.splice(index, 1);
            },
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
