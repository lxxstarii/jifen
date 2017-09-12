package com.reps.jifen.rest;

import static com.reps.jifen.entity.enums.CategoryType.ACTIVITY;
import static com.reps.jifen.util.PageUtil.cps;
import static com.reps.jifen.util.PageUtil.getStartIndex;
import static com.reps.jifen.util.RewardUtil.setReward;

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
import com.reps.jifen.service.IJfActivityRewardService;

@RestController
@RequestMapping(value = "/uapi/activity")
public class JfActivityRewardRest extends RestBaseController {

	private final Log logger = LogFactory.getLog(JfActivityRewardRest.class);

	@Autowired
	private IJfActivityRewardService jfActivityRewardService;
	
	/**
	 * 查询已经发布的活动列表
	 * 
	 * @param jfReward
	 * @param pageIndex
	 * @param pageSize
	 * @return RestResponse<ListResult<JfReward>>
	 */
	@RequestMapping(value = "/list")
	public RestResponse<ListResult<JfReward>> list(JfReward jfReward, Integer pageIndex, Integer pageSize) {
		try {
			setReward(jfReward, ACTIVITY.getIndex());
			pageSize = cps(pageSize);
			ListResult<JfReward> result = jfActivityRewardService.query(getStartIndex(pageIndex, pageSize), pageSize, jfReward);
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
	 * 活动详情
	 * @param id 活动ID
	 * @return RestResponse<JfReward>
	 */
	@RequestMapping(value = "/detail")
	public RestResponse<JfReward> detail(String id) {
		try {
			JfReward jfReward = jfActivityRewardService.get(id);
			return wrap(RestResponseStatus.OK, "查询成功", jfReward);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询异常", e);
			return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
	
}
