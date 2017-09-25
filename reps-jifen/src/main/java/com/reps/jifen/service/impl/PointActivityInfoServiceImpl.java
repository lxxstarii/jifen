package com.reps.jifen.service.impl;

import static com.reps.jifen.entity.enums.ParticipateStatus.CANCEL_PARTICIPATE;
import static com.reps.jifen.entity.enums.ParticipateStatus.PARTICIPATED;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reps.core.exception.RepsException;
import com.reps.core.orm.ListResult;
import com.reps.core.util.StringUtil;
import com.reps.jifen.constant.UrlConstant;
import com.reps.jifen.dao.ActivityInfoDao;
import com.reps.jifen.entity.PointActivityInfo;
import com.reps.jifen.entity.PointReward;
import com.reps.jifen.entity.enums.AuditStatus;
import com.reps.jifen.service.IActivityRewardService;
import com.reps.jifen.service.IPointActivityInfoService;
import com.reps.jifen.util.ConvertUrlUtil;
import com.reps.jifen.util.HttpRequstUtil;

import net.sf.json.JSONObject;

/**
 * 活动统计管理
 * @author qianguobing
 * @date 2017年9月19日 上午9:40:07
 */
@Service
@Transactional
public class PointActivityInfoServiceImpl implements IPointActivityInfoService {
	
	/**兑换类别 1:物品  2:活动*/
	private static final Integer ACTIVITY_TYPE = 2;
	
	@Autowired
	ActivityInfoDao dao;
	
	@Autowired
	IActivityRewardService activityRewardService;
	
	@Override
	public void save(PointActivityInfo activityInfo) throws RepsException{
		if(null == activityInfo) {
			throw new RepsException("参数异常");
		}
		String studentId = activityInfo.getStudentId();
		String rewardId = activityInfo.getRewardId();
		PointActivityInfo info = new PointActivityInfo();
		info.setStudentId(studentId);
		info.setRewardId(rewardId);
		PointActivityInfo pointActivityInfo = get(activityInfo);
		//第一次参与
		if(null == pointActivityInfo) {
			activityInfo.setCreateTime(new Date());
			activityInfo.setIsParticipate(PARTICIPATED.getId());
			//参与活动记录保存
			dao.save(activityInfo);
		//第二次参与,更新状态
		}else {
			Short isParticipate = pointActivityInfo.getIsParticipate();
			if(null == isParticipate) {
				throw new RepsException("该参与活动记录数据异常");
			}
			if(PARTICIPATED.getId().shortValue() == isParticipate.shortValue()) {
				throw new RepsException("参与活动异常:该参与活动记录已经被参与了");
			}
			pointActivityInfo.setIsParticipate(PARTICIPATED.getId());
			update(pointActivityInfo);
		}
		//更新参与人数
		PointReward pointReward = activityRewardService.get(rewardId);
		Integer participatedCount = pointReward.getParticipatedCount();
		pointReward.setParticipatedCount((null == participatedCount ? 0 : participatedCount) + 1);
		activityRewardService.update(pointReward);
	}

	@Override
	public ListResult<PointActivityInfo> query(int start, int pagesize, PointActivityInfo activityInfo) throws RepsException{
		if(null == activityInfo) {
			throw new RepsException("参数异常");
		}
		return dao.query(start, pagesize, activityInfo);
	}

	@Override
	public Long count(String rewardId, Short isParticipate) throws RepsException {
		if(StringUtil.isBlank(rewardId)) {
			throw new RepsException("统计异常:活动ID不能为空");
		}
		if(null == isParticipate) {
			throw new RepsException("统计异常:参与状态不能为空");
		}
		return dao.count(rewardId, isParticipate);
	}

	@Override
	public void update(PointActivityInfo activityInfo) throws RepsException {
		if(null == activityInfo) {
			throw new RepsException("参数异常");
		}
		PointActivityInfo pointActivityInfo = this.get(activityInfo);
		if(null == pointActivityInfo) {
			throw new RepsException("该活动记录不存在");
		}
		Short isParticipate = activityInfo.getIsParticipate();
		if(null != isParticipate) {
			pointActivityInfo.setIsParticipate(isParticipate);
		}
		Short auditStatus = activityInfo.getAuditStatus();
		if(null != auditStatus) {
			pointActivityInfo.setAuditStatus(auditStatus);
		}
		Date createTime = activityInfo.getCreateTime();
		if(null != createTime) {
			pointActivityInfo.setCreateTime(createTime);
		}
		dao.update(pointActivityInfo);
	}
	
