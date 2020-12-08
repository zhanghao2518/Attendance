package tju.cs.attendance.service;

import com.github.pagehelper.PageInfo;
import tju.cs.attendance.entity.UserAttendance;

import java.util.List;

/**
 * (UserAttendance)表服务接口
 *
 * @author weilinquan
 * @since 2020-12-02 21:56:55
 */
public interface UserAttendanceService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    UserAttendance queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<UserAttendance> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param id 实例对象
     * @return 实例对象
     */
    UserAttendance insert(Integer id);

    /**
     * 修改数据
     *
     * @param userAttendance 实例对象
     * @return 实例对象
     */
    UserAttendance update(UserAttendance userAttendance);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    UserAttendance insertForEnd(Integer id);

    PageInfo<UserAttendance> queryAll(Integer uid, String dname,String name,String date ,Integer pageNum, Integer pageSize);

}