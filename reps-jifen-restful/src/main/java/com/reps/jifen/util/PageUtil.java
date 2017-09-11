package com.reps.jifen.util;

public class PageUtil {

	private PageUtil() {
	}

	/**
	 * 检查页码 checkPageNo
	 * 
	 * @param pageNo
	 * @return if pageNo==null or pageNo<1 then return 1 else return pageNo
	 */
	public static int cpn(Integer pageNo) {
		return (pageNo == null || pageNo < 1) ? 1 : pageNo;
	}

	/**
	 * 检查页大小
	 * 
	 * @param pageSize
	 * @return int
	 */
	public static int cps(Integer pageSize) {
		return (pageSize == null || pageSize < 1 ? 20 : pageSize);
	}

	/**
	 * 获取从第几条开始
	 * 
	 * @param pageNow
	 * @param pageSize
	 * @return int
	 */
	public static int getStartIndex(Integer pageNow, Integer pageSize) {
		return (cpn(pageNow) - 1) * cps(pageSize);
	}

}
