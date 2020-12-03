package tju.cs.attendance.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tju.cs.attendance.dao.DepartmentDao;
import tju.cs.attendance.dao.UserDao;
import tju.cs.attendance.dao.UserLeaveDao;
import tju.cs.attendance.entity.Department;
import tju.cs.attendance.entity.User;
import tju.cs.attendance.entity.UserLeave;
import tju.cs.attendance.service.UserLeaveService;

import javax.annotation.Resource;
import java.util.List;

/**
 * (UserLeave)表服务实现类
 *
 * @author zhanghao
 * @since 2020-12-02 20:17:56
 */
@Service("userLeaveService")
public class UserLeaveServiceImpl implements UserLeaveService {
    @Resource
    private UserLeaveDao userLeaveDao;

    @Resource
    private DepartmentDao departmentDao;

    @Resource
    private UserDao userDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public UserLeave queryById(Integer id) {
        return this.userLeaveDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<UserLeave> queryAllByLimit(int offset, int limit) {
        return this.userLeaveDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param userLeave 实例对象
     * @return 实例对象
     */
    @Override
    public UserLeave insert(UserLeave userLeave) {
        Department department = new Department();
        department.setName(userLeave.getDname());
        userLeave.setDid(departmentDao.queryAll(department).get(0).getId());
        if(userLeave.getType() == 1 && Integer.parseInt(userLeave.getEndTime().substring(8)) - Integer.parseInt(userLeave.getStartTime().substring(8)) + 1 > userDao.queryById(userLeave.getUid()).getTimeRemaining()){
            userLeave.setResult(0);
            userLeave.setAdviceByManager("年假余额不足");
        }else{
            userLeave.setResult(2);
            userLeave.setLengthLeave(Integer.parseInt(userLeave.getEndTime().substring(8)) - Integer.parseInt(userLeave.getStartTime().substring(8)) + 1);
        }
        this.userLeaveDao.insert(userLeave);
        return this.userLeaveDao.queryAll(userLeave).get(0);
    }

    /**
     * 修改数据
     *
     * @param userLeave 实例对象
     * @return 实例对象
     */
    @Override
    public UserLeave updateByDivision(UserLeave userLeave) {
        if(userLeave.getReviewByDivisionManager() == 0){
            userLeave.setResult(0);
        }else if(userLeave.getLengthLeave() <= 3){
            userLeave.setResult(1);
            User user = userDao.queryById(userLeave.getUid());
            user.setTimeRemaining(user.getTimeRemaining() - userLeave.getLengthLeave());
            userDao.update(user);
        }
        this.userLeaveDao.update(userLeave);
        return this.queryById(userLeave.getId());
    }

    @Override
    public UserLeave updateByManager(UserLeave userLeave){
        if(userLeave.getReviewByManager() == 0){
            userLeave.setResult(0);
        }else{
            userLeave.setResult(1);
        }
        this.userLeaveDao.update(userLeave);
        return this.queryById(userLeave.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.userLeaveDao.deleteById(id) > 0;
    }

    @Override
    public PageInfo<UserLeave> queryAll(Integer uid,String dname, String name,Integer type ,String date,Integer pageNum, Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        UserLeave userLeave = new UserLeave();
        if(!StringUtils.isEmpty(uid)){
            userLeave.setUid(uid);
        }
        if(!StringUtils.isEmpty(dname)){
            userLeave.setDname(dname);
        }
        if(!StringUtils.isEmpty(name)){
            userLeave.setUname(name);
        }
        if(!StringUtils.isEmpty(type)){
            userLeave.setResult(type);
        }
        List<UserLeave> userLeaves = this.userLeaveDao.queryAll(userLeave);
        if(!StringUtils.isEmpty(date)){
            for(int i=0 ; i < userLeaves.size() ; i++){
                if(!userLeaves.get(i).getStartTime().substring(0,7).equals(date)){
                    userLeaves.remove(i);
                    i--;
                }
            }
        }
        PageInfo<UserLeave> userLeavePageInfo = new PageInfo<>(userLeaves);
        return userLeavePageInfo;
    }

}