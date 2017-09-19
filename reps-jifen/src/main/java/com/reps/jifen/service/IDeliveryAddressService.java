package com.reps.jifen.service;

import java.util.List;

import com.reps.jifen.entity.DeliveryAddress;

public interface IDeliveryAddressService {

	void save(DeliveryAddress data);
	
	void update(DeliveryAddress data);
	
	DeliveryAddress get(String id);
	
	void delete(String id);
	
	/***
	 * 新增默认地址
	 * @param personId
	 */
	void saveNewDefault(DeliveryAddress query);
	
	/**
	 * 修改默认地址
	 * @param query
	 */
	void updateDefault(DeliveryAddress query);
	
	List<DeliveryAddress> find(DeliveryAddress query);
}
