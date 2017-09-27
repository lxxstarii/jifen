package com.reps.jifen.dao;

import static com.reps.jifen.util.SqlUtil.formatSql;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.reps.core.orm.IGenericDao;
import com.reps.core.orm.ListResult;
import com.reps.jifen.entity.ParentPjfzsz;

/**
 * 积分家庭行为评分设置dao
 * 
 * @author qianguobing
 * @date 2017年8月28日 下午4:57:18
 */
@Repository
public class ParentPjfzszDao {

	@Autowired
	IGenericDao<ParentPjfzsz> dao;

	public void save(ParentPjfzsz jfParentPjfzsz) {
		dao.save(jfParentPjfzsz);
	}

	public void delete(ParentPjfzsz jfParentPjfzsz) {
		dao.delete(jfParentPjfzsz);
	}

	public void update(ParentPjfzsz jfParentPjfzsz) {
		dao.update(jfParentPjfzsz);
	}

	public ParentPjfzsz get(String id) {
		return dao.get(ParentPjfzsz.class, id);
	}

	public ListResult<ParentPjfzsz> query(int start, int pagesize, ParentPjfzsz jfParentPjfzsz) {
		DetachedCriteria dc = DetachedCriteria.forClass(ParentPjfzsz.class);
		return dao.query(dc, start, pagesize);
	}
	
	public List<ParentPjfzsz> find(ParentPjfzsz query) {
		DetachedCriteria dc = DetachedCriteria.forClass(ParentPjfzsz.class);
		if (query != null) {
			if (query.getIsEnabled() != null) {
				dc.add(Restrictions.eq("isEnabled", query.getIsEnabled()));
			}
		}
		return dao.findByCriteria(dc);
	}
	
	public void batchDelete(String ids) {
		StringBuilder sb = new StringBuilder("delete JfParentPjfzsz bean");
		sb.append(" where bean.id in (" + formatSql(ids) + ")");
		this.dao.execute(sb.toString());
	}

}
