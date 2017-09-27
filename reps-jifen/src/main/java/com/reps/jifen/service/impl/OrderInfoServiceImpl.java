package com.reps.jifen.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reps.core.orm.ListResult;
import com.reps.jifen.dao.OrderInfoDao;
import com.reps.jifen.entity.OrderInfo;
import com.reps.jifen.service.IOrderInfoService;

@Transactional
@Service("com.reps.jifen.service.impl.OrderInfoServiceImpl")
public class OrderInfoServiceImpl implements IOrderInfoService {
	
	@Autowired
	OrderInfoDao orderInfoDao;

	@Override
	public void save(OrderInfo data) {
		orderInfoDao.save(data);
	}

	@Override
	public void update(OrderInfo data) {
		orderInfoDao.update(data);
	}

	@Override
	public void delete(String id) {
		orderInfoDao.delete(id);
	}

	@Override
	public OrderInfo get(String id, boolean eager) {
		OrderInfo data = orderInfoDao.get(id);
		if (data != null && eager) {
			Hibernate.initialize(data.getReward());
		}
		return data;
	}

	@Override
	public void delete(OrderInfo data) {
		orderInfoDao.delete(data);
	}

	@Override
	public List<OrderInfo> find(OrderInfo query) {
		return orderInfoDao.find(query);
	}

	@Override
	public ListResult<OrderInfo> query(int start, int pageSize, OrderInfo query) {
		return orderInfoDao.query(start, pageSize, query);
	}

}
