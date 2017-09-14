package com.reps.jifen.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.reps.jifen.entity.PointsExchange;

public interface IPointsExchangeRepository extends MongoRepository<PointsExchange, String>{
	
	public Page<PointsExchange> findByPersonId(String personId, Pageable pageable);
}
