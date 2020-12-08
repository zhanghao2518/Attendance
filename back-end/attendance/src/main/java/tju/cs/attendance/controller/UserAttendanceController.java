package tju.cs.attendance.controller;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import tju.cs.attendance.entity.UserAttendance;
import tju.cs.attendance.entity.UserLeave;
import tju.cs.attendance.exception.Exceptions;
import tju.cs.attendance.service.UserAttendanceService;

import javax.annotation.Resource;

/**
 * (UserAttendance)表控制层
 *
 * @author weilinquan
 * @since 2020-12-02 21:56:55
 */
@RestController
@RequestMapping("userAttendance")
@Api(value = "userAttendance",description = "考勤管理")
public class UserAttendanceController {
    /**
     * 服务对象
     */
    @Resource
    private UserAttendanceService userAttendanceService;

    @PostMapping("insert/{id}")
    @ApiOperation(value = "上班打卡")
    public ResponseEntity<UserAttendance> insert(@ApiParam(value = "用户id") @PathVariable(value = "id") Integer id){
        return ResponseEntity.ok(this.userAttendanceService.insert(id));
    }

    @PostMapping("insertForEnd/{id}")
    @ApiOperation(value = "下班打卡")
    public ResponseEntity<UserAttendance> insertForEnd(@ApiParam(value = "用户id") @PathVariable(value = "id") Integer id){
        return ResponseEntity.ok(this.userAttendanceService.insertForEnd(id));
    }

    @ApiOperation(value = "查询考勤列表")
    @GetMapping("/list")
    public ResponseEntity<PageInfo<UserAttendance>> queryAll(
            @ApiParam(value = "员工id") @RequestParam(value = "id",required = false) Integer id,
            @ApiParam(value = "部门名") @RequestParam(value = "dname",required = false) String dname,
            @ApiParam(value = "姓名") @RequestParam(value = "name",required = false) String name,
            @ApiParam(value = "月份") @RequestParam(value = "date",required = false) String date,
            @ApiParam(value = "页数") @RequestParam(value = "pageNum") Integer pageNum,
            @ApiParam(value = "页大小") @RequestParam(value = "pageSize") Integer pageSize
    ){
        return ResponseEntity.ok(this.userAttendanceService.queryAll(id,dname,name,date,pageNum,pageSize));
    }

    @DeleteMapping("delete/{id}")
    @ApiOperation(value = "删除申请")
    public ResponseEntity deleteUser(@ApiParam(value = "请假信息id") @PathVariable(value = "id") Integer id){
        if(StringUtils.isEmpty(id)){
            throw new Exceptions.DataValidationFailedException("请假信息ID不能为空");
        }
        return ResponseEntity.ok(userAttendanceService.deleteById(id));
    }

}