package com.reps.jifen.util;

import java.util.Map;

/**
 * 拼接url
 * @author Lanxumit
 *
 */
public class ConvertUrlUtil {

	/**
	 * map的key、value拼接url
	 * @param url
	 * @param paramMap
	 * @return
	 */
	public static String convertByMap(Map<String,  Object> paramMap) throws Exception {
		String params = "";
		if (paramMap != null && !paramMap.isEmpty()) {
			for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
				params += entry.getKey() + "=" + entry.getValue() + "&";
			}
		}
		return params;
	}
	
}
