package com.reps.jifen.service;

import java.util.List;

import com.reps.core.orm.ListResult;
import com.reps.jifen.entity.JfReward;

/**
 * 活动奖品相关操作
 * 
 * @author qianguobing
 * @date 2017年8月18日 上午10:39:31
 */
public interface IJfActivityRewardService {

	/**
	 * 添加活动
	 * 
	 * @param jfReward
	 */
	public void save(JfReward jfReward);

	/**
	 * 删除活动
	 * 
	 * @param jfReward
	 */
	public void delete(JfReward jfReward);

	/**
	 * 修改活动
	 * 
	 * @param jfReward
	 */
	public void update(JfReward jfReward);

	/**
	 * 通过指定id获得对象
	 * 
	 * @param id
	 * @return JfReward
	 */
	public JfReward get(String id);

	/**
	 * 分页查询
	 * 
	 * @param start
	 * @param pagesize
	 * @param jfReward
	 * @return ListResult<JfReward>
	 */
	ListResult<JfReward> query(int start, int pagesize, JfReward jfReward);

	/**
	 * 根据奖品分类id，得到该分类下的所有活动
	 * 
	 * @param cid 分类id
	 * @return JfReward
	 */
	public List<JfReward> getRewardOfCategory(String cid);

	/**
	 * 根据活动名称，得到活动
	 * 
	 * @param name
	 * @return JfReward
	 */
	public JfReward getRewardByName(String name);

	/**
	 * 查询所有活动
	 * 
	 * @return List<JfReward>
	 */
	public List<JfReward> getAllReward();
	
	/**
	 * 批量删除活动
	 * @param ids
	 */
	public void batchDelete(String ids);

	/**
	 * 批量发布活动
	 * @param ids
	 */
	public void batchPublish(String ids);
	
	/**
	 * 根据分类类别查询活动
	 * @param jfReward
	 * @return List<JfReward>
	 */
	public List<JfReward> getActivityRewardByCategoryType(JfReward jfReward);

	/**
	 * 根据兑截止换时间来判断活动是否下架,设置为已下架 2
	 */
	public void activityExpired();

}
