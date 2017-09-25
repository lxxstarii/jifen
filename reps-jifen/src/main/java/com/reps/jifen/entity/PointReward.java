package com.reps.jifen.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.reps.core.orm.IdEntity;

/**
 * 奖品信息
 * @author qianguobing
 * @date 2017年8月17日 下午5:19:54
 */
@Entity
@Table(name = "reps_jf_reward")
public class PointReward extends IdEntity implements Serializable {

	private static final long serialVersionUID = 5942323150277253519L;

	/** 所属分类ID */
	@Column(name = "category_id", nullable = false, length = 32, insertable=false, updatable=false)
	private String categoryId;
	
	/** 所属分类 */
	@JsonIgnore
    @ManyToOne(cascade = {})
    @JoinColumn(name = "category_id")
    private RewardCategory jfRewardCategory;

	/** 名称 */
	@Column(name = "name", nullable = false, length = 30)
	private String name;

	/** 数量 */
	@Column(name = "numbers")
	private Integer numbers;
	
	/** 扣除积分 */
	@Column(name = "points")
	private Integer points;

	/** 描述 */
	@Column(name = "description", length = 500)
	private String description;
	
	/** 图片 */
	@Column(name = "picture", length = 180)
	private String picture;
	
	/** 是否上架 */
	@Column(name = "is_shown", updatable=false)
	private Short isShown;
	
	/** 登记时间 */
	@Column(name = "create_time")
	private Date createTime;
	
	/** 上架时间 */
	@Column(name = "show_time")
	private Date showTime;
	
	/** 活动开始时间 */
	@Column(name = "begin_time")
	private Date beginTime;
	
	/** 结束时间 */
	@Column(name = "end_time")
	private Date endTime;
	
	/** 截止时间 */
	@Column(name = "finish_time")
	private Date finishTime;
	
	/** 兑换物品数量 */
	@Column(name = "exchanged_count")
	private Integer exchangedCount;
	
	/** 参与人数 */
	@Column(name = "participated_count")
	private Integer participatedCount;
	
	/** 删除标识 1：有效（默认值），9：删除 */
	@Column(name = "valid_record")
	private Short validRecord;
	
	@Transient
	@JsonIgnore
	private String finishTimeDisp;
	
	@Transient
	@JsonIgnore
	private String showTimeDisp;
	
	/** 积分开始 */
	@Transient
	@JsonIgnore
	private Integer pointBegin;
	
	/** 积分结束 */
	@Transient
	@JsonIgnore
	private Integer pointEnd;
	
	/** 物品图片地址 */
	@Transient
	@JsonIgnore
	private String rewardUrlOne;
	
	@Transient
	@JsonIgnore
	private String rewardUrlTwo;
	
	@Transient
	@JsonIgnore
	private String rewardUrlThree;
	
	@Transient
	@JsonIgnore
	private String rewardUrlFour;
	
	@Transient
	@JsonIgnore
	private String rewardUrlFive;
	
	/** 排序字段 exchanged_count（兑换记录），points（积分值），createTime（新品）*/
	@Transient
	@JsonIgnore
	private String sortField;
	
	/** 分类名称 */
	@Transient
	private String categoryName;
	
	/** 排序顺序 asc（升序）、desc（降序）*/
	@Transient
	@JsonIgnore
	private String sortOrder;

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public RewardCategory getJfRewardCategory() {
		return jfRewardCategory;
	}

	public void setJfRewardCategory(RewardCategory jfRewardCategory) {
		this.jfRewardCategory = jfRewardCategory;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getNumbers() {
		return numbers;
	}

	public void setNumbers(Integer numbers) {
		this.numbers = numbers;
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public Short getIsShown() {
		return isShown;
	}

	public void setIsShown(Short isShown) {
		this.isShown = isShown;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getShowTime() {
		return showTime;
	}

	public void setShowTime(Date showTime) {
		this.showTime = showTime;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public String getFinishTimeDisp() {
		return finishTimeDisp;
	}

	public void setFinishTimeDisp(String finishTimeDisp) {
		this.finishTimeDisp = finishTimeDisp;
	}

	public String getShowTimeDisp() {
		return showTimeDisp;
	}

	public void setShowTimeDisp(String showTimeDisp) {
		this.showTimeDisp = showTimeDisp;
	}

	public String getRewardUrlOne() {
		return rewardUrlOne;
	}

	public void setRewardUrlOne(String rewardUrlOne) {
		this.rewardUrlOne = rewardUrlOne;
	}

	public String getRewardUrlTwo() {
		return rewardUrlTwo;
	}

	public void setRewardUrlTwo(String rewardUrlTwo) {
		this.rewardUrlTwo = rewardUrlTwo;
	}

	public String getRewardUrlThree() {
		return rewardUrlThree;
	}

	public void setRewardUrlThree(String rewardUrlThree) {
		this.rewardUrlThree = rewardUrlThree;
	}

	public String getRewardUrlFour() {
		return rewardUrlFour;
	}

	public void setRewardUrlFour(String rewardUrlFour) {
		this.rewardUrlFour = rewardUrlFour;
	}

	public String getRewardUrlFive() {
		return rewardUrlFive;
	}

	public void setRewardUrlFive(String rewardUrlFive) {
		this.rewardUrlFive = rewardUrlFive;
	}

	public Integer getPointBegin() {
		return pointBegin;
	}

	public void setPointBegin(Integer pointBegin) {
		this.pointBegin = pointBegin;
	}

	public Integer getPointEnd() {
		return pointEnd;
	}

	public void setPointEnd(Integer pointEnd) {
		this.pointEnd = pointEnd;
	}

	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public Integer getExchangedCount() {
		return exchangedCount;
	}

	public void setExchangedCount(Integer exchangedCount) {
		this.exchangedCount = exchangedCount;
	}

	public Integer getParticipatedCount() {
		return participatedCount;
	}

	public void setParticipatedCount(Integer participatedCount) {
		this.participatedCount = participatedCount;
	}

	public Short getValidRecord() {
		return validRecord;
	}

	public void setValidRecord(Short validRecord) {
		this.validRecord = validRecord;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
}
