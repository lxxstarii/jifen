package com.reps.jifen.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.reps.jifen.entity.JfPointsExchange;

public interface IJfPointsExchangeRepository extends MongoRepository<JfPointsExchange, String>{
	
	public Page<JfPointsExchange> findByPersonId(String personId, Pageable pageable);
}
