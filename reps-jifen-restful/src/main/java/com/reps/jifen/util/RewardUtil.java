package com.reps.jifen.util;

import static com.reps.core.commons.OrderCondition.OrderDirection.ASC;
import static com.reps.core.commons.OrderCondition.OrderDirection.DESC;
import static com.reps.jifen.entity.enums.RewardStatus.PUBLISHED;

import java.util.HashMap;
import java.util.Map;

import com.reps.core.util.StringUtil;
import com.reps.jifen.entity.JfReward;
import com.reps.jifen.entity.JfRewardCategory;

/**
 * 物品和活动工具类
 * @author qianguobing
 * @date 2017年9月12日 下午3:57:50
 */
public class RewardUtil {

	private RewardUtil() {
	}

	/**
	 * 排序字段map
	 */
	private static final Map<String, String> SORT_FIELD_MAP = new HashMap<>();

	static {
		SORT_FIELD_MAP.put("record", "exchangedCount");
		SORT_FIELD_MAP.put("point", "points");
		SORT_FIELD_MAP.put("time", "createTime");
	}

	/**
	 * 设置奖品属性
	 * 
	 * @param jfReward
	 * @param type
	 */
	public static void setReward(JfReward jfReward, String type) {
		JfRewardCategory jfRewardCategory = new JfRewardCategory();
		jfRewardCategory.setType(type);
		jfReward.setJfRewardCategory(jfRewardCategory);
		jfReward.setIsShown(PUBLISHED.getIndex());
		String sortField = jfReward.getSortField();
		// 获取排序字段，默认按发布时间进行排序
		if (StringUtil.isBlank(sortField) || !SORT_FIELD_MAP.containsKey(sortField)) {
			jfReward.setSortField("createTime");
		} else {
			jfReward.setSortField(SORT_FIELD_MAP.get(sortField));
		}
		String sortOrder = jfReward.getSortOrder();
		if (!ASC.name().equalsIgnoreCase(sortOrder) && !DESC.name().equalsIgnoreCase(sortOrder)) {
			jfReward.setSortOrder(DESC.name());
		}
	}

}
