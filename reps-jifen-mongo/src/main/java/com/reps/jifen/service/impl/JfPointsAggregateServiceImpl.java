package com.reps.jifen.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reps.core.exception.RepsException;
import com.reps.core.util.StringUtil;
import com.reps.jifen.entity.JfPointsAggregate;
import com.reps.jifen.repository.IJfPointsAggregateRepository;
import com.reps.jifen.service.IJfPointsAggregateService;

@Service
public class JfPointsAggregateServiceImpl implements IJfPointsAggregateService{
	
	@Autowired
	private IJfPointsAggregateRepository repository;

	@Override
	public JfPointsAggregate findByPersonId(String personId) throws RepsException {
		if(StringUtil.isBlank(personId)) {
			throw new RepsException("人员ID不能为空");
		}
		List<JfPointsAggregate> pointList = repository.findByPersonId(personId);
		if(null != pointList && pointList.size() > 0) {
			return pointList.get(0);
		}
		return null;
	}

}
