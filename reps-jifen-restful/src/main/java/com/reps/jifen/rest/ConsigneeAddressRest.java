package com.reps.jifen.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.reps.core.restful.RestBaseController;
import com.reps.core.restful.RestResponse;
import com.reps.core.restful.RestResponseStatus;
import com.reps.jifen.entity.ConsigneeAddress;
import com.reps.jifen.service.IConsigneeAddressService;

@RestController
@RequestMapping(value = "/uapi/consignee")
public class ConsigneeAddressRest extends RestBaseController {

	private final Log logger = LogFactory.getLog(ConsigneeAddressRest.class);
	
	//@Autowired
	IConsigneeAddressService consigneeService;
	
	@RequestMapping(value = "/list")
	public RestResponse<Map<String, Object>> list(ConsigneeAddress query) {
		try {
			Map<String, Object> map = new HashMap<>();
			List<ConsigneeAddress> list = consigneeService.find(query);
			List<Map<String, Object>> listMap = new ArrayList<>();
			converListMap(list, listMap);
			map.put("data", listMap);
			return wrap(RestResponseStatus.OK, "获取收货地址列表成功", map);
		}catch (Exception e) {
			logger.error("获取收货地址列表异常", e);
			return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, "获取收货地址列表异常:" + e.getMessage());
		}
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public RestResponse<String> save(ConsigneeAddress info) {
		try {
			if (StringUtils.isBlank(info.getConsigneeName()) 
					|| StringUtils.isBlank(info.getAddress())
					|| StringUtils.isBlank(info.getDetailAddress())
					|| StringUtils.isBlank(info.getPhone())
					|| info.getIsDefault() == null) {
				
				return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, "请求参数错误");
			}
			ConsigneeAddress query = new ConsigneeAddress();
			BeanUtils.copyProperties(info, query);
			query.setPersonId(getCurrentLoginInfo().getPersonId());
			query.setCreateTime(new Date());
			//判断是否更改默认地址
			if (info.getIsDefault() == 1) {
				consigneeService.saveNewDefault(query);
			} else {
				consigneeService.save(query);
			}
			return wrap(RestResponseStatus.OK, "保存成功");
		} catch (Exception e) {
			logger.error("保存收货地址异常", e);
			return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, "保存收货地址异常:" + e.getMessage());
		}
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public RestResponse<String> update(ConsigneeAddress info) {
		try {
			if (StringUtils.isBlank(info.getId())) {
				return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, "请求参数错误");
			}
			ConsigneeAddress old = consigneeService.get(info.getId());
			if (old == null) {
				return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, "收货地址不存在");
			}
			if (StringUtils.isBlank(info.getConsigneeName())) {
				old.setConsigneeName(info.getConsigneeName());
			}
			if (StringUtils.isBlank(info.getAddress())) {
				old.setAddress(info.getAddress());
			}
			if (StringUtils.isBlank(info.getDetailAddress())) {
				old.setDetailAddress(info.getDetailAddress());
			}
			if (info.getIsDefault() != null) {
				old.setIsDefault(info.getIsDefault());
			}
			old.setCreateTime(new Date());
			if (old.getIsDefault() == 1) {
				consigneeService.updateDefault(old);
			} else {
				consigneeService.update(old);	
			}
			return wrap(RestResponseStatus.OK, "修改成功");
		} catch (Exception e) {
			logger.error("修改收货地址异常", e);
			return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, "修改收货地址异常:" + e.getMessage());
		}
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public RestResponse<String> delete(ConsigneeAddress info) {
		try {
			if (StringUtils.isBlank(info.getIds())) {
				return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, "请求参数错误");
			}
			String[] ids = info.getIds().split(",");
			for (String id : ids) {
				consigneeService.delete(id);
			}
			return wrap(RestResponseStatus.OK, "删除成功");
		} catch (Exception e) {
			logger.error("删除收货地址异常", e);
			return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, "删除收货地址异常:" + e.getMessage());
		}
	}
	
	private void converListMap(List<ConsigneeAddress> list, List<Map<String, Object>> listMap) {
		if (list != null && !list.isEmpty() && listMap != null) {
			for (ConsigneeAddress data: list) {
				Map<String, Object> map = new HashMap<>();
				map.put("id", data.getId());
				map.put("consigneeName", data.getConsigneeName());
				map.put("address", data.getAddress());
				map.put("detailAddress", data.getDetailAddress());
				map.put("convertAddress", data.getAddress() + data.getDetailAddress());
				map.put("isDefault", data.getIsDefault());
				map.put("postalcode", data.getPostalcode());
				map.put("phone", data.getPhone());
				listMap.add(map);
			}
		}
	}
}
