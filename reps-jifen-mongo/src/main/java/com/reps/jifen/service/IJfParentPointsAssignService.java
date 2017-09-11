package com.reps.jifen.service;

import java.util.Date;
import java.util.List;

import com.reps.core.exception.RepsException;
import com.reps.core.orm.ListResult;
import com.reps.jifen.entity.JfParentPointsAssign;

public interface IJfParentPointsAssignService {
	
	/**
	 * 查询家长分配给孩子的记录
	 * @param jfParentPointsAssign
	 * @param pageIndex
	 * @param pageSize
	 * @return ListResult<JfParentPointsAssign>
	 * @throws RepsException
	 */
	public ListResult<JfParentPointsAssign> query(JfParentPointsAssign jfParentPointsAssign, Integer pageIndex, Integer pageSize) throws RepsException;
	
	void save(JfParentPointsAssign data);
	
	List<JfParentPointsAssign> find(JfParentPointsAssign query);
	
	List<JfParentPointsAssign> findByTime(Date monday, Date weekend, String parentId, String studentId);
}
