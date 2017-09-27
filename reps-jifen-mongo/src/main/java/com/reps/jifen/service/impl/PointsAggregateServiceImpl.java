package com.reps.jifen.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.reps.jifen.entity.PointsAggregate;
import com.reps.jifen.repository.IPointsAggregateRepository;
import com.reps.jifen.service.IPointsAggregateService;

@Service
public class PointsAggregateServiceImpl implements IPointsAggregateService {
	
	@Autowired
	private IPointsAggregateRepository repository;
	

	@Override
	public List<PointsAggregate> findByPersonId(String personId) {
		return repository.findByPersonId(personId);
	}
	
	@Override
	public List<PointsAggregate> findAll(Sort sort) {
		return repository.findAll(sort);
	}

	@Override
	public void save(PointsAggregate data) {
		repository.save(data);
	}

	@Override
	public void update(PointsAggregate data) {
		repository.save(data);
	}

	@Override
	public PointsAggregate getByPersonId(String personId) {
		return repository.getByPersonId(personId);
	}

}
