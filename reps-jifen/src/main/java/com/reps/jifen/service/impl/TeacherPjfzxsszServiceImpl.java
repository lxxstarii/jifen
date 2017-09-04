package com.reps.jifen.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reps.core.orm.ListResult;
import com.reps.jifen.dao.TeacherPjfzxsszDao;
import com.reps.jifen.entity.TeacherPjfzxssz;
import com.reps.jifen.service.ITeacherPjfzxsszService;

@Transactional
@Service("com.reps.jifen.service.impl.TeacherPjfzxsszServiceImpl")
public class TeacherPjfzxsszServiceImpl implements ITeacherPjfzxsszService {

	@Autowired
	TeacherPjfzxsszDao xsszDao;
	
	@Override
	public void saveOrUpdate(TeacherPjfzxssz data) {
		xsszDao.saveOrUpdate(data);
	}

	@Override
	public List<TeacherPjfzxssz> findAll() {
		return xsszDao.findAll();
	}

	@Override
	public ListResult<TeacherPjfzxssz> query(TeacherPjfzxssz query, int start,
			int pageSize) {
		return xsszDao.query(query, start, pageSize);
	}

}
