package com.reps.jifen.rest;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
import com.reps.jifen.entity.JfParentPointsAssign;
import com.reps.jifen.entity.JfPointsAggregate;
import com.reps.jifen.entity.JfPointsCollect;
import com.reps.jifen.entity.enums.JfSources;
import com.reps.jifen.service.IJfParentPointsAssignService;
import com.reps.jifen.service.IJfPointsAggregateService;
import com.reps.jifen.service.IJfPointsCollectService;

@RestController
@RequestMapping(value = "/uapi/parentpointsassign")
public class JfParentPointsAssignRest extends RestBaseController {

	private static Log logger = LogFactory.getLog(JfParentPointsAssignRest.class);
	
	private static final String LAST_WEEKEND = "0";//上周
	
	private static final String THIS_WEEKEND = "1";//本周

	@Autowired
	IJfParentPointsAssignService jfParentPointsAssignService;

	@Autowired
	IJfPointsAggregateService aggreateService;

	@Autowired
	IJfPointsCollectService collectService;

	@RequestMapping(value = "/list", method = { RequestMethod.GET })
	public RestResponse<ListResult<JfParentPointsAssign>> list(
			JfParentPointsAssign jfParentPointsAssign, Integer pageIndex, Integer pageSize) {
		try {
			ListResult<JfParentPointsAssign> result = jfParentPointsAssignService
					.query(jfParentPointsAssign, pageIndex, pageSize);
			return wrap(RestResponseStatus.OK, "查询成功", result);
		} catch (Exception e) {
			logger.error("查询异常", e);
			return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, "查询异常：" + e.getMessage());
		}
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public RestResponse<String> save(JfParentPointsAssign info) {
		RestResponse<String> result = new RestResponse<>();
		try {
			if (StringUtils.isBlank(info.getParentId())
					|| StringUtils.isBlank(info.getParentName())
					|| StringUtils.isBlank(info.getStudentId())
					|| StringUtils.isBlank(info.getStudentName())
					|| StringUtils.isBlank(info.getSchoolName())
					|| StringUtils.isBlank(info.getSchoolId())
					|| StringUtils.isBlank(info.getSchoolName())
					|| StringUtils.isBlank(info.getRuleName())
					|| StringUtils.isBlank(info.getRuleId())
					|| StringUtils.isBlank(info.getType())
					|| info.getPoints() == null) {

				result.setMessage("请求参数错误");
				result.setStatus(RestResponseStatus.INTERNAL_SERVER_ERROR.code());
				return result;
			}
			//判断本周或上周是否给孩子奖励
			if (isAssess(info)) {
				return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, "已奖励过孩子");
			}
			info.setCreateTime(new Date());
			jfParentPointsAssignService.save(info);
			// 保存学生积分获取记录信息、个人积分汇总表信息
			JfPointsAggregate aggregate = aggreateService.getByPersonId(info.getStudentId());
			if (aggregate == null) {
				aggregate = new JfPointsAggregate();
				aggregate.setPersonId(info.getStudentId());
				aggregate.setTotalPoints(0);
				aggregate.setTotalPointsUsable(0);
				aggregate.setLevel((short) 0);
				aggreateService.save(aggregate);
			}
			// 修改个人积分表
			aggregate.setTotalPoints(aggregate.getTotalPoints()
					+ info.getPoints());
			aggregate.setTotalPointsUsable(aggregate.getTotalPointsUsable()
					+ info.getPoints());
			//TODO 修改个人积分累计级别
			aggreateService.update(aggregate);
			// 添加学生个人记录
			JfPointsCollect jfCollect = new JfPointsCollect();
			jfCollect.setPersonId(info.getStudentId());
			jfCollect.setGetFrom(JfSources.PARENT_REWARD.getValue());
			jfCollect.setPoints(info.getPoints());
			jfCollect.setRuleCode(JfSources.PARENT_REWARD.getName());
			jfCollect.setRecordId(info.getRuleId());
			jfCollect.setRuleName(info.getRuleName());
			jfCollect.setGetTime(new Date());
			jfCollect.setTotalPoints(aggregate.getTotalPoints());
			jfCollect.setTotalPointsUsable(aggregate.getTotalPointsUsable());
			collectService.save(jfCollect);
		} catch (Exception e) {
			logger.error("添加教师积分分配记录异常", e);
			return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR,
					"添加教师积分分配记录异常：" + e.getMessage());
		}
		return wrap(RestResponseStatus.OK, "保存记录成功");
	}

	/**
	 *  
	 * @param type 上周、本周
	 * @param parentId
	 * @param studentId
	 * @return 
	 */
	private boolean isAssess(JfParentPointsAssign info) {
		boolean flag = true;
		Date monday = null;
		Date weekend = null;
		if (LAST_WEEKEND.equals(info.getType())) {
			monday = getMonday(true);
			//记录奖励孩子行为的时间（上周取周四,本周取当前时间）
			info.setBehaviorTime(getThursday(monday));
		} else if (THIS_WEEKEND.equals(info.getType())) {
			monday = getMonday(false);
			info.setBehaviorTime(new Date());
		}
		if (monday != null) {
			weekend = getWeekend(monday);
			if (weekend != null) {
				List<JfParentPointsAssign> list = jfParentPointsAssignService
						.findByTime(monday, weekend, info.getParentId(), info.getStudentId());
				if (list == null || list.isEmpty()) {
					flag = false;
				}
			}
		}
		return flag;
	}
	
	private Date getMonday(boolean isLast) {
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		if (isLast) {
			cal.add(Calendar.DATE, -7);
			cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		} else {
			cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		}
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
	
	private Date getWeekend(Date monday) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(monday);
		cal.set(Calendar.DATE, cal.get(Calendar.DATE) + 6);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MINUTE, 59);
		return cal.getTime();
	}
	
	private Date getThursday(Date monday) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(monday);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
		return cal.getTime();
	}
}
