package com.reps.jifen.dao;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.reps.core.orm.ListResult;
import com.reps.core.orm.wrapper.GenericDao;
import com.reps.jifen.entity.JfParentPjfzsz;

/**
 * 积分家庭行为评分设置dao
 * 
 * @author qianguobing
 * @date 2017年8月28日 下午4:57:18
 */
@Repository
public class JfParentPjfzszDao {

	@Autowired
	GenericDao<JfParentPjfzsz> dao;

	public void save(JfParentPjfzsz jfParentPjfzsz) {
		dao.save(jfParentPjfzsz);
	}

	public void delete(JfParentPjfzsz jfParentPjfzsz) {
		dao.delete(jfParentPjfzsz);
	}

	public void update(JfParentPjfzsz jfParentPjfzsz) {
		dao.update(jfParentPjfzsz);
	}

	public JfParentPjfzsz get(String id) {
		return dao.get(JfParentPjfzsz.class, id);
	}

	public ListResult<JfParentPjfzsz> query(int start, int pagesize, JfParentPjfzsz jfParentPjfzsz) {
		DetachedCriteria dc = DetachedCriteria.forClass(JfParentPjfzsz.class);
		return dao.query(dc, start, pagesize, Order.asc("item"));
	}

}
