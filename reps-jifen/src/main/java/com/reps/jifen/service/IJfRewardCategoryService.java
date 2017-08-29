package com.reps.jifen.service;

import java.util.List;

import com.reps.core.orm.ListResult;
import com.reps.jifen.entity.JfRewardCategory;

/**
 * 积分奖品分类操作
 * @author qianguobing
 * @date 2017年8月15日 下午3:26:16
 */
public interface IJfRewardCategoryService {
	
	/**
	 * 添加分类
	 * @param jfRewardCategory
	 */
	public void save(JfRewardCategory jfRewardCategory);
	
	/**
	 * 删除指定的分类
	 * @param jfRewardCategory
	 */
	public void delete(JfRewardCategory jfRewardCategory);
	
	/**
	 * 修改分类的相关消息
	 * @param jfRewardCategory
	 */
	public void update(JfRewardCategory jfRewardCategory);
	
	/**
	 * 通过id得到分类
	 * @param pId
	 * @return JfRewardCategory
	 */
	public JfRewardCategory get(String pId);
	
	/**
	 * 分页查询
	 * @param start
	 * @param pagesize
	 * @param JfRewardCategory
	 * @return ListResult<JfRewardCategory>
	 */
	ListResult<JfRewardCategory> query(int start, int pagesize, JfRewardCategory jfRewardCategory);
	
	/**
	 * 查询所有在pId下面的分类
	 * @param pId
	 * @return List<JfRewardCategory>
	 */
	public List<JfRewardCategory> queryList(String pId);
	
	/**
	 * 查询所有类型
	 * @return
	 */
	public List<JfRewardCategory> getAllCategory();
	
	/**
	 * 查询所有非上级类型
	 * @return List<JfRewardCategory>
	 */
	public List<JfRewardCategory> getAllNotCategory();
	
	/**
	 * 查询奖品分类信息
	 * @param jfRewardCategory
	 * @return List<JfRewardCategory>
	 */
	public List<JfRewardCategory> getRewardCategory(JfRewardCategory jfRewardCategory);
	
}
