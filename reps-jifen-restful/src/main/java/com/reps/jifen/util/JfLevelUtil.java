package com.reps.jifen.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.reps.jifen.entity.JfPointLevel;

public class JfLevelUtil {

	/**
	 * 根据传入积分数、积分等级列表返回对应的积分等级
	 * @param points
	 * @param list
	 * @return
	 */
	public static int getPointsLevel(Integer points, List<JfPointLevel> list) {
		int level = 0;
		if (list == null || list.isEmpty()) {
			return level;
		}
		Map<Integer, Integer> map = new HashMap<>();
		int[] jfPoints = new int[list.size()];
		for (int i = 0; i < list.size(); i++) {
			JfPointLevel jfPointLevel = list.get(i);
			map.put(jfPointLevel.getPoint(), jfPointLevel.getLevel());
			jfPoints[i] = jfPointLevel.getPoint();
		}
		Arrays.sort(jfPoints);
		if (points > jfPoints[jfPoints.length - 1]) {
			return map.get(jfPoints[jfPoints.length - 1]);
		}
		for (int i = 0; i < jfPoints.length; i++) {
			if (points < jfPoints[i]) {
				if (i == 0) {
					return level;
				}
				level = map.get(jfPoints[i-1]);
				break;
			} else if (points == jfPoints[i]){
				level = map.get(jfPoints[i]);
				break;
			}
		}
		return level;
	}
}
