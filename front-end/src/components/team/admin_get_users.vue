<template lang="html">
  <div class="">
    <el-row :gutter="10">
      <el-col :span="4">
        <div class="">
          <el-input
            type="text"
            placeholder="请输入id"
            suffix-icon="el-icon-search"
            size="small"
            @change="searchUserName">
          </el-input>
        </div>
      </el-col>
    </el-row>
    <el-table
      :data="user_list"
      stripe
      style="width: 100%"
      class="dataTable"
    >
      <el-table-column
        prop="account"
        label="账号"
        align="center">
      </el-table-column>
      <el-table-column
        prop="age"
        label="年龄"
        sortable
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
        label="邮箱"
        prop="email"
        align="center">
      </el-table-column>
      <el-table-column
        prop="gender"
        label="性别"
        align="center">
        <template slot-scope="scope">
          <p v-if="scope.row.gender===0">男</p>
          <p v-if="scope.row.gender===1">女</p>
        </template>
      </el-table-column>
      <el-table-column
        label="级别"
        prop="grade"
        align="center">
        <template slot-scope="scope">
          <p v-if="scope.row.grade===0">普通员工</p>
          <p v-if="scope.row.grade===1">部门经理</p>
          <p v-if="scope.row.grade===2">总经理</p>
          <p v-if="scope.row.grade===3">人事部</p>
        </template>
      </el-table-column>
      <el-table-column
        label="编号"
        prop="id"
        align="center">
      </el-table-column>
      <el-table-column
        label="姓名"
        prop="name"
        align="center">
      </el-table-column>
      <el-table-column
        label="密码"
        prop="password"
        align="center">
      </el-table-column>
      <el-table-column
        label="联系方式"
        prop="telephone"
        align="center">
      </el-table-column>
      <el-table-column
        label="剩余假期"
        prop="timeRemaining"
        align="center">
      </el-table-column>
      <el-table-column label="操作" align="center" min-width="100">
        　　　　<template slot-scope="scope">
        　　　　　　<el-button type="info" @click="click_update(scope.$index)">修改</el-button>
        　　　　　　<el-button type="info" style="margin: 0" @click="click_delete(scope.$index)">删除</el-button>
        　　　　</template>
      </el-table-column>
    </el-table>
    <el-pagination
      :current-page="pageNum"
      :page-sizes="[10, 20, 30]"
      :page-size="pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      @size-change="sizeChange"
      @current-change="currentChange"
      :total="total">
    </el-pagination>
    <el-dialog
      title="用户信息修改"
      :visible.sync="dialogFormVisible"
      :before-close="handleDialogClose">
      <el-form
        :model="infoForm"
        label-position="right"
        label-width="140px">
        <el-form-item
          label="账号">
          <el-input v-model="infoForm.account"
                    type="text"
                         size="small">
          </el-input>
        </el-form-item>
        <el-form-item
          label="年龄">
          <el-input-number
            v-model="infoForm.age"
            :min="1"
            size="small">
          </el-input-number>
        </el-form-item>
        <el-form-item
          label="部门id">
          <el-input-number
            v-model="infoForm.did"
            size="small">
          </el-input-number>
        </el-form-item>
        <el-form-item
          label="部门名称">
          <el-input
            type="text"
            v-model="infoForm.dname"
            size="small">
          </el-input>
        </el-form-item>
        <el-form-item
          label="邮箱">
          <el-input
            type="text"
            v-model="infoForm.email"
            size="small">
          </el-input>
        </el-form-item>
        <el-form-item
          label="性别">
          <el-select
            v-model="infoForm.gender"
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
          label="级别">
          <el-select
            v-model="infoForm.grade"
            size="small">
            <el-option
              v-for="item in level"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item
          label="编号">
          <el-input
            type="number"
            v-model="infoForm.id"
            size="small">
          </el-input>
        </el-form-item>
        <el-form-item
          label="姓名">
          <el-input
            type="text"
            v-model="infoForm.name"
            size="small">
          </el-input>
        </el-form-item>
        <el-form-item
          label="密码">
          <el-input
            type="text"
            v-model="infoForm.password"
            size="small">
          </el-input>
        </el-form-item>
        <el-form-item
          label="联系方式">
          <el-input
            type="text"
            v-model="infoForm.telephone"
            size="small">
          </el-input>
        </el-form-item>
        <el-form-item
          label="剩余假期">
          <el-input
            type="number"
            v-model="infoForm.timeRemaining"
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
      title="删除用户"
      :visible.sync="deleteDialogVisible">
      <div
        slot="footer"
        class="dialog-footer">
        <el-button
          @click="deleteDialogClose"
          size="small">
          取 消
        </el-button>
        <el-button
          type="primary"
          @click="delete_user"
          size="small">
          确认删除用户{{delete_user_id}}
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
        options:[{
          value:0,
          label:'男'
        },{
          value:1,
          label:'女'
        }],
        level:[{
          value:0,
          label:'普通员工'
        },{
          value:1,
          label:'部门经理'
        },{
          value:2,
          label:'总经理'
        },{
          value:3,
          label:'人事部'
        }],
        total:100,
        pageNum:1,
        pageSize:10,
        delete_user_id:0,
        deleteDialogVisible:false,
        infoForm: {
          account:'',
          age:0,
          did:0,
          dname:'',
          email:'',
          gender:0,
          grade:0,
          id:0,
          name:'',
          password:'',
          telephone:'',
          timeRemaining:0
        },
        dialogFormVisible: false,
        user_list:[]
      }
    },
    created() {
      axios.request({
        method:"GET",
        url:'http://localhost:9999/user/list',
        params:{'pageNum':1,'pageSize':10000},
      }).then(res=>{
        this.total=res.data.list.length;
      });
      axios.request({
        method:"GET",
        url:'http://localhost:9999/user/list',
        params:{'pageNum':this.pageNum,'pageSize':this.pageSize},
      }).then(res=>{
        this.user_list=res.data.list;
      });
    },
    methods: {
      //输入框内输入姓名，1.回车 2.点击右侧的搜索图标
      // 可以获得输入框输入的内容。将值传给后台，调用接口即可。
      sizeChange(item){
        this.pageSize=item
        axios.request({
          method:"GET",
          url:'http://localhost:9999/user/list',
          params:{'pageNum':this.pageNum,'pageSize':this.pageSize},
        }).then(res=>{
          this.user_list=res.data.list;
        });
      },
      currentChange(item){
        this.pageNum=item
        axios.request({
          method:"GET",
          url:'http://localhost:9999/user/list',
          params:{'pageNum':this.pageNum,'pageSize':this.pageSize},
        }).then(res=>{
          this.user_list=res.data.list;
        });
      },
      deleteDialogClose(){
        this.deleteDialogVisible=false;
      },
      delete_user(){
        axios.request({
          method:'delete',
          url:'http://localhost:9999/user/delete/'+this.delete_user_id
        }).then(res=>{
          console.log(res.data)
        })
      },
      click_delete(index){
        this.delete_user_id=this.user_list[index].id;
        this.deleteDialogVisible=true;
      },
      submitForm(){
        axios.request({
          method:'put',
          url:'http://localhost:9999/user/update',
          data:this.infoForm
        }).then(res=>{
          console.log(res.data)
        });
        this.handleDialogClose()
      },
      click_update(index){
        this.dialogFormVisible=true;
        this.infoForm=this.user_list[index]
      },
      searchUserName(item) {
        axios.request({
          method:'get',
          url:'http://localhost:9999/user/'+item+'/selectOne'
        }).then(res=>{
          if(res.data===''){
            this.user_list=[]
          }else{
            this.user_list=[res.data]
          }
        })
      },
      handleDialogClose() {
        this.infoForm={
          account:'',
            age:0,
            did:0,
            dname:'',
            email:'',
            gender:0,
            grade:0,
            id:0,
            name:'',
            password:'',
            telephone:'',
            timeRemaining:0
        };
        this.dialogFormVisible = false;
      },
      //处理复选框状态修改
      handleSelectionChange(item) {
        console.log(item);
        this.selectionItem = item

      },
      //要跳转的页

    }
  }
</script>

<style lang="css">
  .dataTable {
    margin: 40px auto;
  }
</style>
