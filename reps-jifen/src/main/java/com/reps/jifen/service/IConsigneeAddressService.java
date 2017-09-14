package com.reps.jifen.service;

import java.util.List;

import com.reps.jifen.entity.ConsigneeAddress;

public interface IConsigneeAddressService {

	void save(ConsigneeAddress data);
	
	void update(ConsigneeAddress data);
	
	ConsigneeAddress get(String id);
	
	void delete(String id);
	
	/***
	 * 新增默认地址
	 * @param personId
	 */
	void saveNewDefault(ConsigneeAddress query);
	
	/**
	 * 修改默认地址
	 * @param query
	 */
	void updateDefault(ConsigneeAddress query);
	
	List<ConsigneeAddress> find(ConsigneeAddress query);
}
