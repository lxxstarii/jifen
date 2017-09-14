package com.reps.jifen.service;

import java.util.List;

import com.reps.core.orm.ListResult;
import com.reps.jifen.entity.ParentPjfzsz;

/**
 * 积分家庭行为评分设置
 * @author qianguobing
 * @date 2017年8月28日 下午5:05:01
 */
public interface IParentPjfzszService {
	
	/**
	 * 添加家庭行为评分项
	 * @param jfParentPjfzsz
	 */
	public void save(ParentPjfzsz jfParentPjfzsz);
	
	/**
	 * 删除家庭行为评分项
	 * @param jfParentPjfzsz
	 */
	public void delete(ParentPjfzsz jfParentPjfzsz);
	
	/**
	 * 修改家庭行为评分项
	 * @param jfParentPjfzsz
	 */
	public void update(ParentPjfzsz jfParentPjfzsz);
	
	/**
	 * 根据id查询家庭行为
	 * @param id
	 * @return JfParentPjfzsz
	 */
	public ParentPjfzsz get(String id);
	
	/**
	 * 分页查询
	 * @param start
	 * @param pagesize
	 * @param jfParentPjfzsz
	 * @return
	 */
	public ListResult<ParentPjfzsz> query(int start, int pagesize, ParentPjfzsz jfParentPjfzsz);
	
	
	List<ParentPjfzsz> find(ParentPjfzsz query);
	
	/**
	 * 批量删除询家庭评分行为
	 * @param ids
	 */
	public void batchDelete(String ids);

}
