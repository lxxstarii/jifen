package com.reps.jifen.dao;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.reps.core.orm.IGenericDao;
import com.reps.core.orm.ListResult;
import com.reps.jifen.entity.TeacherPjkfpjf;

@Repository
public class TeacherPjkfpjfDao {

	@Autowired
	IGenericDao<TeacherPjkfpjf> dao;
	
	public void save(TeacherPjkfpjf data) {
		dao.save(data);
	}
	
	public void update(TeacherPjkfpjf data) {
		dao.update(data);
	}  
	
	public TeacherPjkfpjf get(String id) {
		return dao.get(TeacherPjkfpjf.class, id);
	}
	
	public void delete(String id) {
		TeacherPjkfpjf data = get(id);
		if (data != null) {
			dao.delete(data);
		}
	}
	
	public ListResult<TeacherPjkfpjf> query(int start, int pageSize, TeacherPjkfpjf query) {
		DetachedCriteria dc = DetachedCriteria.forClass(TeacherPjkfpjf.class);
		dc.createAlias("organize", "organize");
		dc.createAlias("teacher", "teacher");
		dc.createAlias("teacher.person", "person");
		if (query != null) {
			if (StringUtils.isNotBlank(query.getAccountId())) {
				dc.add(Restrictions.eq("accountId", query.getAccountId()));
			}
			if (StringUtils.isNotBlank(query.getTeacherId())) {
				dc.add(Restrictions.eq("teacherId", query.getTeacherId()));
			}
			if (StringUtils.isNotBlank(query.getOrganizeId())) {
				dc.add(Restrictions.eq("organizeId", query.getOrganizeId()));
			}
			
			if (query.getTeacher() != null) {
				if (query.getTeacher().getPerson() != null) {
					if (StringUtils.isNotBlank(query.getTeacher().getPerson().getName())) {
						dc.add(Restrictions.eq("person.name", query.getTeacher().getPerson().getName()));
					}
					if (StringUtils.isNotBlank(query.getTeacher().getPerson().getIcNumber())) {
						dc.add(Restrictions.eq("person.icNumber", query.getTeacher().getPerson().getIcNumber()));
					}
				}
			}
			
		}
		
		return dao.query(dc, start, pageSize);
	}
	
	public List<TeacherPjkfpjf> find(TeacherPjkfpjf query) {
		DetachedCriteria dc = DetachedCriteria.forClass(TeacherPjkfpjf.class);
		if (query != null) {
			if (StringUtils.isNotBlank(query.getAccountId())) {
				dc.add(Restrictions.eq("accountId", query.getAccountId()));
			}
			if (StringUtils.isNotBlank(query.getTeacherId())) {
				dc.add(Restrictions.eq("teacherId", query.getTeacherId()));
			}
			if (StringUtils.isNotBlank(query.getOrganizeId())) {
				dc.add(Restrictions.eq("organizeId", query.getOrganizeId()));
			}
			
		}
		return dao.findByCriteria(dc);
	}
}
