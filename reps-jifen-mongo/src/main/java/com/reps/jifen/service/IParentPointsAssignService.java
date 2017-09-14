package com.reps.jifen.service;

import java.util.Date;
import java.util.List;

import com.reps.core.exception.RepsException;
import com.reps.core.orm.ListResult;
import com.reps.jifen.entity.ParentPointsAssign;

public interface IParentPointsAssignService {
	
	/**
	 * 查询家长分配给孩子的记录
	 * @param jfParentPointsAssign
	 * @param pageIndex
	 * @param pageSize
	 * @return ListResult<JfParentPointsAssign>
	 * @throws RepsException
	 */
	public ListResult<ParentPointsAssign> query(ParentPointsAssign jfParentPointsAssign, Integer pageIndex, Integer pageSize) throws RepsException;
	
	void save(ParentPointsAssign data);
	
	List<ParentPointsAssign> find(ParentPointsAssign query);
	
	List<ParentPointsAssign> findByTime(Date monday, Date weekend, String parentId, String studentId);
}
