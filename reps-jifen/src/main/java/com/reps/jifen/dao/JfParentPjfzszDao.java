package com.reps.jifen.dao;

import static com.reps.jifen.util.SqlUtil.formatSql;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.reps.core.orm.IGenericDao;
import com.reps.core.orm.ListResult;
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
	IGenericDao<JfParentPjfzsz> dao;

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
		return dao.query(dc, start, pagesize);
	}
	
	public void batchDelete(String ids) {
		StringBuilder sb = new StringBuilder("delete JfParentPjfzsz bean");
		sb.append(" where bean.id in (" + formatSql(ids) + ")");
		this.dao.execute(sb.toString());
	}

}
