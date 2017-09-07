package com.reps.jifen.rest;

import java.util.Date;
import java.util.Map;

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
import com.reps.jifen.entity.JfTeacherPointsAssign;
import com.reps.jifen.service.IJfTeacherPointsAssignService;

@RestController
@RequestMapping(value = "/uapi/teacherpointsassign")
public class JfTeacherPointsAssignRest extends RestBaseController{
	
	private static Log logger = LogFactory.getLog(JfTeacherPointsAssignRest.class);
	
	@Autowired
	IJfTeacherPointsAssignService tAssignService;

	@RequestMapping(value = "/list", method = { RequestMethod.GET })
	public RestResponse<ListResult<JfTeacherPointsAssign>> list(String teacherId, Integer pageIndex, Integer pageSize) {
		try {
			ListResult<JfTeacherPointsAssign> result = tAssignService.findByTeacherId(teacherId, pageIndex, pageSize);
			return wrap(RestResponseStatus.OK, "查询成功", result);
		} catch (Exception e) {
			logger.error("查询异常", e);
			return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, "查询异常：" + e.getMessage());
		}
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public RestResponse<Map<String, Object>> save(JfTeacherPointsAssign info) {
		RestResponse<Map<String, Object>> result = new RestResponse<>();
		try {
			if (StringUtils.isBlank(info.getTeacherId()) || StringUtils.isBlank(info.getTeacherName())
				|| StringUtils.isBlank(info.getStudentId()) || StringUtils.isBlank(info.getSchoolName())
				|| StringUtils.isBlank(info.getSchoolId()) || StringUtils.isBlank(info.getSchoolName())
				|| StringUtils.isBlank(info.getRuleName())) {
					
					result.setMessage("请求参数错误");
					result.setStatus(RestResponseStatus.INTERNAL_SERVER_ERROR.code());
					return result;
				}
			info.setCreateTime(new Date());
			tAssignService.save(info);
			//TODO 保存学生积分获取记录信息、个人积分汇总表信息
		} catch (Exception e) {
			logger.error("添加教师积分分配记录异常", e);
			return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, "添加教师积分分配记录异常：" + e.getMessage());
		}
		return result;
	}

}
