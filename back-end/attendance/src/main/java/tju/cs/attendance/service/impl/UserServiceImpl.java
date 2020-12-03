package tju.cs.attendance.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tju.cs.attendance.dao.DepartmentDao;
import tju.cs.attendance.dao.UserDao;
import tju.cs.attendance.entity.Department;
import tju.cs.attendance.entity.User;
import tju.cs.attendance.service.UserService;
import tju.cs.attendance.util.TestUtil;

import javax.annotation.Resource;
import javax.jws.soap.SOAPBinding;
import java.util.List;

/**
 * (User)表服务实现类
 *
 * @author zhanghao
 * @since 2020-12-02 15:38:14
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;

    @Resource
    private DepartmentDao departmentDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public User queryById(Integer id) {
        return this.userDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<User> queryAllByLimit(int offset, int limit) {
        return this.userDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    @Override
    public User insert(User user) {
        Department department = new Department();
        department.setName(user.getDname());
        user.setDid(departmentDao.queryAll(department).get(0).getId());
        user.setGrade(0);

        user.setAccount(TestUtil.produce(8));
        List<User> userFiler = this.userDao.queryAll(user);
        while(userFiler.size() != 0){
            user.setAccount(TestUtil.produce(8));
            userFiler = this.userDao.queryAll(user);
        }

        user.setPassword(TestUtil.produce(6));
        user.setTimeRemaining(0);
        this.userDao.insert(user);
        return this.userDao.queryAll(user).get(0);
    }

    /**
     * 修改数据
     *
     * @param userRequest 实例对象
     * @return 实例对象
     */
    @Override
    public User update(User userRequest) {
        User user = this.userDao.queryById(userRequest.getId());
        user.setTimeRemaining(StringUtils.isEmpty(userRequest.getTimeRemaining()) ? user.getTimeRemaining() : userRequest.getTimeRemaining());
        user.setDname(StringUtils.isEmpty(userRequest.getDname()) ? user.getDname() : userRequest.getDname());
        Department department = new Department();
        department.setName(userRequest.getDname());
        user.setDid(StringUtils.isEmpty(userRequest.getDname()) ? user.getDid() : departmentDao.queryAll(department).get(0).getId());
        user.setAccount(StringUtils.isEmpty(userRequest.getAccount()) ? user.getAccount() : userRequest.getAccount());
        user.setName(StringUtils.isEmpty(userRequest.getName()) ? user.getName() : userRequest.getName());
        user.setGender(StringUtils.isEmpty(userRequest.getGender()) ? user.getGender() : userRequest.getGender());
        user.setTelephone(StringUtils.isEmpty(userRequest.getTelephone()) ? user.getTelephone() : userRequest.getTelephone());
        user.setEmail(StringUtils.isEmpty(userRequest.getEmail()) ? user.getEmail() : userRequest.getEmail());
        user.setPassword(StringUtils.isEmpty(userRequest.getPassword()) ? user.getPassword() : userRequest.getPassword());
        user.setAge(StringUtils.isEmpty(userRequest.getAge()) ? user.getAge() : userRequest.getAge());
        user.setGrade(StringUtils.isEmpty(userRequest.getGrade()) ? user.getGrade() : userRequest.getGrade());

        this.userDao.update(user);
        return this.queryById(user.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.userDao.deleteById(id) > 0;
    }

    @Override
    public PageInfo<User> queryAll(String dname,String name,Integer pageNum, Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        User userFiler = new User();
        if(!StringUtils.isEmpty(dname)){
            userFiler.setDname(dname);
        }
        if(!StringUtils.isEmpty(name)){
            userFiler.setName(name);
        }
        List<User> users = this.userDao.queryAll(userFiler);
        for(User user : users){
            user.setPassword("*****");
        }
        PageInfo<User> userPageInfo = new PageInfo<>(users);
        return userPageInfo;
    }
}