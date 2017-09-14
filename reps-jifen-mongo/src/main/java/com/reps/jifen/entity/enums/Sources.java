package com.reps.jifen.entity.enums;

/**
 * 积分来源
 * @author Lanxumit
 *
 */
public enum Sources {
	
	TEACHER_REWARD("TeacherReward", "教师奖励"),
	TEACHER_DEDUCT("TeacherDeduct", "教师扣除"),
	PARENT_REWARD("ParentReward", "家长奖励"),
	PARENT_DEDUCT("ParentDeduct", "家长扣除");

	private String name;
	
	private String value;
	
	private Sources(String name, String value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
