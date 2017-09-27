package com.reps.jifen.service;

import com.reps.core.exception.RepsException;
import com.reps.core.orm.ListResult;
import com.reps.jifen.entity.PointsCollect;

public interface IPointsCollectService {
	
	/**
	 * 积分获取记录保存
	 * @param jfPointsCollect
	 * @return JfPointsCollect
	 * @throws RepsException
	 */
	public PointsCollect save(PointsCollect jfPointsCollect) throws RepsException;
	
	/**
	 * 积分查询，包含教师和学生，家长
	 * @param personId
	 * @param pageIndex
	 * @param pageSize
	 * @return ListResult<JfPointsCollect>
	 * @throws RepsException
	 */
	public ListResult<PointsCollect> findByPersonId(String personId, Integer pageIndex, Integer pageSize) throws RepsException;  
	
}
