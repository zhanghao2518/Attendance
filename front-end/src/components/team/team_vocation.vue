<template lang="html">
    <div class="">
        <div class="">
            <el-button
                type="primary"
                @click="timeoffFormDisplay"
                class="timeoffButton"
                size="small">
                请假申请
            </el-button>
        </div>
        <el-table
            :data="tableData"
            stripe
            style="width: 100%"
            class="tableDatadisplay">
            <el-table-column
                prop="id"
                label="编号"
                align="center">
            </el-table-column>
            <el-table-column
                prop="uid"
                label="员工编号"
                align="center">
            </el-table-column>
            <el-table-column
                prop="uname"
                label="员工姓名"
                align="center">
            </el-table-column>
            <el-table-column
                prop="did"
                label="部门id"
                align="center">
            </el-table-column>
            <el-table-column
                prop="dname"
                label="部门名称"
                align="center">
            </el-table-column>
          <el-table-column
            prop="startTime"
            label="开始时间"
            align="center">
          </el-table-column>
          <el-table-column
            prop="endTime"
            label="结束时间"
            align="center">
          </el-table-column>
          <el-table-column
            prop="lengthLeave"
            label="请假时长"
            align="center">
          </el-table-column>
          <el-table-column
            prop="type"
            label="请假类型"
            align="center">
          </el-table-column>
          <el-table-column
            prop="reviewByDivisionManager"
            label="部门经理审核结果"
            align="center">
          </el-table-column>
          <el-table-column
            prop="adviceByDivisionManager"
            label="部门经理审核意见"
            align="center">
          </el-table-column>
          <el-table-column
            prop="reviewByManager"
            label="总经理审核结果"
            align="center">
          </el-table-column>
          <el-table-column
            prop="adviceByManager"
            label="总经理审核意见"
            align="center">
          </el-table-column>
          <el-table-column
            prop="result"
            label="最终结果"
            align="center">
          </el-table-column>
          <el-table-column>
            <template slot-scope="scope">
              　　　　　　<el-button type="info" @click="review(scope.$index)" :disabled="((tableData[scope.$index].reviewByDivisionManager===1||tableData[scope.$index].reviewByDivisionManager===0)&&$root.user.grade===1)||((tableData[scope.$index].reviewByManager===1||tableData[scope.$index].reviewByManager===0)&&$root.user.grade===2)">审核</el-button>
              　　　　</template>
          </el-table-column>
        </el-table>
        <el-pagination
            @size-change="sizeChange"
            @current-change="currentChange"
            :current-page="pageNum"
            :page-sizes="[5, 7, 10]"
            :page-size="pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total='total'>
        </el-pagination>
        <el-dialog
            title="请假申请单"
            :visible.sync="dialogFormVisible"
            :before-close="handleDialogClose">
            <el-form
                :model="timeoffForm"
                label-position="right"
                label-width="140px">
                <el-form-item
                    label="请假开始日期">
                    <el-input type="text" v-model="timeoffForm.startTime" size="small"></el-input>
                </el-form-item>
              <el-form-item
                label="请假结束日期">
                <el-input type="text" v-model="timeoffForm.endTime" size="small"></el-input>
              </el-form-item>
                <el-form-item
                    label="编号">
                    <el-input-number
                        v-model="timeoffForm.uid"
                        :min="1"
                        size="small">
                    </el-input-number>
                </el-form-item>
                <el-form-item
                    label="请假类型">
                    <el-select
                        v-model="timeoffForm.timeoff_type"
                        placeholder="请选择请假类型"
                        class="timeoffType"
                        size="small">
                        <el-option
                            v-for="item in options"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item
                    label="姓名">
                    <el-input
                        type="text"
                        v-model="timeoffForm.uname"
                        size="small">
                    </el-input>
                </el-form-item>
                <el-form-item
                    label="部门名称">
                    <el-input
                        type="text"
                        v-model="timeoffForm.dname"
                        size="small">
                    </el-input>
                </el-form-item>
            </el-form>
                <div
                    slot="footer"
                    class="dialog-footer">
                    <el-button
                        @click="handleDialogClose"
                        size="small">
                        取 消
                    </el-button>
                    <el-button
                        type="primary"
                        @click="submitForm"
                        size="small">
                        确 定
                    </el-button>
                </div>
          </el-dialog>
      <el-dialog
        title="审核"
        :visible.sync="dialogDisplay">
        <el-form
          :model="displayVocation"
          label-position="right"
          label-width="140px">
          <el-form-item
            label="编号">
            <el-input type="number" v-model="displayVocation.id" size="small" disabled></el-input>
          </el-form-item>
          <el-form-item
            label="员工编号">
            <el-input type="number" v-model="displayVocation.uid" size="small" disabled></el-input>
          </el-form-item>
          <el-form-item
            label="员工姓名">
            <el-input
              type="text"
              v-model="displayVocation.uname"
              size="small" disabled>
            </el-input>
          </el-form-item>
          <el-form-item
            label="部门id">
            <el-input type="number" size="small" v-model="displayVocation.did" disabled></el-input>
          </el-form-item>
          <el-form-item
            label="部门名称">
            <el-input
              type="text"
              v-model="displayVocation.dname"
              size="small" disabled>
            </el-input>
          </el-form-item>
          <el-form-item
            label="开始时间">
            <el-input
              type="text"
              v-model="displayVocation.startTime"
              size="small" disabled>
            </el-input>
          </el-form-item>
          <el-form-item
            label="结束时间">
            <el-input
              type="text"
              v-model="displayVocation.endTime"
              size="small" disabled>
            </el-input>
          </el-form-item>
          <el-form-item
            label="请假时长">
            <el-input
              type="text"
              v-model="displayVocation.lengthLeave"
              size="small" disabled>
            </el-input>
          </el-form-item>
          <el-form-item
            label="请假类型">
            <el-input
              type="text"
              v-model="displayVocation.type"
              size="small" disabled>
            </el-input>
          </el-form-item>
          <el-form-item
            label="部门经理审核结果">
            <el-select
              v-model="displayVocation.reviewByDivisionManager"
              placeholder="请选择是否同意"
              class="timeoffType"
              size="small" :disabled="this.$root.user.grade!==1">
              <el-option
                v-for="item in agree"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item
            label="部门经理审核意见">
            <el-input
              type="text"
              v-model="displayVocation.adviceByDivisionManager"
              size="small" :disabled="this.$root.user.grade!==1">
            </el-input>
          </el-form-item>
          <el-form-item
            label="总经理审核结果">
            <el-select
              v-model="displayVocation.reviewByManager"
              placeholder="请选择是否同意"
              class="timeoffType"
              size="small" :disabled="this.$root.user.grade!==2">
              <el-option
                v-for="item in agree"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item
            label="总经理审核意见">
            <el-input
              type="text"
              v-model="displayVocation.adviceByManager"
              size="small" :disabled="this.$root.user.grade!==2">
            </el-input>
          </el-form-item>
          <el-form-item
            label="最终结果">
            <el-input
              type="number"
              v-model="displayVocation.result"
              size="small" disabled>
            </el-input>
          </el-form-item>
        </el-form>
        <div
          slot="footer"
          class="dialog-footer">
          <el-button
            @click="handleDialogClose"
            size="small">
            取 消
          </el-button>
          <el-button
            type="primary"
            @click="submit_review"
            size="small">
            确 定
          </el-button>
        </div>
      </el-dialog>
    </div>
