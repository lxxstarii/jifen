package com.reps.jifen.service;

import com.reps.core.orm.ListResult;
import com.reps.jifen.entity.JfParentPjfzsz;

/**
 * 积分家庭行为评分设置
 * @author qianguobing
 * @date 2017年8月28日 下午5:05:01
 */
public interface IJfParentPjfzszService {
	
	/**
	 * 添加家庭行为评分项
	 * @param jfParentPjfzsz
	 */
	public void save(JfParentPjfzsz jfParentPjfzsz);
	
	/**
	 * 删除家庭行为评分项
	 * @param jfParentPjfzsz
	 */
	public void delete(JfParentPjfzsz jfParentPjfzsz);
	
	/**
	 * 修改家庭行为评分项
	 * @param jfParentPjfzsz
	 */
	public void update(JfParentPjfzsz jfParentPjfzsz);
	
	/**
	 * 根据id查询家庭行为
	 * @param id
	 * @return JfParentPjfzsz
	 */
	public JfParentPjfzsz get(String id);
	
	/**
	 * 分页查询
	 * @param start
	 * @param pagesize
	 * @param jfParentPjfzsz
	 * @return
	 */
	public ListResult<JfParentPjfzsz> query(int start, int pagesize, JfParentPjfzsz jfParentPjfzsz);

}
