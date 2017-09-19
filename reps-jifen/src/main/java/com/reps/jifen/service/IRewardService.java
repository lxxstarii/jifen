package com.reps.jifen.service;

import java.util.List;

import com.reps.core.exception.RepsException;
import com.reps.core.orm.ListResult;
import com.reps.jifen.entity.PointReward;

/**
 * 物品相关操作
 * 
 * @author qianguobing
 * @date 2017年8月18日 上午10:39:31
 */
public interface IRewardService {

	/**
	 * 添加物品
	 * 
	 * @param jfReward
	 */
	public void save(PointReward jfReward);

	/**
	 * 删除物品
	 * 
	 * @param jfReward
	 */
	public void delete(PointReward jfReward) throws RepsException;

	/**
	 * 修改物品
	 * 
	 * @param jfReward
	 */
	public void update(PointReward jfReward);

	/**
	 * 通过指定id获得物品
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
	ListResult<PointReward> query(int start, int pagesize, PointReward jfReward) throws RepsException;

	/**
	 * 根据奖品分类id，得到该分类下的所有物品
	 * 
	 * @param cid 分类id
	 * @return JfReward
	 */
	public List<PointReward> getRewardOfCategory(String cid);

	/**
	 * 批量删除物品
	 * @param ids
	 */
	public void batchDelete(String ids);

	/**
	 * 批量发布物品
	 * @param ids
	 * @param status
	 */
	public void batchPublish(String ids, Short status) throws RepsException;
	
	/**
	 * 根据分类类别查询物品
	 * @param jfReward
	 * @return List<JfReward>
	 */
	public List<PointReward> getRewardByCategoryType(PointReward jfReward);
	
	/**
	 * 物品库存量为0时自动下架，设置为已下架 2
	 */
	public void rewardSoldOut();

}
