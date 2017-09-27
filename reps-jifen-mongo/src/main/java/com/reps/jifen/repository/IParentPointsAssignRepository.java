package com.reps.jifen.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.reps.jifen.entity.ParentPointsAssign;

public interface IParentPointsAssignRepository extends MongoRepository<ParentPointsAssign, String>{
	
	public Page<ParentPointsAssign> findByParentIdAndStudentId(String parentId, String studentId, Pageable pageable);
	
	@Query(value = "{'parentId': ?2, 'studentId': ?3, 'behaviorTime':{'$gte': ?0, '$lte': ?1}}")
	List<ParentPointsAssign> findBehaviorTimeBetween(Date monday, Date weekend, String parentId, String studentId);
}
