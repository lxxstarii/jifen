package com.reps.jifen.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reps.core.orm.ListResult;
import com.reps.jifen.dao.AssessPointsDao;
import com.reps.jifen.entity.StudyAssessPoints;
import com.reps.jifen.service.IAssessPointsService;

@Transactional
@Service("com.reps.jifen.service.impl.AssessPointsServiceImpl")
public class AssessPointsServiceImpl implements IAssessPointsService {

	@Autowired
	AssessPointsDao assessPointsDao;
	
	@Override
	public void save(StudyAssessPoints data) {
		assessPointsDao.save(data);
	}

	@Override
	public void update(StudyAssessPoints data) {
		assessPointsDao.update(data);
	}

	@Override
	public StudyAssessPoints get(String id) {
		return assessPointsDao.get(id);
	}

	@Override
	public List<StudyAssessPoints> find(StudyAssessPoints query) {
		return assessPointsDao.find(query);
	}

	@Override
	public ListResult<StudyAssessPoints> query(int start, int pageSize,
			StudyAssessPoints query) {
		return assessPointsDao.query(start, pageSize, query);
	}

	@Override
	public void delete(String id) {
		assessPointsDao.delete(id);
	}

}
