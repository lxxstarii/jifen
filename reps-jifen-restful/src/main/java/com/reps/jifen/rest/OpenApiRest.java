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
import com.reps.jifen.entity.JfPointLevel;
import com.reps.jifen.entity.JfSystemConfig;
import com.reps.jifen.service.IJfPointLevelService;
import com.reps.jifen.service.IJfSystemConfigService;
import com.reps.jifen.util.JfLevelUtil;

@RestController
@RequestMapping(value = "/oapi/jfopenapi")
public class OpenApiRest extends RestBaseController {

	private final Log logger = LogFactory.getLog(SystemConfigRest.class);

	@Autowired
	private IJfSystemConfigService jfSystemConfigService;
	
	@Autowired
	private IJfPointLevelService jfPointLevelService;
	
	@RequestMapping(value = "/appuselist")
	public RestResponse<Map<String, Object>> list() {
		try {
			Map<String, Object> map = new HashMap<>();
			List<Map<String, Object>> mapList = new ArrayList<>();
			JfSystemConfig config = new JfSystemConfig();
			config.setIsEnabled(1);
			List<JfSystemConfig> list = jfSystemConfigService.queryAll(config);
			convertMapList(list, mapList);
			map.put("data", mapList);
			return wrap(RestResponseStatus.OK, "查询成功", map);
		} catch (Exception e) {
			logger.error("查询异常", e);
			return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, "查询异常：" + e.getMessage());
		}
	}
	
	@RequestMapping(value = "/getlevel")
	public RestResponse<Integer> getLevel(Integer points) {
		RestResponse<Integer> result = new RestResponse<>();
		try {
			if (points == null) {
				result.setStatus(RestResponseStatus.INTERNAL_SERVER_ERROR.code());
				result.setMessage("请求参数错误");
				return result;
			}
			List<JfPointLevel> list = jfPointLevelService.queryAll(null);
			result.setResult(JfLevelUtil.getPointsLevel(points, list));
			result.setStatus(RestResponseStatus.OK.code());
			result.setMessage("查询成功");
		} catch (Exception e) {
			logger.error("查询异常", e);
			return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, "查询异常：" + e.getMessage());
		}
		return result;
	}
	
	private void convertMapList(List<JfSystemConfig> list, List<Map<String, Object>> mapList) {
		if (list != null && !list.isEmpty()) {
			for (JfSystemConfig config : list) {
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
