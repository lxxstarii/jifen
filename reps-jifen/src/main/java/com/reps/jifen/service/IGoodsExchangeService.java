package com.reps.jifen.service;

import java.util.List;

import com.reps.core.orm.ListResult;
import com.reps.jifen.entity.GoodsExchange;

public interface IGoodsExchangeService {

	void save(GoodsExchange data);
	
	void update(GoodsExchange data);
	
	void delete(String id);
	
	GoodsExchange get(String id);
	
	GoodsExchange delete(GoodsExchange data);
	
	List<GoodsExchange> find(GoodsExchange query);
	
	ListResult<GoodsExchange> query(int start, int pageSize, GoodsExchange query);
}
