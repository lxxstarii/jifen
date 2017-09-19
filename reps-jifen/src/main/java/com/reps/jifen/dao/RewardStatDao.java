package com.reps.jifen.dao;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.reps.core.orm.IGenericDao;
import com.reps.core.orm.ListResult;
import com.reps.jifen.entity.RewardCategory;
import com.reps.jifen.entity.RewardStat;
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
public class RewardStatDao {
	
	@Autowired
	IGenericDao<RewardStat> dao;

	public ListResult<RewardStat> query(int start, int pagesize, RewardStat rewardStat) {
		DetachedCriteria dc = DetachedCriteria.forClass(RewardStat.class);
		dc.createAlias("student.person", "person");
		dc.createAlias("school.organize", "organize");
		if (null != rewardStat) {
			//查询学生
			Student student = rewardStat.getStudent();
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
			School school = rewardStat.getSchool();
			if(null != school) {
				Organize organize = school.getOrganize();
				if(null != organize) {
					String name = organize.getName();
					if (StringUtils.isNotBlank(name)) {
						dc.add(Restrictions.like("organize.name", name, MatchMode.ANYWHERE));
					}
				}
			}
		}
		return dao.query(dc, start, pagesize, Order.asc("name"));
	}
	
}
