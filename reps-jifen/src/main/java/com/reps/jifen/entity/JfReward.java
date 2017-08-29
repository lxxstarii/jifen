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
public class JfReward extends IdEntity implements Serializable {

	private static final long serialVersionUID = 5942323150277253519L;

	public JfReward() {

	}

	public JfReward(String id) {
		super.setId(id);
	}

	/** 所属分类ID */
	@Column(name = "category_id", nullable = false, length = 32, insertable=false, updatable=false)
	private String categoryId;
	
	/** 所属分类 */
	@JsonIgnore
    @ManyToOne(cascade = {})
    @JoinColumn(name = "category_id")
    private JfRewardCategory jfRewardCategory;

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
	@Column(name="create_time", updatable=false)
	private Date createTime;
	
	/** 上架时间 */
	@Column(name="show_time")
	private Date showTime;
	
	/** 截止时间 */
	@Column(name="finish_time")
	private Date finishTime;
	
	@Transient
	private String finishTimeDisp;
	
	@Transient
	private String showTimeDisp;
	
	/** 物品图片地址 */
	@Transient
	private String rewardUrlOne;
	
	@Transient
	private String rewardUrlTwo;
	
	@Transient
	private String rewardUrlThree;
	
	@Transient
	private String rewardUrlFour;
	
	@Transient
	private String rewardUrlFive;

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public JfRewardCategory getJfRewardCategory() {
		return jfRewardCategory;
	}

	public void setJfRewardCategory(JfRewardCategory jfRewardCategory) {
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
	
}
