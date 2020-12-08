package tju.cs.attendance.service;

import tju.cs.attendance.entity.User;

/**
 * login服务接口
 *
 * @author wuhaiying
 * @since 2020-12-02 18:35:06
 */
public interface LoginService {

    User login(String flowerName,String password);

}
