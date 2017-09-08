package com.reps.jifen.service;

import com.reps.core.orm.ListResult;
import com.reps.jifen.entity.JfPointLevel;

public interface IJfPointLevelService {

	/**
	 * 保存积分等级
	 * @param jfPointLevel
	 */
	public void save(JfPointLevel jfPointLevel);
	
	/**
	 * 删除积分等级
	 * @param jfPointLevel
	 */
	public void delete(JfPointLevel jfPointLevel);
	
	/**
	 * 修改积分等级
	 * @param jfPointLevel
	 */
	public void update(JfPointLevel jfPointLevel);
	
	/**
	 * 查询积分等级
	 * @param id
	 * @return JfPointLevel
	 */
	public JfPointLevel get(String id);
	
	/**
	 * 积分等级查询
	 * @param start
	 * @param pagesize
	 * @param jfPointLevel
	 * @return ListResult<JfPointLevel>
	 */
	public ListResult<JfPointLevel> query(int start, int pagesize, JfPointLevel jfPointLevel);
	
}
