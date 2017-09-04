package com.reps.jifen.service;

import java.util.List;

import com.reps.core.orm.ListResult;
import com.reps.jifen.entity.TeacherPjkfpjf;

/**
 * 教师评价可分配积分
 * @author Lanxumit
 *
 */
public interface ITeacherPjkfpjfService {

	void save(TeacherPjkfpjf data);
	
	void update(TeacherPjkfpjf data);
	
	TeacherPjkfpjf findByTeacherId(String teacherId);
	
	List<TeacherPjkfpjf> find(TeacherPjkfpjf query);
	
	ListResult<TeacherPjkfpjf> query(TeacherPjkfpjf query, int start, int pageSize);
}
