package com.reps.jifen.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reps.core.orm.ListResult;
import com.reps.jifen.dao.ParentPjfzszDao;
import com.reps.jifen.entity.ParentPjfzsz;
import com.reps.jifen.service.IParentPjfzszService;

/**
 * 积分家庭行为评分设置service实现
 * @author qianguobing
 * @date 2017年8月28日 下午5:17:49
 */
@Service("com.reps.jifen.service.impl.JfParentPjfzszServiceImpl")
@Transactional
public class ParentPjfzszServiceImpl implements IParentPjfzszService {
	
	@Autowired
	ParentPjfzszDao dao;

	@Override
	public void save(ParentPjfzsz jfParentPjfzsz) {
		dao.save(jfParentPjfzsz);
	}

	@Override
	public void delete(ParentPjfzsz jfParentPjfzsz) {
		dao.delete(jfParentPjfzsz);
	}

	@Override
	public void update(ParentPjfzsz jfParentPjfzsz) {
		dao.update(jfParentPjfzsz);
	}

	@Override
	public ParentPjfzsz get(String id) {
		return dao.get(id);
	}

	@Override
	public ListResult<ParentPjfzsz> query(int start, int pagesize, ParentPjfzsz jfParentPjfzsz) {
		ListResult<ParentPjfzsz> parentPjfzszList = dao.query(start, pagesize, jfParentPjfzsz);
		for (ParentPjfzsz bean : parentPjfzszList.getList()) {
			Short pointsScope = bean.getPointsScope();
			StringBuilder sb = new StringBuilder();
			while((short)pointsScope > 0) {
				sb.insert(0, pointsScope--);
				sb.insert(0, "+");
				sb.insert(0, ", ");
			}
			bean.setPointsScopeDisp(sb.deleteCharAt(sb.toString().indexOf(",")).toString());
		}
		return parentPjfzszList;
	}

	@Override
	public void batchDelete(String ids) {
		dao.batchDelete(ids);
	}

	@Override
	public List<ParentPjfzsz> find(ParentPjfzsz query) {
		return dao.find(query);
	}

}
