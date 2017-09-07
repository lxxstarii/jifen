package com.reps.jifen.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.reps.core.orm.IGenericDao;
import com.reps.core.orm.ListResult;
import com.reps.jifen.entity.JfSystemConfig;

@Repository
public class JfSystemConfigDao {

	@Autowired
	IGenericDao<JfSystemConfig> dao;
	
	public void save(JfSystemConfig data) {
		dao.save(data);
	}
	
	public JfSystemConfig get(String id) {
		return dao.get(JfSystemConfig.class, id);
	}
	
	public void delete(String id) {
		JfSystemConfig data = get(id);
		if (data != null) {
			dao.delete(data);
		}
	}
	
	public ListResult<JfSystemConfig> query(int start, int pageSize, JfSystemConfig query) {
		DetachedCriteria dc = DetachedCriteria.forClass(JfSystemConfig.class);
		if (query != null) {
			
		}
		return dao.query(dc, start, pageSize);
	}
	
	public List<JfSystemConfig> find(JfSystemConfig query) {
		DetachedCriteria dc = DetachedCriteria.forClass(JfSystemConfig.class);
		if (query != null) {
			
		}
		return dao.findByCriteria(dc);
	}
}
