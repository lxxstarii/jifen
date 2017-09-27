package com.reps.jifen.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reps.jifen.dao.DeliveryAddressDao;
import com.reps.jifen.entity.DeliveryAddress;
import com.reps.jifen.service.IDeliveryAddressService;

@Transactional
@Service("com.reps.jifen.service.impl.DeliveryAddressServiceImpl")
public class DeliveryAddressServiceImpl implements IDeliveryAddressService {

	@Autowired
	DeliveryAddressDao shippingDao;
	
	@Override
	public void save(DeliveryAddress data) {
		shippingDao.save(data);
	}

	@Override
	public void update(DeliveryAddress data) {
		shippingDao.update(data);
	}

	@Override
	public DeliveryAddress get(String id) {
		return shippingDao.get(id);
	}

	@Override
	public void delete(String id) {
		shippingDao.delete(id);
	}

	@Override
	public List<DeliveryAddress> find(DeliveryAddress query) {
		return shippingDao.find(query);
	}

	@Override
	public void saveNewDefault(DeliveryAddress query) {
		List<DeliveryAddress> list = find(query);
		if (list != null && !list.isEmpty()) {
			for (DeliveryAddress data : list) {
				data.setIsDefault((short) 0);
				shippingDao.update(data);
			}
		}
		shippingDao.save(query);
	}

	@Override
	public void updateDefault(DeliveryAddress query) {
		shippingDao.update(query);
		List<DeliveryAddress> list = shippingDao.find(query);
		if (list != null && !list.isEmpty()) {
			for (DeliveryAddress data : list) {
				if (!data.getId().equals(query.getId())) {
					data.setIsDefault((short) 0);
					shippingDao.update(data);
				}
			}
		}
	}

}
