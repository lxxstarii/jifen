package com.reps.jifen.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.reps.core.restful.RestBaseController;
import com.reps.core.restful.RestResponse;
import com.reps.core.restful.RestResponseStatus;
import com.reps.jifen.entity.StudyAssessPoints;
import com.reps.jifen.entity.TeacherPjkfpjf;
import com.reps.jifen.entity.enums.MarkCategory;
import com.reps.jifen.service.IAssessPointsService;
import com.reps.jifen.service.ITeacherPjkfpjfService;

/**
 * 校园/学习 奖惩rest接口
 * @author Lanxumit
 *
 */
@RestController
@RequestMapping(value = "/uapi/teachjc")
public class TeachJfRest extends RestBaseController {
	
	private final Log logger = LogFactory.getLog(TeachJfRest.class);
	
	@Autowired
	IAssessPointsService assessPointsService;
	
	@Autowired
	ITeacherPjkfpjfService kfpService;

	@RequestMapping(value = "/list")
	public RestResponse<Map<String, Object>> list(StudyAssessPoints query) {
		RestResponse<Map<String, Object>> result = new RestResponse<>();
		try {
			Map<String, Object> map = new HashMap<>();
			if (query.getMark() == null) {
				result.setStatus(RestResponseStatus.INTERNAL_SERVER_ERROR.code());
				result.setMessage("奖励/惩罚类别不能为空");
				return result;
			}
			query.setIsEnable(1);
			List<Map<String, Object>> listMap = new ArrayList<>();
			List<StudyAssessPoints> list = assessPointsService.find(query);
			fillStudyRewardList(list, listMap);
			map.put("data", listMap);
			result.setResult(map);
		} catch (Exception e) {
			logger.error("获取学习行为规则失败" + e);
			result.setStatus(RestResponseStatus.INTERNAL_SERVER_ERROR.code());
			result.setMessage("获取学习行为规则失败"+ e);
		}
		result.setStatus(RestResponseStatus.OK.code());
		result.setMessage("获取列表成功");
		return result;
	}
	
	@RequestMapping(value = "/set", method = RequestMethod.POST)
	public RestResponse<String> set(Integer points, Integer mark, String teacherId) {	
		RestResponse<String> result = new RestResponse<>();
		try {
			if (points == null || mark == null) {
				result.setMessage("请求参数错误");
				result.setStatus(RestResponseStatus.INTERNAL_SERVER_ERROR.code());
				return result;
			}
			TeacherPjkfpjf kfp = kfpService.findByTeacherId(teacherId, getCurrentLoginInfo().getOrgId());
			if (kfp == null) {
				result.setMessage("该教师尚未初始化可分配积分");
				result.setStatus(RestResponseStatus.INTERNAL_SERVER_ERROR.code());
				return result;
			}
			//奖励
			if (mark == MarkCategory.JIANGLI.getType()) {
				if (kfp.getPointsLeft() == 0 || (kfp.getPointsLeft() - points) < 0) {
					result.setMessage("教师可分配积分不足,请联系管理员增加可分配积分");
					result.setStatus(RestResponseStatus.INTERNAL_SERVER_ERROR.code());
					return result;
				}
				kfp.setPointsLeft(kfp.getPointsLeft() - points);
				kfpService.update(kfp);
			} 
		} catch (Exception e) {
			logger.error("操作失败" + e);
			result.setStatus(RestResponseStatus.INTERNAL_SERVER_ERROR.code());
			result.setMessage("操作失败"+ e);
		}
		result.setMessage("操作成功");
		result.setStatus(RestResponseStatus.OK.code());
		return result;
	}
	
	@RequestMapping(value = "/getkfp")
	public RestResponse<Map<String, Object>> getTeacherKfp(TeacherPjkfpjf query) {
		RestResponse<Map<String, Object>> result = new RestResponse<>();
		try {
			Map<String, Object> map = new HashMap<>();
			//TODO 用token中的教师id
			TeacherPjkfpjf kfp = kfpService.findByTeacherId(getCurrentLoginInfo().getPersonId(), null);
			if (kfp != null) {
				map.put("pointsLeft", kfp.getPointsLeft());
				map.put("totalPoints", kfp.getTotalPointsAuthorized());
			}
			result.setResult(map);
		} catch (Exception e) {
			logger.error("获取可分配积分失败" + e);
			result.setStatus(RestResponseStatus.INTERNAL_SERVER_ERROR.code());
			result.setMessage("获取可分配积分失败"+ e);
		}
		result.setStatus(RestResponseStatus.OK.code());
		return result;
	}
	
	private void fillStudyRewardList(List<StudyAssessPoints> list, List<Map<String, Object>> listMap) {
		if (list != null && !list.isEmpty()) {
			for (StudyAssessPoints data : list) {
				Map<String, Object> map = new HashMap<>();
				map.put("id", data.getId());
				map.put("item", data.getItem());
				map.put("category", data.getCategory());
				map.put("mark", data.getMark());
				map.put("pointsScope", data.getPointsScope());
				map.put("icon", this.getFileHttpPath() + data.getIcon());
				listMap.add(map);
			}
		}
	}
}
