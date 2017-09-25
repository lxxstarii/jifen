package com.reps.jifen.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.reps.jifen.entity.PointsAggregate;

public interface IPointsAggregateRepository extends MongoRepository<PointsAggregate, String> {

	List<PointsAggregate> findByPersonId(String personId);
	
	PointsAggregate getByPersonId(String personId);
	
}
