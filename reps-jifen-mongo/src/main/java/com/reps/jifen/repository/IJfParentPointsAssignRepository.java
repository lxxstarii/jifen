package com.reps.jifen.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.reps.jifen.entity.JfParentPointsAssign;

public interface IJfParentPointsAssignRepository extends MongoRepository<JfParentPointsAssign, String>{
	
	public Page<JfParentPointsAssign> findByParentIdAndStudentId(String parentId, String studentId, Pageable pageable);
}
