package tju.cs.attendance.controller;

import tju.cs.attendance.entity.User;
import tju.cs.attendance.exception.Exceptions;
import tju.cs.attendance.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("login")
@Api(value = "login",description = "用户登陆")
public class LoginController {

    @Resource
    private LoginService loginService;

    @PostMapping
    @ApiOperation(value = "登陆")
    public ResponseEntity<User> login(
            @ApiParam(value = "账号") @RequestParam(value = "account") String account,
            @ApiParam(value = "密码") @RequestParam(value = "password") String password
    ){
        if(StringUtils.isEmpty(account)){
            throw new Exceptions.DataValidationFailedException("花名不能为空");
        }
        if(StringUtils.isEmpty(password)){
            throw new Exceptions.DataValidationFailedException("密码不能为空");
        }
        User user = this.loginService.login(account,password);
        if(user == null){
            throw new IllegalArgumentException("未查到相关用户");
        }
        return ResponseEntity.ok(user);
    }

}
