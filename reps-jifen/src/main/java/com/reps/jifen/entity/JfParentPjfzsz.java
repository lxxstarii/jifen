package com.reps.jifen.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.reps.core.orm.IdEntity;

/**
 * 家长评价分值设置
 * @author qianguobing
 * @date 2017年8月28日 下午4:14:55
 */
@Entity
@Table(name = "reps_jf_parent_pjfzsz")
public class JfParentPjfzsz extends IdEntity implements Serializable{

	private static final long serialVersionUID = -3940790235989605260L;
	
	public JfParentPjfzsz() {
		
	}
	
	public JfParentPjfzsz(String id) {
		super.setId(id);
	}
	
	/** 评价项 */
	@Column(name = "item", nullable = false, length = 30)
	private String item;
	
	/** 行为内容 */
	@Column(name = "content", length = 500)
	private String content;
	
	/** 分值范围 */
	@Column(name = "points_scope")
	private Short pointsScope;
	
	/** 适用年级 */
	@Column(name = "apply_grade", length = 50)
	private String applyGrade;
	
	/** 可用频率 */
	@Column(name = "frequency")
	private Short frequency;
	
	/** 是否启用 */
	@Column(name = "is_enabled")
	private Short isEnabled;
	
	/** 分值范围显示 */
	@Transient
	private String pointsScopeDisp;

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Short getPointsScope() {
		return pointsScope;
	}

	public void setPointsScope(Short pointsScope) {
		this.pointsScope = pointsScope;
	}

	public String getApplyGrade() {
		return applyGrade;
	}

	public void setApplyGrade(String applyGrade) {
		this.applyGrade = applyGrade;
	}

	public Short getFrequency() {
		return frequency;
	}

	public void setFrequency(Short frequency) {
		this.frequency = frequency;
	}

	public Short getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(Short isEnabled) {
		this.isEnabled = isEnabled;
	}

	public String getPointsScopeDisp() {
		return pointsScopeDisp;
	}

	public void setPointsScopeDisp(String pointsScopeDisp) {
		this.pointsScopeDisp = pointsScopeDisp;
	}
	
}
