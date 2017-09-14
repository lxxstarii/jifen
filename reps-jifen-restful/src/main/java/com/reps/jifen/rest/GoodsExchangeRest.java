package com.reps.jifen.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.reps.core.restful.AuthInfo;
import com.reps.core.restful.RestBaseController;
import com.reps.core.restful.RestResponse;
import com.reps.core.restful.RestResponseStatus;
import com.reps.jifen.entity.GoodsExchange;
import com.reps.jifen.service.IGoodsExchangeService;

@RestController
@RequestMapping(value = "/uapi/goodsexchange")
public class GoodsExchangeRest extends RestBaseController {

	private final Log logger = LogFactory.getLog(GoodsExchangeRest.class);
	
	//@Autowired
	IGoodsExchangeService goodsService;
	
	@RequestMapping(value = "/list")
	public RestResponse<Map<String, Object>> query(GoodsExchange info) {
		try {
			Map<String, Object> map = new HashMap<>();
			if (info.getState() == null) {
				return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, "请求参数错误");
			}
			AuthInfo token = getCurrentLoginInfo();
			if (StringUtils.isBlank(info.getPersonId())) {
				info.setPersonId(token.getPersonId());
			}
			List<GoodsExchange> list = goodsService.find(info);
			List<Map<String, Object>> listMap = new ArrayList<>();
			converListMap(list, listMap);
			map.put("data", listMap);
			return wrap(RestResponseStatus.OK, "获取订单列表成功", map);
		} catch (Exception e) {
			logger.error("获取订单列表异常", e);
			return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, "获取订单列表异常：" + e.getMessage());
		}
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public RestResponse<String> save(GoodsExchange info) {
		try {
			if (StringUtils.isBlank(info.getAddress()) || StringUtils.isBlank(info.getConsigneeName())
					|| StringUtils.isBlank(info.getPhone()) || StringUtils.isBlank(info.getRewardId())
					|| StringUtils.isBlank(info.getRewardName()) || info.getNums() == null 
					|| info.getUsedPoints() == null) {
				
				return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, "请求参数错误");
			}
			GoodsExchange data = new GoodsExchange();
			BeanUtils.copyProperties(info, data);
			data.setState(GoodsExchange.UN_HANDLE);
			data.setCreateTime(new Date());
			goodsService.save(data);
			return wrap(RestResponseStatus.OK, "获取订单列表成功");
		} catch (Exception e) {
			logger.error("保存订单异常", e);
			return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, "保存订单异常：" + e.getMessage());
		}
	}
	
	@RequestMapping(value = "/delete")
	public RestResponse<String> delete(String id) {
		try {
			if (StringUtils.isBlank(id)) {
				return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, "请求参数错误");
			}
			GoodsExchange old = goodsService.get(id);
			if (old != null) {
				if (old.getState() != GoodsExchange.FINISH) {
					return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, "当前订单未完成,不可删除");
				}
				goodsService.delete(old);
			}
			return wrap(RestResponseStatus.OK, "获取订单列表成功");
		} catch (Exception e) {
			logger.error("保存订单异常", e);
			return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, "保存订单异常：" + e.getMessage());
		}
	}
	
	
	private void converListMap(List<GoodsExchange> list, List<Map<String, Object>> listMap) {
		if (list != null && !list.isEmpty() && listMap != null) {
			for (GoodsExchange data : list) {
				Map<String, Object> map = new HashMap<>();
				map.put("id", data.getId());
				map.put("consigneeName", data.getConsigneeName());
				map.put("address", data.getAddress());
				map.put("phone", data.getPhone());
				map.put("nums", data.getNums());
				map.put("usedPoints", data.getUsedPoints());
				map.put("totalUsedPoints", data.getNums() * data.getUsedPoints());
				map.put("expressCompany", data.getExpressCompany());
				map.put("wayBillNum", data.getWayBillNum());
				map.put("rewardId", data.getRewardId());
				map.put("rewardName", data.getRewardName());
				listMap.add(map);
			}
		}
	}
}
