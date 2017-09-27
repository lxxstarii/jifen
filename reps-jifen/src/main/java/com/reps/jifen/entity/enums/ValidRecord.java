package com.reps.jifen.entity.enums;

public enum ValidRecord {
	
	VALID((short) 1, "有效"), DELETED((short) 9, "删除");
	
	private Short id;
	private String text;
	
	private ValidRecord(Short id, String text) {
		this.id = id;
		this.text = text;
	}

	public Short getId() {
		return id;
	}

	public void setId(Short id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
