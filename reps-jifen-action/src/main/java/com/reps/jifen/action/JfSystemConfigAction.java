package com.reps.jifen.action;

import com.reps.core.RepsConstant;
import com.reps.core.commons.Pagination;
import com.reps.core.orm.ListResult;
import com.reps.core.web.AjaxStatus;
import com.reps.core.web.BaseAction;
import com.reps.jifen.entity.JfSystemConfig;
import com.reps.jifen.service.IJfSystemConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = RepsConstant.ACTION_BASE_PATH + "/jifen/appuse")
public class JfSystemConfigAction extends BaseAction {

    private static final Logger log = LoggerFactory.getLogger(JfSystemConfigAction.class);

    @Autowired
    IJfSystemConfigService systemConfigService;

    @RequestMapping(value="/list")
    public ModelAndView list(Pagination pager, JfSystemConfig config) {
        ModelAndView mav = getModelAndView("/jifen/appuse/list");
        ListResult<JfSystemConfig> result = systemConfigService.query(pager.getStartRow(), pager.getPageSize(), config);
        pager.setTotalRecord(systemConfigService.count(config));
        mav.addObject("pager", pager);
        mav.addObject("list", result.getList());
        mav.addObject("info", config);
        return mav;
    }

    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(String id) {
        try {
            systemConfigService.delete(id);
            return ajax(AjaxStatus.OK, "删除成功");
        } catch (Exception e){
            log.error("删除失败",e);
            return ajax(AjaxStatus.ERROR, "删除失败");
        }
    }
    @RequestMapping(value = "/toadd")
    public ModelAndView toadd() {
        ModelAndView mav = getModelAndView("/jifen/appuse/add");
        return mav;
    }

    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(JfSystemConfig config) {
        boolean f = systemConfigService.save(config);
        if (f) {
            return ajax(AjaxStatus.OK, "修改成功");
        } else {
            return ajax(AjaxStatus.FAIL, "标识码重复");
        }
    }

    @RequestMapping(value = "/toedit")
    public ModelAndView toedit(String id) {
        ModelAndView mav = getModelAndView("/jifen/appuse/edit");
        JfSystemConfig config = systemConfigService.get(id);
        mav.addObject("info", config);
        return mav;
    }

    @RequestMapping(value = "/edit")
    @ResponseBody
    public Object update(JfSystemConfig config) {
        boolean f = systemConfigService.update(config);
        if (f) {
            return ajax(AjaxStatus.OK, "修改成功");
        } else {
            return ajax(AjaxStatus.FAIL, "标识码重复");
        }
    }

    @RequestMapping(value = "/show")
    public ModelAndView show(String id) {
        ModelAndView mav = getModelAndView("/jifen/appuse/detail");
        JfSystemConfig config = systemConfigService.get(id);
        mav.addObject("info", config);
        return mav;
    }

}
