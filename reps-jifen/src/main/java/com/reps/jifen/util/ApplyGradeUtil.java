package com.reps.jifen.util;

import java.util.HashMap;
import java.util.Map;

public class ApplyGradeUtil {
	
	/**
	 * 适用年级初始化map
	 */
	public static final Map<String, String> GRADE_INITIAL_MAP = new HashMap<>();
	
	static {
		GRADE_INITIAL_MAP.put("21", "一年级");
		GRADE_INITIAL_MAP.put("22", "二年级");
		GRADE_INITIAL_MAP.put("23", "三年级");
		GRADE_INITIAL_MAP.put("24", "四年级");
		GRADE_INITIAL_MAP.put("25", "五年级");
		GRADE_INITIAL_MAP.put("26", "六年级");
		GRADE_INITIAL_MAP.put("31", "初中一年级");
		GRADE_INITIAL_MAP.put("32", "初中二年级");
		GRADE_INITIAL_MAP.put("33", "初中三年级");
		GRADE_INITIAL_MAP.put("41", "高中一年级");
		GRADE_INITIAL_MAP.put("42", "高中二年级");
		GRADE_INITIAL_MAP.put("43", "高中三年级");
	}
	
}
