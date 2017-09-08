package com.reps.jifen.dao;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.reps.core.orm.IGenericDao;
import com.reps.core.orm.ListResult;
import com.reps.jifen.entity.JfPointLevel;

/**
 * 积分等级
 * @author qianguobing
 * @date 2017年9月8日 下午2:46:50
 */
@Repository
public class JfPointLevelDao {

	@Autowired
	IGenericDao<JfPointLevel> dao;

	public void save(JfPointLevel jfPointLevel) {
		dao.save(jfPointLevel);
	}

	public void delete(JfPointLevel jfPointLevel) {
		dao.delete(jfPointLevel);
	}

	public void update(JfPointLevel jfPointLevel) {
		dao.update(jfPointLevel);
	}

	public JfPointLevel get(String id) {
		return dao.get(JfPointLevel.class, id);
	}

	public ListResult<JfPointLevel> query(int start, int pagesize, JfPointLevel jfPointLevel) {
		DetachedCriteria dc = DetachedCriteria.forClass(JfPointLevel.class);
		if (jfPointLevel != null) {
			Integer level = jfPointLevel.getLevel();
			if (null != level) {
				dc.add(Restrictions.eq("level", level));
			}
		}
		return dao.query(dc, start, pagesize);
	}

}
