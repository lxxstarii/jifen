package com.reps.jifen.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.reps.core.orm.IGenericDao;
import com.reps.jifen.entity.ConsigneeAddress;

@Repository
public class ConsigneeAddressDao {

	@Autowired
	IGenericDao<ConsigneeAddress> dao;
	
	public void save(ConsigneeAddress data) {
		dao.save(data);
	}
	
	public void update(ConsigneeAddress data) {
		dao.update(data);
	}
	
	public ConsigneeAddress get(String id) {
		ConsigneeAddress data = dao.get(ConsigneeAddress.class, id);
		return data;
	}
	
	public void delete(String id) {
		ConsigneeAddress data = get(id);
		if (data != null) {
			dao.delete(data);
		}
	}
	
	public List<ConsigneeAddress> find(ConsigneeAddress query) {
		DetachedCriteria dc = DetachedCriteria.forClass(ConsigneeAddress.class);
		if (query != null) {
			if (StringUtils.isBlank(query.getPersonId())) {
				dc.add(Restrictions.eq("personId", query.getPersonId()));
			}
		}
		dc.addOrder(Order.desc("isDefault"));
		return dao.findByCriteria(dc);
	}
	
	
}
