package com.reps.jifen.dao;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.reps.core.orm.IGenericDao;
import com.reps.core.orm.ListResult;
import com.reps.jifen.entity.StudyAssessPoints;

@Repository
public class AssessPointsDao {

	@Autowired
	private IGenericDao<StudyAssessPoints> dao;
	
	public void save(StudyAssessPoints data) {
		dao.save(data);
	}
	
	public StudyAssessPoints get(String id) {
		return dao.get(StudyAssessPoints.class, id);
	}
	
	public void update(StudyAssessPoints data) {
		dao.update(data);
	}
	
	public void delete(String id) {
		StudyAssessPoints data = get(id);
		if (data != null) {
			dao.delete(data);
		}
	}
	
	public ListResult<StudyAssessPoints> query(int start, int pageSize, StudyAssessPoints query) {
		DetachedCriteria dc = DetachedCriteria.forClass(StudyAssessPoints.class);
		if (query != null) {
			if (StringUtils.isNotBlank(query.getCategory())) {
				dc.add(Restrictions.eq("category", query.getCategory()));
			}
			if (StringUtils.isNotBlank(query.getItem())) {
				dc.add(Restrictions.like("item", query.getItem(), MatchMode.ANYWHERE));
			}
			if (query.getMark() != null) {
				dc.add(Restrictions.eq("mark", query.getMark()));
			}
		}
		return dao.query(dc, start, pageSize);
	}
	
	public List<StudyAssessPoints> find(StudyAssessPoints query) {
		DetachedCriteria dc = DetachedCriteria.forClass(StudyAssessPoints.class);
		if (query != null) {
			if (StringUtils.isNotBlank(query.getCategory())) {
				dc.add(Restrictions.eq("category", query.getCategory()));
			}
			if (StringUtils.isNotBlank(query.getItem())) {
				dc.add(Restrictions.like("item", query.getItem(), MatchMode.ANYWHERE));
			}
			if (query.getMark() != null) {
				dc.add(Restrictions.eq("mark", query.getMark()));
			}
			if (query.getIsEnable() != null) {
				dc.add(Restrictions.eq("isEnable", query.getIsEnable()));			}
		} 
		return dao.findByCriteria(dc);
	}
}
