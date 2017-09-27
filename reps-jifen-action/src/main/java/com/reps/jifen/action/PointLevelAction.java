package com.reps.jifen.action;

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
import com.reps.core.web.AjaxStatus;
import com.reps.core.web.BaseAction;
import com.reps.jifen.entity.PointLevel;
import com.reps.jifen.service.IPointLevelService;

@Controller
@RequestMapping(value = RepsConstant.ACTION_BASE_PATH + "/jifen/level")
public class PointLevelAction extends BaseAction {

	protected final Logger logger = LoggerFactory.getLogger(PointLevelAction.class);
	
	@Autowired
	IPointLevelService jfPointLevelService;

	@RequestMapping(value = "/list")
	public ModelAndView list(Pagination pager, PointLevel jfPointLevel) {
		ModelAndView mav = getModelAndView("/jifen/pointlevel/list");
		// 分页数据
		ListResult<PointLevel> listResult = jfPointLevelService.query(pager.getStartRow(), pager.getPageSize(), jfPointLevel);
		mav.addObject("list", listResult.getList());
		// 分页参数
		mav.addObject("pager", pager);
		return mav;
	}

	@RequestMapping(value = "/toadd")
	public ModelAndView toAdd() {
		ModelAndView mav = getModelAndView("/jifen/pointlevel/add");
		return mav;
	}

	@RequestMapping(value = "/add")
	@ResponseBody
	public Object add(PointLevel jfPointLevel){
		try {
			if (jfPointLevel == null) {
				throw new RepsException("数据不完整");
			}
			jfPointLevelService.save(jfPointLevel);
			return ajax(AjaxStatus.OK, "添加成功");
		} catch (RepsException e) {
			e.printStackTrace();
			logger.error("添加失败", e);
			return ajax(AjaxStatus.ERROR, e.getMessage());
		}
	}

	@RequestMapping(value = "/toedit")
	public ModelAndView toEdit(String id) {
		ModelAndView mav = getModelAndView("/jifen/pointlevel/edit");
		PointLevel jfPointLevel = jfPointLevelService.get(id);
		mav.addObject("pointLevel", jfPointLevel);
		return mav;
	}

	@RequestMapping(value = "/edit")
	@ResponseBody
	public Object edit(PointLevel jfPointLevel){
		try {
			if (jfPointLevel == null) {
				throw new RepsException("数据不完整");
			}
			jfPointLevelService.update(jfPointLevel);
			return ajax(AjaxStatus.OK, "修改成功");
		} catch (RepsException e) {
			e.printStackTrace();
			logger.error("修改失败", e);
			return ajax(AjaxStatus.ERROR, e.getMessage());
		}
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
			PointLevel jfPointLevel = jfPointLevelService.get(id);
			if (jfPointLevel != null) {
				jfPointLevelService.delete(jfPointLevel);
			}
			return ajax(AjaxStatus.OK, "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("删除活动失败", e);
			return ajax(AjaxStatus.ERROR, "删除失败");
		}
	}

	@RequestMapping({ "/show" })
	public ModelAndView show(String id) {
		ModelAndView mav = new ModelAndView("/jifen/pointlevel/show");
		PointLevel jfPointLevel = jfPointLevelService.get(id);
		mav.addObject("pointLevel", jfPointLevel);
		return mav;
	}

}
