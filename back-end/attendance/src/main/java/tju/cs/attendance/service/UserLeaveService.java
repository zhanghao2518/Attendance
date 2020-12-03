package tju.cs.attendance.service;

import com.github.pagehelper.PageInfo;
import tju.cs.attendance.entity.User;
import tju.cs.attendance.entity.UserLeave;

import java.util.List;

/**
 * (UserLeave)表服务接口
 *
 * @author zhanghao
 * @since 2020-12-02 20:17:56
 */
public interface UserLeaveService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    UserLeave queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<UserLeave> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param userLeave 实例对象
     * @return 实例对象
     */
    UserLeave insert(UserLeave userLeave);

    /**
     * 修改数据
     *
     * @param userLeave 实例对象
     * @return 实例对象
     */
    UserLeave updateByDivision(UserLeave userLeave);

    UserLeave updateByManager(UserLeave userLeave);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    PageInfo<UserLeave> queryAll(Integer uid, String dname,String name,Integer type,String date ,Integer pageNum, Integer pageSize);

}