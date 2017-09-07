package com.reps.jifen.rest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.reps.core.orm.ListResult;
import com.reps.core.restful.RestBaseController;
import com.reps.core.restful.RestResponse;
import com.reps.core.restful.RestResponseStatus;
import com.reps.jifen.entity.JfParentPointsAssign;
import com.reps.jifen.service.IJfParentPointsAssignService;

@RestController
@RequestMapping(value = "/oapi/parentpointsassign")
public class JfParentPointsAssignRest extends RestBaseController{
	
	private static Log logger = LogFactory.getLog(JfParentPointsAssignRest.class);
	
	@Autowired
	private IJfParentPointsAssignService jfParentPointsAssignService;

	@RequestMapping(value = "/list", method = { RequestMethod.GET })
	public RestResponse<ListResult<JfParentPointsAssign>> list(JfParentPointsAssign jfParentPointsAssign, Integer pageIndex, Integer pageSize) {
		try {
			ListResult<JfParentPointsAssign> result = jfParentPointsAssignService.query(jfParentPointsAssign, pageIndex, pageSize);
			return wrap(RestResponseStatus.OK, "查询成功", result);
		} catch (Exception e) {
			logger.error("添加异常", e);
			return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, "查询异常：" + e.getMessage());
		}
	}

}
