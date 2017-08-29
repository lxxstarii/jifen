package com.reps.jifen.entity.enums;

/**
 * 奖品状态
 * 
 * @author qianguobing
 * @date 2017年8月25日 下午3:32:16
 */
public enum RewardStatus {

	UN_PUBLISH((short) 0, "未发布"), PUBLISHED((short) 1, "已发布"), SOLD_OUT((short) 2, "已下架");

	private Short index;
	private String name;

	RewardStatus(Short index, String name) {
		this.index = index;
		this.name = name;
	}

	public Short getIndex() {
		return index;
	}

	public void setIndex(Short index) {
		this.index = index;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
