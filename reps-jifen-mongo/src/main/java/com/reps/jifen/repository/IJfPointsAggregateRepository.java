package com.reps.jifen.repository;


import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.reps.jifen.entity.JfPointsAggregate;

public interface IJfPointsAggregateRepository extends MongoRepository<JfPointsAggregate, String>{
	
	public List<JfPointsAggregate> findByPersonId(String personId);
}
