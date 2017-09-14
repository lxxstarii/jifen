package com.reps.jifen.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reps.core.exception.RepsException;
import com.reps.core.orm.ListResult;
import com.reps.jifen.dao.PointLevelDao;
import com.reps.jifen.entity.PointLevel;
import com.reps.jifen.service.IPointLevelService;

@Service
@Transactional
public class PointLevelServiceImpl implements IPointLevelService {
	
	@Autowired
	PointLevelDao dao;

	@Override
	public void save(PointLevel jfPointLevel) {
		dao.save(jfPointLevel);
	}

	@Override
	public void delete(PointLevel jfPointLevel) {
		dao.delete(jfPointLevel);
	}

	@Override
	public void update(PointLevel jfPointLevel) {
		dao.update(jfPointLevel);
	}

	@Override
	public PointLevel get(String id) {
		return dao.get(id);
	}

	@Override
	public ListResult<PointLevel> query(int start, int pagesize, PointLevel jfPointLevel) {
		return dao.query(start, pagesize, jfPointLevel);
	}

	@Override
	public List<PointLevel> queryAll(PointLevel jfPointLevel) throws RepsException {
		return dao.queryAll(jfPointLevel);
	}

}
