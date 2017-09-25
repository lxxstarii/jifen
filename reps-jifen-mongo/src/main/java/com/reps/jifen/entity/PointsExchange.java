package com.reps.jifen.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 积分兑换记录
 * @author qianguobing
 * @date 2017年9月6日 下午3:19:42
 */
@Document(collection = "reps_jf_points_exchange")
public class PointsExchange implements Serializable{
	
	private static final long serialVersionUID = 5987311221200585450L;
	
	/**物品类型*/
	public static final Short REWARD_TYPE = 1;
	
	/**活动类型*/
	public static final Short ACTIVITY_TYPE = 2;
	
	/**添加状态*/
	public static final Short ADD_STATUS = 1;
	
	/**取消状态*/
	public static final Short CALCEL_STATUS = 2;
	
	@Id
	private String id;

	/** 人员ID */
	private String personId;
	
	/** 奖品ID / 活动id*/
	private String rewardId;
	
	/** 奖品名称  / 活动名称*/
	private String rewardName;
	
	/** 扣除积分 */
	private Integer points;
	
	/** 兑换时间 */
	private Date exchangeTime;
	
	/** 兑换状态 */
	private Short status;
	
	/** 兑换人姓名 */
	private String name;
	
	/**学校id*/
	private String schoolId;
	
	/** 学校名字 */
	private String schoolName;
	
	/**订单id*/
	private String orderId;
	
	/**1:物品兑换  2:活动*/
	private Short type;

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

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Short getType() {
		return type;
	}

	public void setType(Short type) {
		this.type = type;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "JfPointsExchange [personId=" + personId + ", rewardId=" + rewardId + ", rewardName=" + rewardName + ", points=" + points + ", exchangeTime=" + exchangeTime + ", status=" + status
				+ ", name=" + name + ", schoolName=" + schoolName + "]";
	}

}
