package com.reps.jifen.service;

import java.util.List;

import com.reps.core.exception.RepsException;
import com.reps.core.orm.ListResult;
import com.reps.jifen.entity.PointLevel;

public interface IPointLevelService {

	/**
	 * 保存积分等级
	 * @param jfPointLevel
	 */
	public void save(PointLevel jfPointLevel);
	
	/**
	 * 删除积分等级
	 * @param jfPointLevel
	 */
	public void delete(PointLevel jfPointLevel);
	
	/**
	 * 修改积分等级
	 * @param jfPointLevel
	 */
	public void update(PointLevel jfPointLevel);
	
	/**
	 * 查询积分等级
	 * @param id
	 * @return JfPointLevel
	 */
	public PointLevel get(String id);
	
	/**
	 * 积分等级查询
	 * @param start
	 * @param pagesize
	 * @param jfPointLevel
	 * @return ListResult<JfPointLevel>
	 */
	public ListResult<PointLevel> query(int start, int pagesize, PointLevel jfPointLevel);
	
	/**
	 *  查询所有积分等级列表
	 * @param jfPointLevel
	 * @return List<JfPointLevel>
	 * @throws RepsException
	 */
	public List<PointLevel> queryAll(PointLevel jfPointLevel) throws RepsException;
	
}
