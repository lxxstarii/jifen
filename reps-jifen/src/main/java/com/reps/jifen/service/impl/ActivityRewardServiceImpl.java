package com.reps.jifen.service.impl;

import static com.reps.core.util.DateUtil.format;
import static com.reps.core.util.DateUtil.getDateFromStr;
import static com.reps.jifen.entity.enums.CategoryType.ACTIVITY;
import static com.reps.jifen.entity.enums.RewardStatus.PUBLISHED;
import static com.reps.jifen.entity.enums.RewardStatus.SOLD_OUT;
import static com.reps.jifen.entity.enums.RewardStatus.UN_PUBLISH;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.reps.core.exception.RepsException;
import com.reps.core.orm.ListResult;
import com.reps.core.util.StringUtil;
import com.reps.jifen.dao.PointRewardDao;
import com.reps.jifen.entity.PointReward;
import com.reps.jifen.entity.RewardCategory;
import com.reps.jifen.service.IActivityRewardService;

/**
 * 积分活动业务实现
 * @author qianguobing
 * @date 2017年8月18日 上午10:48:17
 */
@Service
@Transactional
public class ActivityRewardServiceImpl implements IActivityRewardService {
	
	protected final Logger logger = LoggerFactory.getLogger(ActivityRewardServiceImpl.class);

	@Autowired
	PointRewardDao dao;
	
	@Override
	public void save(PointReward jfReward) throws RepsException{
		//设置上线时间
		Date showTime = setShowTimeDisp(jfReward);
		//设置截止时间
		Date finishTime = setFinishTimeDisp(jfReward);
		if(null != showTime && null != finishTime) {
			if(finishTime.getTime() < showTime.getTime()) {
				throw new RepsException("保存异常:截止日期小于上线日期");
			}
		}
		jfReward.setCreateTime(new Date());
		//设置是否发布，默认未发布
		jfReward.setIsShown(UN_PUBLISH.getIndex());
		dao.save(jfReward);
	}

	@Override
	public void delete(PointReward jfReward) throws RepsException{
		if(null == jfReward) {
			throw new RepsException("删除异常");
		}
		Integer exchangedCount = jfReward.getExchangedCount();
		if(null != exchangedCount && exchangedCount.intValue() > 0) {
			throw new RepsException("删除异常:该活动有人参与过不能删除");
		}
		dao.delete(jfReward);
	}

	@Override
	public void update(PointReward jfReward) {
		//设置上线时间
		setShowTimeDisp(jfReward);
		//设置截止时间
		setFinishTimeDisp(jfReward);
		dao.update(jfReward);
	}

	@Override
	public PointReward get(String id) throws RepsException{
		if(StringUtil.isBlank(id)) {
			throw new RepsException("查询异常:活动ID不能为空");
		}
		return dao.get(id);
	}

	@Override
	public ListResult<PointReward> query(int start, int pagesize, PointReward jfReward) {
		//设置截止时间
		setFinishTimeDisp(jfReward);
		return dao.query(start, pagesize, jfReward);
	}

	private Date setFinishTimeDisp(PointReward jfReward) {
		String finishTimeDisp = jfReward.getFinishTimeDisp();
		if(StringUtil.isNotBlank(finishTimeDisp)) {
			Date finishTime = getDateFromStr(finishTimeDisp, "yyyy-MM-dd");
			jfReward.setFinishTime(finishTime);
			return finishTime;
		}else {
			return null;
		}
	}

	private Date setShowTimeDisp(PointReward jfReward) {
		String showTimeDisp = jfReward.getShowTimeDisp();
		if(StringUtil.isNotBlank(showTimeDisp)) {
			Date showTime = getDateFromStr(showTimeDisp, "yyyy-MM-dd");
			jfReward.setShowTime(showTime);
			return showTime;
		}else {
			return null;
		}
	}
	
	@Override
	public List<PointReward> getActivityRewardOfCategory(String cid) {
		return dao.getRewardOfCategory(cid);
	}

	@Override
	public void batchDelete(String ids) {
		dao.batchDelete(ids);
	}
	
	@Override
	public void batchPublish(String ids, Short status) throws RepsException{
		if (StringUtil.isBlank(ids)) {
			throw new RepsException("发布异常:活动ID不能为空");
		}
		if (null == status) {
			throw new RepsException("发布异常:活动状态不能为空");
		}
		String[] idArray = ids.split(",");
		for (String id : idArray) {
			PointReward pointReward = this.get(id);
			Date finishTime = pointReward.getFinishTime();
			if(finishTime.getTime() < getDateFromStr(format(new Date(), "yyyy-MM-dd"), "yyyy-MM-dd").getTime()) {
				throw new RepsException("发布异常:活动截止时间小于当前时间,请修改截止时间后再发布！");
			}
		}
		dao.batchUpdate(ids, status);
	}
	
	@Scheduled(cron = "0 0 2 * * ?")
//	@Scheduled(cron = "*/20 * * * * ?")
	public void activityExpired() {
		try {
			PointReward jfReward = new PointReward();
			RewardCategory category = new RewardCategory();
			category.setType(ACTIVITY.getIndex());
			jfReward.setIsShown(PUBLISHED.getIndex());
			jfReward.setJfRewardCategory(category);
			List<PointReward> activityList = getActivityRewardByCategoryType(jfReward);
			//获取当前系统时间
			long currentTime = getDateFromStr(format(new Date(), "yyyy-MM-dd"), "yyyy-MM-dd").getTime();
			for (PointReward activity : activityList) {
				//获取活动过期时间
				long expireTime = activity.getFinishTime().getTime();
				if(expireTime - currentTime < 0) {
					try {
						dao.batchUpdate(activity.getId(), SOLD_OUT.getIndex());
					} catch (Exception e) {
						logger.error("活动过期状态更新失败,该活动信息为:活动ID " + jfReward.getId() + ", 活动状态  " + jfReward.getIsShown() + ", 活动截止时间 " + jfReward.getFinishTime());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("活动过期异常", e);
		}
	}

	@Override
	public List<PointReward> getActivityRewardByCategoryType(PointReward jfReward) {
		return dao.getRewardByCategoryType(jfReward);
	}
	
	@Override
	public void delay(PointReward jfReward) throws RepsException {
		if(null == jfReward) {
			throw new RepsException("活动数据异常");
		}
		String id = jfReward.getId();
		if(StringUtil.isBlank(id)) {
			throw new RepsException("数据异常:活动ID不能为空");
		}
		String finishTime = jfReward.getFinishTimeDisp();
		if(StringUtil.isBlank(finishTime)) {
			throw new RepsException("数据异常:活动截止时间不能为空");
		}
		dao.updateFinishTime(id, finishTime);
		batchPublish(id, PUBLISHED.getIndex());
	}

}
