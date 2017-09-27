package com.reps.jifen.entity.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 分类类别
 * 
 * @author qianguobing
 * @date 2017年8月24日 下午3:15:01
 */
public enum CategoryType {

	REWARD("1", "物品"), ACTIVITY("2", "活动");

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
	
	/**
	 * 获取分类类别map
	 * @return Map<String, String>
	 */
	public static Map<String, String> getCategoryType(){
		Map<String, String> map = new HashMap<>();
		map.put("", "");
		map.put(REWARD.getIndex(), REWARD.getName());
		map.put(ACTIVITY.getIndex(), ACTIVITY.getName());
		return map;
	}

}
