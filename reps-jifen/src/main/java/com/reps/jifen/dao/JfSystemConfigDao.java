package com.reps.jifen.dao;

import java.util.List;

import com.reps.core.util.StringUtil;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
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
			dc = buildCriteria(dc, query);
		}
		return dao.query(dc, start, pageSize, Order.asc("id"));
	}

	private DetachedCriteria buildCriteria(DetachedCriteria dc, JfSystemConfig query) {
		if (StringUtil.isNotBlank(query.getCode())) {
			dc.add(Restrictions.eq("code", query.getCode()));
		}
		if (StringUtil.isNotBlank(query.getItem())) {
			dc.add(Restrictions.like("item", query.getItem(), MatchMode.ANYWHERE));
		}
		if (StringUtil.isNotBlank(query.getApplicationName())) {
			dc.add(Restrictions.like("applicationName", query.getApplicationName(), MatchMode.ANYWHERE));
		}
		if (query.getIsEnabled() != null) {
			dc.add(Restrictions.eq("isEnabled", query.getIsEnabled()));
		}
		if (query.getMark() != null) {
			dc.add(Restrictions.eq("mark", query.getMark()));
		}
		if (query.getPoints() != null) {
			dc.add(Restrictions.eq("points", query.getPoints()));
		}
		if (query.getNeedCheck() != null) {
			dc.add(Restrictions.eq("needCheck", query.getNeedCheck()));
		}
		return dc;
	}
	public List<JfSystemConfig> find(JfSystemConfig query) {
		DetachedCriteria dc = DetachedCriteria.forClass(JfSystemConfig.class);
		if (query != null) {
			dc = buildCriteria(dc, query);
		}
		return dao.findByCriteria(dc);
	}

	public void update(JfSystemConfig config) {
		dao.getSession().clear();
		dao.update(config);
	}

	public long count(JfSystemConfig query) {
		DetachedCriteria dc = DetachedCriteria.forClass(JfSystemConfig.class);
		if (query != null) {
			dc = buildCriteria(dc, query);
		}
		return dao.getRowCount(dc);
	}
}
