package com.reps.jifen.action;

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
import com.reps.jifen.entity.JfRewardCategory;
import com.reps.jifen.service.IJfRewardCategoryService;


/**
 * 积分奖品分类管理相关操作
 * @author qianguobing
 * @date 2017年8月16日 上午9:15:32
 */
@Controller
@RequestMapping(value = RepsConstant.ACTION_BASE_PATH + "/jifen/rewardcategory")
public class JfRewardCategoryAction extends BaseAction {
	
	@Autowired
	IJfRewardCategoryService jfRewardCategoryService;
	
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 主页面
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/index")
	public ModelAndView index(){
		ModelAndView mav = getModelAndView("/jifen/rewardcategory/index");
		List<JfRewardCategory> rewardCategoryList = jfRewardCategoryService.queryList("");//查询所有的分类
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
	public ModelAndView list(Pagination pager, JfRewardCategory rewardCategory){
		ModelAndView mav = getModelAndView("/jifen/rewardcategory/list");
		ListResult<JfRewardCategory> listResult = jfRewardCategoryService.query(pager.getStartRow(), pager.getPageSize(), rewardCategory);
		Map<String, String> categoryTypeMap = buildCategoryTypeMap();
		mav.addObject("categoryTypeMap", categoryTypeMap);
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
	public ModelAndView toAdd(JfRewardCategory rewardCategory) {
		ModelAndView mav = getModelAndView("/jifen/rewardcategory/add");
		//添加下级分类
		String id = rewardCategory.getId();
		rewardCategory = jfRewardCategoryService.get(id);
		Map<String, String> categoryTypeMap = buildCategoryTypeMap();
		mav.addObject("categoryTypeMap", categoryTypeMap);
		mav.addObject("category", rewardCategory);
		return mav;
	}

	/**
	 * 分类类别下拉框设置
	 * @return Map<String, String>
	 */
	private Map<String, String> buildCategoryTypeMap() {
		Map<String, String> categoryTypeMap = new HashMap<String, String>();
		categoryTypeMap.put("", "");
		categoryTypeMap.put("1", "物品分类");
		categoryTypeMap.put("2", "活动分类");
		return categoryTypeMap;
	}
	
	/**
	 * 奖品分类添加
	 * @param rewardCategory
	 * @return Object
	 * @throws RepsException
	 */
	@RequestMapping(value = "/add")
	@ResponseBody
	public Object add(JfRewardCategory rewardCategory) throws RepsException{
		if(rewardCategory == null){
			throw new RepsException("数据不完整");
		}
		jfRewardCategoryService.save(rewardCategory);
		return ajax(AjaxStatus.OK, "添加成功");
	}
	
	/**
	 * 奖品分类修改页面入口
	 * @param id
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/toedit")
	public ModelAndView toEdit(String id){
		ModelAndView mav = getModelAndView("/jifen/rewardcategory/edit");
		JfRewardCategory category = jfRewardCategoryService.get(id);
		setParentCategory(mav, category);
		Map<String, String> buildCategoryTypeMap = buildCategoryTypeMap();
		mav.addObject("categoryTypeMap", buildCategoryTypeMap);
		mav.addObject("category", category);
		return mav;
	}
	
	/**
	 * 奖品分类修改
	 * @param rewardCategory
	 * @return Object
	 * @throws RepsException
	 */
	@RequestMapping(value = "/edit")
	@ResponseBody
	public Object edit(JfRewardCategory rewardCategory) throws RepsException {
		if(rewardCategory == null){
			throw new RepsException("数据不完整");
		}
		jfRewardCategoryService.update(rewardCategory);
		return ajax(AjaxStatus.OK, "修改成功");
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
			JfRewardCategory category = jfRewardCategoryService.get(id);
			if(category != null){
				jfRewardCategoryService.delete(category);
			}
			return ajax(AjaxStatus.OK, "删除成功");
		}
		catch(Exception e){
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
		JfRewardCategory category = jfRewardCategoryService.get(id);
		setParentCategory(mav, category);
		Map<String, String> buildCategoryTypeMap = buildCategoryTypeMap();
		mav.addObject("category", category);
		mav.addObject("categoryTypeMap", buildCategoryTypeMap);
		return mav;
	}

	private void setParentCategory(ModelAndView mav, JfRewardCategory category) {
		String parentId = category.getParentId();
		if(StringUtil.isNotBlank(parentId) && !"-1".equals(parentId)) {
			JfRewardCategory parentCategory = jfRewardCategoryService.get(parentId);
			mav.addObject("parentCategory", parentCategory);
		}
	}
	
}
