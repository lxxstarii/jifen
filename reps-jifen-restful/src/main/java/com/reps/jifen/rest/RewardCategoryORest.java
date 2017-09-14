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
import com.reps.jifen.entity.RewardCategory;
import com.reps.jifen.service.IRewardCategoryService;

@RestController
@RequestMapping(value = "/oapi/rewardcategory")
public class RewardCategoryORest extends RestBaseController{
	
	private final Log logger = LogFactory.getLog(RewardCategoryORest.class);
	
	@Autowired
	private IRewardCategoryService jfRewardCategoryService;
	
	/**
	 * 查询物品,活动分类信息
	 * @param jfRewardCategory
	 * @return RestResponse<List<JfRewardCategory>>
	 */
	@RequestMapping(value = "/list")
	public RestResponse<List<RewardCategory>> list(RewardCategory jfRewardCategory) {
		try {
			List<RewardCategory> result = jfRewardCategoryService.getRewardCategory(jfRewardCategory);
			return wrap(RestResponseStatus.OK, "查询成功", result);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询异常", e);
			return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
	
}
