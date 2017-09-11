package com.reps.jifen.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reps.core.restful.RestBaseController;
import com.reps.core.restful.RestResponse;
import com.reps.core.restful.RestResponseStatus;
import com.reps.jifen.entity.JfPointLevel;
import com.reps.jifen.service.IJfPointLevelService;

/**
 * 积分等级
 * @author qianguobing
 * @date 2017年9月9日 下午2:59:37
 */
@RestController
@RequestMapping(value = "/uapi/pointlevel")
public class JfPointLevelRest  extends RestBaseController{
	
	protected final Logger logger = LoggerFactory.getLogger(JfPointLevelRest.class);
	
	@Autowired
	private IJfPointLevelService jfPointLevelService;
	
	@RequestMapping(value = "/list")
	public RestResponse<List<JfPointLevel>> list() {
		try {
			List<JfPointLevel> result = jfPointLevelService.queryAll(null);
			return wrap(RestResponseStatus.OK, "查询成功", result);
		} catch (Exception e) {
			logger.error("查询异常", e);
			return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, "查询异常：" + e.getMessage());
		}
	}
	
}
