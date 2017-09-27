package com.reps.jifen.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.reps.jifen.entity.PointsCollect;

public interface PointsCollectRepository extends MongoRepository<PointsCollect, String>{
	
	public Page<PointsCollect> findByPersonId(String personId, Pageable pageable);
}
