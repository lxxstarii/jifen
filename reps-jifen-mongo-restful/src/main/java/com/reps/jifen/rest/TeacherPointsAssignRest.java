package com.reps.jifen.rest;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import com.reps.jifen.entity.TeacherPointsAssign;
import com.reps.jifen.entity.enums.Sources;
import com.reps.jifen.service.IPointsAggregateService;
import com.reps.jifen.service.IPointsCollectService;
import com.reps.jifen.service.ITeacherPointsAssignService;
import com.reps.jifen.util.HttpRequstUtil;

@RestController
@RequestMapping(value = "/uapi/teacherpointsassign")
public class TeacherPointsAssignRest extends RestBaseController{
	
	private static Log logger = LogFactory.getLog(TeacherPointsAssignRest.class);
	
	@Autowired
	ITeacherPointsAssignService tAssignService;
	
	@Autowired
	IPointsAggregateService aggreateService;
	
	@Autowired
	IPointsCollectService collectService;
	
	@Value("${http.jflevel.url}")
	private String levelUrl;

	@RequestMapping(value = "/list", method = { RequestMethod.GET })
	public RestResponse<ListResult<TeacherPointsAssign>> list(String teacherId, Integer pageIndex, Integer pageSize) {
		try {
			ListResult<TeacherPointsAssign> result = tAssignService.findByTeacherId(teacherId, pageIndex, pageSize);
			return wrap(RestResponseStatus.OK, "查询成功", result);
		} catch (Exception e) {
			logger.error("查询异常", e);
			return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, "查询异常：" + e.getMessage());
		}
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public RestResponse<String> save(TeacherPointsAssign info, HttpServletRequest request) {
		RestResponse<String> result = new RestResponse<>();
		try {
			if (StringUtils.isBlank(info.getTeacherId())
					|| StringUtils.isBlank(info.getTeacherName())
					|| StringUtils.isBlank(info.getStudentId())
					|| StringUtils.isBlank(info.getStudentName())
					|| StringUtils.isBlank(info.getSchoolName())
					|| StringUtils.isBlank(info.getSchoolId())
					|| StringUtils.isBlank(info.getRuleName())
					|| StringUtils.isBlank(info.getRuleId())
					|| info.getMark() == null || info.getPoints() == null) {
	
					result.setMessage("请求参数错误");
					result.setStatus(RestResponseStatus.INTERNAL_SERVER_ERROR.code());
					return result;
			}
			info.setCreateTime(new Date());
			tAssignService.save(info);
			// 保存学生积分获取记录信息、个人积分汇总表信息
			PointsAggregate aggregate = aggreateService.getByPersonId(info.getStudentId());
			if (aggregate == null) {
				aggregate = new PointsAggregate();
				aggregate.setPersonId(info.getStudentId());
				aggregate.setTotalPoints(0);
				aggregate.setTotalPointsUsable(0);
				aggregate.setLevel((short) 0);
				aggreateService.save(aggregate);
			}
			//添加学生个人记录
			PointsCollect jfCollect = new PointsCollect();
			jfCollect.setPersonId(info.getStudentId());
			if (info.getMark() == 1) {
				jfCollect.setGetFrom(Sources.TEACHER_REWARD.getValue());
				jfCollect.setPoints(info.getPoints());
				jfCollect.setRuleCode(Sources.TEACHER_REWARD.getName());
				//修改个人积分表
				aggregate.setTotalPoints(aggregate.getTotalPoints() + jfCollect.getPoints());
			} else {
				jfCollect.setGetFrom(Sources.TEACHER_DEDUCT.getValue());
				jfCollect.setPoints(-info.getPoints());
				jfCollect.setRuleCode(Sources.TEACHER_DEDUCT.getName());
			}
			aggregate.setTotalPointsUsable(aggregate.getTotalPointsUsable() + jfCollect.getPoints());
			//获取个人积分级别
			JSONObject jsonObject = HttpRequstUtil.getGetUrlResponse(levelUrl 
					+ "/uapi/pointlevel/getlevel?access_token=" + request.getParameter("access_token") + "&points=" + aggregate.getTotalPointsUsable());
			if (jsonObject != null) {
				if (jsonObject.getInt("status") == 200) {
					aggregate.setLevel((short) jsonObject.getInt("result"));
				} else if (jsonObject.getInt("status") == 403) {
					logger.error(jsonObject.getString("message"));
				} else if (jsonObject.getInt("status") == 500) {
					logger.error(jsonObject.getString("message"));
				} 
			} 
			aggreateService.update(aggregate);
			jfCollect.setRecordId(info.getRuleId());
			jfCollect.setRuleName(info.getRuleName());
			jfCollect.setGetTime(new Date());
			jfCollect.setPersonName(info.getStudentName());
			jfCollect.setTotalPoints(aggregate.getTotalPoints());
			jfCollect.setTotalPointsUsable(aggregate.getTotalPointsUsable());
			collectService.save(jfCollect);
		} catch (Exception e) {
			logger.error("添加教师积分分配记录异常", e);
			return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, "添加教师积分分配记录异常：" + e.getMessage());
		}
		return wrap(RestResponseStatus.OK, "保存记录成功");
	}
}
