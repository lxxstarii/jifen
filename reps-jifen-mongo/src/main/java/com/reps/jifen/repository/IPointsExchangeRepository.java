package com.reps.jifen.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.reps.jifen.entity.PointsExchange;

public interface IPointsExchangeRepository extends MongoRepository<PointsExchange, String>{
	
	public Page<PointsExchange> findByPersonIdAndStatus(String personId, Short status, Pageable pageable);
	
	public Page<PointsExchange> findByPersonId(String personId, Pageable pageable);
	
	List<PointsExchange> findByPersonIdAndRewardIdAndOrderId(String personId, String rewardId, String orderId);
}
