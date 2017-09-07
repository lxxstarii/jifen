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
import com.reps.jifen.entity.JfPointsExchange;
import com.reps.jifen.service.IJfPointsExchangeService;

@RestController
@RequestMapping(value = "/uapi/pointsexchange")
public class JfPointsExchangeRest extends RestBaseController{
	
	private static Log logger = LogFactory.getLog(JfPointsExchangeRest.class);
	
	@Autowired
	private IJfPointsExchangeService jfPointsExchangeService;

	/**
	 * 积分兑换记录查询
	 * 包含教师和学生
	 * @param personId
	 * @param pageIndex
	 * @param pageSize
	 * @return RestResponse<ListResult<JfPointsExchange>>
	 */
	@RequestMapping(value = "/list", method = { RequestMethod.GET })
	public RestResponse<ListResult<JfPointsExchange>> list(String personId, Integer pageIndex, Integer pageSize) {
		try {
			ListResult<JfPointsExchange> result = jfPointsExchangeService.findByPersonId(personId, pageIndex, pageSize);
			return wrap(RestResponseStatus.OK, "查询成功", result);
		} catch (Exception e) {
			logger.error("查询异常", e);
			return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, "查询异常：" + e.getMessage());
		}
	}

}
