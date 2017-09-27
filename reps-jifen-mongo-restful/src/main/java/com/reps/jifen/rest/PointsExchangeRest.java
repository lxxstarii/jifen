package com.reps.jifen.rest;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
@RequestMapping(value = "/uapi/pointsexchange")
public class PointsExchangeRest extends RestBaseController{
	
	private static Log logger = LogFactory.getLog(PointsExchangeRest.class);
	
	@Autowired
	IPointsExchangeService jfPointsExchangeService;
	
	@Autowired
	IPointsAggregateService aggreateService;
	
	@Autowired
	IPointsCollectService jfPointsCollectService;
	
	@Value("${http.jifen.url}")
	private String levelUrl;
	
	/**
	 * 积分兑换记录查询
	 * @param personId
	 * @param pageIndex
	 * @param pageSize
	 * @return RestResponse<ListResult<JfPointsExchange>>
	 */
	@RequestMapping(value = "/list", method = { RequestMethod.GET })
	public RestResponse<ListResult<PointsExchange>> list(String personId, Short status, Integer pageIndex, Integer pageSize) {
		try {
			if (StringUtils.isBlank(personId)) {
				return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, "请求参数错误");
			}
			ListResult<PointsExchange> result = null;
			if (status != null) {
				result = jfPointsExchangeService.findByPersonIdAndStatus(personId, status, pageIndex, pageSize);
			} else {
				result = jfPointsExchangeService.findByPersonId(personId, pageIndex, pageSize);
			}
			transformList(result.getList());
			return wrap(RestResponseStatus.OK, "查询成功", result);
		} catch (Exception e) {
			logger.error("查询异常", e);
			return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, "查询异常：" + e.getMessage());
		}
	}
	

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public RestResponse<String> save(PointsExchange info, HttpServletRequest request) {
		try {
			if (StringUtils.isBlank(info.getPersonId()) || StringUtils.isBlank(info.getRewardId())
					|| StringUtils.isBlank(info.getRewardName()) || info.getPoints() == null
					|| StringUtils.isBlank(info.getName()) || StringUtils.isBlank(info.getSchoolName())
					|| StringUtils.isBlank(info.getSchoolId()) || info.getType() == null) {
				
				return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, "请求参数错误");
			}
			//获取个人积分,并修改
			PointsAggregate aggregate = aggreateService.getByPersonId(info.getPersonId());
			if (aggregate == null) {
				return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, "个人积分记录不存在");
			}
			if (aggregate.getTotalPointsUsable() - info.getPoints() < 0) {
				return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, "当前积分不足");
			}
			aggregate.setTotalPointsUsable(aggregate.getTotalPointsUsable() - info.getPoints());
			aggreateService.update(aggregate);
			//添加兑换记录
			PointsExchange data = new PointsExchange();
			BeanUtils.copyProperties(info, data);
			data.setExchangeTime(new Date());
			data.setStatus(PointsExchange.ADD_STATUS);
			jfPointsExchangeService.save(data);
			//添加记录日志
			PointsCollect collect = new PointsCollect();
			collect.setPersonId(info.getPersonId());
			collect.setPersonName(info.getName());
			if (data.getType() == PointsExchange.REWARD_TYPE) {
				collect.setGetFrom(Sources.GOODS_EXCHANGE.getValue());
				collect.setRuleCode(Sources.GOODS_EXCHANGE.getName());
			} else {
				collect.setGetFrom(Sources.ACTIVITY_PARTICIPATE.getValue());
				collect.setRuleCode(Sources.ACTIVITY_PARTICIPATE.getName());
			}
			collect.setPoints(-info.getPoints());
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
			logger.error("保存兑换记录失败", e);
			return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, "保存兑换记录失败:" + e.getMessage());
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
	
	private void transformList(List<PointsExchange> list) {
		if (list != null && !list.isEmpty()) {
			for (PointsExchange data : list) {
				String name = data.getName();
				data.setName(name.replace(name.charAt(1), '*'));
			}
		}
	}
	
}
