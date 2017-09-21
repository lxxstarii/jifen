package com.reps.jifen.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.reps.core.exception.RepsException;
import com.reps.core.orm.ListResult;
import com.reps.core.util.StringUtil;
import com.reps.jifen.entity.PointsCollect;
import com.reps.jifen.repository.PointsCollectRepository;
import com.reps.jifen.service.IPointsCollectService;

@Service
public class PointsCollectServiceImpl implements IPointsCollectService {

	protected final Logger logger = LoggerFactory
			.getLogger(PointsCollectServiceImpl.class);

	@Autowired
	PointsCollectRepository repository;

	@Override
	public PointsCollect save(PointsCollect jfPointsCollect)
			throws RepsException {
		try {
			// 设置积分收集时间与获取时间，暂时一样
			jfPointsCollect.setCollectTime(new Date());
			jfPointsCollect.setGetTime(new Date());
			return repository.save(jfPointsCollect);
		} catch (Exception e) {
			logger.error("保存失败", e);
			throw new RepsException("保存失败", e);
		}
	}

	@Override
	public ListResult<PointsCollect> findByPersonId(String personId,
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
		Page<PointsCollect> page = repository.findByPersonId(personId,
				pageRequest);
		if (null == page) {
			throw new RepsException("mongodb查询失败");
		}
		ListResult<PointsCollect> listResult = new ListResult<>(
				page.getContent(), page.getTotalElements());
		listResult.setPageSize(pageSize);
		return listResult;
	}

}
