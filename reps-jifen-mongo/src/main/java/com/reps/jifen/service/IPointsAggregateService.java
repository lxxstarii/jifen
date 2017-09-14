package com.reps.jifen.service;

import java.util.List;

import com.reps.jifen.entity.PointsAggregate;

public interface IPointsAggregateService {

	List<PointsAggregate> findByPersonId(String personId);
	
	PointsAggregate getByPersonId(String personId);
	
	void save(PointsAggregate data);
	
	void update(PointsAggregate data);
}
