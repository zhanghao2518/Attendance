<template lang="html">
    <div class="">
        <el-row :gutter="10">
            <el-col :span="4">
                <div class="">
                    <el-input
                        placeholder="请输入姓名"
                        suffix-icon="el-icon-search"
                        v-model="userName"
                        size="small"
                        @change="searchUserName">
                    </el-input>
                </div>
            </el-col>
            <el-col :span="17">
                <div class="">
                    <el-date-picker
                        v-model="userDateRange"
                        type="daterange"
                        align="right"
                        unlink-panels
                        range-separator="~"
                        start-placeholder="开始日期"
                        end-placeholder="结束日期"
                        format="yyyy-MM-dd"
                        value-format="yyyy-MM-dd"
                        @change="searchUserDate"
                        size="small"
                        :picker-options="pickerOptions">
                    </el-date-picker>
                </div>
            </el-col>
            <el-col :span="3">
                <div class="">
                    <el-button
                        type="primary"
                        icon="el-icon-bell"
                        size="small"
                        @click="infoNotify"
                        :disabled="selectionItem.length === 0? true: false">
                        发送提醒
                    </el-button>
                </div>
            </el-col>
        </el-row>
        <el-table
            :data="tableData"
            stripe
            style="width: 100%"
            class="dataTable"
            @filter-change="handleFilterChange"
            @selection-change="handleSelectionChange"
            :default-sort = "{prop: 'attendance_date', order: 'descending'}"
            >
            <el-table-column
                type="selection"
                :selectable="selectedRow">
            </el-table-column>
            <el-table-column
                prop="user_name"
                label="姓名"
                align="center">
            </el-table-column>
            <el-table-column
                prop="attendance_date"
                label="打卡日期"
                sortable
                align="center">
            </el-table-column>
            <el-table-column
                label="打卡记录"
                align="center">
                <template slot-scope="scope">
                    <div :class="{red: (scope.row.status === '异常'), blue: ((scope.row.status === '补打卡') || (scope.row.status === '请假')) }">
                        <span>{{scope.row.updated_start_time}}</span>
                        <span>--</span>
                        <span>{{scope.row.updated_end_time}}</span>
                    </div>
                </template>
            </el-table-column>
            <el-table-column
                label="工作时长(h)"
                align="center">
                <template slot-scope="scope">
                    <span :class="{red:(scope.row.status === '异常'), blue: ((scope.row.status === '请假') || (scope.row.status === '补打卡')) }">
                        {{((scope.row.updated_duration)/3600).toFixed(1)}}
                    </span>
                </template>
            </el-table-column>
            <el-table-column
                label="状态"
                prop="status"
                column-key="status"
                :filters="tableStatus"
                align="center">
                <template slot-scope="scope">
                    <span :class="{red: scope.row.status === '异常', blue: ((scope.row.status === '补打卡') || (scope.row.status === '请假'))}">
                        {{scope.row.status}}
                    </span>
                </template>
            </el-table-column>
            <el-table-column
                label="操作"
                align="center"
                fixed="right">
                <template slot-scope="scope">
                    <el-button-group>
                        <el-button
                            icon="el-icon-edit"
                            size="small"
                            @click="openEditDialog(scope.row)">
                        </el-button>
                        <el-button
                            icon="el-icon-more"
                            size="small"
                            @click="openDisplayInfo(scope.row)">
                        </el-button>
                    </el-button-group>
                </template>
            </el-table-column>
        </el-table>
        <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="currentPage"
            :page-sizes="[10, 20, 30, 40]"
            :page-size="10"
            layout="total, sizes, prev, pager, next, jumper"
            :total="20">
        </el-pagination>
        <el-dialog
            title="状态更改"
            :visible.sync="editDialogVisible"
            width="50%"
            :before-close="editHandleClose">
            <el-row
                :gutter="20"
                class='statusEdit'>
                <el-col :span="4" :offset="3">
                    <div >
                        原始状态
                    </div>
                </el-col>
                <el-col :span="16">
                    <div class="">
                        {{oldStatus}}
                    </div>
                </el-col>
            </el-row>
            <el-row
                :gutter="20"
                class='statusEdit'>
                <el-col :span="4" :offset="3">
                    <div class="editStatusDistance">
                        更改状态
                    </div>
                </el-col>
                <el-col :span="16">
                    <el-select
                        v-model="newStatus"
                        placeholder="请选择"
                        size="small"
                        class="changeStatus">
                        <el-option
                          v-for="item in statusOptions"
                          :key="item.value"
                          :label="item.label"
                          :value="item.value">
                        </el-option>
                    </el-select>
                </el-col>
            </el-row>
            <el-row
                :gutter="20"
                class='statusEdit'
                v-if="newStatus === '补打卡'">
                <el-col :span="4" :offset="3">
                    <div class="editStatusDistance">
                        打卡时间
                    </div>
                </el-col>
                <el-col :span="16">
                    <el-time-picker
                        is-range
                        v-model="patchTime"
                        range-separator="～"
                        start-placeholder="开始时间"
                        end-placeholder="结束时间"
                        placeholder="选择打卡时间"
                        format="HH:mm"
                        value-format="HH:mm"
                        size="small"
                        @change="handlePatchTime">
                    </el-time-picker>
                </el-col>
            </el-row>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editHandleClose">取 消</el-button>
                <el-button type="primary" @click="submitEditDialog">确 定</el-button>
            </span>
        </el-dialog>
        <el-dialog
            :title="userAttendanceName + ' 出勤详情'"
            :visible.sync="displayDialogVisible"
            width="50%"
            :before-close="displayHandleClose">
            <el-table
                :data="displayTableData"
                stripe
                class="displayTable"
                style="width: 100%">
                <el-table-column
                    prop="attendance_date"
                    label="日期"
                    align="center">
                </el-table-column>
                <el-table-column
                    label="打卡记录"
                    align="center">
                    <template slot-scope="scope">
                        <span>{{scope.row.updated_start_time}}</span>
                        <span>--</span>
                        <span>{{scope.row.updated_end_time}}</span>
                    </template>
                </el-table-column>
                <el-table-column
                    label="工作时长"
                    align="center">
                        <span slot-scope="scope">{{(scope.row.updated_duration/3600).toFixed(1)}}</span>
                </el-table-column>
            </el-table>
            <el-pagination
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                :current-page="displaycurrentPage"
                :page-sizes="[5, 10, 20]"
                :page-size="10"
                layout="total, sizes, prev, pager, next, jumper"
                :total="40">
            </el-pagination>
        </el-dialog>
    </div>
