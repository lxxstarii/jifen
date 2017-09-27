package com.reps.jifen.entity.enums;

/**
 * 奖励/扣除
 * @author Lanxumit
 *
 */
public enum MarkCategory {
	
	JIANGLI(1, "奖励"), KOUCHU(0, "扣除");
	
	private int type;
	
	private String value;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	private MarkCategory(int type, String name) {
		this.type = type;
		this.value = name;
	}

	
}
