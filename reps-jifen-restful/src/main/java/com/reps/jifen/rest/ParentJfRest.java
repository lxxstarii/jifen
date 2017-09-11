package com.reps.jifen.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reps.core.restful.RestBaseController;
import com.reps.core.restful.RestResponse;
import com.reps.core.restful.RestResponseStatus;
import com.reps.core.standard.StandardDataContext;
import com.reps.jifen.entity.JfParentPjfzsz;
import com.reps.jifen.service.IJfParentPjfzszService;

@RestController
@RequestMapping(value = "/uapi/parentjc")
public class ParentJfRest extends RestBaseController {

private final Log logger = LogFactory.getLog(TeachJfRest.class);
	
	@Autowired
	IJfParentPjfzszService service;

	@RequestMapping(value = "/list")
	public RestResponse<Map<String, Object>> list(JfParentPjfzsz query) {
		RestResponse<Map<String, Object>> result = new RestResponse<>();
		try {
			Map<String, Object> map = new HashMap<>();
			query.setIsEnabled((short) 1);
			List<Map<String, Object>> listMap = new ArrayList<>();
			List<JfParentPjfzsz> list = service.find(query);
			fillStudyRewardList(list, listMap);
			map.put("data", listMap);
			result.setResult(map);
		} catch (Exception e) {
			logger.error("获取家庭奖励规则失败" + e);
			result.setStatus(RestResponseStatus.INTERNAL_SERVER_ERROR.code());
			result.setMessage("获取家庭奖励规则失败"+ e);
			return result;
		}
		result.setStatus(RestResponseStatus.OK.code());
		result.setMessage("获取列表成功");
		return result;
	}
	
	private void fillStudyRewardList(List<JfParentPjfzsz> list, List<Map<String, Object>> listMap) {
		if (list != null && !list.isEmpty()) {
			for (JfParentPjfzsz data : list) {
				Map<String, Object> map = new HashMap<>();
				map.put("id", data.getId());
				map.put("item", data.getItem());
				map.put("content", data.getContent());
				map.put("pointsScope", data.getPointsScope());
				map.put("applyGrade", data.getApplyGrade());
				if (StringUtils.isNotBlank(data.getApplyGrade())) {
					map.put("applyGrade", StandardDataContext.getDictionaryItemName("grade", data.getApplyGrade()));
				}
				listMap.add(map);
			}
		}
	}
}
