package com.reps.jifen.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.reps.core.orm.IGenericDao;
import com.reps.core.orm.ListResult;
import com.reps.core.util.StringUtil;
import com.reps.jifen.entity.JfRewardCategory;

/**
 * 积分奖品分类dao
 * @author qianguobing
 * @date 2017年8月25日 下午2:17:36
 */
@Repository
public class JfRewardCategoryDao {

	@Autowired
	IGenericDao<JfRewardCategory> dao;

	public void save(JfRewardCategory jfRewardCategory) {
		dao.save(jfRewardCategory);
	}

	public void delete(JfRewardCategory jfRewardCategory) {
		dao.delete(jfRewardCategory);
	}

	public void update(JfRewardCategory jfRewardCategory) {
		dao.update(jfRewardCategory);
	}

	public JfRewardCategory get(String pId) {
		return dao.get(JfRewardCategory.class, pId);
	}

	public ListResult<JfRewardCategory> query(int start, int pagesize, JfRewardCategory info) {
		DetachedCriteria dc = DetachedCriteria.forClass(JfRewardCategory.class);
		if (info != null) {
			if (StringUtils.isNotBlank(info.getName())) {
				dc.add(Restrictions.like("name", info.getName(), MatchMode.ANYWHERE));
			}
			if (StringUtils.isNotBlank(info.getParentId())) {
				dc.add(Restrictions.eq("parentId", info.getParentId()));
			}
			if (StringUtils.isNotBlank(info.getType())) {
				dc.add(Restrictions.eq("type", info.getType()));
			}
		}
		return dao.query(dc, start, pagesize, Order.asc("name"));
	}

	public List<JfRewardCategory> queryList(String pId) {
		DetachedCriteria dc = DetachedCriteria.forClass(JfRewardCategory.class);
		if (StringUtils.isNotBlank(pId)) {
			dc.add(Restrictions.eq("parentId", pId));
		}
		return dao.findByCriteria(dc);
	}

	public List<JfRewardCategory> getRewardCategory(JfRewardCategory info) {
		DetachedCriteria dc = DetachedCriteria.forClass(JfRewardCategory.class);
		if(null != info) {
			String parentId = info.getParentId();
			if(StringUtil.isNotBlank(parentId)) {
				dc.add(Restrictions.eq("parentId", parentId));
			}
			String type = info.getType();
			if(StringUtil.isNotBlank(type)) {
				dc.add(Restrictions.eq("type", type));
			}
		}
		return dao.findByCriteria(dc);
	}
	
}
