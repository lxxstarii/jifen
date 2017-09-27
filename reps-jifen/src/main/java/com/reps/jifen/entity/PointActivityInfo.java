package com.reps.jifen.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.reps.core.orm.IdEntity;
import com.reps.school.entity.Classes;
import com.reps.school.entity.School;
import com.reps.school.entity.Student;

/**
 * 活动统计
 * @author qianguobing
 * @date 2017年9月18日 下午4:44:53
 */
@Entity
@Table(name = "reps_jf_activity_info")
public class PointActivityInfo extends IdEntity implements Serializable{

	private static final long serialVersionUID = 7348847018860989630L;
	
	/** 活动ID */
	@Column(name = "reward_id")
	private String rewardId;
	
	@JsonIgnore
    @ManyToOne(cascade = {})
	@JoinColumn(name = "reward_id", insertable = false, updatable = false)
	private PointReward pointReward;
		
	/** 学生ID */
	@Column(name = "student_id")
	private String studentId;
	
	@JsonIgnore
    @ManyToOne(cascade = {})
	@JoinColumn(name = "student_id", insertable = false, updatable = false)
	private Student student;
	
	/** 学校ID */
	@Column(name = "school_id")
	private String schoolId;
	
	@JsonIgnore
    @ManyToOne(cascade = {})
	@JoinColumn(name = "school_id", insertable = false, updatable = false)
	private School school;
	
	/** 年级 */
	@Column(name = "grade")
	private String grade;
	
	/** 班级ID */
	@Column(name = "classes_id")
	private String classesId;
	
	@ManyToOne(cascade={})
	@JoinColumn(name="classes_id", insertable = false, updatable = false)
	private Classes classes;
	
	/** 审核状态 1:通过 2:驳回*/
	@Column(name = "audit_status")
	private Short auditStatus;
	
	/** 审核意见 */
	@Column(name = "audit_opinion")
	private String auditOpinion;
	
	/** 是否参与 1:已参与 0:取消参与*/
	@Column(name = "is_participate")
	private Short isParticipate;
	
	/** 统计时间 */
	@Column(name = "create_time")
	private Date createTime;

	public String getRewardId() {
		return rewardId;
	}

	public void setRewardId(String rewardId) {
		this.rewardId = rewardId;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getClassesId() {
		return classesId;
	}

	public void setClassesId(String classesId) {
		this.classesId = classesId;
	}

	public Short getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(Short auditStatus) {
		this.auditStatus = auditStatus;
	}

	public String getAuditOpinion() {
		return auditOpinion;
	}

	public void setAuditOpinion(String auditOpinion) {
		this.auditOpinion = auditOpinion;
	}

	public Short getIsParticipate() {
		return isParticipate;
	}

	public void setIsParticipate(Short isParticipate) {
		this.isParticipate = isParticipate;
	}

	public School getSchool() {
		return school;
	}

	public void setSchool(School school) {
		this.school = school;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public PointReward getPointReward() {
		return pointReward;
	}

	public void setPointReward(PointReward pointReward) {
		this.pointReward = pointReward;
	}

	public Classes getClasses() {
		return classes;
	}

	public void setClasses(Classes classes) {
		this.classes = classes;
	}
	
}
