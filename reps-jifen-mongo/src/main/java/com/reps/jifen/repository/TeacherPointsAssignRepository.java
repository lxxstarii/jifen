package com.reps.jifen.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import com.reps.jifen.entity.TeacherPointsAssign;

@Component
public interface TeacherPointsAssignRepository extends MongoRepository<TeacherPointsAssign, String>{

}
