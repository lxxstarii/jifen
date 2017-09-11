package com.reps.jifen.rest;

import static com.reps.jifen.entity.enums.CategoryType.REWARD;
import static com.reps.jifen.entity.enums.RewardStatus.PUBLISHED;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reps.core.orm.ListResult;
import com.reps.core.restful.RestBaseController;
import com.reps.core.restful.RestResponse;
import com.reps.core.restful.RestResponseStatus;
import com.reps.jifen.entity.JfReward;
import com.reps.jifen.entity.JfRewardCategory;
import com.reps.jifen.service.IJfRewardService;
import static com.reps.jifen.util.PageUtil.*;

@RestController
@RequestMapping(value = "/uapi/reward")
public class JfRewardRest extends RestBaseController {

	private final Log logger = LogFactory.getLog(JfRewardRest.class);

	@Autowired
	private IJfRewardService jfRewardService;

	/**
	 * 查询已经发布的物品列表
	 * 
	 * @param pageIndex
	 * @param pageSize
	 * @return RestResponse<ListResult<JfReward>>
	 */
	@RequestMapping(value = "/list")
	public RestResponse<ListResult<JfReward>> list(Integer pageIndex, Integer pageSize) {
		try {
			JfReward jfReward = new JfReward();
			JfRewardCategory jfRewardCategory = new JfRewardCategory();
			jfRewardCategory.setType(REWARD.getIndex());
			jfReward.setJfRewardCategory(jfRewardCategory);
			jfReward.setIsShown(PUBLISHED.getIndex());
			ListResult<JfReward> result = jfRewardService.query(getStartIndex(pageIndex, pageSize), cps(pageSize), jfReward);
			//设置页大小
			result.setPageSize(pageSize);
			return wrap(RestResponseStatus.OK, "查询成功", result);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询异常", e);
			return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, "查询异常:" + e.getMessage());
		}
	}
	
	/**
	 * 物品详情
	 * @param id 物品ID
	 * @return RestResponse<JfReward>
	 */
	@RequestMapping(value = "/detail")
	public RestResponse<JfReward> detail(String id) {
		try {
			JfReward jfReward = jfRewardService.get(id);
			return wrap(RestResponseStatus.OK, "查询成功", jfReward);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询异常", e);
			return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

}
