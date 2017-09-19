package com.reps.jifen.service;

import java.util.List;
import java.util.Map;

import com.reps.core.exception.RepsException;
import com.reps.core.orm.ListResult;
import com.reps.jifen.entity.RewardCategory;

/**
 * 积分奖品分类操作
 * @author qianguobing
 * @date 2017年8月15日 下午3:26:16
 */
public interface IRewardCategoryService {
	
	/**
	 * 添加分类
	 * @param jfRewardCategory
	 */
	public void save(RewardCategory jfRewardCategory);
	
	/**
	 * 删除指定的分类
	 * @param jfRewardCategory
	 */
	public void delete(RewardCategory jfRewardCategory);
	
	/**
	 * 修改分类的相关消息
	 * @param jfRewardCategory
	 */
	public void update(RewardCategory jfRewardCategory);
	
	/**
	 * 通过id得到分类
	 * @param pId
	 * @return JfRewardCategory
	 */
	public RewardCategory get(String pId);
	
	/**
	 * 分页查询
	 * @param start
	 * @param pagesize
	 * @param RewardCategory
	 * @return ListResult<JfRewardCategory>
	 */
	ListResult<RewardCategory> query(int start, int pagesize, RewardCategory jfRewardCategory);
	
	/**
	 * 查询所有在pId下面的分类
	 * @param pId
	 * @return List<JfRewardCategory>
	 */
	public List<RewardCategory> queryList(String pId);
	
	/**
	 * 查询奖品分类信息
	 * @param jfRewardCategory
	 * @return List<JfRewardCategory>
	 */
	public List<RewardCategory> getRewardCategory(RewardCategory jfRewardCategory) throws RepsException;

	/**
	 * 递归构造子树
	 * @param list
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> buildTreeNode(List<RewardCategory> parentlist);
	
}
