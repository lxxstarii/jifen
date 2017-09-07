package com.reps.jifen.service;

import com.reps.core.exception.RepsException;
import com.reps.core.orm.ListResult;
import com.reps.jifen.entity.JfPointsExchange;

public interface IJfPointsExchangeService {
	
	/**
	 * 通过人员id查询兑换记录,包括教师和学生
	 * @param personId
	 * @param startRow
	 * @param pageSize
	 * @return ListResult<JfPointsExchange>
	 */
	public ListResult<JfPointsExchange> findByPersonId(String personId, Integer pageIndex, Integer pageSize) throws RepsException;
	
}
