package com.reps.jifen.action;

import static com.reps.jifen.entity.enums.CategoryType.*;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.reps.core.RepsConstant;
import com.reps.core.commons.Pagination;
import com.reps.core.exception.RepsException;
import com.reps.core.orm.ListResult;
import com.reps.core.util.StringUtil;
import com.reps.core.web.AjaxStatus;
import com.reps.core.web.BaseAction;
import com.reps.jifen.entity.PointReward;
import com.reps.jifen.entity.RewardCategory;
import com.reps.jifen.service.IActivityRewardService;
import com.reps.jifen.service.IRewardCategoryService;
import com.reps.jifen.service.IRewardService;


/**
 * 积分奖品分类管理相关操作
 * @author qianguobing
 * @date 2017年8月16日 上午9:15:32
 */
@Controller
@RequestMapping(value = RepsConstant.ACTION_BASE_PATH + "/jifen/rewardcategory")
public class RewardCategoryAction extends BaseAction {
	
	@Autowired
	IRewardCategoryService jfRewardCategoryService;
	
	@Autowired
	IActivityRewardService jfActivityRewardService;
	
	@Autowired
	IRewardService jfRewardService;
	
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 主页面
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/index")
	public ModelAndView index(){
		ModelAndView mav = getModelAndView("/jifen/rewardcategory/index");
		List<RewardCategory> rewardCategoryList = jfRewardCategoryService.queryList("");//查询所有的分类
		mav.addObject("treelist", rewardCategoryList);
		return mav;
	}
	
	/**
	 * 分类列表
	 * @param pager
	 * @param rewardCategory
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(Pagination pager, RewardCategory rewardCategory){
		ModelAndView mav = getModelAndView("/jifen/rewardcategory/list");
		ListResult<RewardCategory> listResult = jfRewardCategoryService.query(pager.getStartRow(), pager.getPageSize(), rewardCategory);
		mav.addObject("categoryTypeMap", getCategoryType());
		mav.addObject("category", rewardCategory);
		//分页数据
		mav.addObject("list", listResult.getList());
		//分页参数
		mav.addObject("pager", pager);
				
		return mav;
	}
	
	/**
	 * 奖品分类添加页面入口
	 * @param rewardCategory
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/toadd")
	public ModelAndView toAdd(RewardCategory rewardCategory) {
		ModelAndView mav = getModelAndView("/jifen/rewardcategory/add");
		//添加下级分类
		String id = rewardCategory.getId();
		rewardCategory = jfRewardCategoryService.get(id);
		mav.addObject("categoryTypeMap", getCategoryType());
		mav.addObject("category", rewardCategory);
		return mav;
	}

	/**
	 * 奖品分类添加
	 * @param rewardCategory
	 * @return Object
	 */
	@RequestMapping(value = "/add")
	@ResponseBody
	public Object add(RewardCategory rewardCategory){
		try {
			if(rewardCategory == null){
				throw new RepsException("数据不完整");
			}
			jfRewardCategoryService.save(rewardCategory);
			return ajax(AjaxStatus.OK, "添加成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("添加失败", e);
			return ajax(AjaxStatus.ERROR, e.getMessage());
		}
	}
	
	/**
	 * 奖品分类修改页面入口
	 * @param id
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/toedit")
	public ModelAndView toEdit(String id){
		ModelAndView mav = getModelAndView("/jifen/rewardcategory/edit");
		RewardCategory category = jfRewardCategoryService.get(id);
		setParentCategory(mav, category);
		mav.addObject("categoryTypeMap", getCategoryType());
		mav.addObject("category", category);
		return mav;
	}
	
	/**
	 * 奖品分类修改
	 * @param rewardCategory
	 * @return Object
	 */
	@RequestMapping(value = "/edit")
	@ResponseBody
	public Object edit(RewardCategory rewardCategory){
		try {
			if(rewardCategory == null){
				throw new RepsException("数据不完整");
			}
			jfRewardCategoryService.update(rewardCategory);
			return ajax(AjaxStatus.OK, "修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("修改失败", e);
			return ajax(AjaxStatus.ERROR, e.getMessage());
		}
	}
	
	/**
	 * 奖品分类删除
	 * @param id
	 * @return Object
	 */
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Object delete(String id){
		try{
			RewardCategory category = jfRewardCategoryService.get(id);
			List<RewardCategory> categoryList = jfRewardCategoryService.queryList(id);
			if(null != categoryList && 0 != categoryList.size()) {
				return ajax(AjaxStatus.ERROR, "该分类下有子类，请先删除子类！");
			}
			PointReward reward = new PointReward();
			reward.setCategoryId(id);
			reward.setJfRewardCategory(category);
			String type = category.getType();
			List<PointReward> activityRewardList = jfActivityRewardService.getActivityRewardOfCategory(id);
			if(null != activityRewardList && 0 != activityRewardList.size() && ACTIVITY.getIndex().equals(type)) {
				return ajax(AjaxStatus.ERROR, "该分类下有活动不能删除！");
			}
			List<PointReward> rewardList = jfRewardService.getRewardOfCategory(id);
			if(null != rewardList && 0 != rewardList.size() && REWARD.getIndex().equals(type)) {
				return ajax(AjaxStatus.ERROR, "该分类下有物品不能删除！");
			}
			if(category != null){
				jfRewardCategoryService.delete(category);
			}
			return ajax(AjaxStatus.OK, "删除成功");
		}
		catch(Exception e){
			e.printStackTrace();
			logger.error("删除奖品分类失败", e);
			return ajax(AjaxStatus.ERROR, "删除失败");
		}
	}
	
	/**
	 * 奖品分类详情
	 * @param id
	 * @return ModelAndView
	 */
	@RequestMapping({ "/show" })
	public ModelAndView show(String id) {
		ModelAndView mav = new ModelAndView("/jifen/rewardcategory/show");
		RewardCategory category = jfRewardCategoryService.get(id);
		setParentCategory(mav, category);
		mav.addObject("category", category);
		mav.addObject("categoryTypeMap", getCategoryType());
		return mav;
	}

	private void setParentCategory(ModelAndView mav, RewardCategory category) {
		String parentId = category.getParentId();
		if(StringUtil.isNotBlank(parentId) && !"-1".equals(parentId)) {
			RewardCategory parentCategory = jfRewardCategoryService.get(parentId);
			mav.addObject("parentCategory", parentCategory);
		}
	}
	
}
