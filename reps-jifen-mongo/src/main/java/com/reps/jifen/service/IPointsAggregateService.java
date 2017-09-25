package com.reps.jifen.service;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.reps.jifen.entity.PointsAggregate;

public interface IPointsAggregateService {

	List<PointsAggregate> findByPersonId(String personId);
	
	PointsAggregate getByPersonId(String personId);
	
	List<PointsAggregate> findAll(Sort sort);
	
	void save(PointsAggregate data);
	
	void update(PointsAggregate data);
}
