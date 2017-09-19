package com.reps.jifen.action;

import static com.reps.jifen.entity.enums.CategoryType.ACTIVITY;

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
import com.reps.core.commons.Pagination;
import com.reps.core.exception.RepsException;
import com.reps.core.orm.ListResult;
import com.reps.core.util.DateUtil;
import com.reps.core.util.StringUtil;
import com.reps.core.web.AjaxStatus;
import com.reps.core.web.BaseAction;
import com.reps.jifen.entity.PointReward;
import com.reps.jifen.entity.RewardCategory;
import static com.reps.jifen.entity.enums.RewardStatus.*;
import com.reps.jifen.service.IActivityRewardService;
import com.reps.jifen.service.IRewardCategoryService;
import com.reps.jifen.vo.ConfigurePath;

/**
 * 积分活动管理相关操作
 * 
 * @author qianguobing
 * @date 2017年8月16日 上午9:15:32
 */
@Controller
@RequestMapping(value = RepsConstant.ACTION_BASE_PATH + "/jifen/activityreward")
public class ActivityRewardAction extends BaseAction {

	protected final Logger logger = LoggerFactory.getLogger(ActivityRewardAction.class);

	@Autowired
	IActivityRewardService jfActivityRewardService;

	@Autowired
	IRewardCategoryService jfRewardCategoryService;

	/**
	 * 活动管理列表
	 * 
	 * @param pager
	 * @param jfReward
	 *            活动信息
	 * @return Object
	 */
	@RequestMapping(value = "/list")
	public Object list(Pagination pager, PointReward jfReward) {
		ModelAndView mav = getModelAndView("/jifen/activityreward/list");

		RewardCategory jfRewardCategory = jfReward.getJfRewardCategory();
		if (null == jfRewardCategory) {
			jfRewardCategory = new RewardCategory();
			jfReward.setJfRewardCategory(jfRewardCategory);
		}
		// 设置活动类别
		jfRewardCategory.setType(ACTIVITY.getIndex());
		ListResult<PointReward> listResult = jfActivityRewardService.query(pager.getStartRow(), pager.getPageSize(), jfReward);
		// 查询活动类型
		List<RewardCategory> categoryList;
		try {
			categoryList = jfRewardCategoryService.getRewardCategory(jfRewardCategory);
			Map<String, String> activityTypeMap = new HashMap<>();
			activityTypeMap.put("", "全部活动");
			for (RewardCategory category : categoryList) {
				activityTypeMap.put(category.getId(), category.getName());
			}
			mav.addObject("activityTypeMap", activityTypeMap);
			mav.addObject("jfReward", jfReward);
			// 分页数据
			mav.addObject("list", listResult.getList());
			// 分页参数
			pager.setTotalRecord(listResult.getCount().longValue());
			mav.addObject("pager", pager);
			return mav;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询参数失败", e);
			return ajax(AjaxStatus.ERROR, e.getMessage());
		}
	}

