package com.reps.jifen.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.reps.core.orm.IdEntity;

/**
 * 评价分值设置
 * @author Lanxumit
 *
 */
@Entity(name = "com.reps.jifen.entity.AssessPoints")
@Table(name = "reps_jf_study_pjfzsz")
public class StudyAssessPoints extends IdEntity {

	private static final long serialVersionUID = -4027908209803679809L;

	/**分类 : 1:学习活动/2:校园行为*/
	@Column(name = "category")
	private String category;
	
	/**评价项*/
	@Column(name = "item")
	private String item;
	
	/**奖励/扣除  0:扣除  1:奖励*/
	@Column(name =  "mark")
	private Integer mark;
	
	/**分值范围*/
	@Column(name = "points_scope")
	private Integer pointsScope;
	
	/**项目图标*/
	@Column(name = "icon")
	private String icon;
	
	/**说明*/
	@Column(name = "description")
	private String description;
	
	/**是否启用*/
	@Column(name = "is_enabled")
	private Integer isEnable;
	
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public Integer getMark() {
		return mark;
	}

	public void setMark(Integer mark) {
		this.mark = mark;
	}

	public Integer getPointsScope() {
		return pointsScope;
	}

	public void setPointsScope(Integer pointsScope) {
		this.pointsScope = pointsScope;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(Integer isEnable) {
		this.isEnable = isEnable;
	}

}