</template>

<script>
  import axios from 'axios'
export default {
    data() {
        return {
            //当前页
            args:{},
            total:0,
            pageNum:1,
            pageSize:5,
            displayVocation:{},
            dialogDisplay:false,
            currentPage: 1,
            //dialog的显示
            dialogFormVisible: false,
            //表单初始化
            timeoffForm: {
              dname: "",
              endTime: "",
              startTime: "",
              type: 0,
              uid: 0,
              uname: ""
            },
            //选择器
            options: [{
                value: 0,
                label: '事假'
            }, {
                value: 1,
                label: '年假'
            }],
            agree:[{
              value:0,
              label:'不同意'
            },{
              value:1,
              label:'同意'
            }],
            //表格数据
            tableData: []
        }
    },
    created() {
      if(this.$root.user.grade===0){
        this.args={'dname':this.$root.user.dname,'id':this.$root.user.id,'name':this.$root.user.name,'pageNum':1,'pageSize':10000}
      }else if(this.$root.user.grade===1){
        this.args={'dname':this.$root.user.dname,'pageNum':1,'pageSize':10000}
      }else if(this.$root.user.grade===2){
        this.args={'pageNum':1,'pageSize':10000}
      }
      axios.request({
        method:'get',
        url:'http://localhost:9999/userLeave/list',
        params:this.args
      }).then(res=>{
        this.total=res.data.list.length;
      });
      this.args.pageNum=this.pageNum;
      this.args.pageSize=this.pageSize;
      axios.request({
        method:'get',
        url:'http://localhost:9999/userLeave/list',
        params:this.args
      }).then(res=>{
        this.tableData=res.data.list;
      });
    },
  methods: {
        //处理页面跳转请求
        currentChange(item){
          this.args.pageNum=item;
          axios.request({
            method:'get',
            url:'http://localhost:9999/userLeave/list',
            params:this.args
          }).then(res=>{
            this.tableData=res.data.list;
          });
        },
        sizeChange(item){
          this.args.pageSize=item;
          axios.request({
            method:'get',
            url:'http://localhost:9999/userLeave/list',
            params:this.args
          }).then(res=>{
            this.tableData=res.data.list;
          });
        },
        review(index){
          this.displayVocation=this.tableData[index];
          this.dialogDisplay=true;
        },
        handleCurrentChange(item) {
            console.log(item);
        },
        //处理每页的个数发生变化
        handleSizeChange(item) {
            console.log(item);
        },
        //打开请假申请单
        timeoffFormDisplay() {
            this.dialogFormVisible = true;
            this.timeoffForm.dname=this.$root.user.dname;
            this.timeoffForm.uname=this.$root.user.name;
            this.timeoffForm.uid=this.$root.user.id;
        },
        //当对话框关闭时，初始化数据
        handleDialogClose() {
            this.timeoffForm = {
              dname: "",
              endTime: "",
              startTime: "",
              type: 0,
              uid: 0,
              uname: ""
            };
            this.dialogFormVisible = false;
            this.dialogDisplay=false;
        },
        //提交请假申请单
        submitForm() {
            axios.request({
              method:'post',
              url:'http://localhost:9999/userLeave/add',
              data:this.timeoffForm
            }).then(res=>{
              console.log(res.data)
            });
            this.handleDialogClose();
        },
        submit_review(){
          let page='';
          if(this.$root.user.grade===1){
            page='http://localhost:9999/userLeave/updateByDivision'
          }else if(this.$root.user.grade===2){
            page='http://localhost:9999/userLeave/updateByManager'
          }
          axios.request({
            method:'put',
            url:page,
            data:this.displayVocation
          }).then(res=>{
            console.log(res.data)
          })
        }
    }
}
</script>

<style lang="css">
.timeoffButton {
    float: right;
}
.tableDatadisplay {
    margin-bottom: 40px;
}
.timeoffDecs {
    width: 350px;
}
.timeoffType {
    width: 220px;
}
</style>
