package com.reps.jifen.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.reps.core.orm.ListResult;
import com.reps.core.restful.RestBaseController;
import com.reps.core.restful.RestResponse;
import com.reps.core.restful.RestResponseStatus;
import com.reps.jifen.entity.PointsAggregate;
import com.reps.jifen.entity.PointsCollect;
import com.reps.jifen.entity.PointsExchange;
import com.reps.jifen.entity.enums.Sources;
import com.reps.jifen.service.IPointsAggregateService;
import com.reps.jifen.service.IPointsCollectService;
import com.reps.jifen.service.IPointsExchangeService;

@RestController
@RequestMapping(value = "/oapi/pointsexchange")
public class PointsExchangeORest extends RestBaseController{
	
	private static Log logger = LogFactory.getLog(PointsExchangeORest.class);
	
	@Autowired
	IPointsExchangeService jfPointsExchangeService;
	
	@Autowired
	IPointsAggregateService aggreateService;
	
	@Autowired
	IPointsCollectService jfPointsCollectService;
	
	/**
	 * 积分兑换记录查询
	 * @param count
	 * @return RestResponse<ListResult<JfPointsExchange>>
	 */
	@RequestMapping(value = "/listcount", method = { RequestMethod.GET })
	public RestResponse<List<PointsExchange>> list(Integer count) {
		try {
			List<PointsExchange> result = jfPointsExchangeService.findByCount(count);
			return wrap(RestResponseStatus.OK, "查询成功", result);
		} catch (Exception e) {
			logger.error("查询异常", e);
			return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, "查询异常：" + e.getMessage());
		}
	}
	
	/**
	 * 积分兑换记录查询
	 * @param pageIndex
	 * @param pageSize
	 * @return RestResponse<ListResult<JfPointsExchange>>
	 */
	@RequestMapping(value = "/list", method = { RequestMethod.GET })
	public RestResponse<ListResult<PointsExchange>> list(Integer pageIndex, Integer pageSize) {
		try {
			ListResult<PointsExchange> result = jfPointsExchangeService.findAll(pageIndex, pageSize);
			return wrap(RestResponseStatus.OK, "查询成功", result);
		} catch (Exception e) {
			logger.error("查询异常", e);
			return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, "查询异常：" + e.getMessage());
		}
	}
	
	@RequestMapping(value = "/cancel", method = RequestMethod.POST)
	public RestResponse<String> cancel(PointsExchange info, HttpServletRequest request) {
		try {
			if (StringUtils.isBlank(info.getPersonId()) || StringUtils.isBlank(info.getRewardId())
					|| info.getType() == null) {
				
				return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, "请求参数错误");
			}
			PointsExchange data = jfPointsExchangeService.get(info);
			if (data == null) {
				return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, "兑换记录不存在");
			}
			//获取个人积分,并修改
			PointsAggregate aggregate = aggreateService.getByPersonId(info.getPersonId());
			if (aggregate == null) {
				return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, "个人积分记录不存在");
			}
			aggregate.setTotalPointsUsable(aggregate.getTotalPointsUsable() + data.getPoints());
			//修改兑换记录
			data.setStatus(PointsExchange.CALCEL_STATUS);
			jfPointsExchangeService.save(data);
			
			aggreateService.update(aggregate);
			//添加记录日志
			PointsCollect collect = new PointsCollect();
			collect.setPersonId(info.getPersonId());
			collect.setPersonName(info.getName());
			if (data.getType() == PointsExchange.REWARD_TYPE) {
				collect.setGetFrom(Sources.GOODS_EXCHANGE.getValue());
				collect.setRuleCode(Sources.GOODS_EXCHANGE.getName());
			} else {
				collect.setGetFrom(Sources.ACTIVITY_CANCELLED.getValue());
				collect.setRuleCode(Sources.ACTIVITY_CANCELLED.getName());
			}
			collect.setPoints(info.getPoints());
			if (data.getType() == PointsExchange.REWARD_TYPE) {
				collect.setRecordId(info.getOrderId());
			} else {
				collect.setRecordId(info.getRewardId());
			}
			collect.setRuleName(info.getRewardName());
			collect.setTotalPoints(aggregate.getTotalPoints());
			collect.setTotalPointsUsable(aggregate.getTotalPointsUsable());
			jfPointsCollectService.save(collect);
			return wrap(RestResponseStatus.OK, "保存成功");
		} catch (Exception e) {
			logger.error("修改兑换记录失败", e);
			return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, "修改兑换记录失败:" + e.getMessage());
		}
	}
	
}
