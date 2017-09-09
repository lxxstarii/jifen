package com.reps.jifen.service;

import com.reps.core.exception.RepsException;
import com.reps.jifen.entity.JfPointsAggregate;

public interface IJfPointsAggregateService {
	
	public JfPointsAggregate findByPersonId(String personId) throws RepsException;
	
}
