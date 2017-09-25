package com.reps.jifen.rest;

import static com.reps.jifen.entity.enums.ParticipateStatus.CANCEL_PARTICIPATE;
import static com.reps.jifen.entity.enums.ParticipateStatus.PARTICIPATED;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.reps.core.exception.RepsException;
import com.reps.core.restful.AuthInfo;
import com.reps.core.restful.RestBaseController;
import com.reps.core.restful.RestResponse;
import com.reps.core.restful.RestResponseStatus;
import com.reps.core.util.StringUtil;
import com.reps.jifen.constant.UrlConstant;
import com.reps.jifen.entity.PointActivityInfo;
import com.reps.jifen.entity.PointReward;
import com.reps.jifen.service.IActivityRewardService;
import com.reps.jifen.service.IPointActivityInfoService;
import com.reps.jifen.util.ConvertUrlUtil;
import com.reps.jifen.util.HttpRequstUtil;
import com.reps.school.entity.Classes;
import com.reps.school.entity.School;
import com.reps.school.entity.StudentSchool;
import com.reps.school.service.ISchoolService;
import com.reps.school.service.IStudentService;

import net.sf.json.JSONObject;

@RestController
@RequestMapping(value = "/uapi/activity")
public class ActivityRewardRest extends RestBaseController {

	private final Log logger = LogFactory.getLog(ActivityRewardRest.class);
	
	private static final Integer ACTIVITY_TYPE = 2;
	
	private static final String PARTICIPATE_STATUS = "participateStatus";

	@Autowired
	private IPointActivityInfoService activityInfoService;
	
	@Autowired
	private IActivityRewardService activityRewardService;
	
	@Autowired
	private ISchoolService schoolService;
	
	@Autowired
	private IStudentService studentService;
	
