package tju.cs.attendance.controller;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import tju.cs.attendance.entity.User;
import tju.cs.attendance.exception.Exceptions;
import tju.cs.attendance.service.UserService;

import javax.annotation.Resource;

/**
 * (User)表控制层
 *
 * @author zhanghao
 * @since 2020-12-02 15:38:15
 */
@RestController
@RequestMapping("user")
@Api(value = "user",description = "用户管理")
public class UserController {
    /**
     * 服务对象
     */
    @Resource
    private UserService userService;

    @ApiOperation(value = "查询用户信息")
    @GetMapping("/{id}/selectOne")
    public ResponseEntity<User> selectOne(
            @ApiParam(value = "用户ID") @PathVariable(value = "id") Integer id
    ) {
        if(StringUtils.isEmpty(id)){
            throw new Exceptions.DataValidationFailedException("用户ID不能为空");
        }
        return ResponseEntity.ok(this.userService.queryById(id));
    }

    @ApiOperation(value = "查询用户列表")
    @GetMapping("/list")
    public ResponseEntity<PageInfo<User>> queryAll(
            @ApiParam(value = "部门名") @RequestParam(value = "dname",required = false) String dname,
            @ApiParam(value = "姓名") @RequestParam(value = "name",required = false) String name,
            @ApiParam(value = "页数") @RequestParam(value = "pageNum") Integer pageNum,
            @ApiParam(value = "页大小") @RequestParam(value = "pageSize") Integer pageSize
    ){
        return ResponseEntity.ok(this.userService.queryAll(dname,name,pageNum,pageSize));
    }

    @PostMapping("add")
    @ApiOperation(value = "添加用户")
    public ResponseEntity<User> addUser(@ApiParam(value = "用户信息") @RequestBody User user){
        return ResponseEntity.ok(userService.insert(user));
    }

    @PutMapping("update")
    @ApiOperation(value = "修改用户信息")
    public ResponseEntity updateUser(@ApiParam(value = "用户信息") @RequestBody User user){
        User user1 = userService.update(user);
        return user1 == null ? ResponseEntity.ok(false) : ResponseEntity.ok(true);
    }

    @DeleteMapping("delete/{id}")
    @ApiOperation(value = "删除用户")
    public ResponseEntity deleteUser(@ApiParam(value = "用户id") @PathVariable(value = "id") Integer id){
        if(StringUtils.isEmpty(id)){
            throw new Exceptions.DataValidationFailedException("用户ID不能为空");
        }
        return ResponseEntity.ok(userService.deleteById(id));
    }

//    @GetMapping("queryVacation/{id}")
//    @ApiOperation(value = "查询假期")
//    public Integer queryVacation(@ApiParam(value = "用户id") @PathVariable(value = "id") Integer id){
//        return userService.queryById(id).getTimeRemaining();
//    }

}