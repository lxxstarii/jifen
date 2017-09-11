package com.reps.jifen.entity;

import java.io.Serializable;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 个人积分汇总
 * @author qianguobing
 * @date 2017年9月7日 上午9:01:49
 */
@Document(collection = "reps_jf_points_aggregate")
public class JfPointsAggregate implements Serializable{
	
	private static final long serialVersionUID = 879865376296672774L;

	@Id
	private String id;
	
	/** 人员ID */
	private String personId;
	
	/** 总积分 */
	private Integer totalPoints;
	
	/** 可用积分 */
	private Integer totalPointsUsable;
	
	/** 积分累计级别 */
	private Short level;

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public Integer getTotalPoints() {
		return totalPoints;
	}

	public void setTotalPoints(Integer totalPoints) {
		this.totalPoints = totalPoints;
	}

	public Integer getTotalPointsUsable() {
		return totalPointsUsable;
	}

	public void setTotalPointsUsable(Integer totalPointsUsable) {
		this.totalPointsUsable = totalPointsUsable;
	}

	public Short getLevel() {
		return level;
	}

	public void setLevel(Short level) {
		this.level = level;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "JfPointsAggregate [personId=" + personId + ", totalPoints=" + totalPoints + ", totalPointsUsable=" + totalPointsUsable + ", level=" + level + "]";
	} 
	
}
