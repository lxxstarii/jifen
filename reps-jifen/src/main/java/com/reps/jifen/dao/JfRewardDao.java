package com.reps.jifen.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.reps.core.orm.ListResult;
import com.reps.core.orm.wrapper.GenericDao;
import com.reps.core.util.StringUtil;
import com.reps.jifen.entity.JfReward;
import com.reps.jifen.entity.JfRewardCategory;
import static com.reps.jifen.util.SqlUtil.*;

/**
 * 积分奖品dao
 * @author qianguobing
 * @date 2017年8月25日 下午2:16:48
 */
@Repository
public class JfRewardDao {

	@Autowired
	GenericDao<JfReward> dao;

	public void save(JfReward jfReward) {
		dao.save(jfReward);
	}

	public void delete(JfReward jfReward) {
		dao.delete(jfReward);
	}

	public void update(JfReward jfReward) {
		dao.update(jfReward);
	}

	public JfReward get(String id) {
		return dao.get(JfReward.class, id);
	}

	public ListResult<JfReward> query(int start, int pagesize, JfReward jfReward) {
		DetachedCriteria dc = DetachedCriteria.forClass(JfReward.class);
		dc.createAlias("jfRewardCategory", "t");

		if (jfReward != null) {
			String name = jfReward.getName();
			if (StringUtil.isNotBlank(name)) {
				dc.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
			}
			String categoryId = jfReward.getCategoryId();
			if (StringUtil.isNotBlank(categoryId)) {
				dc.add(Restrictions.eq("t.id", categoryId));
			}
			JfRewardCategory jfRewardCategory = jfReward.getJfRewardCategory();
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
		}
		return dao.query(dc, start, pagesize, Order.asc("createTime"));
	}

	public List<JfReward> getRewardOfCategory(String cid) {
		DetachedCriteria dc = DetachedCriteria.forClass(JfReward.class);
		if (StringUtil.isNotBlank(cid)) {
			dc.add(Restrictions.eq("categoryId", cid));
		}
		dc.addOrder(Order.asc("createTime"));

		return dao.findByCriteria(dc);
	}

	public List<JfReward> getAllReward() {
		DetachedCriteria dc = DetachedCriteria.forClass(JfReward.class);
		dc.addOrder(Order.asc("createTime"));

		return dao.findByCriteria(dc);
	}

	public JfReward getRewardByName(String name) {
		DetachedCriteria dc = DetachedCriteria.forClass(JfReward.class);
		if (StringUtil.isNotBlank(name)) {
			dc.add(Restrictions.eq("name", name));
		}
		List<JfReward> itemList = dao.findByCriteria(dc);
		if (itemList != null && itemList.size() > 0) {
			return itemList.get(0);
		}
		return null;
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
	
	public List<JfReward> getRewardByCategoryType(JfReward jfReward) {
		DetachedCriteria dc = DetachedCriteria.forClass(JfReward.class);
		dc.createAlias("jfRewardCategory", "t");
		JfRewardCategory jfRewardCategory = jfReward.getJfRewardCategory();
		if(null != jfRewardCategory) {
			dc.add(Restrictions.eq("t.type", jfRewardCategory.getType()));
		}
		dc.add(Restrictions.eq("isShown", jfReward.getIsShown()));
		return dao.findByCriteria(dc);
	}

}
