<template lang="html">
  <div class="">
    <el-row :gutter="10">
      <el-col :span="4">
        <div class="">
          <el-input
            placeholder="请输入姓名"
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
      :default-sort = "{prop: 'age', order: 'descending'}"
    >
      <el-table-column
        type="selection">
      </el-table-column>
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
        label="部门id"
        align="center">
      </el-table-column>
      <el-table-column
        label="部门名称"
        align="center">
      </el-table-column>
      <el-table-column
        label="邮箱"
        prop="email"
        align="center">
      </el-table-column>
      <el-table-column
        label="性别"
        align="center">
      </el-table-column>
      <el-table-column
        label="级别"
        prop="grade"
        align="center">
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
    </el-table>
    <el-pagination
      :page-sizes="[10, 20, 30]"
      :page-size="10"
      layout="total, sizes, prev, pager, next, jumper"
      :total="20">
    </el-pagination>
  </div>
</template>

<script>
  import axios from 'axios'
  export default {
    data() {
      let user_list=[];
      axios.request({
        method:"GET",
        url:'http://localhost:9999/user/list',
        params:{'dname':'我','name':'我','pageNum':1,'pageSize':1},
      }).then(res=>{
        user_list=res.data.list;
        console.log(res)
      });
      return {
        'user_list':user_list
      }
    },
    created() {

    },
    methods: {
      //输入框内输入姓名，1.回车 2.点击右侧的搜索图标
      // 可以获得输入框输入的内容。将值传给后台，调用接口即可。
      searchUserName(item) {
        console.log(item);
        console.log('筛选姓名');
      },

      //处理复选框状态修改
      handleSelectionChange(item) {
        console.log(item);
        this.selectionItem = item

      },
      //要跳转的页
      handleCurrentChange(item) {
        if (item === 2) {
          this.tableData = [];
        }
        console.log(item);
        console.log('跳转到指定页');
      },
      //处理每页显示数量变化的函数
      handleSizeChange(item) {
        console.log(item);
        console.log('每页的数量变化');
      },
      handleSizeChange() {
        console.log("改变了每页的个数");
      },
      handleCurrentChange() {
        console.log("跳转页数");
      }
    }
  }
</script>

<style lang="css">
  .dataTable {
    margin: 40px auto;
  }
  .red {
    color: red;
  }
  .blue {
    color: #409EFF;
  }
  .statusEdit {
    padding-bottom: 20px;
  }
  .changeStatus {
    width: 350px;
  }
  .editStatusDistance {
    padding-top: 7px;
  }
  .displayTable {
    margin-bottom: 40px;
  }
</style>
