package tju.cs.attendance.entity;

import java.io.Serializable;

/**
 * (Department)实体类
 *
 * @author zhanghao
 * @since 2020-12-02 18:35:06
 */
public class Department implements Serializable {
    private static final long serialVersionUID = 945256835678850993L;
    /**
     * 编号
     */
    private Integer id;
    /**
     * 部门名称
     */
    private String name;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}