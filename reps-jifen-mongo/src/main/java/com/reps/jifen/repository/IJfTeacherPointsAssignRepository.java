package com.reps.jifen.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.reps.jifen.entity.JfTeacherPointsAssign;

public interface IJfTeacherPointsAssignRepository extends MongoRepository<JfTeacherPointsAssign, String>{
	
	public Page<JfTeacherPointsAssign> findByTeacherId(String teacherId, Pageable pageable);
}
