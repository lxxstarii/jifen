package com.reps.jifen.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.reps.jifen.entity.JfParentPointsAssign;

public interface IJfParentPointsAssignRepository extends MongoRepository<JfParentPointsAssign, String>{
	
	public Page<JfParentPointsAssign> findByParentIdAndStudentId(String parentId, String studentId, Pageable pageable);
	
	@Query(value = "{'parentId': ?2, 'studentId': ?3, 'behaviorTime':{'$gte': ?0, '$lte': ?1}}")
	List<JfParentPointsAssign> findBehaviorTimeBetween(Date monday, Date weekend, String parentId, String studentId);
}
