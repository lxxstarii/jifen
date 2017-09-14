package com.reps.jifen.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
@RequestMapping(value = "/oapi/appuse")
public class SystemConfigORest extends RestBaseController{
	
	private final Log logger = LogFactory.getLog(SystemConfigORest.class);

	@Autowired
	private ISystemConfigService jfSystemConfigService;
	
	@RequestMapping(value = "/list")
	public RestResponse<Map<String, Object>> list() {
		try {
			Map<String, Object> map = new HashMap<>();
			List<Map<String, Object>> mapList = new ArrayList<>();
			SystemConfig config = new SystemConfig();
			config.setIsEnabled(1);
			List<SystemConfig> list = jfSystemConfigService.queryAll(config);
			convertMapList(list, mapList);
			map.put("data", mapList);
			return wrap(RestResponseStatus.OK, "查询成功", map);
		} catch (Exception e) {
			logger.error("查询异常", e);
			return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, "查询异常：" + e.getMessage());
		}
	}
	
	private void convertMapList(List<SystemConfig> list, List<Map<String, Object>> mapList) {
		if (list != null && !list.isEmpty()) {
			for (SystemConfig config : list) {
				Map<String, Object> map = new HashMap<>();
				map.put("id", config.getId());
				map.put("applicationName", config.getApplicationName());
				map.put("item", config.getItem());
				map.put("code", config.getCode());
				map.put("mark", config.getMark());
				map.put("points", config.getPoints());
				mapList.add(map);
			}
		}
	}
}
