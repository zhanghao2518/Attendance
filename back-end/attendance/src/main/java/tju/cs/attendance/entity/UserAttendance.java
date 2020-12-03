package tju.cs.attendance.entity;

import java.io.Serializable;

/**
 * (UserAttendance)实体类
 *
 * @author zhanghao
 * @since 2020-12-02 21:56:55
 */
public class UserAttendance implements Serializable {
    private static final long serialVersionUID = 232958364227683322L;
    /**
     * 编号
     */
    private Integer id;
    /**
     * 员工id
     */
    private Integer uid;
    /**
     * 员工名
     */
    private String uname;
    /**
     * 部门id
     */
    private Integer did;
    /**
     * 部门名
     */
    private String dname;
    /**
     * 上班时间
     */
    private String startTime;
    /**
     * 下班时间
     */
    private String endTime;
    /**
     * 缺勤时间
     */
    private Integer lengthAbsence;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public Integer getDid() {
        return did;
    }

    public void setDid(Integer did) {
        this.did = did;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getLengthAbsence() {
        return lengthAbsence;
    }

    public void setLengthAbsence(Integer lengthAbsence) {
        this.lengthAbsence = lengthAbsence;
    }

}