package com.reps.jifen.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.reps.jifen.entity.JfPointsCollect;

public interface JfPointsCollectRepository extends MongoRepository<JfPointsCollect, String>{
	
	public Page<JfPointsCollect> findByPersonId(String personId, Pageable pageable);
}
