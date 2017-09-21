package com.reps.jifen.action;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.reps.core.RepsConstant;
import com.reps.core.commons.Pagination;
import com.reps.core.exception.RepsException;
import com.reps.core.orm.ListResult;
import com.reps.core.web.AjaxStatus;
import com.reps.core.web.BaseAction;
import com.reps.jifen.entity.OrderInfo;
import com.reps.jifen.service.IOrderInfoService;

@Controller("com.reps.jifen.action.OrderInfoAction")
@RequestMapping(value = RepsConstant.ACTION_BASE_PATH + "/jifen/orderinfo")
public class OrderInfoAction extends BaseAction {
	
	private final Log logger = LogFactory.getLog(OrderInfoAction.class);
	
	@Autowired
	IOrderInfoService goodsService;

	@RequestMapping(value = "/list")
	public ModelAndView list(Pagination pager, OrderInfo query) {

		ModelAndView mav = getModelAndView("/jifen/orderinfo/list");
		ListResult<OrderInfo> result = goodsService.query(
				pager.getStartRow(), pager.getPageSize(), query);
		mav.addObject("list", result.getList());
		mav.addObject("pager", pager);
		mav.addObject("query", query);
		mav.addObject("actionBasePath", RepsConstant.ACTION_BASE_PATH);
		return mav;
	}
	
	@RequestMapping(value = "/toedit")
	public ModelAndView toEdit(String id) throws RepsException {
		ModelAndView mav = getModelAndView("/jifen/orderinfo/edit");
		if (StringUtils.isBlank(id)) {
			throw new RepsException("请求参数错误");
		}
		OrderInfo data = goodsService.get(id, true);
		if (data == null) {
			throw new RepsException("该订单不存在");
		}
		mav.addObject("data", data);
		return mav;
	}
	
	@RequestMapping(value = "/edit")
	@ResponseBody
	public Object update(OrderInfo data){
		try {
			if (StringUtils.isBlank(data.getId())) {
				throw new RepsException("请求参数错误");
			}
			OrderInfo old = goodsService.get(data.getId(), false);
			if (old == null) {
				throw new RepsException("该订单不存在");
			}
			if (StringUtils.isNotBlank(data.getAddress())) {
				old.setAddress(data.getAddress());
			}
			if (data.getStatus() != null) {
				old.setStatus(data.getStatus());
			}
			if (StringUtils.isNotBlank(data.getPhone())) {
				old.setPhone(data.getPhone());
			}
			if (StringUtils.isNotBlank(data.getExpressCompany())) {
				old.setExpressCompany(data.getExpressCompany());
			}
			if (StringUtils.isNotBlank(data.getShipmentNo())) {
				old.setShipmentNo(data.getShipmentNo());
			}
			goodsService.update(old);
			return ajax(AjaxStatus.OK, "修改成功");
		} catch (RepsException e) {
			logger.error("修改订单失败", e);
			return ajax(AjaxStatus.ERROR, "修改订单失败：" + e.getMessage());
		}
	}
}
