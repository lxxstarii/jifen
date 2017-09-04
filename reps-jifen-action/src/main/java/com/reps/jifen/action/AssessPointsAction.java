package com.reps.jifen.action;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import com.reps.jifen.entity.StudyAssessPoints;
import com.reps.jifen.entity.enums.AccessCategory;
import com.reps.jifen.service.IAssessPointsService;
import com.reps.jifen.vo.ConfigurePath;

/**
 * 校园/学习评价分值设置
 * @author zf
 * 
 */
@Controller("com.reps.jifen.action.AssessPointsAction")
@RequestMapping(value = RepsConstant.ACTION_BASE_PATH + "/jifen/pjfzsz")
public class AssessPointsAction extends BaseAction {
	
	private final Log logger = LogFactory.getLog(AssessPointsAction.class);
	
	@Autowired
	IAssessPointsService assessPointsService;

	@RequestMapping(value = "/xxlist")
	public ModelAndView xxlist(Pagination pager, StudyAssessPoints query) {

		ModelAndView mav = getModelAndView("/jifen/pjfzsz/xxlist");
		// 学习评价指标设置
		query.setCategory(AccessCategory.XXHD.getValue());
		ListResult<StudyAssessPoints> result = assessPointsService.query(
				pager.getStartRow(), pager.getPageSize(), query);

		mav.addObject("list", result.getList());
		mav.addObject("pager", pager);
		mav.addObject("query", query);
		mav.addObject("actionBasePath", RepsConstant.ACTION_BASE_PATH);
		return mav;
	}
	
	@RequestMapping(value = "/xylist")
	public ModelAndView xylist(Pagination pager, StudyAssessPoints query) {

		ModelAndView mav = getModelAndView("/jifen/pjfzsz/xylist");
		// 校园评价指标设置
		query.setCategory(AccessCategory.XYXW.getValue());
		ListResult<StudyAssessPoints> result = assessPointsService.query(
				pager.getStartRow(), pager.getPageSize(), query);

		mav.addObject("list", result.getList());
		mav.addObject("pager", pager);
		mav.addObject("query", query);
		mav.addObject("actionBasePath", RepsConstant.ACTION_BASE_PATH);
		return mav;
	}
	
	@RequestMapping(value = "/toadd")
	public ModelAndView toAdd(String category) {
		ModelAndView mav = getModelAndView("/jifen/pjfzsz/add");
		mav.addObject("imageUploadPath", ConfigurePath.IMG_UPLOAD_PATH);
		mav.addObject("imagePath", ConfigurePath.IMG_FILE_PATH);
		mav.addObject("category", category);
		return mav;
	}
	
	@RequestMapping(value = "/save")
	@ResponseBody
	public Object save(StudyAssessPoints info) throws RepsException {
		try {
			StudyAssessPoints data = new StudyAssessPoints();
			data.setCategory(info.getCategory());
			data.setDescription(info.getDescription());
			data.setIcon(info.getIcon());
			data.setIsEnable(info.getIsEnable());
			data.setItem(info.getItem());
			data.setMark(info.getMark());
			data.setPointsScope(Math.abs(info.getPointsScope()));
			assessPointsService.save(data);
			return ajax(AjaxStatus.OK, "评价指标设置成功");
		} catch (Exception e) {
			logger.error("评价分值设置异常", e);
			return ajax(AjaxStatus.ERROR, "评价指标设置异常");
		}
	}
	
	@RequestMapping(value = "/toedit")
	public ModelAndView toEdit(String id) {
		ModelAndView mav = getModelAndView("/jifen/pjfzsz/edit");
		StudyAssessPoints data = assessPointsService.get(id);
		mav.addObject("data", data);
		mav.addObject("imageUploadPath", ConfigurePath.IMG_UPLOAD_PATH);
		mav.addObject("imagePath", ConfigurePath.IMG_FILE_PATH);
		return mav;
	}
	
	@RequestMapping(value = "/update")
	@ResponseBody
	public Object update(StudyAssessPoints info) throws RepsException {
		try {
			StudyAssessPoints old = assessPointsService.get(info.getId());
			if (old == null) {
				return ajax(AjaxStatus.ERROR, "该评价指标设置不存在");
			}
			if (StringUtils.isNotBlank(info.getItem())) {
				old.setItem(info.getItem());
			}
			if (info.getMark() != null) {
				old.setMark(info.getMark());
			}
			if (info.getPointsScope() != null) {
				old.setPointsScope(info.getPointsScope());
			}
			if (info.getIsEnable() != null) {
				old.setIsEnable(info.getIsEnable());
			}
			if (StringUtils.isNotBlank(info.getDescription())) {
				old.setDescription(info.getDescription());
			}
			old.setIcon(info.getIcon());
			assessPointsService.update(old);
			return ajax(AjaxStatus.OK, "修改评价指标成功");
		} catch (Exception e) {
			logger.error("评价分值设置异常", e);
			return ajax(AjaxStatus.ERROR, "修改评价指标异常");
		}
	}
	
	@RequestMapping(value = "/showdetail")
	public ModelAndView showDetail(String id) {
		ModelAndView mav = new ModelAndView("/jifen/pjfzsz/detail");
		StudyAssessPoints data = assessPointsService.get(id);
		mav.addObject("data", data);
		return mav;
	}
	
	@RequestMapping(value = "/isenable")
	@ResponseBody
	public Object updateIsEnable(StudyAssessPoints info) throws RepsException {
		StudyAssessPoints data = assessPointsService.get(info.getId());
		if (data != null) {
			data.setIsEnable(info.getIsEnable());
			assessPointsService.update(data);
			return ajax(AjaxStatus.OK, "修改成功");
		} else {
			return ajax(AjaxStatus.ERROR, "该评价指标设置不存在");
		}
	}
	
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Object delete(String ids) throws RepsException {
		if (StringUtils.isBlank(ids)) {
			return ajax(AjaxStatus.ERROR, "请求参数错误");
		}
		String[] sIds = ids.split(",");
		for (String id : sIds) {
			assessPointsService.delete(id);
		}
		return ajax(AjaxStatus.OK, "删除成功");
	}
	
}
