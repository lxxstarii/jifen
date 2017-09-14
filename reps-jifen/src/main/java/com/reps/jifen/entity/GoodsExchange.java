package com.reps.jifen.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.reps.core.orm.IdEntity;

@Entity
@Table(name = "reps_jf_goods_exchange")
public class GoodsExchange extends IdEntity {

	private static final long serialVersionUID = 8912407883547937837L;
	
	public static final Integer UN_HANDLE = 0;//未处理
	
	public static final Integer HANDLE = 1; //已处理
	
	public static final Integer CANCLE = 2; //取消
	
	public static final Integer FINISH = 3; //完成

	/**订单号*/
	private String orderNum;
	
	/***/
	private String personId;
	
	/**收货人*/
	private String consigneeName;
	
	/**收货地址*/
	private String address;
	
	/**联系电话*/
	private String phone;
	
	/**物品id*/
	private String rewardId;
	
	/**物品名称*/
	private String rewardName;
	
	/**兑换数量*/
	private Integer nums;
	
	/**所需积分*/
	private Integer usedPoints;
	
	/**订单状态*/
	private Integer state;
	
	private Date createTime;
	
	/**快递公司*/
	private String expressCompany;
	
	/**运单号 */
	private String wayBillNum;

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

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

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
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

	public String getWayBillNum() {
		return wayBillNum;
	}

	public void setWayBillNum(String wayBillNum) {
		this.wayBillNum = wayBillNum;
	}

	public String getRewardName() {
		return rewardName;
	}

	public void setRewardName(String rewardName) {
		this.rewardName = rewardName;
	}
}