</template>

<script>
export default {
    data() {
        return {
            //用户名
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
            displayTableData: [{
                "attendance_date": "2018-05-30",
                "create_time": 1527647626,
                "email": "xiaotao.lei@ihandysoft.com",
                "id": "xiaotao.lei@ihandysoft.com2018-05-30",
                "is_changed": true,
                "original_duration": 45972,
                "status": "normal",
                "update_time": 1527665339,
                "updated_duration": 45972,
                "updated_end_time": "21:21",
                "updated_start_time": "07:34",
                "workday": 0
                }],
            //模拟表格数据
            tableData: [
                {
                "attendance_date": "2018-05-14",
                "create_time": 1527561778,
                "division": "030",
                "email": "yuqing.han@ihandysoft.com",
                "id": "yuqing.han@ihandysoft.com2018-05-28",
                "is_changed": false,
                "original_duration": 0,
                "status": "\u5f02\u5e38",
                "update_time": 1527561778,
                "updated_duration": 0,
                "updated_end_time": "",
                "updated_start_time": "",
                "user_name": "\u97e9\u96e8\u6674",
                "workday": 1
                }]

        }
    },
    methods: {
        //输入框内输入姓名，1.回车 2.点击右侧的搜索图标
        // 可以获得输入框输入的内容。将值传给后台，调用接口即可。
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
        //要跳转的页
        handleCurrentChange(item) {
            if (item === 2) {
                this.tableData = [
                    {
                    "attendance_date": "2018-05-09",
                    "create_time": 1527561778,
                    "division": "085",
                    "email": "ruyan.guo@ihandysoft.com",
                    "id": "ruyan.guo@ihandysoft.com2018-05-28",
                    "is_changed": false,
                    "original_duration": 0,
                    "status": "\u6b63\u5e38",
                    "update_time": 1527561778,
                    "updated_duration": 0,
                    "updated_end_time": "",
                    "updated_start_time": "",
                    "user_name": "\u90ed\u5982\u8273",
                    "workday": 1
                }];
            }
            console.log(item);
            console.log('跳转到指定页');
        },
        //处理每页显示数量变化的函数
        handleSizeChange(item) {
            console.log(item);
            console.log('每页的数量变化');
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
