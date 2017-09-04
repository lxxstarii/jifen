package com.reps.jifen.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.reps.core.orm.IdEntity;
import com.reps.school.entity.Teacher;
import com.reps.system.entity.Organize;

/**
 * 教师评价可分配积分
 * @author Lanxumit
 *
 */
@Entity(name = "com.reps.jifen.entity.TeacherPjkfpjf")
@Table(name = "reps_jf_teacher_pjkfpjf")
public class TeacherPjkfpjf extends IdEntity {
	
	private static final long serialVersionUID = -8480346523824613875L;
	
	/**系数积分*/
	public static final Integer sxjf = 1;
	
	/**分配积分*/
	public static final Integer fpjf = 2;

	/**教师id*/
	@Column(name = "teacher_id")
	private String teacherId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "teacher_id", insertable = false, updatable = false)
	private Teacher teacher;
	
	/**账号id*/
	@Column(name = "account_id")
	private String accountId;
	
	/**用户id*/
	@Column(name = "user_id")
	private String userId;
	
	/**机构id*/
	@Column(name = "organize_id")
	private String organizeId;
	
	/** 关联机构信息 */
	@OneToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "organize_id", insertable = false, updatable = false)
	private Organize organize;
	
	/**当前可分配积分*/
	@Column(name = "points_left")
	private Integer pointsLeft;
	
	/**授权总积分*/
	@Column(name = "total_points_authorized")
	private Integer totalPointsAuthorized;
	
	@Column(name = "authorized_from")
	private Integer authorizedFrom;
	
	/**奖励积分*/
	@Transient
	private Integer jljf;

	public String getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOrganizeId() {
		return organizeId;
	}

	public void setOrganizeId(String organizeId) {
		this.organizeId = organizeId;
	}

	public Integer getPointsLeft() {
		return pointsLeft;
	}

	public void setPointsLeft(Integer pointsLeft) {
		this.pointsLeft = pointsLeft;
	}

	public Integer getTotalPointsAuthorized() {
		return totalPointsAuthorized;
	}

	public void setTotalPointsAuthorized(Integer totalPointsAuthorized) {
		this.totalPointsAuthorized = totalPointsAuthorized;
	}

	public Integer getAuthorizedFrom() {
		return authorizedFrom;
	}

	public void setAuthorizedFrom(Integer authorizedFrom) {
		this.authorizedFrom = authorizedFrom;
	}

	public Integer getJljf() {
		return jljf;
	}

	public void setJljf(Integer jljf) {
		this.jljf = jljf;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public Organize getOrganize() {
		return organize;
	}

	public void setOrganize(Organize organize) {
		this.organize = organize;
	}
}
