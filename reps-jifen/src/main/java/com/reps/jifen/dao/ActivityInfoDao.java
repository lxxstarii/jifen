package com.reps.jifen.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.reps.core.orm.IGenericDao;
import com.reps.core.orm.ListResult;
import com.reps.core.util.StringUtil;
import com.reps.jifen.entity.PointActivityInfo;
import com.reps.school.entity.School;
import com.reps.school.entity.Student;
import com.reps.system.entity.Organize;
import com.reps.system.entity.Person;

/**
 * 活动统计
 * @author qianguobing
 * @date 2017年9月18日 下午5:11:38
 */
@Repository
public class ActivityInfoDao {
	
	@Autowired
	IGenericDao<PointActivityInfo> dao;
	
	public void save(PointActivityInfo activityInfo) {
		dao.save(activityInfo);
	}
	
	public void update(PointActivityInfo activityInfo) {
		dao.update(activityInfo);
	}
	
	public PointActivityInfo get(String id) {
		return dao.get(PointActivityInfo.class, id);
	}

	public ListResult<PointActivityInfo> query(int start, int pagesize, PointActivityInfo activityInfo) {
		DetachedCriteria dc = DetachedCriteria.forClass(PointActivityInfo.class);
		dc.createAlias("student", "student");
		dc.createAlias("school", "school");
		dc.createAlias("student.person", "person");
		dc.createAlias("school.organize", "organize");
		if (null != activityInfo) {
			//查询学生
			Student student = activityInfo.getStudent();
			if(null != student) {
				Person person = student.getPerson();
				if(null != person) {
					String name = person.getName();
					if (StringUtils.isNotBlank(name)) {
						dc.add(Restrictions.like("person.name", name, MatchMode.ANYWHERE));
					}
				}
			}
			//查询学校
			School school = activityInfo.getSchool();
			if(null != school) {
				Organize organize = school.getOrganize();
				if(null != organize) {
					String name = organize.getName();
					if (StringUtils.isNotBlank(name)) {
						dc.add(Restrictions.like("organize.name", name, MatchMode.ANYWHERE));
					}
				}
			}
			//查询活动
			String rewardId = activityInfo.getRewardId();
			if(StringUtil.isNotBlank(rewardId)) {
				dc.add(Restrictions.eq("rewardId", rewardId));
			}
			Short isParticipate = activityInfo.getIsParticipate();
			if(null != isParticipate) {
				dc.add(Restrictions.eq("isParticipate", isParticipate));
			}
		}
		return dao.query(dc, start, pagesize, Order.asc("createTime"));
	}
	
	/**
	 * 统计活动参与取消人数
	 * @param rewardId
	 * @param isParticipate
	 * @return Long
	 */
	public Long count(String rewardId, Short isParticipate) {
	    DetachedCriteria dc = DetachedCriteria.forClass(PointActivityInfo.class);
	    dc.add(Restrictions.eq("rewardId", rewardId));
	    dc.add(Restrictions.eq("isParticipate", isParticipate));
	    return this.dao.getRowCount(dc);
	}
	
	/**
	 * @param activityInfo
	 * @return List<PointActivityInfo>
	 */
	public List<PointActivityInfo> find(PointActivityInfo activityInfo) {
		 DetachedCriteria dc = DetachedCriteria.forClass(PointActivityInfo.class);
		 if(null != activityInfo) {
			 String rewardId = activityInfo.getRewardId();
			 if(StringUtil.isNotBlank(rewardId)) {
				 dc.add(Restrictions.eq("rewardId", rewardId));
			 }
			 String studentId = activityInfo.getStudentId();
			 if(StringUtil.isNotBlank(studentId)) {
				 dc.add(Restrictions.eq("studentId", studentId));
			 }
		 }
	     return dao.findByCriteria(dc);
	}
	
}
