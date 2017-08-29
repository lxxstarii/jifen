package com.reps.jifen.util;

import com.reps.core.util.StringUtil;

public class SqlUtil {

	private SqlUtil() {
	}

	public static String formatSql(String ids) {
		String idStr = "";
		if (StringUtil.isNotBlank(ids)) {
			String[] arr = ids.split(",");
			if (ids.length() > 0) {
				for (int i = 0; i < arr.length; i++) {
					if ("".equals(idStr))
						idStr = idStr + "'" + arr[i] + "'";
					else {
						idStr = idStr + ",'" + arr[i] + "'";
					}
				}
			}
		}
		return idStr;
	}
	
}
