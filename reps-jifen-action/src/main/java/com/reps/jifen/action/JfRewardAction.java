package com.reps.jifen.action;

import static com.reps.jifen.entity.enums.CategoryType.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.reps.core.RepsConstant;
import com.reps.core.RepsContext;
import com.reps.core.commons.Pagination;
import com.reps.core.exception.RepsException;
import com.reps.core.orm.ListResult;
import com.reps.core.util.StringUtil;
import com.reps.core.web.AjaxStatus;
import com.reps.core.web.BaseAction;
import com.reps.jifen.entity.JfReward;
import com.reps.jifen.entity.JfRewardCategory;
import com.reps.jifen.service.IJfRewardService;
import com.reps.jifen.service.IJfRewardCategoryService;

/**
 * 积分物品管理相关操作
 * 
 * @author qianguobing
 * @date 2017年8月16日 上午9:15:32
 */
@Controller
@RequestMapping(value = RepsConstant.ACTION_BASE_PATH + "/jifen/reward")
public class JfRewardAction extends BaseAction {

	protected final Logger logger = LoggerFactory.getLogger(JfRewardAction.class);
	
	@Autowired
	IJfRewardService jfRewardService;

	@Autowired
	IJfRewardCategoryService jfRewardCategoryService;

	/**
	 * 图片上传路径
	 */
	public static final String IMAGE_PATH = RepsContext.getConst("jifen", "rewardImageUploadPath");

	/**
	 * 积分物品管理列表
	 * 
	 * @param pager
	 * @param jfReward
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(Pagination pager, JfReward jfReward) {
		ModelAndView mav = getModelAndView("/jifen/reward/list");

		JfRewardCategory jfRewardCategory = jfReward.getJfRewardCategory();
		if (null == jfRewardCategory) {
			jfRewardCategory = new JfRewardCategory();
			jfReward.setJfRewardCategory(jfRewardCategory);
		}
		// 设置物品类别
		jfRewardCategory.setType(REWARD.getIndex());

		ListResult<JfReward> listResult = jfRewardService.query(pager.getStartRow(), pager.getPageSize(), jfReward);
		// 查询物品类型
		List<JfRewardCategory> categoryList = jfRewardCategoryService.getRewardCategory(jfRewardCategory);
		Map<String, String> activityTypeMap = new HashMap<>();
		activityTypeMap.put("", "全部物品");
		for (JfRewardCategory category : categoryList) {
			activityTypeMap.put(category.getId(), category.getName());
		}
		mav.addObject("rewardTypeMap", activityTypeMap);
		mav.addObject("jfReward", jfReward);
		// 分页数据
		mav.addObject("list", listResult.getList());
		// 分页参数
		mav.addObject("pager", pager);
		return mav;
	}

	/**
	 * 添加物品页面入口
	 * 
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/toadd")
	public ModelAndView toAdd() {
		ModelAndView mav = getModelAndView("/jifen/reward/add");
		mav.addObject("imagePath", IMAGE_PATH);
		return mav;
	}

	/**
	 * 添加物品
	 * 
	 * @param jfReward
	 * @return Object
	 * @throws RepsException
	 */
	@RequestMapping(value = "/add")
	@ResponseBody
	public Object add(JfReward jfReward) throws RepsException {
		if (jfReward == null) {
			throw new RepsException("数据不完整");
		}
		jfRewardService.save(jfReward);
		return ajax(AjaxStatus.OK, "添加成功");
	}

	/**
	 * 物品管理添加页面选择物品分类，展示树形结构
	 * 
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/choose")
	public ModelAndView choose() {
		ModelAndView mav = new ModelAndView("/jifen/reward/choose");
		JfRewardCategory jfRewardCategory = new JfRewardCategory();
		// 设置物品类别
		jfRewardCategory.setType(REWARD.getIndex());
		List<JfRewardCategory> rewardCategoryList = jfRewardCategoryService.getRewardCategory(jfRewardCategory);
		mav.addObject("treelist", rewardCategoryList);
		return mav;
	}

	/**
	 * 修改物品页面入口
	 * 
	 * @param id
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/toedit")
	public ModelAndView toEdit(String id) {
		ModelAndView mav = getModelAndView("/jifen/reward/edit");
		JfReward jfReward = jfRewardService.get(id);
		mav.addObject("imagePath", IMAGE_PATH);
		mav.addObject("reward", jfReward);
		return mav;
	}

	/**
	 * 修改物品
	 * 
	 * @param jfReward
	 * @return Object
	 * @throws RepsException
	 */
	@RequestMapping(value = "/edit")
	@ResponseBody
	public Object edit(JfReward jfReward) throws RepsException {
		if (jfReward == null) {
			throw new RepsException("数据不完整");
		}
		jfRewardService.update(jfReward);
		return ajax(AjaxStatus.OK, "修改成功");
	}

	/**
	 * 删除物品
	 * 
	 * @param id
	 * @return Object
	 */
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Object delete(String id) {
		try {
			JfReward jfReward = jfRewardService.get(id);
			if (jfReward != null) {
				jfRewardService.delete(jfReward);
			}
			return ajax(AjaxStatus.OK, "删除成功");
		} catch (Exception e) {
			logger.error("删除活动失败", e);
			return ajax(AjaxStatus.ERROR, "删除失败");
		}
	}

	/**
	 * 批量删除物品
	 * 
	 * @param ids
	 *            物品id以逗号分隔拼接
	 * @return Object
	 */
	@RequestMapping(value = "/batchdelete")
	@ResponseBody
	public Object batchDelete(String ids) {
		try {
			if (StringUtil.isBlank(ids)) {
				return ajax(AjaxStatus.ERROR, "删除失败");
			}
			jfRewardService.batchDelete(ids);
			return ajax(AjaxStatus.OK, "删除成功");
		} catch (Exception e) {
			logger.error("批量删除活动失败", e);
			return ajax(AjaxStatus.ERROR, "删除失败");
		}
	}

	/**
	 * 批量发布物品
	 * 
	 * @param ids
	 * @return Object
	 */
	@RequestMapping(value = "/batchpublish")
	@ResponseBody
	public Object batchPublish(String ids) {
		try {
			if (StringUtil.isBlank(ids)) {
				return ajax(AjaxStatus.ERROR, "发布失败");
			}
			jfRewardService.batchPublish(ids);
			return ajax(AjaxStatus.OK, "发布成功");
		} catch (Exception e) {
			logger.error("批量发布活动失败", e);
			return ajax(AjaxStatus.ERROR, "发布失败");
		}
	}

	/**
	 * 物品详情展示页面
	 * 
	 * @param id
	 * @return ModelAndView
	 */
	@RequestMapping({ "/show" })
	public ModelAndView show(String id) {
		ModelAndView mav = new ModelAndView("/jifen/reward/show");
		JfReward jfReward = jfRewardService.get(id);
		mav.addObject("reward", jfReward);
		return mav;
	}

}
