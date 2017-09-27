package com.reps.jifen.entity.enums;

/**
 * 行为类别
 * @author Lanxumit
 *
 */
public enum AccessCategory {

	XXHD("1", "学习活动"), XYXW("2", "校园行为");
	
	private String code;
	
	private String value;
	
	private AccessCategory(String code, String value) {
		this.code = code;
		this.value = value;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
