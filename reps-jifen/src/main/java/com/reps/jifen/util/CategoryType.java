package com.reps.jifen.util;

/**
 * 分类类别
 * 
 * @author qianguobing
 * @date 2017年8月24日 下午3:15:01
 */
public enum CategoryType {

	REWARD("1", "物品类别"), ACTIVITY("2", "活动类别");

	private String index;
	private String name;

	private CategoryType(String index, String name) {
		this.index = index;
		this.name = name;
	}

	public String getIndex() {
		return index;
	}

	public String getName() {
		return name;
	}

}
