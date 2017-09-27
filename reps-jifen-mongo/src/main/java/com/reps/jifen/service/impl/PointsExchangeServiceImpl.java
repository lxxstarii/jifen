package com.reps.jifen.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import com.reps.core.exception.RepsException;
import com.reps.core.orm.ListResult;
import com.reps.core.util.StringUtil;
import com.reps.jifen.entity.PointsExchange;
import com.reps.jifen.repository.IPointsExchangeRepository;
import com.reps.jifen.service.IPointsExchangeService;

@Service
public class PointsExchangeServiceImpl implements IPointsExchangeService {

	protected final Logger logger = LoggerFactory
			.getLogger(PointsExchangeServiceImpl.class);

	@Autowired
	private IPointsExchangeRepository repository;

	@Override
	public ListResult<PointsExchange> findByPersonIdAndStatus(String personId, Short status,
			Integer pageIndex, Integer pageSize) throws RepsException {
		if (StringUtil.isBlank(personId)) {
			throw new RepsException("人员ID不能为空");
		}
		// 设置分页参数
		pageIndex = null == pageIndex ? 1 : pageIndex;
		pageSize = null == pageSize ? 20 : pageSize;
		// 构建分页对象
		PageRequest pageRequest = new PageRequest(pageIndex - 1,
				pageSize);
		Page<PointsExchange> page = repository.findByPersonIdAndStatus(personId, status,
				pageRequest);
		if (null == page) {
			throw new RepsException("mongodb查询失败");
		}
		ListResult<PointsExchange> listResult = new ListResult<>(
				page.getContent(), page.getTotalElements());
		listResult.setPageSize(pageSize);
		return listResult;
	}
	
	@Override
	public List<PointsExchange> findByCount(Integer count) throws RepsException {
		if (null == count) {
			throw new RepsException("查询异常:count不能为空");
		}
		// 构建分页对象
		PageRequest pageRequest = new PageRequest(0,
				count, new Sort(new Order(Direction.DESC, "exchangeTime")));
		Page<PointsExchange> page = repository.findAll(pageRequest);
		if (null == page) {
			throw new RepsException("mongodb查询失败");
		}
		return page.getContent();
	}
	
	@Override
	public ListResult<PointsExchange> findAll(Integer pageIndex, Integer pageSize) throws RepsException {
		// 设置分页参数
		pageIndex = null == pageIndex ? 1 : pageIndex;
		pageSize = null == pageSize ? 20 : pageSize;
		// 构建分页对象
		PageRequest pageRequest = new PageRequest(pageIndex - 1,
				pageSize, new Sort(new Order(Direction.DESC, "exchangeTime")));
		Page<PointsExchange> page = repository.findAll(pageRequest);
		if (null == page) {
			throw new RepsException("mongodb查询失败");
		}
		ListResult<PointsExchange> listResult = new ListResult<>(
				page.getContent(), page.getTotalElements());
		listResult.setPageSize(pageSize);
		return listResult;
	}
	
	@Override
	public ListResult<PointsExchange> findByPersonId(String personId,
			Integer pageIndex, Integer pageSize) throws RepsException {
		if (StringUtil.isBlank(personId)) {
			throw new RepsException("人员ID不能为空");
		}
		// 设置分页参数
		pageIndex = null == pageIndex ? 1 : pageIndex;
		pageSize = null == pageSize ? 20 : pageSize;
		// 构建分页对象
		PageRequest pageRequest = new PageRequest(pageIndex - 1,
				pageSize);
		Page<PointsExchange> page = repository.findByPersonId(personId, pageRequest);
		if (null == page) {
			throw new RepsException("mongodb查询失败");
		}
		ListResult<PointsExchange> listResult = new ListResult<>(
				page.getContent(), page.getTotalElements());
		listResult.setPageSize(pageSize);
		return listResult;
	}

	@Override
	public void save(PointsExchange data) {
		repository.save(data);
	}

	@Override
	public PointsExchange get(PointsExchange query) {
		List<PointsExchange> list = repository.findByPersonIdAndRewardIdAndOrderId(query.getPersonId(), query.getRewardId(), query.getOrderId());
		return list == null || list.isEmpty() ? null : list.get(0);
	}

}
