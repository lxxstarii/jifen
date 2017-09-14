package com.reps.jifen.rest;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reps.core.restful.RestBaseController;
import com.reps.core.restful.RestResponse;
import com.reps.core.restful.RestResponseStatus;
import com.reps.jifen.entity.SystemConfig;
import com.reps.jifen.service.ISystemConfigService;

/**
 * 获取积分规则
 * @author qianguobing
 * @date 2017年9月9日 上午11:03:09
 */
@RestController
@RequestMapping(value = "/uapi/appuse")
public class SystemConfigRest extends RestBaseController{
	
	private final Log logger = LogFactory.getLog(SystemConfigRest.class);

	@Autowired
	private ISystemConfigService jfSystemConfigService;
	
	@RequestMapping(value = "/list")
	public RestResponse<List<SystemConfig>> list() {
		try {
			SystemConfig config = new SystemConfig();
			config.setIsEnabled(1);
			List<SystemConfig> result = jfSystemConfigService.queryAll(config);
			return wrap(RestResponseStatus.OK, "查询成功", result);
		} catch (Exception e) {
			logger.error("查询异常", e);
			return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, "查询异常：" + e.getMessage());
		}
	}
	
}
