package com.reps.jifen.service;

import java.util.List;

import com.reps.core.orm.ListResult;
import com.reps.jifen.entity.TeacherPjfzxssz;

/**
 * 教师评价分值系数设置
 * @author Lanxumit
 *
 */
public interface ITeacherPjfzxsszService {

	void saveOrUpdate(TeacherPjfzxssz data);
	
	List<TeacherPjfzxssz> findAll();
	
	ListResult<TeacherPjfzxssz> query(TeacherPjfzxssz query, int start, int pageSize);
	
}
