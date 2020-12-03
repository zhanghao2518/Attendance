package tju.cs.attendance.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tju.cs.attendance.dao.UserAttendanceDao;
import tju.cs.attendance.dao.UserDao;
import tju.cs.attendance.entity.User;
import tju.cs.attendance.entity.UserAttendance;
import tju.cs.attendance.service.UserAttendanceService;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * (UserAttendance)表服务实现类
 *
 * @author zhanghao
 * @since 2020-12-02 21:56:55
 */
@Service("userAttendanceService")
public class UserAttendanceServiceImpl implements UserAttendanceService {
    @Resource
    private UserAttendanceDao userAttendanceDao;

    @Resource
    private UserDao userDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public UserAttendance queryById(Integer id) {
        return this.userAttendanceDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<UserAttendance> queryAllByLimit(int offset, int limit) {
        return this.userAttendanceDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param id 实例对象
     * @return 实例对象
     */
    @Override
    public UserAttendance insert(Integer id) {
        User user = userDao.queryById(id);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String time = dateFormat.format(calendar.getTime());//记录打卡时间
        UserAttendance userAttendance = new UserAttendance();
        userAttendance.setUid(user.getId());
        userAttendance.setUname(user.getName());
        userAttendance.setDid(userAttendance.getDid());
        userAttendance.setDname(userAttendance.getDname());
        userAttendance.setStartTime(time);
        this.userAttendanceDao.insert(userAttendance);
        return userAttendanceDao.queryAll(userAttendance).get(0);
    }

    /**
     * 修改数据
     *
     * @param userAttendance 实例对象
     * @return 实例对象
     */
    @Override
    public UserAttendance update(UserAttendance userAttendance) {
        this.userAttendanceDao.update(userAttendance);
        return this.queryById(userAttendance.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.userAttendanceDao.deleteById(id) > 0;
    }

    @Override
    public UserAttendance insertForEnd(Integer id){
        UserAttendance userAttendance = this.userAttendanceDao.queryById(id);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String time = dateFormat.format(calendar.getTime());//记录打卡时间
        userAttendance.setEndTime(time);
        String start = userAttendance.getStartTime();
        if(Integer.parseInt(time.substring(11,13))-Integer.parseInt(start.substring(11,13)) >= 8){
            userAttendance.setLengthAbsence(0);
        }else{
            userAttendance.setLengthAbsence(8-Integer.parseInt(time.substring(11,13))-Integer.parseInt(start.substring(11,13)));
        }
        this.userAttendanceDao.update(userAttendance);
        return userAttendance;
    }

    @Override
    public PageInfo<UserAttendance> queryAll(Integer uid, String dname,String name,String date ,Integer pageNum, Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        UserAttendance userAttendance = new UserAttendance();
        if(!StringUtils.isEmpty(uid)){
            userAttendance.setUid(uid);
        }
        if(!StringUtils.isEmpty(dname)){
            userAttendance.setDname(dname);
        }
        if(!StringUtils.isEmpty(name)){
            userAttendance.setUname(name);
        }
        List<UserAttendance> userAttendances = this.userAttendanceDao.queryAll(userAttendance);
        if(!StringUtils.isEmpty(date)){
            for(int i=0 ; i < userAttendances.size() ; i++){
                if(!userAttendances.get(i).getStartTime().substring(0,7).equals(date)){
                    userAttendances.remove(i);
                    i--;
                }
            }
        }
        PageInfo<UserAttendance> userAttendancePageInfo = new PageInfo<>(userAttendances);
        return userAttendancePageInfo;
    }

}