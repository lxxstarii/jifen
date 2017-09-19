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
	public static String convertByMap(Map<String,  Object> paramMap) {
		String params = "";
		if (paramMap != null && !paramMap.isEmpty()) {
			int i = 0;
			for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
				if (i == 0) {
					params += "?" + entry.getKey() + "=" + entry.getValue();
				} else {
					params += "&" + entry.getKey() + "=" + entry.getValue();
				}
				i++;
			}
		}
		return params;
	}
	
}
