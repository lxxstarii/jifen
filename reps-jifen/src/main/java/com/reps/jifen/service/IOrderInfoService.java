package com.reps.jifen.service;

import java.util.List;

import com.reps.core.orm.ListResult;
import com.reps.jifen.entity.OrderInfo;

public interface IOrderInfoService {

	void save(OrderInfo data);
	
	void update(OrderInfo data);
	
	void delete(String id);
	
	OrderInfo get(String id, boolean eager);
	
	void delete(OrderInfo data);
	
	List<OrderInfo> find(OrderInfo query);
	
	ListResult<OrderInfo> query(int start, int pageSize, OrderInfo query);
}