	/**
	 * 活动管理添加页面入口
	 * 
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/toadd")
	public ModelAndView toAdd() {
		ModelAndView mav = getModelAndView("/jifen/activityreward/add");
		mav.addObject("imageUploadPath", ConfigurePath.IMG_UPLOAD_PATH);
		mav.addObject("imagePath", ConfigurePath.IMG_FILE_PATH);
		mav.addObject("minDate", DateUtil.getCurDateTime("yyyy-MM-dd"));
		return mav;
	}

	/**
	 * 活动管理添加
	 * 
	 * @param jfReward
	 * @return Object
	 */
	@RequestMapping(value = "/add")
	@ResponseBody
	public Object add(PointReward jfReward) {
		try {
			if (jfReward == null) {
				throw new RepsException("数据不完整");
			}
			jfActivityRewardService.save(jfReward);
			return ajax(AjaxStatus.OK, "添加成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("添加失败", e);
			return ajax(AjaxStatus.ERROR, e.getMessage());
		}
	}

	/**
	 * 活动管理添加页面选择活动分类，展示树形结构
	 * 
	 * @return Object
	 */
	@RequestMapping(value = "/choose")
	public Object choose() {
		ModelAndView mav = new ModelAndView("/jifen/activityreward/choose");
		RewardCategory jfRewardCategory = new RewardCategory();
		// 设置活动类别
		jfRewardCategory.setType(ACTIVITY.getIndex());
		List<RewardCategory> rewardCategoryList;
		try {
			rewardCategoryList = jfRewardCategoryService.getRewardCategory(jfRewardCategory);
			mav.addObject("treelist", rewardCategoryList);
			return mav;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询参数失败", e);
			return ajax(AjaxStatus.ERROR, e.getMessage());
		}
	}

	/**
	 * 活动管理修改页面入口
	 * 
	 * @param id
	 * @return Object
	 */
	@RequestMapping(value = "/toedit")
	public Object toEdit(String id) {
		try {
			ModelAndView mav = getModelAndView("/jifen/activityreward/edit");
			PointReward jfReward = jfActivityRewardService.get(id);
			mav.addObject("imageUploadPath", ConfigurePath.IMG_UPLOAD_PATH);
			mav.addObject("imagePath", ConfigurePath.IMG_FILE_PATH);
			mav.addObject("activity", jfReward);
			mav.addObject("minDate", DateUtil.getCurDateTime("yyyy-MM-dd"));
			return mav;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("进入编辑页面异常", e);
			return ajax(AjaxStatus.ERROR, e.getMessage());
		}
	}

	/**
	 * 修改活动信息
	 * 
	 * @param jfReward
	 * @return Object
	 */
	@RequestMapping(value = "/edit")
	@ResponseBody
	public Object edit(PointReward jfReward) {
		try {
			if (jfReward == null) {
				throw new RepsException("数据不完整");
			}
			jfActivityRewardService.update(jfReward);
			return ajax(AjaxStatus.OK, "修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("修改失败", e);
			return ajax(AjaxStatus.ERROR, e.getMessage());
		}
	}
	
	/**
	 * 活动延期页面入口
	 * 
	 * @param id
	 * @return Object
	 */
	@RequestMapping(value = "/todelay")
	public Object delay(String id) {
		try {
			ModelAndView mav = getModelAndView("/jifen/activityreward/delay");
			PointReward jfReward = jfActivityRewardService.get(id);
			mav.addObject("activity", jfReward);
			mav.addObject("minDate", DateUtil.getCurDateTime("yyyy-MM-dd"));
			return mav;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("进入延期页面异常", e);
			return ajax(AjaxStatus.ERROR, e.getMessage());
		}
	}
	
	/**
	 * 活动延期
	 * @param jfReward
	 * @return Object
	 */
	@RequestMapping(value = "/delay")
	@ResponseBody
	public Object delay(PointReward jfReward) {
		try {
			jfActivityRewardService.delay(jfReward);
			return ajax(AjaxStatus.OK, "活动延期成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("修改失败", e);
			return ajax(AjaxStatus.ERROR, e.getMessage());
		}
	}

	/**
	 * 删除活动信息
	 * 
	 * @param id
	 * @return Object
	 */
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Object delete(String id) {
		try {
			PointReward jfReward = jfActivityRewardService.get(id);
			if (jfReward != null) {
				jfActivityRewardService.delete(jfReward);
			}
			return ajax(AjaxStatus.OK, "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("删除活动失败", e);
			return ajax(AjaxStatus.ERROR, e.getMessage());
		}
	}

	/**
	 * 批量删除活动信息
	 * 
	 * @param ids
	 *            活动id字符串，以逗号分隔拼接
	 * @return Object
	 */
	@RequestMapping(value = "/batchdelete")
	@ResponseBody
	public Object batchDelete(String ids) {
		try {
			if (StringUtil.isBlank(ids)) {
				return ajax(AjaxStatus.ERROR, "删除失败");
			}
			jfActivityRewardService.batchDelete(ids);
			return ajax(AjaxStatus.OK, "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("批量删除活动失败", e);
			return ajax(AjaxStatus.ERROR, e.getMessage());
		}
	}

	/**
	 * 批量发布活动信息，修改状态 为已发布,1
	 * 
	 * @param ids
	 *            活动id字符串，以逗号分隔拼接
	 * @return Object
	 */
	@RequestMapping(value = "/batchpublish")
	@ResponseBody
	public Object batchPublish(String ids, Short status) {
		try {
			jfActivityRewardService.batchPublish(ids, status);
			if(UN_PUBLISH.getIndex() == status) {
				return ajax(AjaxStatus.OK, "取消发布成功");
			}else {
				return ajax(AjaxStatus.OK, "发布成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("操作失败", e);
			return ajax(AjaxStatus.ERROR, e.getMessage());
		}
	}

	/**
	 * 活动详情
	 * 
	 * @param id
	 * @return Object
	 */
	@RequestMapping({ "/show" })
	public Object show(String id) {
		try {
			ModelAndView mav = new ModelAndView("/jifen/activityreward/show");
			PointReward jfReward = jfActivityRewardService.get(id);
			mav.addObject("imagePath", ConfigurePath.IMG_FILE_PATH);
			mav.addObject("activity", jfReward);
			return mav;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取详情失败", e);
			return ajax(AjaxStatus.ERROR, e.getMessage());
		}
	}

}
