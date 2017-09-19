package com.reps.jifen.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.reps.core.restful.AuthInfo;
import com.reps.core.restful.RestBaseController;
import com.reps.core.restful.RestResponse;
import com.reps.core.restful.RestResponseStatus;
import com.reps.core.util.DateUtil;
import com.reps.jifen.entity.OrderInfo;
import com.reps.jifen.service.IOrderInfoService;
import com.reps.jifen.util.ConvertUrlUtil;
import com.reps.jifen.util.HttpRequstUtil;
import com.reps.jifen.vo.UrlConstant;

@RestController
@RequestMapping(value = "/uapi/orderinfo")
public class OrderInfoRest extends RestBaseController {

	private final Log logger = LogFactory.getLog(OrderInfoRest.class);
	
	@Autowired
	IOrderInfoService orderInfoService;
	
	@Value("${http.jifenmongo.url}")
	private String mongoUrl;
	
	@RequestMapping(value = "/list")
	public RestResponse<Map<String, Object>> query(OrderInfo info) {
		try {
			Map<String, Object> map = new HashMap<>();
			if (info.getStatus() == null) {
				return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, "请求参数错误");
			}
			AuthInfo token = getCurrentLoginInfo();
			if (StringUtils.isBlank(info.getPersonId())) {
				info.setPersonId(token.getPersonId());
			}
			List<OrderInfo> list = orderInfoService.find(info);
			List<Map<String, Object>> listMap = new ArrayList<>();
			converListMap(list, listMap);
			map.put("data", listMap);
			return wrap(RestResponseStatus.OK, "获取订单列表成功", map);
		} catch (Exception e) {
			logger.error("获取订单列表异常", e);
			return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, "获取订单列表异常：" + e.getMessage());
		}
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public RestResponse<String> add(OrderInfo info, HttpServletRequest request) {
		try {
			if (StringUtils.isBlank(info.getAddress()) || StringUtils.isBlank(info.getConsigneeName())
					|| StringUtils.isBlank(info.getPhone()) || StringUtils.isBlank(info.getRewardId())
					|| StringUtils.isBlank(info.getRewardName()) || info.getNums() == null 
					|| info.getUsedPoints() == null || StringUtils.isBlank(info.getSchoolId())
					|| StringUtils.isBlank(info.getSchoolName())) {
				
				return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, "请求参数错误");
			}
			OrderInfo data = new OrderInfo();
			BeanUtils.copyProperties(info, data);
			data.setStatus(OrderInfo.UN_HANDLE);
			data.setPersonId(getCurrentLoginInfo().getPersonId());
			data.setCreateTime(new Date());
			data.setOrderNo(DateUtil.format(new Date(), "yyyyMMddHHmm").substring(2));
			//提交订单(1：先获取个人积分,判断是否满足提交订单条件 2：添加个人兑换记录)
			JSONObject jsonObject = saveExchange(data, getCurrentLoginInfo(), request.getParameter("access_token"));
			if (jsonObject != null) {
				if (jsonObject.getInt("status") == 403) {
					return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, jsonObject.getString("message"));
				} else if (jsonObject.getInt("status") == 500) {
					return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, jsonObject.getString("message"));
				} 
			} 
			orderInfoService.save(data);
			return wrap(RestResponseStatus.OK, "提交订单成功");
		} catch (Exception e) {
			logger.error("提交订单异常", e);
			return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, "提交订单异常：" + e.getMessage());
		}
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public RestResponse<String> update(OrderInfo info) {
		try {
			if (info.getStatus() == null || StringUtils.isBlank(info.getId())) {
				return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, "请求参数错误");
			}
			OrderInfo old = orderInfoService.get(info.getId());
			if (old != null) {
				info.setStatus(info.getStatus());
				orderInfoService.update(old);
			}
			return wrap(RestResponseStatus.OK, "修改成功");
		} catch (Exception e) {
			logger.error("修改异常", e);
			return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, "修改异常：" + e.getMessage());
		}
	}
	
	@RequestMapping(value = "/delete")
	public RestResponse<String> delete(String id) {
		try {
			if (StringUtils.isBlank(id)) {
				return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, "请求参数错误");
			}
			OrderInfo old = orderInfoService.get(id);
			if (old != null) {
				if (old.getStatus() != OrderInfo.FINISH) {
					return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, "当前订单未完成,不可删除");
				}
				orderInfoService.delete(old);
			}
			return wrap(RestResponseStatus.OK, "删除订单成功");
		} catch (Exception e) {
			logger.error("删除订单异常", e);
			return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, "删除订单异常：" + e.getMessage());
		}
	}
	
	@RequestMapping(value = "/get")
	public RestResponse<Map<String, Object>> get(String id) {
		try {
			Map<String, Object> map = new HashMap<>();
			if (StringUtils.isBlank(id)) {
				return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, "请求参数错误");
			}
			OrderInfo data = orderInfoService.get(id);
			convertMap(data, map);
			return wrap(RestResponseStatus.OK, "获取订单详情成功", map);
		} catch (Exception e) {
			logger.error("获取订单详情异常", e);
			return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, "获取订单详情异常：" + e.getMessage());
		}
	}
	
	private void converListMap(List<OrderInfo> list, List<Map<String, Object>> listMap) {
		if (list != null && !list.isEmpty() && listMap != null) {
			for (OrderInfo data : list) {
				Map<String, Object> map = new HashMap<>();
				convertMap(data, map);
				listMap.add(map);
			}
		}
	}
	
	private void convertMap(OrderInfo data, Map<String, Object> map) {
		map.put("id", data.getId());
		map.put("consigneeName", data.getConsigneeName());
		map.put("address", data.getAddress());
		map.put("phone", data.getPhone());
		map.put("nums", data.getNums());
		map.put("usedPoints", data.getUsedPoints());
		map.put("totalUsedPoints", data.getNums() * data.getUsedPoints());
		map.put("expressCompany", data.getExpressCompany());
		map.put("wayBillNum", data.getShipmentNo());
		map.put("rewardId", data.getRewardId());
		map.put("rewardName", data.getRewardName());
	}
	
	private JSONObject saveExchange(OrderInfo info, AuthInfo authInfo, String accessToken) throws Exception{
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("personId", authInfo.getPersonId());
		paramMap.put("name", authInfo.getName());
		paramMap.put("rewardId", info.getRewardId());
		paramMap.put("rewardName", info.getRewardName());
		paramMap.put("points", info.getNums() * info.getUsedPoints());
		paramMap.put("schoolId", info.getSchoolId());
		paramMap.put("schoolName", info.getSchoolName());
		String params = ConvertUrlUtil.convertByMap(paramMap);
		return HttpRequstUtil.getPostUrlResponse(mongoUrl + UrlConstant.SAVE_EXCHANGE + "?access_token=" + accessToken, params);
	}
}
