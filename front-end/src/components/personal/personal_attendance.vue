<template lang="html">
    <div class="">
        <el-table
            :data="tableData"
            stripe
            style="width: 100%"
            class="dataTable"
            >
            <el-table-column
                label="上班时间"
                prop="startTime"
                align="center">
            </el-table-column>
            <el-table-column
                label="下班时间"
                prop="endTime"
                align="center"
                fixed="right">
            </el-table-column>
          <el-table-column
            label="缺勤时长"
            prop="lengthAbsence"
            align="center"
            fixed="right">
          </el-table-column>
        </el-table>
        <el-pagination
            @size-change="sizeChange"
            @current-change="currentChange"
            :current-page="pageNum"
            :page-sizes="[10, 20, 30, 40]"
            :page-size="pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total">
        </el-pagination>
    </div>
</template>

<script>
  import axios from 'axios'
export default {
    data() {
        return {
            //用户名
            args:{},
            total:0,
            pageNum:1,
            pageSize:10,
            userName: '',
            //日期
            userDateRange: [],
            //信息被选中的列表
            selectionItem: [],
            //
            tableStatus: [{ text: '异常', value: '异常' },{ text: '正常', value: '正常' },{ text: '请假', value: '请假' },{ text: '打卡', value: '打卡' }],
            //默认页
            currentPage: 1,
            //编辑对话框的展示与退出
            editDialogVisible: false,
            //原始状态
            oldStatus: '',
            // 新状态
            newStatus: '补打卡',
            // 确定修改哪行
            editId: '',
            //状态选择器内备选项
            statusOptions: [{
                value: '补打卡',
                label: '补打卡'
            },{
                value: '请假',
                label: '请假'
            }],
            //出勤详情的名字
            userAttendanceName: '',
            //个人出勤详情对话框
            displayDialogVisible: false,
            //展示个人出勤详情的当前页
            displaycurrentPage: 1,
            //打卡时间初始值
            patchTime: ["08:00","08:00"],
            //日历选择器的快捷选项
            pickerOptions: {
                shortcuts: [{
                text: '最近一周',
                onClick(picker) {
                  const end = new Date();
                  const start = new Date();
                  start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
                  picker.$emit('pick', [start, end]);
                }
                }, {
                text: '最近一个月',
                onClick(picker) {
                  const end = new Date();
                  const start = new Date();
                  start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
                  picker.$emit('pick', [start, end]);
                }
                }, {
                text: '最近三个月',
                onClick(picker) {
                  const end = new Date();
                  const start = new Date();
                  start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
                  picker.$emit('pick', [start, end]);
                }
                }]
            },
            //个人出勤详情的数据
            displayTableData: [],
            //模拟表格数据
            tableData: []

        }
    },
    created() {
      this.args={'id':this.$root.user.id,'name':this.$root.user.name,'dname':this.$root.user.dname,'pageNum':1,'pageSize':10000}
      axios.request({
        method:'get',
        url:'http://localhost:9999/userAttendance/list',
        params:this.args
      }).then(res=>{
        this.total=res.data.list.length
      })
      this.args.pageNum=this.pageNum
      this.args.pageSize=this.pageSize
      axios.request({
        method:'get',
        url:'http://localhost:9999/userAttendance/list',
        params:this.args
      }).then(res=>{
        this.tableData=res.data.list
      })
    },
  methods: {
        //输入框内输入姓名，1.回车 2.点击右侧的搜索图标
        // 可以获得输入框输入的内容。将值传给后台，调用接口即可。
        currentChange(item){
          this.args.pageNum=item
          axios.request({
            method:'get',
            url:'http://localhost:9999/userAttendance/list',
            params:this.args
          }).then(res=>{
            this.tableData=res.data.list
          })
        },
        sizeChange(item){
          this.args.pageSize=item
          axios.request({
            method:'get',
            url:'http://localhost:9999/userAttendance/list',
            params:this.args
          }).then(res=>{
            this.tableData=res.data.list
          })
        },
        searchUserName(item) {
            console.log(item);
            console.log('筛选姓名');
        },
        //日历选择器上选择好日期，即可获得此时的输入内容
        searchUserDate(item) {
            console.log(item);
            console.log('筛选日期');
        },
        //如果在下侧的表格中选中了数据，则点击此按钮，可以将选中的信息发给后台
        infoNotify() {
            console.log('发送提醒');
        },
        //处理筛选条件变化，向后台发送数据，重新获取信息就可以
        handleFilterChange(filters) {
            console.log(filters);
            console.log('筛选条件变化');
        },
        //处理复选框状态修改
        handleSelectionChange(item) {
            console.log(item);
            this.selectionItem = item

        },
        //点击编辑按钮后，打开编辑对话框
        openEditDialog(item) {
            console.log(item);
            this.editDialogVisible = true;
            this.oldStatus = item.status;
            this.editId = item.id;
            console.log('打开编辑对话框');
        },
        //关闭对话框之前，先清空数据，最后关闭对话框
        editHandleClose() {
            this.oldStatus = '';
            this.newStatus = '补打卡';
            this.patchTime = ["08:00","08:00"];
            this.editDialogVisible = false;
            console.log('退出编辑对话框');
        },
        //修改确定时
        submitEditDialog() {
            for (var i = 0; i < this.tableData.length; i++) {
                if (this.editId === this.tableData[i].id) {
                    console.log(this.patchTime);
                    this.tableData[i].updated_start_time = this.patchTime[0];
                    this.tableData[i].updated_end_time = this.patchTime[1];
                    this.tableData[i].status = this.newStatus;
                }
            }
            this.editHandleClose();
            console.log("");
        },
        //当打卡时间变化时，触发的事件
        handlePatchTime(item) {
           console.log(item);
       },
       //只有状态为异常时，复选框才可以勾选
       selectedRow(row,index) {
           // console.log(index);
           return (row.status === '异常');
       },
       //展示个人的出勤详情
       openDisplayInfo(item) {
           this.userAttendanceName = item.user_name;
           this.displayDialogVisible = true;
       },
       //关闭个人出勤详情页面
       displayHandleClose() {
           this.userAttendanceName = '';
           this.displaycurrentPage = 1;
           this.displayDialogVisible = false;
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
