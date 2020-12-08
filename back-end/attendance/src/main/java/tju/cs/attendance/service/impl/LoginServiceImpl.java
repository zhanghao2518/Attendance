package tju.cs.attendance.service.impl;

import tju.cs.attendance.dao.UserDao;
import tju.cs.attendance.entity.User;
import tju.cs.attendance.exception.Exceptions;
import tju.cs.attendance.service.LoginService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * login服务实现类
 *
 * @author wuhaiying
 * @since 2020-12-02 18:35:06
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    private UserDao userDao;

    public User login(String account,String password){
        User user = new User();
        user.setAccount(account);
        List<User> users = userDao.queryAll(user);
        if(users == null || "".equals(users.get(0).getPassword()) || !password.equals(users.get(0).getPassword())){
            throw new Exceptions.DataValidationFailedException("账号或者密码不正确");
        }
        users.get(0).setPassword("");
        return users.get(0);
    }
}
