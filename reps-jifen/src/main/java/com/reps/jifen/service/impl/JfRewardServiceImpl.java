package com.reps.jifen.service.impl;

import static com.reps.jifen.entity.enums.CategoryType.*;
import static com.reps.jifen.entity.enums.RewardStatus.*;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.reps.core.orm.ListResult;
import com.reps.core.util.StringUtil;
import com.reps.jifen.dao.JfRewardDao;
import com.reps.jifen.entity.JfReward;
import com.reps.jifen.entity.JfRewardCategory;
import com.reps.jifen.service.IJfRewardService;

/**
 * 积分物品业务实现类
 * @author qianguobing
 * @date 2017年8月18日 上午10:48:17
 */
@Service
public class JfRewardServiceImpl implements IJfRewardService {

	@Autowired
	JfRewardDao dao;
	
	@Override
	public void save(JfReward jfReward) {
		jfReward.setCreateTime(new Date());
		//设置是否发布，默认未发布
		jfReward.setIsShown(UN_PUBLISH.getIndex());
		//设置图片信息
		setPictureUrl(jfReward);
		dao.save(jfReward);
	}
	
	private void setPictureUrl(JfReward jfReward) {
		String rewardUrlOne = jfReward.getRewardUrlOne();
		String rewardUrlTwo = jfReward.getRewardUrlTwo();
		String rewardUrlThree = jfReward.getRewardUrlThree();
		String rewardUrlFour = jfReward.getRewardUrlFour();
		String rewardUrlFive = jfReward.getRewardUrlFive();
		String pictureUrlStr = StringUtil.join(new String[] { rewardUrlOne, rewardUrlTwo, rewardUrlThree, rewardUrlFour, rewardUrlFive }, ",");
		jfReward.setPicture(pictureUrlStr);
	}

	@Override
	public void delete(JfReward jfReward) {
		dao.delete(jfReward);
	}

	@Override
	public void update(JfReward jfReward) {
		setPictureUrl(jfReward);
		dao.update(jfReward);
	}

	@Override
	public JfReward get(String id) {
		JfReward jfReward = dao.get(id);
		String picture = jfReward.getPicture();
		//设置前台页面图片展示
		if(StringUtil.isNotBlank(picture)) {
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
	public ListResult<JfReward> query(int start, int pagesize, JfReward jfReward) {
		return dao.query(start, pagesize, jfReward);
	}

	@Override
	public List<JfReward> getRewardOfCategory(String cid) {
		return dao.getRewardOfCategory(cid);
	}

	@Override
	public JfReward getRewardByName(String name) {
		return dao.getRewardByName(name);
	}

	@Override
	public List<JfReward> getAllReward() {
		return dao.getAllReward();
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
	public List<JfReward> getRewardByCategoryType(JfReward jfReward) {
		return dao.getRewardByCategoryType(jfReward);
	}
	
	@Override
	@Scheduled(cron = "0 0 2 * * ?")
//	@Scheduled(cron = "*/20 * * * * ?")
	public void rewardSoldOut() {
		JfReward reward = new JfReward();
		JfRewardCategory category = new JfRewardCategory();
		category.setType(REWARD.getIndex());
		reward.setIsShown(PUBLISHED.getIndex());
		reward.setJfRewardCategory(category);
		List<JfReward> rewardList = getRewardByCategoryType(reward);
		for (JfReward jfReward : rewardList) {
			if(0 == jfReward.getNumbers().intValue()) {
				dao.batchUpdate(jfReward.getId(), SOLD_OUT.getIndex());
			}
		}
	}

}
