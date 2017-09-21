package com.reps.jifen.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reps.core.exception.RepsException;
import com.reps.core.orm.ListResult;
import com.reps.core.util.StringUtil;
import com.reps.jifen.dao.ActivityInfoDao;
import com.reps.jifen.entity.PointActivityInfo;
import com.reps.jifen.service.IPointActivityInfoService;

/**
 * 活动统计管理
 * @author qianguobing
 * @date 2017年9月19日 上午9:40:07
 */
@Service
@Transactional
public class PointActivityInfoServiceImpl implements IPointActivityInfoService {
	
	@Autowired
	ActivityInfoDao dao;

	@Override
	public void save(PointActivityInfo activityInfo) {
		dao.save(activityInfo);
	}

	@Override
	public ListResult<PointActivityInfo> query(int start, int pagesize, PointActivityInfo activityInfo) throws RepsException{
		if(null == activityInfo) {
			throw new RepsException("参数异常");
		}
		return dao.query(start, pagesize, activityInfo);
	}

	@Override
	public Long count(String rewardId, Short isParticipate) throws RepsException {
		if(StringUtil.isBlank(rewardId)) {
			throw new RepsException("统计异常:活动ID不能为空");
		}
		if(null == isParticipate) {
			throw new RepsException("统计异常:参与状态不能为空");
		}
		return dao.count(rewardId, isParticipate);
	}

}
