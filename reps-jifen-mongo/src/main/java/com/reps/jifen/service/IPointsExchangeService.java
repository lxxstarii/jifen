package com.reps.jifen.service;

import java.util.List;

import com.reps.core.exception.RepsException;
import com.reps.core.orm.ListResult;
import com.reps.jifen.entity.PointsExchange;

public interface IPointsExchangeService {
	
	/**
	 * 通过人员id查询兑换记录,包括教师和学生
	 * @param personId
	 * @param startRow
	 * @param pageSize
	 * @return ListResult<JfPointsExchange>
	 */
	public ListResult<PointsExchange> findByPersonIdAndStatus(String personId, Short status, Integer pageIndex, Integer pageSize) throws RepsException;
	
	public ListResult<PointsExchange> findByPersonId(String personId, Integer pageIndex, Integer pageSize) throws RepsException;
	
	PointsExchange get(PointsExchange query);
	
	void save(PointsExchange data);

	public List<PointsExchange> findByCount(Integer count) throws RepsException;

	public ListResult<PointsExchange> findAll(Integer pageIndex, Integer pageSize) throws RepsException;
	
}
