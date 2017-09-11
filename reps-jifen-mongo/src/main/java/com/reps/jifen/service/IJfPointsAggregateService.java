package com.reps.jifen.service;

import java.util.List;

import com.reps.jifen.entity.JfPointsAggregate;

public interface IJfPointsAggregateService {

	List<JfPointsAggregate> findByPersonId(String personId);
	
	JfPointsAggregate getByPersonId(String personId);
	
	void save(JfPointsAggregate data);
	
	void update(JfPointsAggregate data);
}
