package com.reps.jifen.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.reps.core.orm.IdEntity;

@Entity
@Table(name = "reps_jf_order_info")
public class OrderInfo extends IdEntity {

	private static final long serialVersionUID = 8912407883547937837L;
	
	public static final Integer UN_HANDLE = 0;//未处理
	
	public static final Integer HANDLE = 1; //已处理
	
	public static final Integer CANCLE = 2; //取消
	
	public static final Integer FINISH = 3; //确认收货

	/**订单号*/
	@Column(name = "order_no")
	private String orderNo;
	
	/***/
	@Column(name = "person_id")
	private String personId;
	
	/**收货人*/
	@Column(name = "consignee_name")
	private String consigneeName;
	
	/**收货地址*/
	@Column(name = "address")
	private String address;
	
	/**联系电话*/
	@Column(name = "phone")
	private String phone;
	
	/**物品id*/
	@Column(name = "reward_id")
	private String rewardId;
	
	/** 物品信息 */
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "reward_id", insertable = false, updatable = false)
    private PointReward reward;
	
	/**兑换数量*/
	@Column(name = "nums")
	private Integer nums;
	
	/**所需积分*/
	@Column(name = "used_points")
	private Integer usedPoints;
	
	/**订单状态*/
	@Column(name = "status")
	private Integer status;
	
	@Column(name = "create_time")
	private Date createTime;
	
	/**快递公司*/
	@Column(name = "express_company")
	private String expressCompany;
	
	/**运单号 */
	@Column(name = "shipment_no")
	private String shipmentNo;
	
	@Transient
	private String schoolId;
	
	@Transient
	private String schoolName;
	
	/**物品名称*/
	@Transient
	private String rewardName;

	public String getConsigneeName() {
		return consigneeName;
	}

	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRewardId() {
		return rewardId;
	}

	public void setRewardId(String rewardId) {
		this.rewardId = rewardId;
	}

	public Integer getNums() {
		return nums;
	}

	public void setNums(Integer nums) {
		this.nums = nums;
	}

	public Integer getUsedPoints() {
		return usedPoints;
	}

	public void setUsedPoints(Integer usedPoints) {
		this.usedPoints = usedPoints;
	}


	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getExpressCompany() {
		return expressCompany;
	}

	public void setExpressCompany(String expressCompany) {
		this.expressCompany = expressCompany;
	}

	public String getRewardName() {
		return rewardName;
	}

	public void setRewardName(String rewardName) {
		this.rewardName = rewardName;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getShipmentNo() {
		return shipmentNo;
	}

	public void setShipmentNo(String shipmentNo) {
		this.shipmentNo = shipmentNo;
	}

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public PointReward getReward() {
		return reward;
	}

	public void setReward(PointReward reward) {
		this.reward = reward;
	}
}
