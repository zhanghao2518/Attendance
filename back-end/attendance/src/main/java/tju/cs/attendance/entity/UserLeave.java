package tju.cs.attendance.entity;

import java.io.Serializable;

/**
 * (UserLeave)实体类
 *
 * @author zhanghao
 * @since 2020-12-02 20:17:56
 */
public class UserLeave implements Serializable {
    private static final long serialVersionUID = -80786153787844478L;
    /**
     * 编号
     */
    private Integer id;
    /**
     * 员工id
     */
    private Integer uid;
    /**
     * 员工姓名
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
     * 开始时间
     */
    private String startTime;
    /**
     * 结束时间
     */
    private String endTime;
    /**
     * 请假时长
     */
    private Integer lengthLeave;
    /**
     * 0:事假 1:年假
     */
    private Integer type;
    /**
     * 0:审核不通过 1:审核通过 2:未审核
     */
    private Integer reviewByDivisionManager;
    /**
     * 审核意见
     */
    private String adviceByDivisionManager;
    /**
     * 0:审核不通过 1:审核通过 2:未审核
     */
    private Integer reviewByManager;
    /**
     * 审核意见
     */
    private String adviceByManager;
    /**
     * 最后结果 0:不通过 1:通过 2:审批中
     */
    private Integer result;


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

    public Integer getLengthLeave() {
        return lengthLeave;
    }

    public void setLengthLeave(Integer lengthLeave) {
        this.lengthLeave = lengthLeave;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getReviewByDivisionManager() {
        return reviewByDivisionManager;
    }

    public void setReviewByDivisionManager(Integer reviewByDivisionManager) {
        this.reviewByDivisionManager = reviewByDivisionManager;
    }

    public String getAdviceByDivisionManager() {
        return adviceByDivisionManager;
    }

    public void setAdviceByDivisionManager(String adviceByDivisionManager) {
        this.adviceByDivisionManager = adviceByDivisionManager;
    }

    public Integer getReviewByManager() {
        return reviewByManager;
    }

    public void setReviewByManager(Integer reviewByManager) {
        this.reviewByManager = reviewByManager;
    }

    public String getAdviceByManager() {
        return adviceByManager;
    }

    public void setAdviceByManager(String adviceByManager) {
        this.adviceByManager = adviceByManager;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

}