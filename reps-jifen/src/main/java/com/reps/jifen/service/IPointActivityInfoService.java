package com.reps.jifen.service;

import com.reps.core.exception.RepsException;
import com.reps.core.orm.ListResult;
import com.reps.jifen.entity.PointActivityInfo;

public interface IPointActivityInfoService {
	
	public void save(PointActivityInfo activityInfo);
	
	public ListResult<PointActivityInfo> query(int start, int pagesize, PointActivityInfo activityInfo) throws RepsException;
	
	public Long count(String rewardId, Short isParticipate) throws RepsException;
	
}
