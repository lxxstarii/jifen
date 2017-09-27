package com.reps.jifen.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reps.core.orm.ListResult;
import com.reps.jifen.dao.TeacherPjkfpjfDao;
import com.reps.jifen.entity.TeacherPjkfpjf;
import com.reps.jifen.service.ITeacherPjkfpjfService;

@Transactional
@Service("com.reps.jifen.service.impl.TeacherPjkfpjfServiceImpl")
public class TeacherPjkfpjfServiceImpl implements ITeacherPjkfpjfService {
	
	@Autowired
	TeacherPjkfpjfDao kfpDao;
	
	@Override
	public void save(TeacherPjkfpjf data) {
		kfpDao.save(data);
	}

	@Override
	public void update(TeacherPjkfpjf data) {
		kfpDao.update(data);
	}

	@Override
	public TeacherPjkfpjf findByTeacherId(String teacherId, String organizeId) {
		TeacherPjkfpjf query = new TeacherPjkfpjf();
		query.setTeacherId(teacherId);
		query.setOrganizeId(organizeId);
		List<TeacherPjkfpjf> list = find(query);
		return list == null || list.isEmpty() ? null : list.get(0);
	}

	@Override
	public List<TeacherPjkfpjf> find(TeacherPjkfpjf query) {
		return kfpDao.find(query);
	}

	@Override
	public ListResult<TeacherPjkfpjf> query(TeacherPjkfpjf query, int start, int pageSize) {
		return kfpDao.query(start, pageSize, query);
	}

}
