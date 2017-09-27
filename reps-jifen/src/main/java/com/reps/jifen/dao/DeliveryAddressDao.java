package com.reps.jifen.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.reps.core.orm.IGenericDao;
import com.reps.jifen.entity.DeliveryAddress;

@Repository
public class DeliveryAddressDao {

	@Autowired
	IGenericDao<DeliveryAddress> dao;
	
	public void save(DeliveryAddress data) {
		dao.save(data);
	}
	
	public void update(DeliveryAddress data) {
		dao.update(data);
	}
	
	public DeliveryAddress get(String id) {
		DeliveryAddress data = dao.get(DeliveryAddress.class, id);
		return data;
	}
	
	public void delete(String id) {
		DeliveryAddress data = get(id);
		if (data != null) {
			dao.delete(data);
		}
	}
	
	public List<DeliveryAddress> find(DeliveryAddress query) {
		DetachedCriteria dc = DetachedCriteria.forClass(DeliveryAddress.class);
		if (query != null) {
			if (StringUtils.isBlank(query.getPersonId())) {
				dc.add(Restrictions.eq("personId", query.getPersonId()));
			}
		}
		dc.addOrder(Order.desc("isDefault"));
		return dao.findByCriteria(dc);
	}
	
	
}
