package com.reps.jifen.service;

import java.util.List;

import com.reps.core.exception.RepsException;
import com.reps.core.orm.ListResult;
import com.reps.jifen.entity.PointReward;

/**
 * 活动奖品相关操作
 * 
 * @author qianguobing
 * @date 2017年8月18日 上午10:39:31
 */
public interface IActivityRewardService {

	/**
	 * 添加活动
	 * 
	 * @param jfReward
	 */
	public void save(PointReward jfReward) throws RepsException;

	/**
	 * 删除活动
	 * 
	 * @param jfReward
	 */
	public void delete(PointReward jfReward) throws RepsException;

	/**
	 * 修改活动
	 * 
	 * @param jfReward
	 */
	public void update(PointReward jfReward);

	/**
	 * 通过指定id获得对象
	 * 
	 * @param id
	 * @return JfReward
	 */
	public PointReward get(String id) throws RepsException;

	/**
	 * 分页查询
	 * 
	 * @param start
	 * @param pagesize
	 * @param jfReward
	 * @return ListResult<JfReward>
	 */
	ListResult<PointReward> query(int start, int pagesize, PointReward jfReward);

	/**
	 * 根据奖品分类id，得到该分类下的所有活动
	 * 
	 * @param cid 分类id
	 * @return JfReward
	 */
	public List<PointReward> getActivityRewardOfCategory(String cid);

	/**
	 * 批量删除活动
	 * @param ids
	 */
	public void batchDelete(String ids);

	/**
	 * 批量发布活动
	 * @param ids
	 * @param status
	 */
	public void batchPublish(String ids, Short status);
	
	/**
	 * 根据分类类别查询活动
	 * @param jfReward
	 * @return List<JfReward>
	 */
	public List<PointReward> getActivityRewardByCategoryType(PointReward jfReward);

	/**
	 * 根据兑截止换时间来判断活动是否下架,设置为已下架 2
	 */
	public void activityExpired();

}
