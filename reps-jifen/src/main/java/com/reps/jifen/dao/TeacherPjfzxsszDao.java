package com.reps.jifen.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.reps.core.orm.IGenericDao;
import com.reps.core.orm.ListResult;
import com.reps.jifen.entity.TeacherPjfzxssz;

@Repository
public class TeacherPjfzxsszDao {

	@Autowired
	IGenericDao<TeacherPjfzxssz> dao;
	
	public void save(TeacherPjfzxssz data) {
		dao.save(data);
	}
	
	public void update(TeacherPjfzxssz data) {
		dao.update(data);
	}
	
	public void saveOrUpdate(TeacherPjfzxssz data) {
		dao.saveOrUpdate(data);
	}
	
	public List<TeacherPjfzxssz> findAll() {
		return dao.findAll(TeacherPjfzxssz.class);
	}
	
	public ListResult<TeacherPjfzxssz> query(TeacherPjfzxssz query, int start, int pageSize) {
		DetachedCriteria dc = DetachedCriteria.forClass(TeacherPjfzxssz.class);
		
		return dao.query(dc, start, pageSize);
	}
}
