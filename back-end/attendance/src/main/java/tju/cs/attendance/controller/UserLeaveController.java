package tju.cs.attendance.controller;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import tju.cs.attendance.entity.User;
import tju.cs.attendance.entity.UserLeave;
import tju.cs.attendance.exception.Exceptions;
import tju.cs.attendance.service.UserLeaveService;
import tju.cs.attendance.service.UserService;

import javax.annotation.Resource;

/**
 * (UserLeave)表控制层
 *
 * @author zhanghao
 * @since 2020-12-02 20:17:57
 */
@RestController
@RequestMapping("userLeave")
@Api(value = "userLeave",description = "请假管理")
public class UserLeaveController {
    /**
     * 服务对象
     */
    @Resource
    private UserLeaveService userLeaveService;

    @Resource
    private UserService userService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @ApiOperation(value = "查询请假信息")
    @GetMapping("/{id}/selectOne")
    public ResponseEntity<UserLeave> selectOne(
            @ApiParam(value = "请假信息ID") @PathVariable(value = "id") Integer id
    ) {
        if(StringUtils.isEmpty(id)){
            throw new Exceptions.DataValidationFailedException("请假信息ID不能为空");
        }
        return ResponseEntity.ok(this.userLeaveService.queryById(id));
    }

    @ApiOperation(value = "查询请假列表")
    @GetMapping("/list")
    public ResponseEntity<PageInfo<UserLeave>> queryAll(
            @ApiParam(value = "员工id") @RequestParam(value = "id",required = false) Integer id,
            @ApiParam(value = "部门名") @RequestParam(value = "dname",required = false) String dname,
            @ApiParam(value = "姓名") @RequestParam(value = "name",required = false) String name,
            @ApiParam(value = "结果") @RequestParam(value = "type",required = false) Integer type,
            @ApiParam(value = "月份") @RequestParam(value = "date",required = false) String date,
            @ApiParam(value = "页数") @RequestParam(value = "pageNum") Integer pageNum,
            @ApiParam(value = "页大小") @RequestParam(value = "pageSize") Integer pageSize
    ){
        return ResponseEntity.ok(this.userLeaveService.queryAll(id,dname,name,type,date,pageNum,pageSize));
    }

    @PostMapping("add")
    @ApiOperation(value = "添加请假申请")
    public ResponseEntity<UserLeave> addUser(@ApiParam(value = "请假信息") @RequestBody UserLeave userLeave){
        return ResponseEntity.ok(userLeaveService.insert(userLeave));
    }

    @PutMapping("updateByDivision")
    @ApiOperation(value = "部门经理审批")
    public ResponseEntity updateByDivision(@ApiParam(value = "请假信息") @RequestBody UserLeave userLeave){
        UserLeave userLeave1 = this.userLeaveService.updateByDivision(userLeave);
        return userLeave1 == null ? ResponseEntity.ok(false) : ResponseEntity.ok(true);
    }

    @PutMapping("updateByManager")
    @ApiOperation(value = "总经理审批")
    public ResponseEntity updateByManager(@ApiParam(value = "请假信息") @RequestBody UserLeave userLeave){
        UserLeave userLeave1 = this.userLeaveService.updateByManager(userLeave);
        return userLeave1 == null ? ResponseEntity.ok(false) : ResponseEntity.ok(true);
    }

    @DeleteMapping("delete/{id}")
    @ApiOperation(value = "删除申请")
    public ResponseEntity deleteUser(@ApiParam(value = "请假信息id") @PathVariable(value = "id") Integer id){
        if(StringUtils.isEmpty(id)){
            throw new Exceptions.DataValidationFailedException("请假信息ID不能为空");
        }
        return ResponseEntity.ok(userLeaveService.deleteById(id));
    }

    @GetMapping("queryVacation/{id}")
    @ApiOperation(value = "查询假期")
    public Integer queryVacation(@ApiParam(value = "用户id") @PathVariable(value = "id") Integer id){
        return userService.queryById(id).getTimeRemaining();
    }

}