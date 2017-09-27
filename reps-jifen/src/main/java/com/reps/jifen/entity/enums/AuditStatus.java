package com.reps.jifen.entity.enums;

public enum AuditStatus {
	
	PASSED((short)1, "通过"), REJECTED((short) 2, "驳回");
	
	private Short id;
	
	private String status;
	
	AuditStatus(Short id, String status) {
		this.id = id;
		this.status = status;
	}

	public Short getId() {
		return id;
	}

	public void setId(Short id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
