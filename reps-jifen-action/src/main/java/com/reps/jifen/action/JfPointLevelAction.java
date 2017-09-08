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
import com.reps.jifen.entity.JfPointLevel;
import com.reps.jifen.service.IJfPointLevelService;

@Controller
@RequestMapping(value = RepsConstant.ACTION_BASE_PATH + "/jifen/level")
public class JfPointLevelAction extends BaseAction {

	protected final Logger logger = LoggerFactory.getLogger(JfPointLevelAction.class);
	
	@Autowired
	IJfPointLevelService jfPointLevelService;

	@RequestMapping(value = "/list")
	public ModelAndView list(Pagination pager, JfPointLevel jfPointLevel) {
		ModelAndView mav = getModelAndView("/jifen/pointlevel/list");
		// 分页数据
		ListResult<JfPointLevel> listResult = jfPointLevelService.query(pager.getStartRow(), pager.getPageSize(), jfPointLevel);
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
	public Object add(JfPointLevel jfPointLevel) throws RepsException {
		if (jfPointLevel == null) {
			throw new RepsException("数据不完整");
		}
		jfPointLevelService.save(jfPointLevel);
		return ajax(AjaxStatus.OK, "添加成功");
	}

	@RequestMapping(value = "/toedit")
	public ModelAndView toEdit(String id) {
		ModelAndView mav = getModelAndView("/jifen/pointlevel/edit");
		JfPointLevel jfPointLevel = jfPointLevelService.get(id);
		mav.addObject("pointLevel", jfPointLevel);
		return mav;
	}

	@RequestMapping(value = "/edit")
	@ResponseBody
	public Object edit(JfPointLevel jfPointLevel) throws RepsException {
		if (jfPointLevel == null) {
			throw new RepsException("数据不完整");
		}
		jfPointLevelService.update(jfPointLevel);
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
			JfPointLevel jfPointLevel = jfPointLevelService.get(id);
			if (jfPointLevel != null) {
				jfPointLevelService.delete(jfPointLevel);
			}
			return ajax(AjaxStatus.OK, "删除成功");
		} catch (Exception e) {
			logger.error("删除活动失败", e);
			return ajax(AjaxStatus.ERROR, "删除失败");
		}
	}

	@RequestMapping({ "/show" })
	public ModelAndView show(String id) {
		ModelAndView mav = new ModelAndView("/jifen/pointlevel/show");
		JfPointLevel jfPointLevel = jfPointLevelService.get(id);
		mav.addObject("pointLevel", jfPointLevel);
		return mav;
	}

}
