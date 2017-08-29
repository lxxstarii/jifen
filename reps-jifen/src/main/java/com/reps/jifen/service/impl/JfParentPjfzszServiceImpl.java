package com.reps.jifen.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reps.core.orm.ListResult;
import com.reps.jifen.dao.JfParentPjfzszDao;
import com.reps.jifen.entity.JfParentPjfzsz;
import com.reps.jifen.service.IJfParentPjfzszService;

/**
 * 积分家庭行为评分设置service实现
 * @author qianguobing
 * @date 2017年8月28日 下午5:17:49
 */
@Service
public class JfParentPjfzszServiceImpl implements IJfParentPjfzszService {
	
	@Autowired
	JfParentPjfzszDao dao;

	@Override
	public void save(JfParentPjfzsz jfParentPjfzsz) {
		dao.save(jfParentPjfzsz);
	}

	@Override
	public void delete(JfParentPjfzsz jfParentPjfzsz) {
		dao.delete(jfParentPjfzsz);
	}

	@Override
	public void update(JfParentPjfzsz jfParentPjfzsz) {
		dao.update(jfParentPjfzsz);
	}

	@Override
	public JfParentPjfzsz get(String id) {
		return dao.get(id);
	}

	@Override
	public ListResult<JfParentPjfzsz> query(int start, int pagesize, JfParentPjfzsz jfParentPjfzsz) {
		return dao.query(start, pagesize, jfParentPjfzsz);
	}

}
