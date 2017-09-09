package com.reps.jifen.entity;

import com.reps.core.orm.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "reps_jf_application_used_fzsz")
public class JfSystemConfig extends IdEntity implements Serializable {

    /**应用名称*/
    @Column(name = "application_name", length = 15, nullable=false)
    private String applicationName;

    /**积分项*/
    @Column(name = "item", length = 20, nullable=false)
    private String item;

    /**标识码*/
    @Column(name = "code", length = 30, nullable=false)
    private String code;

    /**分值*/
    @Column(name = "points", nullable=false)
    private Integer points;

    /**奖励/扣除*/
    @Column(name = "mark", nullable=false)
    private Integer mark;// 0 扣除 1 奖励

    /**是否需要审核 */
    @Column(name = "need_check", nullable=false)
    private Integer needCheck; //0 不审核 1 审核

    /**是否启用*/
    @Column(name = "is_enabled", nullable=false)
    private Integer isEnabled;// 0 不启用 1 启用

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

    public Integer getNeedCheck() {
        return needCheck;
    }

    public void setNeedCheck(Integer needCheck) {
        this.needCheck = needCheck;
    }

    public Integer getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Integer isEnabled) {
        this.isEnabled = isEnabled;
    }
}
