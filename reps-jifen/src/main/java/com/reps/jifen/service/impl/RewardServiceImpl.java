package com.reps.jifen.service.impl;

import static com.reps.jifen.entity.enums.CategoryType.*;
import static com.reps.jifen.entity.enums.RewardStatus.*;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reps.core.exception.RepsException;
import com.reps.core.orm.ListResult;
import com.reps.core.util.StringUtil;
import com.reps.jifen.dao.PointRewardDao;
import com.reps.jifen.entity.PointReward;
import com.reps.jifen.entity.RewardCategory;
import com.reps.jifen.service.IRewardService;

/**
 * 积分物品业务实现类
 * 
 * @author qianguobing
 * @date 2017年8月18日 上午10:48:17
 */
@Service
@Transactional
public class RewardServiceImpl implements IRewardService {

	@Autowired
	PointRewardDao dao;

	@Override
	public void save(PointReward jfReward) {
		jfReward.setCreateTime(new Date());
		// 设置是否发布，默认未发布
		jfReward.setIsShown(UN_PUBLISH.getIndex());
		// 设置图片信息
		setPictureUrl(jfReward);
		dao.save(jfReward);
	}

	private void setPictureUrl(PointReward jfReward) {
		String rewardUrlOne = jfReward.getRewardUrlOne();
		String rewardUrlTwo = jfReward.getRewardUrlTwo();
		String rewardUrlThree = jfReward.getRewardUrlThree();
		String rewardUrlFour = jfReward.getRewardUrlFour();
		String rewardUrlFive = jfReward.getRewardUrlFive();
		String pictureUrlStr = StringUtil.join(new String[] { rewardUrlOne, rewardUrlTwo, rewardUrlThree, rewardUrlFour, rewardUrlFive }, ",");
		jfReward.setPicture(pictureUrlStr);
	}

	@Override
	public void delete(PointReward jfReward) throws RepsException{
		if(null == jfReward) {
			throw new RepsException("删除异常");
		}
		jfReward = this.get(jfReward.getId());
		Integer exchangedCount = jfReward.getExchangedCount();
		if(null != exchangedCount && exchangedCount.intValue() > 0) {
			throw new RepsException("删除异常:该物品有兑换记录不能删除");
		}
		dao.delete(jfReward);
	}

	@Override
	public void update(PointReward jfReward) {
		setPictureUrl(jfReward);
		dao.update(jfReward);
	}

	@Override
	public PointReward get(String id) throws RepsException{
		if(StringUtil.isBlank(id)) {
			throw new RepsException("查询异常:物品ID不能为空");
		}
		PointReward jfReward = dao.get(id);
		if(null == jfReward) {
			throw new RepsException("查询异常:查询物品不存在");
		}
		String picture = jfReward.getPicture();
		// 设置前台页面图片展示
		if (StringUtil.isNotBlank(picture)) {
			String[] picUrls = picture.split(",", -1);
			jfReward.setRewardUrlOne(picUrls[0]);
			jfReward.setRewardUrlTwo(picUrls[1]);
			jfReward.setRewardUrlThree(picUrls[2]);
			jfReward.setRewardUrlFour(picUrls[3]);
			jfReward.setRewardUrlFive(picUrls[4]);
		}
		return jfReward;
	}

	@Override
	public ListResult<PointReward> query(int start, int pagesize, PointReward jfReward) throws RepsException{
		return dao.query(start, pagesize, jfReward);
	}

	@Override
	public List<PointReward> getRewardOfCategory(String cid) {
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

	@Override
	public List<PointReward> getRewardByCategoryType(PointReward jfReward) {
		return dao.getRewardByCategoryType(jfReward);
	}

	@Override
	@Scheduled(cron = "0 0 2 * * ?")
	// @Scheduled(cron = "*/20 * * * * ?")
	public void rewardSoldOut() {
		PointReward reward = new PointReward();
		RewardCategory category = new RewardCategory();
		category.setType(REWARD.getIndex());
		reward.setIsShown(PUBLISHED.getIndex());
		reward.setJfRewardCategory(category);
		List<PointReward> rewardList = getRewardByCategoryType(reward);
		for (PointReward jfReward : rewardList) {
			if (0 == jfReward.getNumbers().intValue()) {
				dao.batchUpdate(jfReward.getId(), SOLD_OUT.getIndex());
			}
		}
	}

}
