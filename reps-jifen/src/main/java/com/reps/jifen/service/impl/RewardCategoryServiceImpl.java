package com.reps.jifen.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reps.core.exception.RepsException;
import com.reps.core.orm.ListResult;
import com.reps.core.util.StringUtil;
import com.reps.jifen.dao.RewardCategoryDao;
import com.reps.jifen.entity.RewardCategory;
import com.reps.jifen.service.IRewardCategoryService;

/**
 * 积分奖励分类操作
 * @author qianguobing
 * @date 2017年8月15日 下午3:29:35
 */
@Service
@Transactional
public class RewardCategoryServiceImpl implements IRewardCategoryService {
	
	@Autowired
	RewardCategoryDao dao;

	@Override
	public void save(RewardCategory jfRewardCategory) {
		dao.save(jfRewardCategory);
	}

	@Override
	public void delete(RewardCategory jfRewardCategory) {
		dao.delete(jfRewardCategory);
	}

	@Override
	public void update(RewardCategory jfRewardCategory) {
		dao.update(jfRewardCategory);

	}

	@Override
	public ListResult<RewardCategory> query(int start, int pagesize,
			RewardCategory jfRewardCategory) {
		return dao.query(start, pagesize, jfRewardCategory);
	}

	@Override
	public List<RewardCategory> queryList(String pId) {
		return dao.queryList(pId);
	}

	@Override
	public RewardCategory get(String pId) {
		return dao.get(pId);
	}

	@Override
	public List<RewardCategory> getRewardCategory(RewardCategory jfRewardCategory) throws RepsException{
		if(null == jfRewardCategory) {
			throw new RepsException("查询异常:查询参数错误");
		}
		String type = jfRewardCategory.getType();
		if(StringUtil.isBlank(type)) {
			throw new RepsException("查询异常:分类类别不能为空");
		}
		return dao.getRewardCategory(jfRewardCategory);
	}
	
	@Override
	public List<Map<String, Object>> buildTreeNode(List<RewardCategory> parentlist) {
		List<Map<String, Object>> treeNodeList = new ArrayList<>();
		for (RewardCategory rewardCategory : parentlist) {
			Map<String, Object> buildNodeMap = buildNodeMap(rewardCategory);
			List<Map<String, Object>> bindChildByParent = bindChildByParent(rewardCategory.getId());
			buildNodeMap.put("children", bindChildByParent);
			treeNodeList.add(buildNodeMap);
		}
		return treeNodeList;
	}
	
	/**
	 * 递归构造子树
	 * @param pId
	 * @return List<Map<String, Object>>
	 */
	private List<Map<String, Object>> bindChildByParent(String pId) {
		List<Map<String, Object>> retList = new ArrayList<>();
		List<RewardCategory> categorys = this.queryList(pId);
		for (RewardCategory category : categorys) {
			Map<String, Object> childNode = buildNodeMap(category);
			String id = category.getId();
			List<Map<String, Object>> childList = this.bindChildByParent(id);
			if (null != childList && childList.size() > 0) {
				childNode.put("children", childList);
			}
			retList.add(childNode);
		}
		return retList;
	}

	private Map<String, Object> buildNodeMap(RewardCategory category) {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", category.getId());
		data.put("name", category.getName());
		data.put("parentId", category.getParentId());
		return data;
	}

}
