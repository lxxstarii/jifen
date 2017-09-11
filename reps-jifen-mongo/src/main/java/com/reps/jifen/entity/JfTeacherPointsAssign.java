package com.reps.jifen.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Transient;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 教师分配给学生记录
 * @author qianguobing
 * @date 2017年9月6日 下午3:13:46
 */
@Document(collection = "reps_jf_teacher_points_assign")
public class JfTeacherPointsAssign implements Serializable{
	
	private static final long serialVersionUID = -3351355659593489505L;

	/** 教师ID */
	private String teacherId;
	
	/** 学生ID */
	private String studentId;
	
	/** 学校ID */
	private String schoolId;
	
	/** 教师姓名 */
	private String teacherName;
	
	/** 学生姓名 */
	private String studentName;
	
	/** 学校名称 */
	private String schoolName;
	
	
	/** 规则名称 */
	private String ruleName;
	
	/** 积分 */
	private Integer points;
	
	/** 奖励/扣除 0 - 扣除, 1 - 奖励*/
	private Short mark;
	
	/** 分配时间 */
	private Date createTime;
	
	/**规则id*/
	@Transient
	private String ruleId;
	
	public String getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	public Short getMark() {
		return mark;
	}

	public void setMark(Short mark) {
		this.mark = mark;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getRuleId() {
		return ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}
	
	@Override
	public String toString() {
		return "JfTeacherPointsAssign [teacherId=" + teacherId + ", studentId=" + studentId + ", schoolId=" + schoolId + ", teacherName=" + teacherName + ", studentName=" + studentName
				+ ", schoolName=" + schoolName + ", ruleName=" + ruleName + ", points=" + points + ", mark=" + mark + ", createTime=" + createTime + "]";
	}
	
}
