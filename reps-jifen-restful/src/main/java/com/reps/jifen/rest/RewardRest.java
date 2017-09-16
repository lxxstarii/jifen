package com.reps.jifen.rest;

import static com.reps.jifen.entity.enums.CategoryType.REWARD;
import static com.reps.jifen.util.PageUtil.cps;
import static com.reps.jifen.util.PageUtil.getStartIndex;
import static com.reps.jifen.util.RewardUtil.setPictureUrls;
import static com.reps.jifen.util.RewardUtil.setReward;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reps.core.exception.RepsException;
import com.reps.core.orm.ListResult;
import com.reps.core.restful.RestBaseController;
import com.reps.core.restful.RestResponse;
import com.reps.core.restful.RestResponseStatus;
import com.reps.jifen.entity.PointReward;
import com.reps.jifen.service.IRewardService;

@RestController
@RequestMapping(value = "/uapi/reward")
public class RewardRest extends RestBaseController {

	private final Log logger = LogFactory.getLog(RewardRest.class);

	@Autowired
	private IRewardService jfRewardService;

	/**
	 * 查询我能兑换的物品列表
	 * 
	 * @param jfReward
	 * @param pageIndex
	 * @param pageSize
	 * @return RestResponse<ListResult<JfReward>>
	 */
	@RequestMapping(value = "/list")
	public RestResponse<ListResult<PointReward>> list(PointReward jfReward, Integer pageIndex, Integer pageSize) {
		try {
			Integer points = jfReward.getPoints();
			if(null == points) {
				throw new RepsException("个人积分不能为空");
			}
			setReward(jfReward, REWARD.getIndex());
			pageSize = cps(pageSize);
			ListResult<PointReward> result = jfRewardService.query(getStartIndex(pageIndex, pageSize), pageSize, jfReward);
			if(null == result) {
				throw new RepsException("查询物品列表异常");
			}
			setPictureUrls(result.getList(), this.getFileHttpPath());
			// 设置页大小
			result.setPageSize(pageSize);
			return wrap(RestResponseStatus.OK, "查询成功", result);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询异常", e);
			return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, "查询异常:" + e.getMessage());
		}
	}

}
