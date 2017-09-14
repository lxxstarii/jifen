package com.reps.jifen.dao;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.reps.core.orm.IGenericDao;
import com.reps.core.orm.ListResult;
import com.reps.jifen.entity.GoodsExchange;

@Repository
public class GoodsExchangeDao {

	@Autowired
	IGenericDao<GoodsExchange> dao;
	
	public void save(GoodsExchange data) {
		dao.save(data);
	}
	
	public void update(GoodsExchange data) {
		dao.update(data);
	}
	
	public GoodsExchange get(String id) {
		return dao.get(GoodsExchange.class, id);
	}
	
	public void delete(String id) {
		GoodsExchange data = get(id);
		if (data != null) {
			dao.delete(data);
		}
	}
	
	public List<GoodsExchange> find(GoodsExchange query) {
		DetachedCriteria dc = DetachedCriteria.forClass(GoodsExchange.class);
		if (query != null) {
			if (StringUtils.isNotBlank(query.getPersonId())) {
				dc.add(Restrictions.eq("personId", query.getPersonId()));
			}
			if (query.getState() != null) {
				dc.add(Restrictions.eq("state", query.getState()));
			}
		}
		dc.addOrder(Order.desc("createTime"));
		return dao.findByCriteria(dc);
	}
	
	public ListResult<GoodsExchange> query(int start, int pageSize, GoodsExchange query) {
		DetachedCriteria dc = DetachedCriteria.forClass(GoodsExchange.class);
		if (query != null) {
			if (StringUtils.isNotBlank(query.getPersonId())) {
				dc.add(Restrictions.eq("personId", query.getPersonId()));
			}
			if (query.getState() != null) {
				dc.add(Restrictions.eq("state", query.getState()));
			}
		}
		return dao.query(dc, start, pageSize);
	}
 }
