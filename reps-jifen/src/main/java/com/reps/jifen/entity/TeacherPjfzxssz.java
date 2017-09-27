package com.reps.jifen.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.reps.core.orm.IdEntity;

/**
 * 教师评价分值系数设置
 * @author Lanxumit
 *
 */
@Entity(name = "com.reps.jifen.entity.TeacherPjfzxssz")
@Table(name = "reps_jf_teacher_pjfzxssz")
public class TeacherPjfzxssz extends IdEntity {

	private static final long serialVersionUID = -352225580290194543L;

	/**班主任*/
	@Column(name = "bzr")
	private Integer bzr;
	
	/**教师*/
	@Column(name = "rkjs")
	private Integer rkjs;
	
	@Column(name = "school_id")
	private String schoolId;
	
	/**选择教师类型*/
	@Transient
	private String option;
	
	/**系数*/
	@Transient
	private Integer ratio;

	public Integer getBzr() {
		return bzr;
	}

	public void setBzr(Integer bzr) {
		this.bzr = bzr;
	}

	public Integer getRkjs() {
		return rkjs;
	}

	public void setRkjs(Integer rkjs) {
		this.rkjs = rkjs;
	}

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

	public Integer getRatio() {
		return ratio;
	}

	public void setRatio(Integer ratio) {
		this.ratio = ratio;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}
	
}
