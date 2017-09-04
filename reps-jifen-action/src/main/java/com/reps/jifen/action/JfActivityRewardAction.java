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
import com.reps.core.util.StringUtil;
import com.reps.core.web.AjaxStatus;
import com.reps.core.web.BaseAction;
import com.reps.jifen.entity.JfReward;
import com.reps.jifen.entity.JfRewardCategory;
import com.reps.jifen.service.IJfActivityRewardService;
import com.reps.jifen.service.IJfRewardCategoryService;
import com.reps.jifen.vo.ConfigurePath;


/**
 * 积分活动管理相关操作
 * @author qianguobing
 * @date 2017年8月16日 上午9:15:32
 */
@Controller
@RequestMapping(value = RepsConstant.ACTION_BASE_PATH + "/jifen/activityreward")
public class JfActivityRewardAction extends BaseAction {
	
	protected final Logger logger = LoggerFactory.getLogger(JfActivityRewardAction.class);
	
	@Autowired
	IJfActivityRewardService jfActivityRewardService;
	
	@Autowired
	IJfRewardCategoryService jfRewardCategoryService;
	
	/**
	 * 活动管理列表
	 * @param pager
	 * @param jfReward 活动信息
	 * @return
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(Pagination pager, JfReward jfReward){
		ModelAndView mav = getModelAndView("/jifen/activityreward/list");
		
		JfRewardCategory jfRewardCategory = jfReward.getJfRewardCategory();
		if(null == jfRewardCategory) {
			jfRewardCategory = new JfRewardCategory();
			jfReward.setJfRewardCategory(jfRewardCategory);
		}
		//设置活动类别
		jfRewardCategory.setType(ACTIVITY.getIndex());
		ListResult<JfReward> listResult = jfActivityRewardService.query(pager.getStartRow(), pager.getPageSize(), jfReward);
		//查询活动类型
		List<JfRewardCategory> categoryList = jfRewardCategoryService.getRewardCategory(jfRewardCategory );
		Map<String, String> activityTypeMap = new HashMap<>();
		activityTypeMap.put("", "全部活动");
		for (JfRewardCategory category : categoryList) {
			activityTypeMap.put(category.getId(), category.getName());
		}
		mav.addObject("activityTypeMap", activityTypeMap);
		mav.addObject("jfReward", jfReward);
		//分页数据
		mav.addObject("list", listResult.getList());
		//分页参数
		mav.addObject("pager", pager);
		return mav;
	}
	
	/**
	 * 活动管理添加页面入口
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/toadd")
	public ModelAndView toAdd() {
		ModelAndView mav = getModelAndView("/jifen/activityreward/add");
		mav.addObject("imageUploadPath", ConfigurePath.IMG_UPLOAD_PATH);
		mav.addObject("imagePath", ConfigurePath.IMG_FILE_PATH);
		return mav;
	}
	
	/**
	 * 活动管理添加
	 * @param jfReward
	 * @return Object
	 * @throws RepsException
	 */
	@RequestMapping(value = "/add")
	@ResponseBody
	public Object add(JfReward jfReward) throws RepsException{
		if(jfReward == null){
			throw new RepsException("数据不完整");
		}
		jfActivityRewardService.save(jfReward);
		return ajax(AjaxStatus.OK, "添加成功");
	}
	
	/**
	 * 活动管理添加页面选择活动分类，展示树形结构
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/choose")
	public ModelAndView choose() {
		ModelAndView mav = new ModelAndView("/jifen/activityreward/choose");
		JfRewardCategory jfRewardCategory = new JfRewardCategory();
		//设置活动类别
		jfRewardCategory.setType(ACTIVITY.getIndex());
		List<JfRewardCategory> rewardCategoryList = jfRewardCategoryService.getRewardCategory(jfRewardCategory);
		mav.addObject("treelist", rewardCategoryList);
		return mav;
	}
	
	/**
	 * 活动管理修改页面入口
	 * @param id
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/toedit")
	public ModelAndView toEdit(String id){
		ModelAndView mav = getModelAndView("/jifen/activityreward/edit");
		JfReward jfReward = jfActivityRewardService.get(id);
		mav.addObject("imageUploadPath", ConfigurePath.IMG_UPLOAD_PATH);
		mav.addObject("imagePath", ConfigurePath.IMG_FILE_PATH);
		mav.addObject("activity", jfReward);
		return mav;
	}
	
	/**
	 * 修改活动信息
	 * @param jfReward
	 * @return Object
	 * @throws RepsException
	 */
	@RequestMapping(value = "/edit")
	@ResponseBody
	public Object edit(JfReward jfReward) throws RepsException {
		if(jfReward == null){
			throw new RepsException("数据不完整");
		}
		jfActivityRewardService.update(jfReward);
		return ajax(AjaxStatus.OK, "修改成功");
	}
	
	/**
	 * 删除活动信息
	 * @param id
	 * @return Object
	 */
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Object delete(String id){
		try{
			JfReward jfReward = jfActivityRewardService.get(id);
			if(jfReward != null){
				jfActivityRewardService.delete(jfReward);
			}
			return ajax(AjaxStatus.OK, "删除成功");
		}
		catch(Exception e){
			logger.error("删除活动失败", e);
			return ajax(AjaxStatus.ERROR, "删除失败");
		}
	}
	
	/**
	 * 批量删除活动信息
	 * @param ids 活动id字符串，以逗号分隔拼接
	 * @return Object
	 */
	@RequestMapping(value = "/batchdelete")
	@ResponseBody
	public Object batchDelete(String ids){
		try{
			if(StringUtil.isBlank(ids)) {
				 return ajax(AjaxStatus.ERROR, "删除失败");
			}
			jfActivityRewardService.batchDelete(ids);
			return ajax(AjaxStatus.OK, "删除成功");
		}
		catch(Exception e){
			logger.error("批量删除活动失败", e);
			return ajax(AjaxStatus.ERROR, "删除失败");
		}
	}
	
	/**
	 * 批量发布活动信息，修改状态 为已发布,1
	 * @param ids 活动id字符串，以逗号分隔拼接
	 * @return Object
	 */
	@RequestMapping(value = "/batchpublish")
	@ResponseBody
	public Object batchPublish(String ids){
		try{
			if(StringUtil.isBlank(ids)) {
				 return ajax(AjaxStatus.ERROR, "发布失败");
			}
			jfActivityRewardService.batchPublish(ids);
			return ajax(AjaxStatus.OK, "发布成功");
		}
		catch(Exception e){
			logger.error("批量发布活动失败", e);
			return ajax(AjaxStatus.ERROR, "发布失败");
		}
	}
	
	/**
	 * 活动详情
	 * @param id
	 * @return ModelAndView
	 */
	@RequestMapping({ "/show" })
	public ModelAndView show(String id) {
		ModelAndView mav = new ModelAndView("/jifen/activityreward/show");
		JfReward jfReward = jfActivityRewardService.get(id);
		mav.addObject("imagePath", ConfigurePath.IMG_FILE_PATH);
		mav.addObject("activity", jfReward);
		return mav;
	}
	
}
