package com.reps.jifen.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reps.jifen.entity.JfPointsAggregate;
import com.reps.jifen.repository.IJfPointsAggregateRepository;
import com.reps.jifen.service.IJfPointsAggregateService;

@Service
public class JfPointsAggregateServiceImpl implements IJfPointsAggregateService {
	
	@Autowired
	private IJfPointsAggregateRepository repository;
	

	@Override
	public List<JfPointsAggregate> findByPersonId(String personId) {
		return repository.findByPersonId(personId);
	}

	@Override
	public void save(JfPointsAggregate data) {
		repository.save(data);
	}

	@Override
	public void update(JfPointsAggregate data) {
		repository.save(data);
	}

	@Override
	public JfPointsAggregate getByPersonId(String personId) {
		return repository.getByPersonId(personId);
	}

}
