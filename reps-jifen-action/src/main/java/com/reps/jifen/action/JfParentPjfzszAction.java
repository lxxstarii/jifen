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
import com.reps.core.util.StringUtil;
import com.reps.core.web.AjaxStatus;
import com.reps.core.web.BaseAction;
import com.reps.jifen.entity.JfParentPjfzsz;
import com.reps.jifen.service.IJfParentPjfzszService;

/**
 * 积分家庭行为评分设置
 * @author qianguobing
 * @date 2017年8月28日 下午5:20:45
 */
@Controller
@RequestMapping(value = RepsConstant.ACTION_BASE_PATH + "/jifen/parentpjfzsz")
public class JfParentPjfzszAction extends BaseAction {
	
	protected final Logger logger = LoggerFactory.getLogger(JfParentPjfzszAction.class);
	
	@Autowired
	IJfParentPjfzszService jfParentPjfzszService;
	
	/**
	 * 家庭行为评分设置列表 
	 * @param pager
	 * @param jfParentPjfzsz
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(Pagination pager, JfParentPjfzsz jfParentPjfzsz){
		ModelAndView mav = getModelAndView("/jifen/parentpjfzsz/list");
		ListResult<JfParentPjfzsz> listResult = jfParentPjfzszService.query(pager.getStartRow(), pager.getPageSize(), jfParentPjfzsz);
		//分页数据
		mav.addObject("list", listResult.getList());
		//分页参数
		mav.addObject("pager", pager);
		return mav;
	}
	
	/**
	 * 家庭行为评分设置入口
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/toadd")
	public ModelAndView toAdd() {
		ModelAndView mav = getModelAndView("/jifen/parentpjfzsz/add");
		return mav;
	}
	
	/**
	 * 家庭行为评分设置新增
	 * @param jfParentPjfzsz
	 * @return Object
	 * @throws RepsException
	 */
	@RequestMapping(value = "/add")
	@ResponseBody
	public Object add(JfParentPjfzsz jfParentPjfzsz) throws RepsException {
		if (jfParentPjfzsz == null) {
			throw new RepsException("数据不完整");
		}
		jfParentPjfzszService.save(jfParentPjfzsz);
		return ajax(AjaxStatus.OK, "添加成功");
	}
	
	/**
	 * 家庭行为评分设置修改入口
	 * @param id
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/toedit")
	public ModelAndView toEdit(String id) {
		ModelAndView mav = getModelAndView("/jifen/parentpjfzsz/edit");
		JfParentPjfzsz jfParentPjfzsz = jfParentPjfzszService.get(id);
		mav.addObject("parentPjfzsz", jfParentPjfzsz);
		return mav;
	}
	
	/**
	 * 家庭行为评分设置修改
	 * @param jfParentPjfzsz
	 * @return Object
	 * @throws RepsException
	 */
	@RequestMapping(value = "/edit")
	@ResponseBody
	public Object edit(JfParentPjfzsz jfParentPjfzsz) throws RepsException {
		if (jfParentPjfzsz == null) {
			throw new RepsException("数据不完整");
		}
		jfParentPjfzszService.update(jfParentPjfzsz);
		return ajax(AjaxStatus.OK, "修改成功");
	}
	
	/**
	 * 家庭行为评分设置删除
	 * @param id
	 * @return Object
	 */
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Object delete(String id) {
		try {
			JfParentPjfzsz jfParentPjfzsz = jfParentPjfzszService.get(id);
			if (jfParentPjfzsz != null) {
				jfParentPjfzszService.delete(jfParentPjfzsz);
			}
			return ajax(AjaxStatus.OK, "删除成功");
		} catch (Exception e) {
			logger.error("删除失败", e);
			return ajax(AjaxStatus.ERROR, "删除失败");
		}
	}
	
	/**
	 * 家庭行为评分设置批量删除
	 * @param ids
	 * @return Object
	 */
	@RequestMapping(value = "/batchdelete")
	@ResponseBody
	public Object batchDelete(String ids) {
		try {
			if (StringUtil.isBlank(ids)) {
				return ajax(AjaxStatus.ERROR, "删除失败");
			}
			jfParentPjfzszService.batchDelete(ids);
			return ajax(AjaxStatus.OK, "删除成功");
		} catch (Exception e) {
			logger.error("批量删除失败", e);
			return ajax(AjaxStatus.ERROR, "删除失败");
		}
	}
	
	/**
	 * 家庭行为评分设置详情
	 * @param id
	 * @return ModelAndView
	 */
	@RequestMapping({ "/show" })
	public ModelAndView show(String id) {
		ModelAndView mav = new ModelAndView("/jifen/parentpjfzsz/show");
		JfParentPjfzsz jfParentPjfzsz = jfParentPjfzszService.get(id);
		mav.addObject("parentPjfzsz", jfParentPjfzsz);
		return mav;
	}

}
