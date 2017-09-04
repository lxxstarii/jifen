package com.reps.jifen.entity.enums;

/**
 * 积分来源
 * @author Lanxumit
 *
 */
public enum JfComeFrom {

	JFJLSX(1, "积分奖励系数"),
	JFFP(2, "积分分配");
	
	private int code;
	
	private String value;

	private JfComeFrom(int code, String value) {
		this.code = code;
		this.value = value;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
