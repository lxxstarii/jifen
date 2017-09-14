package com.reps.jifen.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reps.jifen.dao.ConsigneeAddressDao;
import com.reps.jifen.entity.ConsigneeAddress;
import com.reps.jifen.service.IConsigneeAddressService;

@Transactional
@Service("com.reps.jifen.service.impl.ShippingAddressServiceImpl")
public class ConsigneeAddressServiceImpl implements IConsigneeAddressService {

	@Autowired
	ConsigneeAddressDao shippingDao;
	
	@Override
	public void save(ConsigneeAddress data) {
		shippingDao.save(data);
	}

	@Override
	public void update(ConsigneeAddress data) {
		shippingDao.update(data);
	}

	@Override
	public ConsigneeAddress get(String id) {
		return shippingDao.get(id);
	}

	@Override
	public void delete(String id) {
		shippingDao.delete(id);
	}

	@Override
	public List<ConsigneeAddress> find(ConsigneeAddress query) {
		return shippingDao.find(query);
	}

	@Override
	public void saveNewDefault(ConsigneeAddress query) {
		List<ConsigneeAddress> list = find(query);
		if (list != null && !list.isEmpty()) {
			for (ConsigneeAddress data : list) {
				data.setIsDefault((short) 0);
				update(data);
			}
		}
		save(query);
	}

	@Override
	public void updateDefault(ConsigneeAddress query) {
		List<ConsigneeAddress> list = find(query);
		if (list != null && !list.isEmpty()) {
			for (ConsigneeAddress data : list) {
				if (!data.getId().equals(query.getId())) {
					data.setIsDefault((short) 0);
					update(data);
				}
			}
		}
		update(query);
	}

}