	@Override
	public void cancelActivity(PointActivityInfo activityInfo) throws RepsException{
		PointActivityInfo pointActivityInfo = this.get(activityInfo);
		if(null == pointActivityInfo) {
			throw new RepsException("取消活动异常:该活动记录不存在");
		}
		Short isParticipate = pointActivityInfo.getIsParticipate();
		Short auditStatus = pointActivityInfo.getAuditStatus();
		if(null != auditStatus) {
			if(AuditStatus.PASSED.getId().shortValue() == auditStatus.shortValue()) {
				throw new RepsException("取消活动异常:该参与活动已经审核通过，不能取消");
			}
		}
		if(null == isParticipate) {
			throw new RepsException("该参与活动记录数据异常");
		}
		if(CANCEL_PARTICIPATE.getId().shortValue() == isParticipate.shortValue()) {
			throw new RepsException("取消活动异常:该参与活动记录已经被取消了");
		}
		//取消活动
		activityInfo.setIsParticipate(CANCEL_PARTICIPATE.getId());
		this.update(activityInfo);
		//更新活动表，已参与人数
		PointReward jfReward = activityRewardService.get(activityInfo.getRewardId());
		Integer participatedCount = jfReward.getParticipatedCount();
		jfReward.setParticipatedCount(null == participatedCount ? 0 : participatedCount - 1);
		activityRewardService.update(jfReward);
	}

	@Override
	public PointActivityInfo get(String id) throws RepsException {
		if(StringUtil.isBlank(id)) {
			throw new RepsException("查询异常:活动参与ID不能为空");
		}
		PointActivityInfo activityInfo = dao.get(id);
		if(null == activityInfo) {
			throw new RepsException("查询异常:该活动参与记录不存在");
		}
		return activityInfo;
	}
	
	@Override
	public PointActivityInfo get(PointActivityInfo activityInfo) throws RepsException{
		if(null == activityInfo) {
			throw new RepsException("参数异常");
		}
		List<PointActivityInfo> infoList = dao.find(activityInfo);
		if(null != infoList && infoList.size() > 0) {
			return infoList.get(0);
		}else {
			return null;
		}
	}

	@Override
	public void audit(PointActivityInfo activityInfo, String serverPath) throws Exception {
		if(null == activityInfo) {
			throw new RepsException("参数异常");
		}
		Short auditStatus = activityInfo.getAuditStatus();
		if(null == auditStatus) {
			throw new RepsException("审核异常:审核状态不能为空");
		}
		String id = activityInfo.getId();
		if(StringUtil.isBlank(id)) {
			throw new RepsException("审核异常:活动参与记录ID不能为空");
		}
		PointActivityInfo pointActivityInfo = this.get(id);
		String studentId = pointActivityInfo.getStudentId();
		String rewardId = pointActivityInfo.getRewardId();
		if(StringUtil.isBlank(id) || StringUtil.isBlank(rewardId)) {
			throw new RepsException("活动参与记录数据异常");
		}
		PointReward pointReward = activityRewardService.get(rewardId);
		Integer points = pointReward.getPoints();
		if(null == points) {
			throw new RepsException("该活动数据异常");
		}
		if(AuditStatus.REJECTED.getId().shortValue() == auditStatus.shortValue()) {
			//请求mongodb 修改个人积分，保存积分日志
			doPosts(studentId, rewardId, points, serverPath);
			//修改活动记录表和活动信息表状态
			cancelActivity(activityInfo);
		}
		pointActivityInfo.setAuditStatus(auditStatus);
		pointActivityInfo.setAuditOpinion(activityInfo.getAuditOpinion());
		this.update(pointActivityInfo);
	}
	
	private void doPosts(String studentId, String rewardId, Integer points, String serverPath) throws Exception{
		//构造请求积分收集参数MAP
		Map<String, Object> paramsMap = new HashMap<>();
		paramsMap.put("personId", studentId);
		paramsMap.put("rewardId", rewardId);
		paramsMap.put("points", points);
		//设置活动类型
		paramsMap.put("type", ACTIVITY_TYPE);
		String params = ConvertUrlUtil.convertByMap(paramsMap);
		JSONObject jsonObject = HttpRequstUtil.getPostUrlResponse(serverPath + UrlConstant.O_CANCEL_EXCHANGE, params);
		if (null != jsonObject) {
			if (200 != jsonObject.getInt("status")) {
				throw new RepsException(jsonObject.getString("message"));
			} 
		}else {
			throw new RepsException("网络异常");
		}
	}

}
