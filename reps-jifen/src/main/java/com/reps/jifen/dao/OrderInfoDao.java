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
import com.reps.jifen.entity.OrderInfo;

@Repository
public class OrderInfoDao {

	@Autowired
	IGenericDao<OrderInfo> dao;
	
	public void save(OrderInfo data) {
		dao.save(data);
	}
	
	public void update(OrderInfo data) {
		dao.update(data);
	}
	
	public OrderInfo get(String id) {
		return dao.get(OrderInfo.class, id);
	}
	
	public void delete(String id) {
		OrderInfo data = get(id);
		if (data != null) {
			dao.delete(data);
		}
	}
	
	public void delete(OrderInfo data) {
		dao.delete(data);;
	}
	
	public List<OrderInfo> find(OrderInfo query) {
		DetachedCriteria dc = DetachedCriteria.forClass(OrderInfo.class);
		if (query != null) {
			if (StringUtils.isNotBlank(query.getPersonId())) {
				dc.add(Restrictions.eq("personId", query.getPersonId()));
			}
			if (query.getStatus() != null) {
				dc.add(Restrictions.eq("status", query.getStatus()));
			}
		}
		dc.addOrder(Order.desc("createTime"));
		return dao.findByCriteria(dc);
	}
	
	public ListResult<OrderInfo> query(int start, int pageSize, OrderInfo query) {
		DetachedCriteria dc = DetachedCriteria.forClass(OrderInfo.class);
		if (query != null) {
			if (StringUtils.isNotBlank(query.getPersonId())) {
				dc.add(Restrictions.eq("personId", query.getPersonId()));
			}
			if (StringUtils.isNotBlank(query.getOrderNo())) {
				dc.add(Restrictions.eq("orderNo", query.getOrderNo()));
			}
			if (query.getStatus() != null) {
				dc.add(Restrictions.eq("status", query.getStatus()));
			}
		}
		return dao.query(dc, start, pageSize);
	}
 }
