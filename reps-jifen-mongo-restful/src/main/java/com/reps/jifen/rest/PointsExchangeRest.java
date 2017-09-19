package com.reps.jifen.rest;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

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
import com.reps.jifen.util.HttpRequstUtil;
import com.reps.jifen.vo.UrlConstant;

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
	 * 包含教师和学生
	 * @param personId
	 * @param pageIndex
	 * @param pageSize
	 * @return RestResponse<ListResult<JfPointsExchange>>
	 */
	@RequestMapping(value = "/list", method = { RequestMethod.GET })
	public RestResponse<ListResult<PointsExchange>> list(String personId, Integer pageIndex, Integer pageSize) {
		try {
			ListResult<PointsExchange> result = jfPointsExchangeService.findByPersonId(personId, pageIndex, pageSize);
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
					|| StringUtils.isBlank(info.getSchoolId())) {
				
				return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, "请求参数错误");
			}
			//获取个人积分,并修改
			PointsAggregate aggregate = aggreateService.getByPersonId(info.getPersonId());
			if (aggregate == null) {
				return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, "个人积分记录不存在");
			}
			if (aggregate.getTotalPointsUsable() < info.getPoints()) {
				return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, "当前积分不足");
			}
			aggregate.setTotalPointsUsable(aggregate.getTotalPointsUsable() - info.getPoints());
			//获取个人积分级别
			JSONObject jsonObject = HttpRequstUtil.getGetUrlResponse(levelUrl 
					+ UrlConstant.GET_LEVEL + "access_token=" + request.getParameter("access_token") + "&points=" + aggregate.getTotalPointsUsable());
			if (jsonObject != null) {
				if (jsonObject.getInt("status") == 200) {
					aggregate.setLevel((short) jsonObject.getInt("result"));
				} else if (jsonObject.getInt("status") == 403) {
					return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, jsonObject.getString("message"));
				} else if (jsonObject.getInt("status") == 500) {
					return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, jsonObject.getString("message"));
				} 
			} 
			aggreateService.update(aggregate);
			//添加兑换记录
			PointsExchange data = new PointsExchange();
			BeanUtils.copyProperties(info, data);
			data.setExchangeTime(new Date());
			jfPointsExchangeService.save(data);
			//添加记录日志
			PointsCollect collect = new PointsCollect();
			collect.setPersonId(info.getPersonId());
			collect.setPersonName(info.getName());
			collect.setGetFrom(Sources.GOODS_EXCHANGE.getValue());
			collect.setRuleCode(Sources.GOODS_EXCHANGE.getName());
			collect.setPoints(-info.getPoints());
			collect.setRecordId(info.getRewardId());
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

}
