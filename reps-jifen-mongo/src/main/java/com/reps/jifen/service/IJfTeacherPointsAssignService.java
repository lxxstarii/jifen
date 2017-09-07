package com.reps.jifen.service;

import com.reps.core.exception.RepsException;
import com.reps.core.orm.ListResult;
import com.reps.jifen.entity.JfTeacherPointsAssign;

public interface IJfTeacherPointsAssignService {

	void save(JfTeacherPointsAssign data);
	
	/**
	 * 通过教师ID查询分配给所有学生的记录
	 * @param teacherId
	 * @param pageIndex
	 * @param pageSize
	 * @return ListResult<JfTeacherPointsAssign>
	 * @throws RepsException
	 */
	ListResult<JfTeacherPointsAssign> findByTeacherId(String teacherId, Integer pageIndex, Integer pageSize) throws RepsException;
	
}
