package com.reps.jifen.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reps.core.orm.ListResult;
import com.reps.jifen.dao.JfRewardCategoryDao;
import com.reps.jifen.entity.JfRewardCategory;
import com.reps.jifen.service.IJfRewardCategoryService;

/**
 * 积分奖励分类操作
 * @author qianguobing
 * @date 2017年8月15日 下午3:29:35
 */
@Service
public class JfRewardCategoryServiceImpl implements IJfRewardCategoryService {
	
	@Autowired
	JfRewardCategoryDao dao;

	@Override
	public void save(JfRewardCategory jfRewardCategory) {
		dao.save(jfRewardCategory);
	}

	@Override
	public void delete(JfRewardCategory jfRewardCategory) {
		dao.delete(jfRewardCategory);
	}

	@Override
	public void update(JfRewardCategory jfRewardCategory) {
		dao.update(jfRewardCategory);

	}

	@Override
	public ListResult<JfRewardCategory> query(int start, int pagesize,
			JfRewardCategory jfRewardCategory) {
		return dao.query(start, pagesize, jfRewardCategory);
	}

	@Override
	public List<JfRewardCategory> queryList(String pId) {
		return dao.queryList(pId);
	}

	@Override
	public JfRewardCategory get(String pId) {
		return dao.get(pId);
	}

	@Override
	public List<JfRewardCategory> getAllCategory() {
		return dao.getAllCategory();
	}

	@Override
	public List<JfRewardCategory> getAllNotCategory() {
		return dao.getAllNotCategory();
	}

	@Override
	public List<JfRewardCategory> getRewardCategory(JfRewardCategory jfRewardCategory) {
		return dao.getRewardCategory(jfRewardCategory);
	}

}
