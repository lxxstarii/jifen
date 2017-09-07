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
import com.reps.jifen.entity.JfPointsCollect;
import com.reps.jifen.service.IJfPointsCollectService;

@RestController
@RequestMapping(value = "/uapi/pointscollect")
public class JfPointsCollectRest extends RestBaseController{
	
	private static Log logger = LogFactory.getLog(JfPointsCollectRest.class);
	
	@Autowired
	private IJfPointsCollectService jfPointsCollectService;

	@RequestMapping(value = "/save", method = { RequestMethod.POST })
	public RestResponse<JfPointsCollect> save(JfPointsCollect jfPointsCollect) {
		try {
			JfPointsCollect bean = jfPointsCollectService.save(jfPointsCollect);
			return wrap(RestResponseStatus.OK, "保存成功", bean);
		} catch (Exception e) {
			logger.error("添加异常", e);
			return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, "添加异常：" + e.getMessage());
		}
	}
	
	@RequestMapping(value = "/list", method = { RequestMethod.GET })
	public RestResponse<ListResult<JfPointsCollect>> list(String personId, Integer pageIndex, Integer pageSize) {
		try {
			ListResult<JfPointsCollect> result = jfPointsCollectService.findByPersonId(personId, pageIndex, pageSize);
			return wrap(RestResponseStatus.OK, "查询成功", result);
		} catch (Exception e) {
			logger.error("查询异常", e);
			return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, "查询异常：" + e.getMessage());
		}
	}

}
