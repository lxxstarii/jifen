package com.reps.jifen.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 积分兑换记录
 * @author qianguobing
 * @date 2017年9月6日 下午3:19:42
 */
@Document(collection = "reps_jf_points_exchange")
public class PointsExchange implements Serializable{
	
	private static final long serialVersionUID = 5987311221200585450L;

	/** 人员ID */
	private String personId;
	
	/** 奖品ID */
	private String rewardId;
	
	/** 奖品名称 */
	private String rewardName;
	
	/** 扣除积分 */
	private Integer points;
	
	/** 兑换时间 */
	private Date exchangeTime;
	
	/** 兑换状态 */
	private Short status;
	
	/** 兑换人姓名 */
	private String name;
	
	/** 学校名字 */
	private String schoolName;

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public String getRewardId() {
		return rewardId;
	}

	public void setRewardId(String rewardId) {
		this.rewardId = rewardId;
	}

	public String getRewardName() {
		return rewardName;
	}

	public void setRewardName(String rewardName) {
		this.rewardName = rewardName;
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	public Date getExchangeTime() {
		return exchangeTime;
	}

	public void setExchangeTime(Date exchangeTime) {
		this.exchangeTime = exchangeTime;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	@Override
	public String toString() {
		return "JfPointsExchange [personId=" + personId + ", rewardId=" + rewardId + ", rewardName=" + rewardName + ", points=" + points + ", exchangeTime=" + exchangeTime + ", status=" + status
				+ ", name=" + name + ", schoolName=" + schoolName + "]";
	}

}
