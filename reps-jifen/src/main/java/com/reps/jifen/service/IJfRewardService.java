package com.reps.jifen.service;

import java.util.List;

import com.reps.core.orm.ListResult;
import com.reps.jifen.entity.JfReward;

/**
 * 物品相关操作
 * 
 * @author qianguobing
 * @date 2017年8月18日 上午10:39:31
 */
public interface IJfRewardService {

	/**
	 * 添加物品
	 * 
	 * @param jfReward
	 */
	public void save(JfReward jfReward);

	/**
	 * 删除物品
	 * 
	 * @param jfReward
	 */
	public void delete(JfReward jfReward);

	/**
	 * 修改物品
	 * 
	 * @param jfReward
	 */
	public void update(JfReward jfReward);

	/**
	 * 通过指定id获得物品
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
	 * 根据奖品分类id，得到该分类下的所有物品
	 * 
	 * @param cid 分类id
	 * @return JfReward
	 */
	public List<JfReward> getRewardOfCategory(String cid);

	/**
	 * 根据物品名称，得到物品
	 * 
	 * @param name
	 * @return JfReward
	 */
	public JfReward getRewardByName(String name);

	/**
	 * 查询所有物品
	 * 
	 * @return List<JfReward>
	 */
	public List<JfReward> getAllReward();
	
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
	public void batchPublish(String ids, Short status);
	
	/**
	 * 根据分类类别查询物品
	 * @param jfReward
	 * @return List<JfReward>
	 */
	public List<JfReward> getRewardByCategoryType(JfReward jfReward);
	
	/**
	 * 物品库存量为0时自动下架，设置为已下架 2
	 */
	public void rewardSoldOut();

}
