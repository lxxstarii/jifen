package com.reps.jifen.dao;

import static com.reps.jifen.util.SqlUtil.formatSql;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.reps.core.orm.IGenericDao;
import com.reps.core.orm.ListResult;
import com.reps.core.util.StringUtil;
import com.reps.jifen.entity.PointReward;
import com.reps.jifen.entity.RewardCategory;

/**
 * 积分奖品dao
 * @author qianguobing
 * @date 2017年8月25日 下午2:16:48
 */
@Repository
public class PointRewardDao {

	@Autowired
	IGenericDao<PointReward> dao;

	public void save(PointReward jfReward) {
		dao.save(jfReward);
	}

	public void delete(PointReward jfReward) {
		dao.delete(jfReward);
	}

	public void update(PointReward jfReward) {
		dao.update(jfReward);
	}

	public PointReward get(String id) {
		return dao.get(PointReward.class, id);
	}

	public ListResult<PointReward> query(int start, int pagesize, PointReward jfReward) {
		DetachedCriteria dc = DetachedCriteria.forClass(PointReward.class);
		dc.createAlias("jfRewardCategory", "t");
		Order order = Order.desc("createTime");
		if (jfReward != null) {
			String name = jfReward.getName();
			if (StringUtil.isNotBlank(name)) {
				dc.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
			}
			String categoryId = jfReward.getCategoryId();
			if (StringUtil.isNotBlank(categoryId)) {
				dc.add(Restrictions.eq("t.id", categoryId));
			}
			RewardCategory jfRewardCategory = jfReward.getJfRewardCategory();
			String type = null != jfRewardCategory ? jfRewardCategory.getType() : "";
			if (StringUtil.isNotBlank(type)) {
				dc.add(Restrictions.eq("t.type", type));
			}
			Date finishTime = jfReward.getFinishTime();
			if (null != finishTime) {
				dc.add(Restrictions.le("finishTime", finishTime));
			}
			// 库存量查询
			Integer numbers = jfReward.getNumbers();
			if (null != numbers) {
				dc.add(Restrictions.eq("numbers", numbers));
			}
			Short isShown = jfReward.getIsShown();
			if (null != isShown) {
				dc.add(Restrictions.eq("isShown", isShown));
			}
			Integer pointsBegin = jfReward.getPointBegin();
			if (null != pointsBegin) {
				dc.add(Restrictions.ge("points", pointsBegin));
			}
			Integer pointsEnd = jfReward.getPointEnd();
			if (null != pointsEnd) {
				dc.add(Restrictions.le("points", pointsEnd));
			}
			String sortField = jfReward.getSortField();
			sortField = StringUtil.isBlank(sortField) ? "points" : sortField;
			order = "asc".equalsIgnoreCase(jfReward.getSortOrder()) ? Order.asc(sortField) : Order.desc(sortField);
		}
		return dao.query(dc, start, pagesize, order);
	}

	public List<PointReward> getRewardOfCategory(String cid) {
		DetachedCriteria dc = DetachedCriteria.forClass(PointReward.class);
		if (StringUtil.isNotBlank(cid)) {
			dc.add(Restrictions.eq("categoryId", cid));
		}
		dc.addOrder(Order.asc("createTime"));

		return dao.findByCriteria(dc);
	}

	public void batchDelete(String ids) {
		StringBuilder sb = new StringBuilder("delete JfReward bean");
		sb.append(" where bean.id in (" + formatSql(ids) + ")");
		this.dao.execute(sb.toString());
	}

	public void batchUpdate(String ids, Short isShown) {
		StringBuffer sb = new StringBuffer("update JfReward bean");
		sb.append(" set bean.isShown=" + isShown);
		sb.append(" where bean.id in (" + formatSql(ids) + ")");
		this.dao.execute(sb.toString());
	}
	
	public List<PointReward> getRewardByCategoryType(PointReward jfReward) {
		DetachedCriteria dc = DetachedCriteria.forClass(PointReward.class);
		dc.createAlias("jfRewardCategory", "t");
		if(null != jfReward) {
			RewardCategory jfRewardCategory = jfReward.getJfRewardCategory();
			if(null != jfRewardCategory) {
				dc.add(Restrictions.eq("t.type", jfRewardCategory.getType()));
			}
			Short isShown = jfReward.getIsShown();
			if(null != isShown) {
				dc.add(Restrictions.eq("isShown", isShown));
			}
		}
		return dao.findByCriteria(dc);
	}

}
