package com.reps.jifen.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reps.core.orm.ListResult;
import com.reps.jifen.dao.JfPointLevelDao;
import com.reps.jifen.entity.JfPointLevel;
import com.reps.jifen.service.IJfPointLevelService;

@Service
public class JfPointLevelServiceImpl implements IJfPointLevelService {
	
	@Autowired
	JfPointLevelDao dao;

	@Override
	public void save(JfPointLevel jfPointLevel) {
		dao.save(jfPointLevel);
	}

	@Override
	public void delete(JfPointLevel jfPointLevel) {
		dao.delete(jfPointLevel);
	}

	@Override
	public void update(JfPointLevel jfPointLevel) {
		dao.update(jfPointLevel);
	}

	@Override
	public JfPointLevel get(String id) {
		return dao.get(id);
	}

	@Override
	public ListResult<JfPointLevel> query(int start, int pagesize, JfPointLevel jfPointLevel) {
		return dao.query(start, pagesize, jfPointLevel);
	}

}
