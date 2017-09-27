package com.reps.jifen.rest;


import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reps.core.restful.RestBaseController;
import com.reps.core.restful.RestResponse;
import com.reps.core.restful.RestResponseStatus;
import com.reps.jifen.entity.PointsAggregate;
import com.reps.jifen.entity.PointsCollect;
import com.reps.jifen.service.IPointsAggregateService;
import com.reps.jifen.service.IPointsCollectService;
import com.reps.jifen.util.HttpRequstUtil;

@RestController
@RequestMapping(value = "/oapi/openapi")
public class OpenApiRest extends RestBaseController {
	
	private final Log logger = LogFactory.getLog(OpenApiRest.class);
	
	@Autowired
	IPointsCollectService collectService;
	
	@Autowired
	IPointsAggregateService aggreateService;
	
	@Value("${http.jifen.url}")
	private String levelUrl;

	@RequestMapping(value = "/savejfcollect")
	public RestResponse<String> saveJfPointsCollect(PointsCollect info) {
		RestResponse<String> result = new RestResponse<>();
		try {
			if (StringUtils.isBlank(info.getPersonId()) || StringUtils.isBlank(info.getPersonName())
					|| StringUtils.isBlank(info.getGetFrom()) || StringUtils.isBlank(info.getRecordId())
					|| StringUtils.isBlank(info.getRuleCode()) || StringUtils.isBlank(info.getRuleName())
					|| info.getPoints() == null ) {
				
				return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, "请求参数错误");
			}
			//修改个人积分表
			PointsAggregate aggregate = aggreateService.getByPersonId(info.getPersonId());
			if (aggregate == null) {
				aggregate = new PointsAggregate();
				aggregate.setPersonId(info.getPersonId());
				aggregate.setTotalPoints(info.getPoints() < 0 ? 0 : (0 + info.getPoints()));
				aggregate.setTotalPointsUsable(info.getPoints());
				//获取个人积分级别
				JSONObject jsonObject = HttpRequstUtil.getGetUrlResponse(levelUrl 
						+ "/oapi/jfopenapi/getlevel?points=" + aggregate.getTotalPoints());
				if (jsonObject != null) {
					if (jsonObject.getInt("status") == 200) {
						aggregate.setLevel((short) jsonObject.getInt("result"));
					} else if (jsonObject.getInt("status") == 403) {
						logger.error(jsonObject.getString("message"));
						throw new Exception(jsonObject.getString("message"));
					} else if (jsonObject.getInt("status") == 500) {
						logger.error(jsonObject.getString("message"));
						throw new Exception(jsonObject.getString("message"));
					} 
				} 
				aggreateService.save(aggregate);
			}
			collectService.save(info);
		} catch (Exception e) {
			logger.error("保存积分日志记录异常", e);
			return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, "保存积分日志记录异常：" + e.getMessage());
		}
		return result;
	}
}
