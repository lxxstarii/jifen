package com.reps.jifen.rest;

import java.text.DecimalFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.reps.core.restful.RestBaseController;
import com.reps.core.restful.RestResponse;
import com.reps.core.restful.RestResponseStatus;
import com.reps.jifen.entity.PointsAggregate;
import com.reps.jifen.service.IPointsAggregateService;
import com.reps.jifen.util.HttpRequstUtil;
import com.reps.jifen.vo.UrlConstant;

import net.sf.json.JSONObject;

@RestController
@RequestMapping(value = "/uapi/pointsaggregate")
public class PointsAggregateRest extends RestBaseController{
	
	private static Log logger = LogFactory.getLog(PointsAggregateRest.class);
	
	@Value("${http.jifen.url}")
	private String levelUrl;
	
	@Autowired
	private IPointsAggregateService jfPointsAggregateService;

	@RequestMapping(value = "/level", method = { RequestMethod.GET })
	public RestResponse<PointsAggregate> level(String personId, HttpServletRequest request) {
		try {
			List<PointsAggregate> pointList = jfPointsAggregateService.findAll(new Sort(new Order(Direction.ASC, "totalPointsUsable")));
			PointsAggregate pointsAggregate = null;
			if (pointList != null && !pointList.isEmpty()) {
				for (int i = 0; i < pointList.size(); i++) {
					PointsAggregate data = pointList.get(i);
					if (personId.equals(data.getPersonId())) {
						pointsAggregate = new PointsAggregate();
						BeanUtils.copyProperties(data, pointsAggregate);
						if (i == 0) {
							pointsAggregate.setRanking("0%");
						} else if (i == pointList.size() - 1) {
							pointsAggregate.setRanking("99%");
						} else {
							pointsAggregate.setRanking(getPercent(Float.intBitsToFloat(i), Float.intBitsToFloat(pointList.size())));
						}
						JSONObject jsonObject = HttpRequstUtil.getGetUrlResponse(levelUrl 
								+ UrlConstant.GET_LV_POINTS + "?access_token=" + request.getParameter("access_token")
								+ "&points=" + pointsAggregate.getTotalPoints() + "&level=" + pointsAggregate.getLevel());
						if (jsonObject != null) {
							if (jsonObject.getInt("status") == 200) {
								pointsAggregate.setNeedPoints(Integer.parseInt(jsonObject.getString("result")));
							} else if (jsonObject.getInt("status") == 500) {
								return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, jsonObject.getString("message"));
							} 
						} 
					}
				}
			}
			if (pointsAggregate == null) {
				return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, "该用户个人积分记录不存在");
			}
			return wrap(RestResponseStatus.OK, "查询成功", pointsAggregate);
		} catch (Exception e) {
			logger.error("查询异常", e);
			return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, "查询异常：" + e.getMessage());
		}
	}
	
	@RequestMapping(value = "/allowoption")
	public RestResponse<String> isAllowOption(String personId, Integer points) {
		try {
			if (StringUtils.isBlank(personId) || points == null) {
				return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, "请求参数错误");
			}
			PointsAggregate data = jfPointsAggregateService.getByPersonId(personId);
			if (data == null) {
				return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, "个人积分记录不存在");
			}
			if (data.getTotalPointsUsable() - points < 0) {
				return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, "当前积分不足");
			}
			return wrap(RestResponseStatus.OK, "请求成功");
		} catch (Exception e) {
			logger.error("查询异常", e);
			return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, "查询异常：" + e.getMessage());
		}
	}
	
	private String getPercent(float index, float size) {
		DecimalFormat df = new DecimalFormat("##%");
 		return df.format((index / size));
	}
}
