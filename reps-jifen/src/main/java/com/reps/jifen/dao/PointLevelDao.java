package com.reps.jifen.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.reps.core.orm.IGenericDao;
import com.reps.core.orm.ListResult;
import com.reps.jifen.entity.PointLevel;

/**
 * 积分等级
 * @author qianguobing
 * @date 2017年9月8日 下午2:46:50
 */
@Repository
public class PointLevelDao {

	@Autowired
	IGenericDao<PointLevel> dao;

	public void save(PointLevel jfPointLevel) {
		dao.save(jfPointLevel);
	}

	public void delete(PointLevel jfPointLevel) {
		dao.delete(jfPointLevel);
	}

	public void update(PointLevel jfPointLevel) {
		dao.update(jfPointLevel);
	}

	public PointLevel get(String id) {
		return dao.get(PointLevel.class, id);
	}

	public ListResult<PointLevel> query(int start, int pagesize, PointLevel jfPointLevel) {
		DetachedCriteria dc = DetachedCriteria.forClass(PointLevel.class);
		if (jfPointLevel != null) {
			Integer level = jfPointLevel.getLevel();
			if (null != level) {
				dc.add(Restrictions.eq("level", level));
			}
		}
		return dao.query(dc, start, pagesize);
	}
	
	public List<PointLevel> queryAll(PointLevel jfPointLevel) {
		DetachedCriteria dc = DetachedCriteria.forClass(PointLevel.class);
		dc.addOrder(Order.asc("points"));
		return dao.findByCriteria(dc);
	}

}
