package com.reps.jifen.rest;

import java.text.SimpleDateFormat;
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

import com.reps.core.commons.Pagination;
import com.reps.core.orm.ListResult;
import com.reps.core.restful.AuthInfo;
import com.reps.core.restful.RestBaseController;
import com.reps.core.restful.RestResponse;
import com.reps.core.restful.RestResponseStatus;
import com.reps.core.util.DateUtil;
import com.reps.jifen.constant.UrlConstant;
import com.reps.jifen.entity.OrderInfo;
import com.reps.jifen.entity.PointReward;
import com.reps.jifen.service.IOrderInfoService;
import com.reps.jifen.service.IRewardService;
import com.reps.jifen.util.ConvertUrlUtil;
import com.reps.jifen.util.HttpRequstUtil;

@RestController
@RequestMapping(value = "/uapi/orderinfo")
public class OrderInfoRest extends RestBaseController {

	private final Log logger = LogFactory.getLog(OrderInfoRest.class);
	
	private final Integer ORDER_TYPE = 1;
	
	@Autowired
	IOrderInfoService orderInfoService;
	
	@Autowired
	IRewardService rewardService;
	
	@Value("${http.jifenmongo.url}")
	private String mongoUrl;
	
	@RequestMapping(value = "/list")
	public RestResponse<Map<String, Object>> query(Pagination pager, OrderInfo info) {
		try {
			Map<String, Object> map = new HashMap<>();
			
			AuthInfo token = getCurrentLoginInfo();
			if (StringUtils.isBlank(info.getPersonId())) {
				info.setPersonId(token.getPersonId());
			}
			ListResult<OrderInfo> result = orderInfoService.query(pager.getStartRow(), pager.getPageSize(), info);
			List<Map<String, Object>> listMap = new ArrayList<>();
			converListMap(result.getList(), listMap);
			pager.setTotalRecord(result.getCount());
			map.put("data", listMap);
			map.put("pager", pager);
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
					|| StringUtils.isBlank(info.getSchoolName()) || info.getNums() == null 
					|| info.getUsedPoints() == null || StringUtils.isBlank(info.getSchoolId())) {
				
				return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, "请求参数错误");
			}
			//判断库存是否足够
			PointReward pointReward = rewardService.get(info.getRewardId());
			if (pointReward == null) {
				return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, "兑换物品不存在");
			}
			if (info.getNums() > pointReward.getNumbers()) {
				return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, "物品库存不足");
			}
			OrderInfo data = new OrderInfo();
			BeanUtils.copyProperties(info, data);
			data.setStatus(OrderInfo.UN_HANDLE);
			data.setPersonId(getCurrentLoginInfo().getPersonId());
			data.setRewardName(pointReward.getName());
			data.setCreateTime(new Date());
			data.setOrderNo(DateUtil.format(new Date(), "yyyyMMddHHmm").substring(2));
			JSONObject isAllowObject = isAllowOption(data.getPersonId(), info.getNums() * info.getUsedPoints(), request.getParameter("access_token"));
			if (isAllowObject != null) {
				if (isAllowObject.getInt("status") == 403) {
					return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, isAllowObject.getString("message"));
				} else if (isAllowObject.getInt("status") == 500) {
					return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, isAllowObject.getString("message"));
				} 
			} 
			orderInfoService.save(data);
			//提交订单(1：先获取个人积分,判断是否满足提交订单条件 2：添加个人兑换记录)
			JSONObject jsonObject = saveExchange(data, getCurrentLoginInfo(), request.getParameter("access_token"));
			if (jsonObject != null) {
				if (jsonObject.getInt("status") == 403) {
					return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, jsonObject.getString("message"));
				} else if (jsonObject.getInt("status") == 500) {
					return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, jsonObject.getString("message"));
				} 
			} 
			//修改物品库存及兑换数量
			pointReward.setExchangedCount(pointReward.getExchangedCount() == null ? info.getNums() : pointReward.getExchangedCount() + info.getNums());
			pointReward.setNumbers(pointReward.getNumbers() - info.getNums());
			rewardService.update(pointReward);
			return wrap(RestResponseStatus.OK, "提交订单成功");
		} catch (Exception e) {
			logger.error("提交订单异常", e);
			return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, "提交订单异常：" + e.getMessage());
		}
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public RestResponse<String> update(OrderInfo info, HttpServletRequest request) {
		try {
			if (info.getStatus() == null || StringUtils.isBlank(info.getId())) {
				return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, "请求参数错误");
			}
			OrderInfo old = orderInfoService.get(info.getId(), false);
			if (old != null) {
				if (info.getStatus() == OrderInfo.CANCLE) {
					if (old.getStatus() != OrderInfo.UN_HANDLE) {
						return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, "只能取消未完成的订单");
					}
					//取消订单,返还学生积分
					JSONObject jsonObject = cancelExchange(old, request.getParameter("access_token"));
					if (jsonObject != null) {
						if (jsonObject.getInt("status") == 403) {
							return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, jsonObject.getString("message"));
						} else if (jsonObject.getInt("status") == 500) {
							return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, jsonObject.getString("message"));
						} 
					}
				} else if (info.getStatus() == OrderInfo.FINISH) {
					if (old.getStatus() != OrderInfo.HANDLE) {
						return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, "只能确认已完成的订单");
					}
				}
				old.setStatus(info.getStatus());
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
			OrderInfo old = orderInfoService.get(id, false);
			if (old != null) {
				if (old.getStatus() != OrderInfo.CANCLE) {
					return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, "只能删除已取消的订单");
				}
				orderInfoService.delete(id);
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
			OrderInfo data = orderInfoService.get(id, true);
			if (data == null) {
				return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, "订单不存在");
			}
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
		map.put("status", data.getStatus());
		map.put("createTime", new SimpleDateFormat("yyyy-MM-dd HH:mm").format(data.getCreateTime()));
		if (data.getReward() != null) {
			map.put("rewardName", data.getReward().getName());
			if (StringUtils.isNotBlank(data.getReward().getPicture())) 
				map.put("pictures", this.getFileFullUrl(data.getReward().getPicture(), null));
		}
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
		paramMap.put("orderId", info.getId());
		paramMap.put("type", ORDER_TYPE);
		String params = ConvertUrlUtil.convertByMap(paramMap);
		return HttpRequstUtil.getPostUrlResponse(mongoUrl + UrlConstant.SAVE_EXCHANGE + "?access_token=" + accessToken, params);
	}
	
	private JSONObject cancelExchange(OrderInfo info, String accessToken) throws Exception{
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("personId", info.getPersonId());
		paramMap.put("rewardId", info.getRewardId());
		paramMap.put("orderId", info.getId());
		paramMap.put("type", ORDER_TYPE);
		String params = ConvertUrlUtil.convertByMap(paramMap);
		System.out.println(mongoUrl + UrlConstant.CANCEL_EXCHANGE + "?access_token=" + accessToken);
		return HttpRequstUtil.getPostUrlResponse(mongoUrl + UrlConstant.CANCEL_EXCHANGE + "?access_token=" + accessToken, params);
	}
	
	private JSONObject isAllowOption(String personId, Integer points, String accessToken) throws Exception{
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("personId", personId);
		paramMap.put("points", points);
		paramMap.put("access_token", accessToken);
		String params = ConvertUrlUtil.convertByMap(paramMap);
		return HttpRequstUtil.getGetUrlResponse(mongoUrl + UrlConstant.IS_ALLOW_OPTION + "?" + params);
	}
}