	@Value("${http.jifenmongo.url}")
	private String mongoUrl;

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public RestResponse<PointReward> save(PointActivityInfo activityInfo, HttpServletRequest request) {
		try {
			AuthInfo currentLoginInfo = this.getCurrentLoginInfo();
			if(null == currentLoginInfo) {
				return wrap(RestResponseStatus.BAD_REQUEST, "登陆超时");
			}
			String schoolId = activityInfo.getSchoolId();
			if(StringUtil.isBlank(schoolId)) {
				throw new RepsException("参加活动异常:学校ID不能为空");
			}
			String rewardId = activityInfo.getRewardId();
			if(StringUtil.isBlank(rewardId)) {
				throw new RepsException("参加活动异常:活动ID不能为空");
			}
			String personId = activityInfo.getStudentId();
			if(StringUtil.isBlank(personId)) {
				throw new RepsException("参加活动异常:学生ID不能为空");
			}
			//设置学生ID
			activityInfo.setStudentId(personId);
			//查询活动信息
			PointReward activity = activityRewardService.get(rewardId);
			//构造请求积分收集参数MAP
			Map<String, Object> params = new HashMap<>();
			params.put("personId", personId);
			params.put("rewardId", rewardId);
			params.put("rewardName", activity.getName());
			params.put("points", activity.getPoints());
			params.put("name", currentLoginInfo.getName());
			params.put("schoolId", schoolId);
			School school = schoolService.get(schoolId);
			if(null == school) {
				throw new RepsException("活动参与异常:该学生所在学校不存在");
			}
			//查询学校名字
			String schoolName = school.getOrganize().getName();
			params.put("schoolName", schoolName);
			//设置活动类型
			params.put("type", ACTIVITY_TYPE);
			String convertByMap = ConvertUrlUtil.convertByMap(params);
			doPost(request, convertByMap, UrlConstant.SAVE_EXCHANGE);
			//学生参加活动保存记录
			StudentSchool studentSchool = studentService.getByStudentId(personId, schoolId, true);
			if(null == studentSchool) {
				throw new RepsException("参加活动异常:该学生不在此学校");
			}
			List<Classes> clazzes = studentSchool.getClazzes();
			if(null == clazzes || clazzes.size() <= 0 ) {
				throw new RepsException("参加活动异常:该学生班级不存在");
			}
			//默认取第一个班级
			Classes classes = clazzes.get(0);
			activityInfo.setClassesId(classes.getId());
			//年级
			activityInfo.setGrade(classes.getGrade());
			activityInfoService.save(activityInfo);
			return wrap(RestResponseStatus.OK, "活动参与成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("活动参与异常", e);
			return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	private void doPost(HttpServletRequest request, String convertByMap, String uri) throws Exception {
		//查询个人积分，保存积分记录
		JSONObject jsonObject = HttpRequstUtil.getPostUrlResponse(mongoUrl + uri + "?access_token=" + request.getParameter("access_token"), convertByMap);
		if (null != jsonObject) {
			if (200 != jsonObject.getInt("status")) {
				throw new RepsException(jsonObject.getString("message"));
			} 
		}else {
			throw new RepsException("网络异常");
		}
	}
	
	@RequestMapping(value = "/cancel", method = RequestMethod.POST)
	public RestResponse<PointReward> cancel(PointActivityInfo activityInfo, HttpServletRequest request) {
		try {
			AuthInfo currentLoginInfo = this.getCurrentLoginInfo();
			if(null == currentLoginInfo) {
				return wrap(RestResponseStatus.BAD_REQUEST, "登陆超时");
			}
			String rewardId = activityInfo.getRewardId();
			if(StringUtil.isBlank(rewardId)) {
				throw new RepsException("参加活动异常:活动ID不能为空");
			}
			String personId = activityInfo.getStudentId();
			if(StringUtil.isBlank(personId)) {
				throw new RepsException("参加活动异常:学生ID不能为空");
			}
			//设置学生ID
			activityInfo.setStudentId(personId);
			//查询活动信息
			PointReward activity = activityRewardService.get(rewardId);
			if(null == activity) {
				throw new RepsException("取消活动异常:该活动不存在");
			}
			//构造请求积分收集参数MAP
			Map<String, Object> params = new HashMap<>();
			params.put("personId", personId);
			params.put("rewardId", rewardId);
			params.put("points", activity.getPoints());
			//设置活动类型
			params.put("type", ACTIVITY_TYPE);
			String convertByMap = ConvertUrlUtil.convertByMap(params);
			doPost(request, convertByMap, UrlConstant.CANCEL_EXCHANGE);
			activityInfoService.cancelActivity(activityInfo);
			return wrap(RestResponseStatus.OK, "活动取消成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("活动参与异常", e);
			return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
	
	/**
	 * 活动ID
	 * @param id
	 * @return RestResponse<Map<String, Short>>
	 */
	@RequestMapping(value = "/checkstatus", method = RequestMethod.GET)
	public RestResponse<Map<String, Short>> checkStatus(String rewardId, String studentId){
		try {
			AuthInfo currentLoginInfo = this.getCurrentLoginInfo();
			if(null == currentLoginInfo) {
				return wrap(RestResponseStatus.BAD_REQUEST, "登陆超时");
			}
			if(StringUtil.isBlank(studentId)) {
				throw new RepsException("检查活动状态异常:学生ID不能为空");
			}
			if(StringUtil.isBlank(rewardId)) {
				throw new RepsException("检查状态异常:活动ID不能为空");
			}
			//查询此学生是否参与了该活动
			PointActivityInfo activityInfo = new PointActivityInfo();
			activityInfo.setRewardId(rewardId);
			activityInfo.setStudentId(studentId);
			PointActivityInfo pointActivityInfo = activityInfoService.get(activityInfo);
			Map<String, Short> statusMap = new HashMap<>();
			statusMap.put(PARTICIPATE_STATUS, CANCEL_PARTICIPATE.getId());
			if(null != pointActivityInfo) {
				Short isParticipate = pointActivityInfo.getIsParticipate();
				if(null == isParticipate) {
					throw new RepsException("参与活动记录数据异常");
				}
				if(PARTICIPATED.getId().shortValue() == isParticipate.shortValue()) {
					statusMap.put(PARTICIPATE_STATUS, PARTICIPATED.getId());
				}else if(CANCEL_PARTICIPATE.getId().shortValue() == isParticipate.shortValue()) {
					statusMap.put(PARTICIPATE_STATUS, CANCEL_PARTICIPATE.getId());
				}
			}
			return wrap(RestResponseStatus.OK, "检查活动状态正常", statusMap);
		}catch(Exception e) {
			e.printStackTrace();
			logger.error("检查活动状态异常", e);
			return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
	
}
