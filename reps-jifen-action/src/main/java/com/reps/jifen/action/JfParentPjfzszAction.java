package com.reps.jifen.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.reps.core.RepsConstant;
import com.reps.core.commons.Pagination;
import com.reps.core.orm.ListResult;
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

}
