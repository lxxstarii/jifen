package com.reps.jifen.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import com.reps.jifen.entity.JfTeacherPointsAssign;

@Component
public interface JfTeacherPointsAssignRepository extends MongoRepository<JfTeacherPointsAssign, String>{

}
