package com.reps.jifen.service.impl;

import static com.reps.jifen.entity.enums.CategoryType.*;
import static com.reps.jifen.entity.enums.RewardStatus.*;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.reps.core.orm.ListResult;
import com.reps.core.util.DateUtil;
import com.reps.core.util.StringUtil;
import com.reps.jifen.dao.JfRewardDao;
import com.reps.jifen.entity.JfReward;
import com.reps.jifen.entity.JfRewardCategory;
import com.reps.jifen.service.IJfActivityRewardService;

/**
 * 积分活动业务实现
 * @author qianguobing
 * @date 2017年8月18日 上午10:48:17
 */
@Service
public class JfActivityRewardServiceImpl implements IJfActivityRewardService {

	@Autowired
	JfRewardDao dao;
	
	@Override
	public void save(JfReward jfReward) {
		//设置上线时间
		setShowTimeDisp(jfReward);
		//设置截止时间
		setFinishTimeDisp(jfReward);
		jfReward.setCreateTime(new Date());
		//设置是否发布，默认未发布
		jfReward.setIsShown(UN_PUBLISH.getIndex());
		dao.save(jfReward);
	}

	@Override
	public void delete(JfReward jfReward) {
		dao.delete(jfReward);
	}

	@Override
	public void update(JfReward jfReward) {
		//设置上线时间
		setShowTimeDisp(jfReward);
		//设置截止时间
		setFinishTimeDisp(jfReward);
		dao.update(jfReward);
	}

	@Override
	public JfReward get(String id) {
		return dao.get(id);
	}

	@Override
	public ListResult<JfReward> query(int start, int pagesize, JfReward jfReward) {
		//设置截止时间
		setFinishTimeDisp(jfReward);
		return dao.query(start, pagesize, jfReward);
	}

	private void setFinishTimeDisp(JfReward jfReward) {
		String finishTimeDisp = jfReward.getFinishTimeDisp();
		if(StringUtil.isNotBlank(finishTimeDisp)) {
			Date finishTime = DateUtil.getDateFromStr(finishTimeDisp, "yyyy-MM-dd");
			jfReward.setFinishTime(finishTime);
		}
	}

	private void setShowTimeDisp(JfReward jfReward) {
		String showTimeDisp = jfReward.getShowTimeDisp();
		if(StringUtil.isNotBlank(showTimeDisp)) {
			Date showTime = DateUtil.getDateFromStr(showTimeDisp, "yyyy-MM-dd");
			jfReward.setShowTime(showTime);
		}
	}
	
	@Override
	public List<JfReward> getActivityRewardOfCategory(String cid) {
		return dao.getRewardOfCategory(cid);
	}

	@Override
	public void batchDelete(String ids) {
		dao.batchDelete(ids);
	}
	
	@Override
	public void batchPublish(String ids, Short status) {
		dao.batchUpdate(ids, status);
	}
	
	@Scheduled(cron = "0 0 2 * * ?")
//	@Scheduled(cron = "*/20 * * * * ?")
	public void activityExpired() {
		JfReward jfReward = new JfReward();
		JfRewardCategory category = new JfRewardCategory();
		category.setType(ACTIVITY.getIndex());
		jfReward.setIsShown(PUBLISHED.getIndex());
		jfReward.setJfRewardCategory(category);
		List<JfReward> activityList = getActivityRewardByCategoryType(jfReward);
		//获取当前系统时间
		long currentTime = System.currentTimeMillis();
		for (JfReward activity : activityList) {
			//获取活动过期时间
			long expireTime = activity.getFinishTime().getTime();
			if(expireTime - currentTime < 0) {
				dao.batchUpdate(activity.getId(), SOLD_OUT.getIndex());
			}
		}
	}

	@Override
	public List<JfReward> getActivityRewardByCategoryType(JfReward jfReward) {
		return dao.getRewardByCategoryType(jfReward);
	}

}
