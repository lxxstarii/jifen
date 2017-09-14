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
import com.reps.jifen.entity.PointsExchange;
import com.reps.jifen.service.IPointsExchangeService;

@RestController
@RequestMapping(value = "/uapi/pointsexchange")
public class PointsExchangeRest extends RestBaseController{
	
	private static Log logger = LogFactory.getLog(PointsExchangeRest.class);
	
	@Autowired
	private IPointsExchangeService jfPointsExchangeService;

	/**
	 * 积分兑换记录查询
	 * 包含教师和学生
	 * @param personId
	 * @param pageIndex
	 * @param pageSize
	 * @return RestResponse<ListResult<JfPointsExchange>>
	 */
	@RequestMapping(value = "/list", method = { RequestMethod.GET })
	public RestResponse<ListResult<PointsExchange>> list(String personId, Integer pageIndex, Integer pageSize) {
		try {
			ListResult<PointsExchange> result = jfPointsExchangeService.findByPersonId(personId, pageIndex, pageSize);
			return wrap(RestResponseStatus.OK, "查询成功", result);
		} catch (Exception e) {
			logger.error("查询异常", e);
			return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, "查询异常：" + e.getMessage());
		}
	}

}
