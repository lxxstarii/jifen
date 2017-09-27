package com.reps.jifen.service;

import java.util.List;

import com.reps.core.orm.ListResult;
import com.reps.jifen.entity.StudyAssessPoints;


public interface IAssessPointsService {

	void save(StudyAssessPoints data);
	
	void update(StudyAssessPoints data);
	
	StudyAssessPoints get(String id);
	
	void delete(String id);
	
	List<StudyAssessPoints> find(StudyAssessPoints query);
	
	ListResult<StudyAssessPoints> query(int start, int pageSize, StudyAssessPoints query);
}
