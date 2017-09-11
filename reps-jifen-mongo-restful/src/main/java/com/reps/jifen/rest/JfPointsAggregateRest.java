package com.reps.jifen.rest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.reps.core.restful.RestBaseController;
import com.reps.core.restful.RestResponse;
import com.reps.core.restful.RestResponseStatus;
import com.reps.jifen.entity.JfPointsAggregate;
import com.reps.jifen.service.IJfPointsAggregateService;

@RestController
@RequestMapping(value = "/uapi/pointsaggregate")
public class JfPointsAggregateRest extends RestBaseController{
	
	private static Log logger = LogFactory.getLog(JfPointsAggregateRest.class);
	
	@Autowired
	private IJfPointsAggregateService jfPointsAggregateService;

	@RequestMapping(value = "/level", method = { RequestMethod.GET })
	public RestResponse<JfPointsAggregate> level(String personId) {
		try {
			JfPointsAggregate point = jfPointsAggregateService.getByPersonId(personId);
			return wrap(RestResponseStatus.OK, "查询成功", point);
		} catch (Exception e) {
			logger.error("查询异常", e);
			return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, "查询异常：" + e.getMessage());
		}
	}

}
